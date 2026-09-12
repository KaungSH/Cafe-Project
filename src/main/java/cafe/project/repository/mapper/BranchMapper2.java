package cafe.project.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import cafe.project.repository.entity.Branch;

public class BranchMapper2 implements RowMapper<Branch> {

	@Override
	public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
		Branch branch = new BranchMapper().mapRow(rs, rowNum);
		branch.setStatus_name(rs.getString("status_name"));
		return branch;
	}

}
