package cafe.project.repositories;

import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.models.IngredientTypeDto;
import cafe.project.models.UnitDto;
import cafe.project.repositories.entities.IngredientType;
import cafe.project.repositories.mappers.IngredientTypeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientTypeRepository {

	private final JdbcTemplate jdbcTemplate;
	private final IngredientTypeMapper mapper;
	private final DeleteRecordRepository deleteRecordRepo;

	public IngredientTypeRepository(JdbcTemplate jdbcTemplate, IngredientTypeMapper mapper,
			DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.mapper = mapper;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	public List<UnitDto> findAllUnits() {
		String sql = "SELECT unit_id, employee_id, name, abbreviation, is_active, isedited, isdeleted, created_at "
				+ "FROM units WHERE isdeleted = 0 AND is_active = 1 ORDER BY name ASC";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			UnitDto u = new UnitDto();
			u.setUnit_id(rs.getString("unit_id"));
			u.setEmployee_id(rs.getString("employee_id"));
			u.setName(rs.getString("name"));
			u.setAbbreviation(rs.getString("abbreviation"));
			u.setIs_active(rs.getBoolean("is_active"));
			u.setIsedited(rs.getBoolean("isedited"));
			u.setIsdeleted(rs.getBoolean("isdeleted"));
			if (rs.getTimestamp("created_at") != null) {
				u.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
			}
			return u;
		});
	}

	public void save(IngredientType entity) {
		System.out.println("name in repo = " + entity.getName());
		System.out.println("id in repo = " + entity.getIngredient_type_id());
		System.out.println("description = " + entity.getDescription());

		Boolean isActive = jdbcTemplate.queryForObject("SELECT is_active FROM units WHERE unit_id = ?", Boolean.class,
				entity.getUnit_id());
		if (isActive == null || !isActive) {
			throw new IllegalArgumentException(
					"Operation denied: Cannot add ingredient type because the selected unit is inactive.");
		}

		String sql = "INSERT INTO ingredient_types (ingredient_type_id, name, description, unit_id, isdeleted, created_at) "
				+ "VALUES (?, ?, ?, ?, 0, NOW())";
		jdbcTemplate.update(sql, entity.getIngredient_type_id(), entity.getName(), entity.getDescription(),
				entity.getUnit_id());
	}

	public List<IngredientTypeDto> findAllActive() {
		String sql = "SELECT it.ingredient_type_id, it.name, it.description, it.unit_id, u.name AS unit_name, it.isdeleted "
				+ "FROM ingredient_types it " + "JOIN units u ON it.unit_id = u.unit_id "
				+ "WHERE it.isdeleted = 0 AND it.ingredient_type_id != 'deleted' ORDER BY it.created_at DESC";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			IngredientTypeDto dto = new IngredientTypeDto();
			dto.setIngredient_type_id(rs.getString("ingredient_type_id"));
			dto.setName(rs.getString("name"));
			dto.setDescription(rs.getString("description"));
			dto.setUnit_id(rs.getString("unit_id"));
			dto.setUname(rs.getString("unit_name"));
			dto.setIsdeleted(rs.getBoolean("isdeleted"));
			return dto;
		});
	}

	public List<IngredientTypeDto> findAllDeleted() {
		String sql = "SELECT it.ingredient_type_id, it.name, it.description, it.unit_id, u.name AS unit_name, it.isdeleted "
				+ "FROM ingredient_types it " + "JOIN units u ON it.unit_id = u.unit_id "
				+ "WHERE it.isdeleted = 1 ORDER BY it.created_at DESC";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			IngredientTypeDto dto = new IngredientTypeDto();
			dto.setIngredient_type_id(rs.getString("ingredient_type_id"));
			dto.setName(rs.getString("name"));
			dto.setDescription(rs.getString("description"));
			dto.setUnit_id(rs.getString("unit_id"));
			dto.setUname(rs.getString("unit_name"));
			dto.setIsdeleted(rs.getBoolean("isdeleted"));
			return dto;
		});
	}

	public Optional<IngredientType> findById(String id) {
		String sql = "SELECT * FROM ingredient_types WHERE ingredient_type_id = ?";
		List<IngredientType> list = jdbcTemplate.query(sql, mapper, id);
		return list.stream().findFirst();
	}

	public void update(IngredientType entity) {
		Boolean isActive = jdbcTemplate.queryForObject("SELECT is_active FROM units WHERE unit_id = ?", Boolean.class,
				entity.getUnit_id());
		if (isActive == null || !isActive) {
			throw new IllegalArgumentException(
					"Operation denied: Cannot update ingredient type because the selected unit is inactive.");
		}
		
		String sql = "UPDATE ingredient_types SET name = ?, description = ?, unit_id = ? WHERE ingredient_type_id = ?";
		jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getUnit_id(),
				entity.getIngredient_type_id());
	}

	public void softDelete(String id) {
		if (!"deleted".equals(id)) {
			recordDelete(id);
			recordDelete2(id);
			setProducts_ingredients(id);
			setIngredient_batches(id);
			jdbcTemplate.update("UPDATE ingredient_types SET isdeleted = 1 WHERE ingredient_type_id = ?", id);
		}
	}

	public void recover(String id) {
		for (DeleteRecord dr : deleteRecordRepo.getByParentId(id)) {
			jdbcTemplate.update(
					"UPDATE products_ingredients SET ingredient_type_id = ? WHERE ingredient_id = 'deleted' AND product_id = ?",
					dr.getParent_id(), dr.getChild_id());
			jdbcTemplate.update(
					"UPDATE ingredient_batches SET ingredient_type_id = ? WHERE ingredient_id = 'deleted' AND batch_id = ?",
					dr.getParent_id(), dr.getChild_id());
		}
		deleteRecordRepo.deleteByParentId(id);
		jdbcTemplate.update("UPDATE ingredient_types SET isdeleted = 0 WHERE ingredient_type_id = ?", id);
	}

	public void hardDelete(String id) {
		if (!"deleted".equals(id)) {
			deleteRecordRepo.deleteByParentId(id);
			jdbcTemplate.update("DELETE FROM ingredient_types WHERE ingredient_type_id = ?", id);
		}
	}

	private int setProducts_ingredients(String type_id) {
		return jdbcTemplate.update(
				"UPDATE products_ingredients SET ingredient_type_id = 'deleted' WHERE ingredient_type_id = ?", type_id);
	}

	private int setIngredient_batches(String type_id) {
		return jdbcTemplate.update(
				"UPDATE ingredient_batches SET ingredient_type_id = 'deleted' WHERE ingredient_type_id = ?", type_id);
	}

	private int recordDelete(String type_id) {
		int i = 0;
		for (String childId : getChildIds(type_id)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(type_id);
			dr.setParent_table_name("ingredient_types");
			dr.setChild_id(childId);
			dr.setChild_table_name("ingredient_batches");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}

	private int recordDelete2(String type_id) {
		int i = 0;
		for (String childId : getChildIds2(type_id)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(type_id);
			dr.setParent_table_name("ingredient_types");
			dr.setChild_id(childId);
			dr.setChild_table_name("products_ingredients");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}

	private List<String> getChildIds(String type_id) {
		List<String> child_ids = new ArrayList<String>();
		for (DeleteRecord dr : deleteRecordRepo.getChildIds(type_id, "ingredient_type_id", "ingredient_batches",
				"batch_id")) {
			for (String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		return child_ids;
	}

	private List<String> getChildIds2(String type_id) {
		List<String> child_ids = new ArrayList<String>();
		for (DeleteRecord dr : deleteRecordRepo.getChildIds(type_id, "ingredient_type_id", "products_ingredients",
				"product_id")) {
			for (String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		return child_ids;
	}

}