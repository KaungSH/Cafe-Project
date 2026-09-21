package cafe.project.KaungSattHein.repositories.mappers.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.KaungSattHein.models.DiscountListModel;
import cafe.project.KaungSattHein.repositories.mappers.DiscountListModelMapper;

public class DiscountListModelResultSetExctractor implements ResultSetExtractor<List<DiscountListModel>> {

	private final DiscountListModelMapper rowMapper = new DiscountListModelMapper();

	@Override
	public List<DiscountListModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<DiscountListModel> list = new ArrayList<>();
		int rowNum = 0;
		while (rs.next()) {
			list.add(rowMapper.mapRow(rs, rowNum++));
		}
		return list;
	}
}
