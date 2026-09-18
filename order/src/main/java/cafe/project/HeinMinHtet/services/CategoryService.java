package cafe.project.HeinMinHtet.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cafe.project.HeinMinHtet.models.CategoryDto;
import cafe.project.HeinMinHtet.repositories.CategoryRepository;
import cafe.project.HeinMinHtet.repositories.entities.Category;



@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<CategoryDto> findAll() {

        List<Category> entities = repo.findAll();

        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryDto findById(String id) {

        Category entity = repo.findById(id);
        
        return toDto(entity);
    }

    public int add(CategoryDto dto) {

        Category entity = toEntity(dto);
        entity.setCategory_id(UUID.randomUUID().toString());
        return repo.save(entity);
    }

    public int edit(String id, CategoryDto dto) {

        Category entity = toEntity(dto);

        return repo.edit(id, entity);
    }

    public int delete(String id) {

        return repo.delete(id);
    }

    private CategoryDto toDto(Category entity) {

        CategoryDto dto = new CategoryDto();

        dto.setCategory_id(entity.getCategory_id());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setIs_active(entity.getIs_active());
        dto.setIsdeleted(entity.getIsedited());
        dto.setIsdeleted(entity.getIsdeleted());
        dto.setCreated_at(entity.getCreated_at());
        dto.setEmployee_id(entity.getEmployee_id());

        return dto;
    }

    private Category toEntity(CategoryDto dto) {

        Category entity = new Category();

        entity.setCategory_id(dto.getCategory_id());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setIs_active(dto.getIs_active());
        entity.setIsdeleted(dto.getIsedited());
        entity.setIsdeleted(dto.getIsdeleted());
        entity.setCreated_at(dto.getCreated_at());
        entity.setEmployee_id(dto.getEmployee_id());

        return entity;
    }
}
