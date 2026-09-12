package cafe.project.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repository.entity.BranchStatus;
import cafe.project.repository.mapper.BranchStatusMapper;

@Repository
public class BranchStatusRepository {

	private final JdbcTemplate jdbcTemplate;

	public BranchStatusRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<BranchStatus> findAll() {
		String sql = "SELECT * FROM branch_statuses";
		return this.jdbcTemplate.query(sql, new BranchStatusMapper());
	}
}