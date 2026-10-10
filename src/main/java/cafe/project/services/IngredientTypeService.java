package cafe.project.services;

import cafe.project.models.IngredientTypeDto;
import cafe.project.models.UnitDto;
import cafe.project.repositories.IngredientTypeRepository;
import cafe.project.repositories.entities.IngredientType;
import cafe.project.repositories.mappers.IngredientTypeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientTypeService {

	private final IngredientTypeRepository repository;
	private final IngredientTypeMapper mapper;

	public IngredientTypeService(IngredientTypeRepository repository, IngredientTypeMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<UnitDto> getAllUnits() {
		return repository.findAllUnits();
	}

	public void addIngredientType(IngredientTypeDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient Type data is required");
		}
		dto.setIngredient_type_id("ING-" + UUID.randomUUID().toString().substring(0, 8));
		IngredientType entity = mapper.toEntity(dto);
		repository.save(entity);
	}

	public List<IngredientTypeDto> getAllActiveIngredientTypes() {
		return repository.findAllActive();
	}

	public List<IngredientTypeDto> getAllDeletedIngredientTypes() {
		return repository.findAllDeleted();
	}

	public IngredientTypeDto getById(String id) {
		IngredientType entity = repository.findById(id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Type");
		}
		return mapper.toDto(entity);
	}

	public void updateIngredientType(IngredientTypeDto dto) {
		if (dto == null || dto.getIngredient_type_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient Type data is required");
		}
		IngredientType existingEntity = repository.findById(dto.getIngredient_type_id()).orElse(null);
		if (existingEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Type");
		}
		IngredientType entity = mapper.toEntity(dto);
		repository.update(entity);
	}

	public void softDelete(String id) {
		IngredientType entity = repository.findById(id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Type");
		}
		repository.softDelete(id);
	}

	public void recover(String id) {
		IngredientType entity = repository.findById(id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Type");
		}
		repository.recover(id);
	}

	public void hardDelete(String id) {
		IngredientType entity = repository.findById(id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Type");
		}
		repository.hardDelete(id);
	}
}