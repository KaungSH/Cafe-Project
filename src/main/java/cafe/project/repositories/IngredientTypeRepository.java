package cafe.project.repositories;

import cafe.project.models.IngredientTypeDto;
import cafe.project.models.UnitDto;
import cafe.project.repositories.entities.IngredientType;
import cafe.project.repositories.mappers.IngredientTypeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientTypeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientTypeMapper mapper;

    public IngredientTypeRepository(JdbcTemplate jdbcTemplate, IngredientTypeMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public List<UnitDto> findAllUnits() {
        String sql = "SELECT unit_id, employee_id, name, abbreviation, is_active, isedited, isdeleted, created_at " +
                     "FROM units WHERE isdeleted = 0 ORDER BY name ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UnitDto u = new UnitDto();
            u.setUnit_id(rs.getString("unit_id"));
            u.setEmployee_id(rs.getString("employee_id"));
            u.setName(rs.getString("name"));
            u.setAbbreviation(rs.getString("abbreviation"));
            u.setIs_active(rs.getBoolean("is_active"));
            u.setIsedited(rs.getBoolean("isedited"));
            u.setIsdeleted(rs.getBoolean("isdeleted"));
            if (rs.getTimestamp("created_at") != null) {
                u.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            }
            return u;
        });
    }

    public void save(IngredientType entity) {
    	System.out.println("name in repo = " + entity.getName());
    	System.out.println("id in repo = " + entity.getIngredient_type_id());
    	System.out.println("description = " + entity.getDescription());
        String sql = "INSERT INTO ingredient_types (ingredient_type_id, name, description, unit_id, isdeleted, created_at) " +
                     "VALUES (?, ?, ?, ?, 0, NOW())";
        jdbcTemplate.update(sql, entity.getIngredient_type_id(), entity.getName(), entity.getDescription(), entity.getUnit_id());
    }

    public List<IngredientTypeDto> findAllActive() {
        String sql = "SELECT it.ingredient_type_id, it.name, it.description, it.unit_id, u.name AS unit_name, it.isdeleted " +
                     "FROM ingredient_types it " +
                     "JOIN units u ON it.unit_id = u.unit_id " +
                     "WHERE it.isdeleted = 0 ORDER BY it.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            IngredientTypeDto dto = new IngredientTypeDto();
            dto.setIngredient_type_id(rs.getString("ingredient_type_id"));
            dto.setName(rs.getString("name"));
            dto.setDescription(rs.getString("description"));
            dto.setUnit_id(rs.getString("unit_id"));
            dto.setUname(rs.getString("unit_name"));
            dto.setIsdeleted(rs.getBoolean("isdeleted"));
            return dto;
        });
    }

    public List<IngredientTypeDto> findAllDeleted() {
        String sql = "SELECT it.ingredient_type_id, it.name, it.description, it.unit_id, u.name AS unit_name, it.isdeleted " +
                     "FROM ingredient_types it " +
                     "JOIN units u ON it.unit_id = u.unit_id " +
                     "WHERE it.isdeleted = 1 ORDER BY it.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            IngredientTypeDto dto = new IngredientTypeDto();
            dto.setIngredient_type_id(rs.getString("ingredient_type_id"));
            dto.setName(rs.getString("name"));
            dto.setDescription(rs.getString("description"));
            dto.setUnit_id(rs.getString("unit_id"));
            dto.setUname(rs.getString("unit_name"));
            dto.setIsdeleted(rs.getBoolean("isdeleted"));
            return dto;
        });
    }

    public Optional<IngredientType> findById(String id) {
        String sql = "SELECT * FROM ingredient_types WHERE ingredient_type_id = ?";
        List<IngredientType> list = jdbcTemplate.query(sql, mapper, id);
        return list.stream().findFirst();
    }

    public void update(IngredientType entity) {
        String sql = "UPDATE ingredient_types SET name = ?, description = ?, unit_id = ? WHERE ingredient_type_id = ?";
        jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getUnit_id(), entity.getIngredient_type_id());
    }

    public void softDelete(String id) {
        jdbcTemplate.update("UPDATE ingredient_types SET isdeleted = 1 WHERE ingredient_type_id = ?", id);
    }

    public void recover(String id) {
        jdbcTemplate.update("UPDATE ingredient_types SET isdeleted = 0 WHERE ingredient_type_id = ?", id);
    }

    public void hardDelete(String id) {
        jdbcTemplate.update("DELETE FROM ingredient_types WHERE ingredient_type_id = ?", id);
    }
}