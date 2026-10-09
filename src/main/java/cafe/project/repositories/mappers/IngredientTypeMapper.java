package cafe.project.repositories.mappers;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import cafe.project.models.IngredientTypeDto;
import cafe.project.repositories.entities.IngredientType;

@Component
public class IngredientTypeMapper implements RowMapper<IngredientType> {

    public IngredientType toEntity(IngredientTypeDto dto) {
        if (dto == null) return null;
        IngredientType entity = new IngredientType();
        entity.setIngredient_type_id(dto.getIngredient_type_id());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUnit_id(dto.getUnit_id());
        entity.setDeleted(dto.isIsdeleted());
        return entity;
    }

    @Override
    public IngredientType mapRow(ResultSet rs, int rowNum) throws SQLException {
        IngredientType entity = new IngredientType();
        entity.setIngredient_type_id(rs.getString("ingredient_type_id"));
        entity.setName(rs.getString("name"));
        entity.setDescription(rs.getString("description"));
        entity.setUnit_id(rs.getString("unit_id"));
        entity.setDeleted(rs.getBoolean("isdeleted"));
        return entity;
    }

    public IngredientTypeDto toDto(IngredientType entity) {
        if (entity == null) return null;
        IngredientTypeDto dto = new IngredientTypeDto();
        dto.setIngredient_type_id(entity.getIngredient_type_id());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUnit_id(entity.getUnit_id());
        dto.setIsdeleted(entity.isDeleted());
        return dto;
    }
}
