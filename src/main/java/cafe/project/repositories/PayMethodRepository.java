package cafe.project.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.models.PayMethodDto;
import cafe.project.repositories.entities.PayMethod;
import cafe.project.repositories.mappers.PayMethodMapper;
import cafe.project.repositories.mappers.resultsetextractors.PayMethodResultSetExtractor;

@Repository
public class PayMethodRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DeleteRecordRepository deleteRecordRepo;

	public PayMethodRepository(JdbcTemplate jdbcTemplate, DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	public List<PayMethod> findAll() {
		String sql = "SELECT * FROM pay_methods WHERE isdeleted = 0 AND method_id != 'deleted'";
		return jdbcTemplate.query(sql, new PayMethodMapper());
	}

	public List<PayMethod> findAllActive() {
		String sql = "SELECT * FROM pay_methods WHERE isdeleted = 0 AND is_active = 1 AND method_id != 'deleted'";
		return jdbcTemplate.query(sql, new PayMethodMapper());
	}

	public List<PayMethodDto> findAllWithRelations() {
		String sql = "SELECT " + "    pm.method_id   AS method_id, " + "    pm.name        AS method_name, "
				+ "    pm.description AS description, " + "    pm.logopath    AS logopath, "
				+ "    pm.is_active   AS is_active, " + "    pm.isedited    AS isedited, "
				+ "    pm.isdeleted   AS isdeleted, " + "    pm.created_at  AS created_at, "
				+ "    pm.employee_id AS employee_id, " + "    e.name         AS employee_name "
				+ "FROM pay_methods pm " + "LEFT JOIN employees e ON pm.employee_id = e.employee_id "
				+ "WHERE pm.isdeleted = 0 AND pm.method_id != 'deleted'" + "ORDER BY pm.created_at DESC";

		return jdbcTemplate.query(sql, new PayMethodResultSetExtractor());
	}

	public PayMethod findById(String methodId) {
		String sql = "SELECT * FROM pay_methods WHERE method_id = ? AND isdeleted = 0 AND method_id != 'deleted'";
		List<PayMethod> list = jdbcTemplate.query(sql, new PayMethodMapper(), methodId);
		return list.isEmpty() ? null : list.get(0);
	}

	public PayMethodDto findByIdWithRelations(String methodId) {
		String sql = """
				SELECT
				    pm.method_id       AS method_id,
				    pm.name            AS method_name,
				    pm.description     AS description,
				    pm.logopath        AS logopath,
				    pm.is_active       AS is_active,
				    pm.isedited        AS isedited,
				    pm.isdeleted       AS isdeleted,
				    pm.created_at      AS created_at,
				    pm.employee_id     AS employee_id,
				    e.name             AS employee_name
				FROM pay_methods pm
				LEFT JOIN employees e ON pm.employee_id = e.employee_id
				WHERE pm.method_id = ? AND pm.isdeleted = 0 AND pm.method_id != 'deleted'
				""";

		List<PayMethodDto> results = jdbcTemplate.query(sql, new PayMethodResultSetExtractor(), methodId);

		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}

		return null;
	}

	public int add(PayMethod pm) {
		String sql = "INSERT INTO pay_methods (method_id, name,description,logopath,is_active, isedited,isdeleted,created_at,employee_id) VALUES (?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, pm.getMethodId(), pm.getName(), pm.getDescription(), pm.getLogoPath(),
				pm.isActive() ? 1 : 0, pm.isEdited() ? 1 : 0, pm.isDeleted() ? 1 : 0, pm.getCreatedAt(),
				pm.getEmployeeId());
	}

	public int update(PayMethod pm) {
		String sql = "UPDATE pay_methods SET name = ?,description = ?,  logopath = ?,is_active = ?, employee_id =?,isedited= 1 WHERE method_id =?";
		return jdbcTemplate.update(sql, pm.getName(), pm.getDescription(), pm.getLogoPath(), pm.isActive(), pm.getEmployeeId(), pm.getMethodId());
	}
	
	public int changeIsActive(String methodId, boolean isActive) {
		return jdbcTemplate.update("UPDATE pay_methods SET is_active = ? WHERE method_id = ? ", isActive, methodId);
	}

	public int softDelete(String methodId) {
		if (!"deleted".equals(methodId)) {
			recordDelete(methodId);
			setPayments(methodId);
			String sql = "UPDATE pay_methods SET isdeleted = 1, isedited = 1 WHERE method_id = ?";
			return jdbcTemplate.update(sql, methodId);
		}
		return 0;
	}

	public List<PayMethodDto> deletedList() {
		String sql = "SELECT pm.method_id AS method_id, pm.name AS method_name, \r\n"
				+ "pm.description AS description, pm.logopath AS logopath, \r\n"
				+ "pm.is_active AS is_active,pm.isedited AS isedited, \r\n"
				+ "pm.isdeleted AS isdeleted,pm.created_at AS created_at, \r\n"
				+ "pm.employee_id AS employee_id, e.name AS employee_name \r\n"
				+ "FROM pay_methods pm LEFT JOIN employees e ON pm.employee_id = e.employee_id \r\n"
				+ "WHERE pm.isdeleted = 1 ORDER BY pm.created_at DESC";
		return jdbcTemplate.query(sql, new PayMethodResultSetExtractor());
	}

	public int restore(String methodId) {
		for(DeleteRecord dr : deleteRecordRepo.getByParentId(methodId)) {
			jdbcTemplate.update("UPDATE payments SET method_id = ? WHERE method_id = 'deleted' AND payment_id = ?", dr.getParent_id(), dr.getChild_id());
		}
		deleteRecordRepo.deleteByParentId(methodId);
		String sql = "UPDATE pay_methods SET isdeleted=0 WHERE method_id=?";
		return jdbcTemplate.update(sql, methodId);
	}
	
	private int setPayments(String methodId) {
		return jdbcTemplate.update("UPDATE payments SET method_id = 'deleted' WHERE method_id = ?", methodId);
	}
	
	private int recordDelete(String methodId) {
		int i = 0;
		for (String childId : getChildIds(methodId)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(methodId); dr.setParent_table_name("pay_methods"); dr.setChild_id(childId); dr.setChild_table_name("paymethods");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}
	
	private List<String> getChildIds(String methodId) {
		List<String> child_ids = new ArrayList<String>();
		for(DeleteRecord dr : deleteRecordRepo.getChildIds(methodId, "method_id", "payments", "payment_id")) {
			for(String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		
		return child_ids;
	}

}
