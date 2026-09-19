package cafe.project.NayZarLinn.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.NayZarLinn.models.BatchesAndExpiryDto;
import cafe.project.NayZarLinn.repositories.entities.IngredientBatch;
import cafe.project.NayZarLinn.repositories.mappers.IngredientBatchMapper;
import cafe.project.NayZarLinn.repositories.mappers.BatchesAndExpiryMapper;

@Repository
public class IngredientBatchRepository {

	private final JdbcTemplate jdbcTemplate;

	public IngredientBatchRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<IngredientBatch> findAll() {
		String sql = "SELECT ib.*,\r\n" + "b.name AS branch_name, \r\n" + "it.name AS ingredient_type_name \r\n"
				+ "FROM ingredient_batches ib \r\n" + "LEFT JOIN branches b \r\n" + "ON ib.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN ingredient_types it \r\n" + "ON ib.ingredient_type_id = it.ingredient_type_id\r\n"
				+ " WHERE ib.isdeleted = 0";
		List<IngredientBatch> entities = this.jdbcTemplate.query(sql, new IngredientBatchMapper());
		return entities;
	}

	public List<BatchesAndExpiryDto> batchesAndExpiry() {
		String sql = "SELECT ib.batch_id,\r\n" + "    it.name AS ingredient_type_name,\r\n"
				+ "    ib.manufactured_date,\r\n" + "    ib.expire_date,\r\n" + "    ib.remaining_quantity,\r\n"
				+ "    ib.unit_cost,\r\n" + "    sid.import_detail_id,\r\n" + "    sid.quantity_ordered,\r\n"
				+ "    sid.line_total\r\n" + "FROM ingredient_batches ib\r\n" + "INNER JOIN ingredient_types it\r\n"
				+ "    ON ib.ingredient_type_id = it.ingredient_type_id\r\n" + "INNER JOIN stock_import_details sid\r\n"
				+ "    ON ib.import_detail_id = sid.import_detail_id\r\n" + "WHERE ib.isdeleted = 0\r\n"
				+ "ORDER BY ib.expire_date ASC";
		return jdbcTemplate.query(sql, new BatchesAndExpiryMapper());

	}

	public IngredientBatch findByBatchId(String batchId) {
		String sql = " SELECT ib.*, \r\n" + "b.name AS branch_name,\r\n" + "it.name AS ingredient_type_name\r\n"
				+ "FROM ingredient_batches ib \r\n" + "LEFT JOIN branches b \r\n" + "ON ib.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN ingredient_types it\r\n" + "ON ib.ingredient_type_id = it.ingredient_type_id \r\n"
				+ "WHERE ib.batch_id = ?\r\n" + "AND ib.isdeleted = 0";
		List<IngredientBatch> entities = this.jdbcTemplate.query(sql, new IngredientBatchMapper(), batchId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int save(IngredientBatch entity) {
		String sql = "INSERT INTO ingredient_batches\r\n"
				+ "(batch_id,remaining_quantity,manufactured_date,expire_date,branch_id,import_detail_id,\r\n"
				+ "isexpired, isdeleted,created_at,ingredient_type_id,unit_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return this.jdbcTemplate.update(sql, entity.getBatchId(), entity.getRemainingQuantity(),
				entity.getManufacturedDate(), entity.getExpireDate(), entity.getBranchId(), entity.getImportDetailId(),
				0, 0, entity.getCreatedAt(), entity.getIngredientTypeId(), entity.getUnitCost());
	}

	public int edit(String batchId, IngredientBatch entity) {
		String sql = "UPDATE ingredient_batches SET remaining_quantity = ?, manufactured_date = ?,expire_date = ?,\r\n"
				+ " branch_id = ?, import_detail_id = ?,  isexpired = ?,isdeleted=?, created_at=?,ingredient_type_id = ?, unit_cost = ? WHERE batch_id = ?";
		return this.jdbcTemplate.update(sql, entity.getRemainingQuantity(), entity.getManufacturedDate(),
				entity.getExpireDate(), entity.getBranchId(), entity.getImportDetailId(), 0, 0, entity.getCreatedAt(),
				entity.getIngredientTypeId(), entity.getUnitCost(), batchId);
	}

	public int softDelete(String batchId) {
		String sql = "UPDATE ingredient_batches SET isdeleted = 1 WHERE batch_id = ?";
		return this.jdbcTemplate.update(sql, batchId);
	}

	public List<IngredientBatch> findExpired() {
		String sql = "SELECT ib.*,\r\n" + "b.name AS branch_name,\r\n" + "it.name AS ingredient_type_name\r\n"
				+ "FROM ingredient_batches ib\r\n" + "LEFT JOIN branches b\r\n" + "ON ib.branch_id = b.branch_id\r\n"
				+ "LEFT JOIN ingredient_types it\r\n" + "ON ib.ingredient_type_id = it.ingredient_type_id\r\n"
				+ "WHERE ib.expire_date <= CURRENT_DATE\r\n" + "AND ib.isdeleted = 0";
		return this.jdbcTemplate.query(sql, new IngredientBatchMapper());
	}

	public int updateExpiredStatus() {

		String sql = "UPDATE ingredient_batches \r\n" + "SET isexpired = 1\r\n"
				+ "WHERE expire_date <= CURRENT_DATE \r\n" + "AND isdeleted = 0";

		return this.jdbcTemplate.update(sql);
	}

	public int softDeleteExpired(String batchId) {

		String sql = "UPDATE ingredient_batches " + "SET isexpired = 1, isdeleted = 1 " + "WHERE batch_id = ?";

		return this.jdbcTemplate.update(sql, batchId);
	}

	public List<IngredientBatch> DeletedList() {
		String sql = "SELECT ib.*,\r\n" + "b.name AS branch_name,\r\n" + "it.name AS ingredient_type_name\r\n"
				+ "FROM ingredient_batches ib\r\n" + "LEFT JOIN branches b\r\n" + "ON ib.branch_id = b.branch_id\r\n"
				+ "LEFT JOIN ingredient_types it\r\n" + "ON ib.ingredient_type_id = it.ingredient_type_id\r\n"
				+ "WHERE ib.isdeleted = 1\r\n" + "";
		return this.jdbcTemplate.query(sql, new IngredientBatchMapper());
	}

	public int restore(String batchId) {
		String sql = "UPDATE ingredient_batches \r\n" + "SET isdeleted = 0\r\n" + "WHERE batch_id = ? \r\n"
				+ "AND isexpired = 0";
		return jdbcTemplate.update(sql, batchId);
	}

	public int hardDelete(String batchId) {

		String sql1 = "DELETE FROM waste_logs WHERE batch_id = ?";
		this.jdbcTemplate.update(sql1, batchId);

		String sql2 = "DELETE FROM ingredient_batches WHERE batch_id = ?";
		return this.jdbcTemplate.update(sql2, batchId);

	}
}
