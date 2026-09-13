package cafe.project.YatiWinLatt.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import cafe.project.YatiWinLatt.repositories.entities.Branch;

public class BranchMapper2 implements RowMapper<Branch> {

	@Override
	public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
		Branch branch = new BranchMapper().mapRow(rs, rowNum);
		branch.setStatus_name(rs.getString("status_name"));
		return branch;
	}

}
