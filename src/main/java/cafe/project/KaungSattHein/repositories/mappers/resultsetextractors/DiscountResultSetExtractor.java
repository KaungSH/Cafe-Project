package cafe.project.KaungSattHein.repositories.mappers.resultsetextractors;



import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.KaungSattHein.repositories.entities.Discount;
import cafe.project.KaungSattHein.repositories.mappers.DiscountMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountResultSetExtractor implements ResultSetExtractor<List<Discount>> {

    private final DiscountMapper rowMapper = new DiscountMapper();

    @Override
    public List<Discount> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Discount> list = new ArrayList<>();
        int rowNum = 0;
        while (rs.next()) {
            list.add(rowMapper.mapRow(rs, rowNum++));
        }
        return list;
    }
}