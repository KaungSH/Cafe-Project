package cafe.project.NayZarLinn.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.models.IngredientTypeDto;
import cafe.project.NayZarLinn.repositories.IngredientTypeRepository;
import cafe.project.NayZarLinn.repositories.entities.IngredientType;

@Service
public class IngredientTypeService {
	private final IngredientTypeRepository repo;
	
	public IngredientTypeService(IngredientTypeRepository repo) {
		this.repo=repo;
	}
	
	public List<IngredientTypeDto> findAll() {
		List<IngredientType> entities = this.repo.findAll();
		List<IngredientTypeDto> ingredientType = entities.stream().map(this::toDto).toList();
		return ingredientType;
	}

	public IngredientTypeDto findById(String ingredient_type_id) {
		IngredientType entity = this.repo.findById(ingredient_type_id);
		if (entity == null)
			return null;
		return toDto(entity);
	}

	public int add(IngredientTypeDto dto) {
		IngredientType entity = toEntity(dto);
		return this.repo.save(entity);
	}

	public int edit(String ingredient_type_id, IngredientTypeDto dto) {
		IngredientType entity = toEntity(dto);
		return this.repo.edit(ingredient_type_id, entity);
	}

	public int delete(String supplier_id) {
		return this.repo.delete(supplier_id);
	}

	private IngredientTypeDto toDto(IngredientType entity) {
		IngredientTypeDto dto = new IngredientTypeDto();
		dto.setIngredient_type_id(entity.getIngredient_type_id());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setUnit_id(entity.getUnit_id());
		dto.setIsdeleted(entity.isIsdeleted());
		dto.setCreated_at(entity.getCreated_at());
		return dto;
	}

	private IngredientType toEntity(IngredientTypeDto dto) {
		IngredientType entity = new IngredientType();
		entity.setIngredient_type_id(dto.getIngredient_type_id());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setUnit_id(dto.getUnit_id());
		entity.setIsdeleted(dto.isIsdeleted());
		entity.setCreated_at(dto.getCreated_at());
		return entity;

	}
}
