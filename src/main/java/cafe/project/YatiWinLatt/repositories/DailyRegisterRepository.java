package cafe.project.YatiWinLatt.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import cafe.project.YatiWinLatt.models.DailyRegisterListDto;
import cafe.project.YatiWinLatt.repositories.entities.DailyRegister;
import cafe.project.YatiWinLatt.repositories.mappers.DailyRegisterListDtoMapper;
import cafe.project.YatiWinLatt.repositories.mappers.DailyRegisterMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class DailyRegisterRepository {

	private final JdbcTemplate jdbcTemplate;

	public DailyRegisterRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<DailyRegisterListDto> findAllDto() {
		String sql = "SELECT * from daily_registers WHERE isdeleted = 0 order by date DESC, opened_at DESC";
		return jdbcTemplate.query(sql, new DailyRegisterListDtoMapper());
	}

	public Optional<DailyRegister> findById(String register_id) {
		String sql = "SELECT * from daily_registers where register_id = ? AND isdeleted = 0";
		List<DailyRegister> list = jdbcTemplate.query(sql, new DailyRegisterMapper(), register_id);
		return list.stream().findFirst();
	}

	public int save(DailyRegister register) {
		String sql = "INSERT into daily_registers (register_id, branch_id, employee_id, register_status_id, date, opened_at, closed_at, isedited, isdeleted)\r\n"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, register.getRegister_id(), register.getBranch_id(), register.getEmployee_id(),
				register.getRegister_status_id(), register.getDate(), register.getOpened_at(), register.getClosed_at(),
				register.isIsedited(), register.isIsdeleted());

	}

	public int edit(DailyRegister register) {
		String sql = "UPDATE daily_registers\r\n"
				+ " SET branch_id = ?, employee_id = ?, register_status_id = ?, date = ?, opened_at = ?, closed_at = ?, isedited = 1\r\n"
				+ "	WHERE register_id = ? AND isdeleted = 0";
				
				
		return jdbcTemplate.update(sql, register.getBranch_id(), register.getEmployee_id(),
				register.getRegister_status_id(), register.getDate(), register.getOpened_at(), register.getClosed_at(),
				register.getRegister_id());

	}

	public int delete(String register_id) {
		String sql = "UPDATE daily_registers set isdeleted = 1 where register_id = ?";
		return jdbcTemplate.update(sql, register_id);
	}
}