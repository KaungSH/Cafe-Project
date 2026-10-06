package cafe.project.repositories;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import cafe.project.repositories.entities.IngredientBatch;
import cafe.project.repositories.mappers.IngredientBatchMapper;

@Repository
public class IngredientBatchRepository {
	private final JdbcTemplate jdbcTemplate;

	public IngredientBatchRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void add(IngredientBatch entity) {
		String sql = """
				    INSERT INTO ingredient_batches
				    (batch_id, remaining_quantity, manufactured_date, expire_date, branch_id,
				     isdeleted, created_at, ingredient_type_id, unit_cost, quantity_ordered, total_import_cost, import_id)
				    VALUES (?, ?, ?, ?, ?, 0, NOW(), ?, ?, ?, ?, ?)
				""";

		jdbcTemplate.update(sql, entity.getBatch_id(), entity.getRemaining_quantity(), entity.getManufactured_date(),
				entity.getExpire_date(), entity.getBranch_id(), entity.getIngredient_type_id(), entity.getUnit_cost(),
				entity.getQuantity_ordered(), entity.getTotal_import_cost(), entity.getImport_id());
	}

	public List<IngredientBatch> findAll() {
		String sql = """
				    SELECT ib.*, b.name AS branch_name, it.name AS ingredient_type_name
				    FROM ingredient_batches ib
				    LEFT JOIN branches b ON ib.branch_id = b.branch_id
				    LEFT JOIN ingredient_types it ON ib.ingredient_type_id = it.ingredient_type_id
				    WHERE ib.isdeleted = 0
				    ORDER BY ib.created_at DESC
				""";
		return jdbcTemplate.query(sql, new IngredientBatchMapper());
	}

	public IngredientBatch findById(String batchId) {
		String sql = """
				    SELECT ib.*, b.name AS branch_name, it.name AS ingredient_type_name
				    FROM ingredient_batches ib
				    LEFT JOIN branches b ON ib.branch_id = b.branch_id
				    LEFT JOIN ingredient_types it ON ib.ingredient_type_id = it.ingredient_type_id
				    WHERE ib.batch_id = ?
				""";
		return jdbcTemplate.queryForObject(sql, new IngredientBatchMapper(), batchId);
	}

	public void edit(IngredientBatch entity) {
		String sql = """
				    UPDATE ingredient_batches
				    SET remaining_quantity = ?, manufactured_date = ?, expire_date = ?,
				        branch_id = ?, ingredient_type_id = ?, unit_cost = ?,
				        quantity_ordered = ?, total_import_cost = ?, import_id = ?
				    WHERE batch_id = ?
				""";

		jdbcTemplate.update(sql, entity.getRemaining_quantity(), entity.getManufactured_date(), entity.getExpire_date(),
				entity.getBranch_id(), entity.getIngredient_type_id(), entity.getUnit_cost(),
				entity.getQuantity_ordered(), entity.getTotal_import_cost(), entity.getImport_id(),
				entity.getBatch_id());
	}

	public void softDelete(String batchId) {
		String sql = "UPDATE ingredient_batches SET isdeleted = 1 WHERE batch_id = ?";
		jdbcTemplate.update(sql, batchId);
	}

	public void recover(String batchId) {
		String sql = "UPDATE ingredient_batches SET isdeleted = 0 WHERE batch_id = ?";
		jdbcTemplate.update(sql, batchId);
	}

	public List<IngredientBatch> findDeletedAll() {
		String sql = """
				    SELECT ib.*, b.name AS branch_name, it.name AS ingredient_type_name
				    FROM ingredient_batches ib
				    LEFT JOIN branches b ON ib.branch_id = b.branch_id
				    LEFT JOIN ingredient_types it ON ib.ingredient_type_id = it.ingredient_type_id
				    WHERE ib.isdeleted = 1
				    ORDER BY ib.created_at DESC
				""";
		return jdbcTemplate.query(sql, new IngredientBatchMapper());
	}

	public void hardDelete(String batchId) {
		String sql = "DELETE FROM ingredient_batches WHERE batch_id = ?";
		jdbcTemplate.update(sql, batchId);
	}
}