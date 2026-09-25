package cafe.project.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.models.WasteLogsListDto;
import cafe.project.repositories.entities.IngredientBatch;
import cafe.project.repositories.entities.WasteLogs;
import cafe.project.repositories.mappers.IngredientBatchMapper;
import cafe.project.repositories.mappers.WasteLogsListDtoMapper;
import cafe.project.repositories.mappers.WasteLogsMapper;

@Repository
public class WasteLogsRepository {

	private final JdbcTemplate jdbcTemplate;

	public WasteLogsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<WasteLogsListDto> findAll() {
		String sql = "SELECT w.*, r.reason_name, e.name " + "FROM waste_logs w "
				+ "LEFT JOIN waste_reasons r ON w.waste_reason_id = r.waste_reason_id "
				+ "LEFT JOIN employees e ON w.employee_id = e.employee_id "
				+ "WHERE w.isdeleted = 0 ORDER BY w.logged_at DESC";
		return jdbcTemplate.query(sql, new WasteLogsListDtoMapper());
	}

	public WasteLogs findById(String waste_id) {
		String sql = "SELECT * FROM waste_logs WHERE waste_id = ? AND isdeleted = 0";
		return jdbcTemplate.queryForObject(sql, new WasteLogsMapper(), waste_id);
	}

	public int save(WasteLogs entity) {
		String sql = "INSERT INTO waste_logs (waste_id, batch_id, waste_reason_id, quantity_lost, financial_loss, employee_id, notes) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getBatch_id(), entity.getWaste_reason_id(),
				entity.getQuantity_lost(), entity.getFinancial_loss(), entity.getEmployee_id(), entity.getNotes());

	}

	public int update(WasteLogs entity) {
		String sql = "UPDATE waste_logs SET batch_id = ?, waste_reason_id = ?, quantity_lost = ?, financial_loss = ?, employee_id = ?, notes = ? "
				+ "WHERE waste_id = ? AND isdeleted = 0";
		return jdbcTemplate.update(sql, entity.getBatch_id(), entity.getWaste_reason_id(), entity.getQuantity_lost(),
				entity.getFinancial_loss(), entity.getEmployee_id(), entity.getNotes(), entity.getWaste_id());

	}

	public int softDelete(String waste_id) {
		String sql = "UPDATE waste_logs SET isdeleted = 1 WHERE waste_id = ?";
		return jdbcTemplate.update(sql, waste_id);
	}
	
	public List<IngredientBatch> findExpiredBatchToday() {
		String sql = "SELECT ib.*, b.name AS branch_name, it.name AS ingredient_type_name FROM ingredient_batches ib LEFT JOIN branches b ON ib.branch_id = b.branch_id LEFT JOIN ingredient_types it ON ib.ingredient_type_id = it.ingredient_type_id WHERE ib.expire_date = CURRENT_DATE AND ib.isdeleted = 0";
		return jdbcTemplate.query(sql, new IngredientBatchMapper());
		
	}
}