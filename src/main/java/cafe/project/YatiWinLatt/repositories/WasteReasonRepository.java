package cafe.project.YatiWinLatt.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YatiWinLatt.repositories.entities.WasteReason;
import cafe.project.YatiWinLatt.repositories.mappers.WasteReasonMapper;

@Repository
public class WasteReasonRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	WasteReasonRepository(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<WasteReason> findReasonsAll() {
			return jdbcTemplate.query("SELECT * FROM waste_reasons", new WasteReasonMapper());
		
	}
	
	public WasteReason findReasonById(String id) {
			return jdbcTemplate.queryForObject("SELECT * FROM waste_reasons WHERE waste_reason_id = ?", new WasteReasonMapper(), id);
	}
	
	public WasteReason findReasonByName(String name) {
		return jdbcTemplate.queryForObject("SELECT * FROM waste_reasons WHERE reason_name = ?", new WasteReasonMapper(), name);
}
	
	public int addReason(String reason_name) {
			return jdbcTemplate.update("INSERT INTO waste_reasons(waste_reason_id, reason_name) VALUES(?, ?)", UUID.randomUUID().toString(), reason_name);
	}
	
	public int editReason(String id, String reason_name) {
			return jdbcTemplate.update("UPDATE waste_reasons SET reason_name = ? WHERE waste_reason_id = ?", reason_name, id);
	}
	
	public int deleteReason(String id) {
		setWasteLogs(id);
			return jdbcTemplate.update("DELETE FROM waste_reasons WHERE waste_reason_id = ?", id);
	}
	
	private int setWasteLogs(String id) {
			return jdbcTemplate.update("UPDATE waste_logs SET waste_reason_id = 'deleted' WHERE waste_reason_id = ?", id);
	}

}
