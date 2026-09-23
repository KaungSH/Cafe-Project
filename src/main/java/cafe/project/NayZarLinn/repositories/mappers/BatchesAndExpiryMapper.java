package cafe.project.NayZarLinn.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.NayZarLinn.models.BatchesAndExpiryDto;

public class BatchesAndExpiryMapper implements ResultSetExtractor<List<BatchesAndExpiryDto>> {

	@Override
	public List<BatchesAndExpiryDto> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<BatchesAndExpiryDto> list = new ArrayList<>();

		while (rs.next()) {

			BatchesAndExpiryDto dto = new BatchesAndExpiryDto();

			dto.setBatchId(rs.getString("batch_id"));

			dto.setIngredientTypeName(rs.getString("ingredient_type_name"));

			dto.setManufacturedDate(rs.getObject("manufactured_date", LocalDate.class));

			dto.setExpireDate(rs.getObject("expire_date", LocalDate.class));

			dto.setRemainingQty(rs.getBigDecimal("remaining_quantity"));

			dto.setUnitCost(rs.getBigDecimal("unit_cost"));

			dto.setImportDetailId(rs.getString("import_detail_id"));

			dto.setQuantityOrdered(rs.getBigDecimal("quantity_ordered"));

			dto.setLineTotal(rs.getBigDecimal("line_total"));
			
			dto.setUnit_abbreviation(rs.getString("unit_abbreviation"));

			long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), dto.getExpireDate());

			dto.setDaysLeft(daysLeft);

			if (daysLeft < 0) {
				dto.setStatus("Expired");
			} else if (daysLeft <= 2) {
				dto.setStatus("NearExpiry");
			} else {
				dto.setStatus("Fresh");
			}

			list.add(dto);
		}

		return list;
	}
}