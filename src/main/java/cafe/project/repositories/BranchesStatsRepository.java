package cafe.project.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.BranchesStats;
import cafe.project.repositories.mappers.BranchesStatsMapper;

@Repository
public class BranchesStatsRepository {

	private final JdbcTemplate jdbcTemplate;

	public BranchesStatsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<BranchesStats> findAll() {
		String sql = "SELECT bs.*, b.name AS branch_name, e.name AS employee_name\r\n" + "FROM branches_stats bs\r\n"
				+ "LEFT JOIN branches b ON bs.branch_id= b.branch_id\r\n"
				+ "LEFT JOIN employees e ON bs.employee_id = e.employee_id\r\n" + "WHERE bs.isdeleted = 0";
		List<BranchesStats> entities = this.jdbcTemplate.query(sql, new BranchesStatsMapper());
		return entities;
	}

	public BranchesStats findById(String branch_stats_id) {
		String sql = "SELECT bs.*, \r\n" + "b.name AS branch_name, \r\n" + "e.name AS employee_name \r\n"
				+ "FROM branches_stats bs \r\n" + "LEFT JOIN branches b ON bs.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN employees e ON bs.employee_id = e.employee_id \r\n" + "WHERE bs.branch_stat_id = ? \r\n"
				+ "AND bs.isdeleted = 0";
		List<BranchesStats> entities = this.jdbcTemplate.query(sql, new BranchesStatsMapper(), branch_stats_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public BranchesStats findByMonth(String month) {
		String sql = "SELECT bs.*, \r\n" + "b.name AS branch_name, \r\n" + "e.name AS employee_name \r\n"
				+ "FROM branches_stats bs \r\n" + "LEFT JOIN branches b ON bs.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN employees e ON bs.employee_id = e.employee_id \r\n" + "WHERE bs.month = ? \r\n"
				+ "AND bs.isdeleted = 0";
		List<BranchesStats> entities = this.jdbcTemplate.query(sql, new BranchesStatsMapper(), month);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int add(BranchesStats entity) {
		String sql = "INSERT INTO branches_stats (branch_stat_id,branch_id,salecount, saleamount, month, isedited, isdeleted,created_at, employee_costs,employee_id)\r\n"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getBranch_id(), entity.getSalecount(),
				entity.getSaleamount(), entity.getMonth(), entity.isIsedited(), entity.isIsdeleted(),
				entity.getCreated_at(), entity.getEmployee_cost(), entity.getEmployee_id());
	}

	public int edit(String branch_stats_id, BranchesStats entity) {
		String sql = "UPDATE branches_stats SET branch_id=?,salecount=?, saleamount=?,month=? ,isedited=?, isdeleted=?, created_at=?, employee_costs=?, employee_id=? WHERE branch_stat_id=?;";
		return this.jdbcTemplate.update(sql, entity.getBranch_id(), entity.getSalecount(), entity.getSaleamount(),
				entity.getMonth(), entity.isIsedited(), entity.isIsdeleted(), entity.getCreated_at(),
				entity.getEmployee_cost(), entity.getEmployee_id(), branch_stats_id);
	}

	public int delete(String branch_stats_id) {
		String sql = "UPDATE branches_stats SET isdeleted=1 WHERE branch_stat_id=?";
		return this.jdbcTemplate.update(sql, branch_stats_id);
	}

	public List<BranchesStats> findDeleted() {
		String sql = "SELECT bs.*,\r\n" + "b.name AS branch_name, \r\n" + "e.name AS employee_name\r\n"
				+ "FROM branches_stats bs \r\n" + "LEFT JOIN branches b ON bs.branch_id = b.branch_id\r\n"
				+ "LEFT JOIN employees e ON bs.employee_id = e.employee_id \r\n" + "WHERE bs.isdeleted = 1";
		return jdbcTemplate.query(sql, new BranchesStatsMapper());
	}

	public int restore(String branch_stats_id) {
		String sql = "UPDATE branches_stats SET isdeleted=0 WHERE branch_stat_id=?";
		return jdbcTemplate.update(sql, branch_stats_id);
	}

	public int hardDelte(String branch_stats_id) {
		String sql = "DELETE FROM branches_stats WHERE branch_stat_id=?";
		return this.jdbcTemplate.update(sql, branch_stats_id);
	}
}
