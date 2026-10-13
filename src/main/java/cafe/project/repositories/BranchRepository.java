package cafe.project.repositories;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.Branch;
import cafe.project.repositories.mappers.BranchMapper;
import cafe.project.repositories.mappers.BranchMapper2;

@Repository
public class BranchRepository {

	private final JdbcTemplate jdbcTemplate;

	public BranchRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Branch> findAll() {
		String sql = "SELECT * FROM branches WHERE branch_id != 'deleted'";
		return this.jdbcTemplate.query(sql, new BranchMapper());
	}

	public List<Branch> findAllWithRelation() {
		String sql = "SELECT b.*, s.name AS status_name FROM branches b "
				+ "INNER JOIN branch_statuses s ON b.branch_status_id = s.branch_status_id "
				+ "WHERE branch_id != 'deleted'";
		return this.jdbcTemplate.query(sql, new BranchMapper2());
	}

	public Branch findById(String branch_id) {
		String sql = "SELECT * FROM branches WHERE branch_id = ? AND branch_id != 'deleted'";
		List<Branch> list = this.jdbcTemplate.query(sql, new BranchMapper(), branch_id);
		return list.isEmpty() ? null : list.get(0);
	}

	public Branch findByIdWithRelation(String branch_id) {
		String sql = "SELECT b.*, s.name AS status_name FROM branches b "
				+ "INNER JOIN branch_statuses s ON b.branch_status_id = s.branch_status_id "
				+ "WHERE b.branch_id = ? AND branch_id != 'deleted'";
		List<Branch> list = this.jdbcTemplate.query(sql, new BranchMapper2(), branch_id);
		return list.isEmpty() ? null : list.get(0);
	}
	
	public int changeStatus(String branch_id, String status_id) {
		return jdbcTemplate.update("UPDATE branches SET branch_status_id = ? WHERE branch_id = ?", status_id, branch_id);
	}

	public int save(Branch entity) {
		String sql = "INSERT INTO branches (branch_id, name, location, description, branch_status_id, opening_time, closing_time, branch_finance, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return this.jdbcTemplate.update(sql, entity.getBranch_id(), entity.getName(), entity.getLocation(), entity.getDescription(), entity.getBranch_status_id(), entity.getOpening_time(), entity.getClosing_time(), entity.getBranch_finance(), entity.getCreated_at());
	}

	public int edit(String branch_id, Branch entity) {
		String sql = "UPDATE branches SET name = ?, location = ?, description = ?, branch_status_id = ?, opening_time = ?, closing_time = ?, branch_finance = ? WHERE branch_id = ?";
		return this.jdbcTemplate.update(sql, entity.getName(), entity.getLocation(), entity.getDescription(), entity.getBranch_status_id(), entity.getOpening_time(), entity.getClosing_time(), entity.getBranch_finance(), branch_id);
	}
	
	public int updateFinance(String branch_id, BigDecimal finance) {
		return this.jdbcTemplate.update("UPDATE branches SET branch_finance = ? WHERE branch_id = ?", finance, branch_id);
	}

}