package cafe.project.common.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.common.repositories.entities.DeleteRecord;

public class DeleteRecordResultSetExtractor implements ResultSetExtractor<List<DeleteRecord>>{

	@Override
	public List<DeleteRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, DeleteRecord> deleteRecordMap = new LinkedHashMap<>();
		
		while(rs.next()) {
			String parentId = rs.getString("parent_id");
			DeleteRecord dr = deleteRecordMap.get(parentId);
			dr.setChild_ids(new ArrayList<String>());
			
			String child_id = rs.getString("child_id");
			if (child_id  != null) {
				dr.getChild_ids().add(child_id);
			}
			
			deleteRecordMap.put(parentId, dr);
		}
		return new ArrayList<DeleteRecord>(deleteRecordMap.values());
	}

}
