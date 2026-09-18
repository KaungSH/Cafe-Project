package cafe.project.YinminThiriSoe.repositories.mappers.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.YinminThiriSoe.models.PayMethodDto;

public class PayMethodResultSetExtractor implements ResultSetExtractor<List<PayMethodDto>> {

	@Override
	public List<PayMethodDto> extractData(ResultSet rs) throws SQLException, DataAccessException {

		Map<String, PayMethodDto> methodMap = new LinkedHashMap<>();

		while (rs.next()) {

			String methodId = rs.getString("method_id");

			PayMethodDto dto = methodMap.get(methodId);

			if (dto == null) {
				dto = new PayMethodDto();

				dto.setMethodId(methodId);
				dto.setName(rs.getString("method_name"));
				dto.setDescription(rs.getString("description"));
				dto.setLogoPath(rs.getString("logopath"));
				dto.setActive(rs.getBoolean("is_active"));
				dto.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

				String employeeName = rs.getString("employee_name");
				dto.setEmployeeName(employeeName);

				methodMap.put(methodId, dto);
			}
		}

		return new ArrayList<>(methodMap.values());
	}
}