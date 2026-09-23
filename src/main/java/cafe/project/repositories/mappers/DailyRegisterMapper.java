package cafe.project.repositories.mappers;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.springframework.jdbc.core.RowMapper;
import cafe.project.YatiWinLatt.repositories.entities.DailyRegister;

public class DailyRegisterMapper implements RowMapper<DailyRegister> {

  @Override
  public DailyRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
    DailyRegister entity = new DailyRegister();

    entity.setRegister_id(rs.getString("register_id"));
    entity.setBranch_id(rs.getString("branch_id"));
    entity.setEmployee_id(rs.getString("employee_id"));
    entity.setRegister_status_id(rs.getString("register_status_id"));

    Date date = rs.getDate("date");
    if (date != null) {
      entity.setDate(date.toLocalDate());
    }

    Time openedAt = rs.getTime("opened_at");
    if (openedAt != null) {
     entity.setOpened_at(openedAt);
    }

    Time closedAt = rs.getTime("closed_at");
    if (closedAt != null) {
   entity.setClosed_at(closedAt);
    }

    entity.setIsedited(rs.getBoolean("isedited"));
    entity.setIsdeleted(rs.getBoolean("isdeleted"));

    return entity;
  }
}