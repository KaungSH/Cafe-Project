package cafe.project.repositories.mappers;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.DailyRegisterListDto;

public class DailyRegisterListDtoMapper implements RowMapper<DailyRegisterListDto> {

  @Override
  public DailyRegisterListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    DailyRegisterListDto dto = new DailyRegisterListDto();

    dto.setRegister_id(rs.getString("register_id"));
    dto.setBranch_name(rs.getString("branch_name"));
    dto.setEmployee_name(rs.getString("employee_name"));
    dto.setStatus_name(rs.getString("status_name"));

    Date date = rs.getDate("date");
    if (date != null) {
      dto.setDate(date.toLocalDate());
    }

    Time openedAt = rs.getTime("opened_at");
    if (openedAt != null) {
      dto.setOpened_at(openedAt.toLocalTime());
    }

    Time closedAt = rs.getTime("closed_at");
    if (closedAt != null) {
      dto.setClosed_at(closedAt.toLocalTime());
    }

    return dto;
  }
}
