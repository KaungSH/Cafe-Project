package cafe.project.YatiWinLatt.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class StatusRepository {

    private final JdbcTemplate jdbcTemplate;

    public StatusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String[] getTableAndColumns(String type) {
        switch (type.toLowerCase().trim()) {
            case "branch": return new String[]{"branch_statuses", "branch_status_id", "name"};
            case "employee": return new String[]{"employee_statuses", "employee_status_id", "name"};
            case "customer": return new String[]{"customer_statuses", "customer_status_id", "name"};
            case "register": return new String[]{"register_statuses", "register_status_id", "name"};
            case "gender":return new String[]{"gender_name","gender_id"};
            default: throw new IllegalArgumentException("Invalid status type: " + type);
        }
    }

    public List<Map<String, Object>> findAll(String type) {
        String[] config = getTableAndColumns(type);
        String sql = "SELECT " + config[1] + " AS id, " + config[2] + " AS name FROM " + config[0];
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> findById(String type, String id) {
        String[] config = getTableAndColumns(type);
        String sql = "SELECT " + config[1] + " AS id, " + config[2] + " AS name FROM " + config[0] + " WHERE " + config[1] + " = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public int save(String type, String id, String name) {
        String[] config = getTableAndColumns(type);
        String sql = "INSERT INTO " + config[0] + " (" + config[1] + ", " + config[2] + ") VALUES (?, ?)";
        return jdbcTemplate.update(sql, id, name);
    }

    public int update(String type, String id, String name) {
        String[] config = getTableAndColumns(type);
        String sql = "UPDATE " + config[0] + " SET " + config[2] + " = ? WHERE " + config[1] + " = ?";
        return jdbcTemplate.update(sql, name, id);
    }

    public int delete(String type, String id) {
        String[] config = getTableAndColumns(type);
        String sql = "DELETE FROM " + config[0] + " WHERE " + config[1] + " = ?";
        return jdbcTemplate.update(sql, id);
    }
}