package cafe.project.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.DailyRegister;
import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.models.DailyRegisterListDto;
import cafe.project.repositories.mappers.DailyRegisterListDtoMapper;
import cafe.project.repositories.mappers.DailyRegisterMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DailyRegisterRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DeleteRecordRepository deleteRecordRepo;

	public DailyRegisterRepository(JdbcTemplate jdbcTemplate, DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	public List<DailyRegisterListDto> findAllDto() {
        String sql = "SELECT dr.register_id,\r\n"
        		+ "       dr.date,\r\n"
        		+ "       dr.opened_at,\r\n"
        		+ "       dr.closed_at,\r\n"
        		+ "       b.name AS branch_name,\r\n"
        		+ "       e.name AS employee_name,\r\n"
        		+ "       s.name AS status_name\r\n"
        		+ "FROM daily_registers dr\r\n"
        		+ "JOIN branches b ON dr.branch_id = b.branch_id\r\n"
        		+ "JOIN employees e ON dr.employee_id = e.employee_id\r\n"
        		+ "JOIN register_statuses s ON dr.register_status_id = s.register_status_id\r\n"
        		+ "WHERE dr.isdeleted = 0\r\n"
        		+ "ORDER BY dr.date DESC, dr.opened_at DESC;\r\n"
        		+ "";
        return jdbcTemplate.query(sql, new DailyRegisterListDtoMapper());
    }
	
	public List<DailyRegisterListDto> findAllDeleted() {
        String sql = "SELECT dr.register_id,\r\n"
        		+ "       dr.date,\r\n"
        		+ "       dr.opened_at,\r\n"
        		+ "       dr.closed_at,\r\n"
        		+ "       b.name AS branch_name,\r\n"
        		+ "       e.name AS employee_name,\r\n"
        		+ "       s.name AS status_name\r\n"
        		+ "FROM daily_registers dr\r\n"
        		+ "JOIN branches b ON dr.branch_id = b.branch_id\r\n"
        		+ "JOIN employees e ON dr.employee_id = e.employee_id\r\n"
        		+ "JOIN register_statuses s ON dr.register_status_id = s.register_status_id\r\n"
        		+ "WHERE dr.isdeleted = 1\r\n"
        		+ "ORDER BY dr.date DESC, dr.opened_at DESC;\r\n"
        		+ "";
        return jdbcTemplate.query(sql, new DailyRegisterListDtoMapper());
    }
	
	public List<DailyRegisterListDto> findAllOpened() {
        String sql = "SELECT dr.register_id, dr.date, dr.opened_at, dr.closed_at, b.name AS branch_name, e.name AS employee_name, s.name AS status_name FROM daily_registers dr JOIN branches b ON dr.branch_id = b.branch_id JOIN employees e ON dr.employee_id = e.employee_id JOIN register_statuses s ON dr.register_status_id = s.register_status_id WHERE dr.isdeleted = 0 AND s.name = 'OPENED' ORDER BY dr.date DESC, dr.opened_at DESC;";
        return jdbcTemplate.query(sql, new DailyRegisterListDtoMapper());
    }
	
	public List<DailyRegisterListDto> findAllClosed() {
        String sql = "SELECT dr.register_id, dr.date, dr.opened_at, dr.closed_at, b.name AS branch_name, e.name AS employee_name, s.name AS status_name FROM daily_registers dr JOIN branches b ON dr.branch_id = b.branch_id JOIN employees e ON dr.employee_id = e.employee_id JOIN register_statuses s ON dr.register_status_id = s.register_status_id WHERE dr.isdeleted = 0 AND s.name = 'CLOSED' ORDER BY dr.date DESC, dr.opened_at DESC;";
        return jdbcTemplate.query(sql, new DailyRegisterListDtoMapper());
    }

	public Optional<DailyRegister> findById(String register_id) {
		String sql = "SELECT * from daily_registers where register_id = ? AND isdeleted = 0";
		List<DailyRegister> list = jdbcTemplate.query(sql, new DailyRegisterMapper(), register_id);
		return list.stream().findFirst();
	}
	
	public DailyRegister findByDate(LocalDate date, String employee_id) {
		String sql = "SELECT * from daily_registers where date = ? AND isdeleted = 0 AND employee_id = ?";
		List<DailyRegister> entities = jdbcTemplate.query(sql, new DailyRegisterMapper(), date, employee_id);
		return entities.isEmpty()?null:entities.get(0);
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
		setDailyReports(register_id);
		String sql = "UPDATE daily_registers set isdeleted = 1 where register_id = ?";
		return jdbcTemplate.update(sql, register_id);
	}
	
	public int recover(String register_id) {
		String sql = "UPDATE daily_registers set isdeleted = 0 where register_id = ?";
		return jdbcTemplate.update(sql, register_id);
	}
	
	public int hardDelete(String register_id) {
		String sql="DELETE FROM daily_registers WHERE register_id";
		return jdbcTemplate.update(sql, register_id);
	}
	
	private int setDailyReports(String register_id) {
		return jdbcTemplate.update("UPDATE daily_reports SET register_id = 'deleted' WHERE register_id = ?", register_id);
	}
	
	private int recordDelete() {
		return 0;
	}
	
	private List<String> getChildIds(String register_id) {
		List<String> child_ids = new ArrayList<String>();
		for(DeleteRecord dr : deleteRecordRepo.getChildIds(register_id, "register_id", "daily_reports", "report_id")) {
			for(String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		
		return child_ids;
	}
	
}