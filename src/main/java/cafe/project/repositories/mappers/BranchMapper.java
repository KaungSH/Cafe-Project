package cafe.project.YatiWinLatt.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import cafe.project.YatiWinLatt.repositories.entities.Branch;

public class BranchMapper implements RowMapper<Branch> {
	
	@Override
	public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Branch branch = new Branch();
	    branch.setBranch_id(rs.getString("branch_id"));
	    branch.setName(rs.getString("name"));
	    branch.setLocation(rs.getString("location"));
	    branch.setDescription(rs.getString("description"));
	    branch.setBranch_status_id(rs.getString("branch_status_id"));
	    branch.setOpening_time(rs.getTime("opening_time"));
	    branch.setClosing_time(rs.getTime("closing_time"));
	    branch.setIsdeleted(rs.getBoolean("isdeleted"));
	    branch.setCreated_at(rs.getTimestamp("created_at")!= null ? rs.getTimestamp("created_at").toLocalDateTime():null);
	    return branch;
	
	    
}
}