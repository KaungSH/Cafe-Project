package cafe.project.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.models.SizeEntryDto;
import cafe.project.repositories.entities.Size;
import cafe.project.repositories.mappers.SizeMapper2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SizeRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DeleteRecordRepository deleteRecordRepo;

	SizeRepository(JdbcTemplate jdbcTemplate, DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	public List<Size> findAll() {
		String sql = "SELECT s.*, e.name AS employee_name \r\n" + "FROM sizes s \r\n"
				+ "LEFT JOIN employees e ON s.employee_id = e.employee_id \r\n" + "WHERE s.isdeleted = false AND s.size_id != 'deleted' \r\n"
				+ "ORDER BY s.created_at DESC";
		return jdbcTemplate.query(sql, new SizeMapper2());
	}

	public Size findById(String size_id) {
		String sql = "SELECT s.*, e.name AS employee_name \r\n" + "FROM sizes s \r\n"
				+ "LEFT JOIN employees e ON s.employee_id = e.employee_id \r\n"
				+ "WHERE s.size_id = ? AND s.size_id != 'deleted' AND s.isdeleted = false";

		return jdbcTemplate.queryForObject(sql, new SizeMapper2(), size_id);
	}

	public int save(SizeEntryDto dto) {
		String generatedId = UUID.randomUUID().toString();
		String sql = "INSERT INTO sizes (size_id, employee_id, name, size_code, is_active, isedited, isdeleted, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, generatedId, dto.getEmployee_id(), dto.getName(), dto.getSize_code(),
				dto.getIs_active() != null ? dto.getIs_active() : true, false, false, LocalDateTime.now());
	}

	public int update(SizeEntryDto dto) {
		String sql = "UPDATE sizes SET name = ?, size_code = ?, is_active = ?, employee_id = ?, isedited = ? WHERE size_id = ?";
		return jdbcTemplate.update(sql, dto.getName(), dto.getSize_code(), dto.getIs_active(), dto.getEmployee_id(), true, dto.getSize_id());
	}

	public int deleteById(String size_id) {// soft
		if (!"deleted".equals(size_id)) {
			recordDelete(size_id);
			setProducts(size_id);
			String sql = "UPDATE sizes SET isdeleted = true WHERE size_id = ?";
			return jdbcTemplate.update(sql, size_id);
		}
		return 0;
	}

	public List<Size> findDeletedAll() {
		String sql = "SELECT s.*, e.name AS employee_name " + "FROM sizes s "
				+ "LEFT JOIN employees e ON s.employee_id = e.employee_id " + "WHERE s.isdeleted = true "
				+ "ORDER BY s.created_at DESC";
		return jdbcTemplate.query(sql, new SizeMapper2());
	}

	public int restoreById(String size_id) {
		for(DeleteRecord dr : deleteRecordRepo.getByParentId(size_id)) {
			jdbcTemplate.update("UPDATE products SET size_id = ? WHERE size_id = 'deleted' AND product_id = ?", dr.getParent_id(), dr.getChild_id());
		}
		String sql = "UPDATE sizes SET isdeleted = false WHERE size_id = ?";
		return jdbcTemplate.update(sql, size_id);
	}

	public int hardDeleteById(String size_id) {
		if (!"deleted".equals(size_id)) {
			deleteRecordRepo.deleteByParentId(size_id);
			String sql = "DELETE FROM sizes WHERE size_id = ?";
			return jdbcTemplate.update(sql, size_id);
		}
		return 0;
	}
	
	
	
	private int setProducts(String size_id) {
		return jdbcTemplate.update("UPDATE products SET size_id = 'deleted' WHERE size_id = ?", size_id);
	}
	
	private int recordDelete(String size_id) {
		int i = 0;
		for (String childId : getChildIds(size_id)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(size_id); dr.setParent_table_name("sizes"); dr.setChild_id(childId); dr.setChild_table_name("products");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}
	
	private List<String> getChildIds(String size_id) {
		List<String> child_ids = new ArrayList<String>();
		for(DeleteRecord dr : deleteRecordRepo.getChildIds(size_id, "size_id", "products", "product_id")) {
			for(String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		
		return child_ids;
	}

}