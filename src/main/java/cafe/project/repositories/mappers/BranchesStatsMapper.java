package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.BranchesStats;

public class BranchesStatsMapper implements RowMapper<BranchesStats> {

	@Override
	public BranchesStats mapRow(ResultSet rs, int rowNum) throws SQLException {
			
		LocalDateTime created_at = rs.getObject("created_at", LocalDateTime.class);
		
		return new BranchesStats(
					rs.getString("branch_stat_id"),
					rs.getString("branch_id"),
					rs.getInt("salecount"),
					rs.getDouble("saleamount"),
					rs.getString("month"),
					rs.getBoolean("isedited"),
					rs.getBoolean("isdeleted"),
					created_at,
					rs.getDouble("employee_costs"),
					rs.getString("employee_id"),
					rs.getString("branch_name"),
					rs.getString("employee_name")
					);
	}

}
