package cafe.project.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.repositories.entities.Unit;
import cafe.project.repositories.mappers.UnitMapper;
import cafe.project.repositories.mappers.UnitMapper2;
@Repository
public class UnitRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DeleteRecordRepository deleteRecordRepo;

	public UnitRepository(JdbcTemplate jdbcTemplate, DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	// GET ALL
	public List<Unit> findAll() {

		String sql = "SELECT * FROM units WHERE isdeleted = false AND unit_id != 'deleted'";

		return jdbcTemplate.query(sql, new UnitMapper());
	}
	public List<Unit> findAllByRelation() {

		String sql = "SELECT u.*,e.name employee_name FROM units u LEFT JOIN employees e ON u.employee_id=e.employee_id WHERE isdeleted = false AND unit_id != 'deleted'";

		return jdbcTemplate.query(sql, new UnitMapper2());
	}

	public List<Unit> findDeletedAll() {

		String sql = "SELECT * FROM units WHERE isdeleted = true AND unit_id != 'deleted'";

		return jdbcTemplate.query(sql, new UnitMapper());
	}

	// GET BY ID
	public Unit findById(String id) {

		String sql = "SELECT * FROM units WHERE unit_id = ? AND unit_id != 'deleted'";

		return jdbcTemplate.queryForObject(sql, new UnitMapper(), id);
	}

	// SAVE
	public int save(Unit entity) {

		String sql = "INSERT INTO units (unit_id, employee_id, name, abbreviation,is_active, isedited, isdeleted)VALUES (?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql, entity.getUnit_id(), entity.getEmployee_id(), entity.getName(),
				entity.getAbbreviation(), entity.getIs_active(), entity.getIsedited(), entity.getIsdeleted());
	}

	// UPDATE
	public int edit(String id, Unit entity) {

		String sql = "UPDATE units SET name = ?, abbreviation = ?, is_active = ?, isedited = 1 WHERE unit_id = ?";

		return jdbcTemplate.update(sql, entity.getName(), entity.getAbbreviation(), entity.getIs_active(), id);
	}

	// SOFT DELETE
	public int delete(String id) {
		if (!"deleted".equals(id)) {
			recordDelete(id);
			setIngredientTypes(id);
	
			String sql = "UPDATE units SET isdeleted = 1 WHERE unit_id = ?";
	
			return jdbcTemplate.update(sql, id);
		}
		
		return 0;
	}
	
	public int restore(String id) {
		for(DeleteRecord dr : deleteRecordRepo.getByParentId(id)) {
			jdbcTemplate.update("UPDATE ingredient_types SET unit_id = ? WHERE unit_id = 'deleted' AND ingredient_type_id = ?", dr.getParent_id(), dr.getChild_id());
		}
		deleteRecordRepo.deleteByParentId(id);
		
		String sql = "UPDATE units SET isdeleted = 0 WHERE unit_id = ?";
		
		return jdbcTemplate.update(sql,id);
	}
	
	public int hardDelete(String id) {
		if (!"deleted".equals(id)) {
			deleteRecordRepo.deleteByParentId(id);
			String sql = "DELETE FROM units WHERE unit_id = ?";
			return jdbcTemplate.update(sql,id);
		}
		return 0;
	}
	
	
	private int setIngredientTypes(String unit_id) {
		return jdbcTemplate.update("UPDATE ingredient_types SET unit_id = 'deleted' WHERE unit_id = ?", unit_id);
	}
	
	private int recordDelete(String unit_id) {
		int i = 0;
		for (String childId : getChildIds(unit_id)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(unit_id); dr.setParent_table_name("units"); 
			dr.setChild_id(childId); 
			dr.setChild_table_name("ingredient_types");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}
	
	private List<String> getChildIds(String unit_id) {
		List<String> child_ids = new ArrayList<String>();
		for(DeleteRecord dr : deleteRecordRepo.getChildIds(unit_id, "unit_id", "ingredient_types", "ingredient_type_id")) {
			for(String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		
		return child_ids;
	}
	
}
