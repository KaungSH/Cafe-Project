package cafe.project.repository.mapper;

	import java.sql.ResultSet;
	import java.sql.SQLException;
	import org.springframework.jdbc.core.RowMapper;
	import cafe.project.repository.entity.BranchStatus;

	public class BranchStatusMapper implements RowMapper<BranchStatus> {
	    @Override
	    public BranchStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
	        BranchStatus status = new BranchStatus();
	        status.setBranch_status_id(rs.getString("branch_status_id"));
	        status.setName(rs.getString("name"));
	        return status;
	    }
	}

