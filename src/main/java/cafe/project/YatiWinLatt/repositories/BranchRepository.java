package cafe.project.YatiWinLatt.repositories;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YatiWinLatt.repositories.entities.Branch;
import cafe.project.YatiWinLatt.repositories.mappers.BranchMapper;
import cafe.project.YatiWinLatt.repositories.mappers.BranchMapper2;

@Repository
public class BranchRepository {

	private final JdbcTemplate jdbcTemplate;

	public BranchRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Branch> findAll() {
		String sql = "SELECT * FROM branches WHERE isdeleted = false";
		return this.jdbcTemplate.query(sql, new BranchMapper());
	}

	public List<Branch> findAllWithRelation() {
		String sql = "SELECT b.*, s.name AS status_name FROM branches b "
				+ "INNER JOIN branch_statuses s ON b.branch_status_id = s.branch_status_id "
				+ "WHERE b.isdeleted = false";
		return this.jdbcTemplate.query(sql, new BranchMapper2());
	}

	public Branch findById(String branch_id) {
		String sql = "SELECT * FROM branches WHERE branch_id = ? AND isdeleted = false";
		List<Branch> list = this.jdbcTemplate.query(sql, new BranchMapper(), branch_id);
		return list.isEmpty() ? null : list.get(0);
	}

	public Branch findByIdWithRelation(String branch_id) {
		String sql = "SELECT b.*, s.name AS status_name FROM branches b "
				+ "INNER JOIN branch_statuses s ON b.branch_status_id = s.branch_status_id "
				+ "WHERE b.isdeleted = false AND b.branch_id = ?";
		List<Branch> list = this.jdbcTemplate.query(sql, new BranchMapper2(), branch_id);
		return list.isEmpty() ? null : list.get(0);
	}

	public int save(Branch entity) {
		String sql = "INSERT INTO branches (branch_id, name, location, description, branch_status_id,"
				+ " opening_time, closing_time, isdeleted, created_at) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return this.jdbcTemplate.update(sql, entity.getBranch_id(), entity.getName(), entity.getLocation(),
				entity.getDescription(), entity.getBranch_status_id(), entity.getOpening_time(),
				entity.getClosing_time(), entity.Isdeleted(), entity.getCreated_at());
	}

	public int edit(String branch_id, Branch entity) {
		String sql = "UPDATE branches SET name = ?, location = ?, description = ?, branch_status_id = ?, "
				+ "opening_time = ?, closing_time = ? " + "WHERE branch_id = ?";
		return this.jdbcTemplate.update(sql, entity.getName(), entity.getLocation(), entity.getDescription(),
				entity.getBranch_status_id(), entity.getOpening_time(), entity.getClosing_time(), branch_id);
	}

	public int delete(String branch_id) {
		String sql = "UPDATE branches SET isdeleted = true WHERE branch_id = ?";
		return this.jdbcTemplate.update(sql, branch_id);
	}
}