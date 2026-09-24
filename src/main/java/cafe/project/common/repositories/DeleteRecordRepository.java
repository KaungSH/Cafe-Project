package cafe.project.common.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.common.repositories.mappers.DeleteRecordMapper;
import cafe.project.common.repositories.mappers.DeleteRecordResultSetExtractor;

@Repository
public class DeleteRecordRepository {
	
	private final JdbcTemplate jdbcTemplate;

	public DeleteRecordRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<DeleteRecord> getByParentId(String parent_id) {
		return jdbcTemplate.query("SELECT * FROM delete_record WHERE parent_id = ?", new DeleteRecordMapper(), parent_id);
	}
	
	public List<DeleteRecord> getByChildId(String child_id) {
		return jdbcTemplate.query("SELECT * FROM delete_record WHERE child_id = ?", new DeleteRecordMapper(), child_id);
	}
	
	public int recordDelete(DeleteRecord dr) {
		return jdbcTemplate.update("INSERT INTO delete_record (parent_id, parent_table_name, child_id, child_table_name) VALUES(?, ?, ?, ?)", dr.getParent_id(), dr.getParent_table_name(), dr.getChild_id(), dr.getChild_table_name());
	}
	
	public int deleteByParentId(String parent_id) {
		return jdbcTemplate.update("DELETE FROM delete_record WHERE parent_id = ?", parent_id);
	}
	
	public int deleteByChildId(String child_id) {
		return jdbcTemplate.update("DELETE FROM delete_record WHERE child_id = ?", child_id);
	}
	
	public List<DeleteRecord> getChildIds(String parent_id, String parent_id_name, String child_table_name, String child_id_name) {
		return jdbcTemplate.query("SELECT ? parent_id, ? child_id FROM ? WHERE ? = ?", new DeleteRecordResultSetExtractor(), parent_id_name, child_id_name, child_table_name, parent_id_name, parent_id);
	}

}
