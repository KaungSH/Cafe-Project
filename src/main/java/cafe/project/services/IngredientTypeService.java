package cafe.project.services;



import cafe.project.models.IngredientTypeDto;
import cafe.project.models.UnitDto;
import cafe.project.repositories.IngredientTypeRepository;
import cafe.project.repositories.entities.IngredientType;
import cafe.project.repositories.mappers.IngredientTypeMapper;
import org.springframework.stereotype.Service;

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
        IngredientType entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        return mapper.toDto(entity);
    }

    public void updateIngredientType(IngredientTypeDto dto) {
        IngredientType entity = mapper.toEntity(dto);
        repository.update(entity);
    }

    public void softDelete(String id) { repository.softDelete(id); }
    public void recover(String id) { repository.recover(id); }
    public void hardDelete(String id) { repository.hardDelete(id); }
}