package cafe.project.HeinMinHtet.services;



import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cafe.project.HeinMinHtet.models.UnitDto;
import cafe.project.HeinMinHtet.repositories.UnitRepository;
import cafe.project.HeinMinHtet.repositories.entities.Unit;



@Service
public class UnitService {

    private final UnitRepository repo;

    public UnitService(UnitRepository repo) {
        this.repo = repo;
    }

    public List<UnitDto> findAll() {

        List<Unit> entities = repo.findAll();

        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public UnitDto findById(String id) {

        Unit entity = repo.findById(id);
       
        return toDto(entity);
    }

    public int add(UnitDto dto) {

        Unit entity = toEntity(dto);
        entity.setUnit_id(UUID.randomUUID().toString());
        return repo.save(entity);
    }

    public int edit(String id, UnitDto dto) {

        Unit entity = toEntity(dto);

        return repo.edit(id, entity);
    }

    public int delete(String id) {

        return repo.delete(id);
    }

    private UnitDto toDto(Unit entity) {

        UnitDto dto = new UnitDto();

        dto.setUnit_id(entity.getUnit_id());
        dto.setEmployee_id(entity.getEmployee_id());
        dto.setName(entity.getName());
        dto.setAbbreviation(entity.getAbbreviation());
        dto.setIs_active(entity.getIs_active());
        dto.setIsdeleted(entity.getIsedited());
        dto.setIsdeleted(entity.getIsdeleted());
        dto.setCreated_at(entity.getCreated_at());

        return dto;
    }

    private Unit toEntity(UnitDto dto) {

        Unit entity = new Unit();

        entity.setUnit_id(dto.getUnit_id());
        entity.setEmployee_id(dto.getEmployee_id());
        entity.setName(dto.getName());
        entity.setAbbreviation(dto.getAbbreviation());
        entity.setIs_active(dto.getIs_active());
        entity.setIsdeleted(dto.getIsedited());
        entity.setIsdeleted(dto.getIsdeleted());
        entity.setCreated_at(dto.getCreated_at());

        return entity;
    }
}
