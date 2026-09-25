package cafe.project.common.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.common.repositories.entities.DeleteRecord;

public class DeleteRecordMapper implements RowMapper<DeleteRecord>  {

	@Override
	public DeleteRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DeleteRecord(rs.getString("parent_id"), rs.getString("parent_table_name"), rs.getString("child_id"), rs.getString("child_table_name"));
	}

}
