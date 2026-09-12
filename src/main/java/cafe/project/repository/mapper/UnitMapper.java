package cafe.project.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repository.entities.Unit;



public class UnitMapper implements RowMapper<Unit> {

    @Override
    public Unit mapRow(ResultSet rs, int rowNum) throws SQLException {

        Unit unit = new Unit();

        unit.setUnit_id(rs.getString("unit_id"));
        unit.setEmployee_id(rs.getString("employee_id"));
        unit.setName(rs.getString("name"));
        unit.setAbbreviation(rs.getString("abbreviation"));
        unit.setIs_active(rs.getBoolean("is_active"));
        unit.setIsdeleted(rs.getBoolean("isedited"));
        unit.setIsdeleted(rs.getBoolean("isdeleted"));
        
        if (!rs.getString("employee_name").equals(null) || !rs.getString("employee_name").isEmpty()) {
            unit.setEmployee_name(rs.getString("employee_name"));
   		}

        unit.setCreated_at(
                rs.getTimestamp("created_at").toLocalDateTime()
        );

        return unit;
    }
}