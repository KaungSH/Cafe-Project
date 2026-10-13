package cafe.project.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.CategoryDto;
import cafe.project.repositories.CategoryRepository;
import cafe.project.repositories.entities.Category;

@Service
public class CategoryService {

	private final CategoryRepository repo;
	private final BranchService bservice;

	public CategoryService(CategoryRepository repo, BranchService bservice) {
		this.repo = repo;
		this.bservice = bservice;
	}

	public List<CategoryDto> findAllActive() {
		List<Category> entities = repo.findAllActive();
		return entities.stream().map(this::toDto).toList();
	}

	public List<Category> findAllByRelation(String branch_id) {
		return repo.findAllByRelation(branch_id);
	}

	public List<Category> findAllByRelationAdmin() {
		return repo.findAllByRelationAdmin();
	}

	public CategoryDto findById(String id) {
		Category entity = repo.findById(id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category");
		return toDto(entity);
	}

	public int add(CategoryDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category data is required");
		}
		Category entity = toEntity(dto);
		entity.setCategory_id(UUID.randomUUID().toString());
		return repo.save(entity);
	}

	public int edit(String id, CategoryDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category data is required");
		}
		Category existingEntity = repo.findById(id);
		if (existingEntity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category");

		Category entity = toEntity(dto);
		return repo.edit(id, entity);
	}

	public int delete(String id) {
		Category entity = repo.findById(id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category");
		return repo.delete(id);
	}

	public List<Category> deletedList(String branch_id) {
		return this.repo.findDeletedAll(branch_id);
	}

	public int restore(String id) {
		Category entity = repo.findById(id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category");
		return this.repo.restore(id);
	}

	public int hardDelete(String id) {
		Category entity = repo.findById(id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category");
		return this.repo.hardDelete(id);
	}

	private CategoryDto toDto(Category entity) {
		CategoryDto dto = new CategoryDto();
		dto.setCategory_id(entity.getCategory_id());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setIs_active(entity.getIs_active());
		dto.setIsedited(entity.getIsedited());
		dto.setIsdeleted(entity.getIsdeleted());
		dto.setCreated_at(entity.getCreated_at());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setBranch_name(bservice.findById(entity.getBranch_id()).getName());
		return dto;
	}

	private Category toEntity(CategoryDto dto) {
		Category entity = new Category();
		entity.setCategory_id(dto.getCategory_id());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setIs_active(dto.getIs_active());
		entity.setIsedited(dto.getIsedited());
		entity.setIsdeleted(dto.getIsdeleted());
		entity.setCreated_at(dto.getCreated_at());
		entity.setEmployee_id(dto.getEmployee_id());
		entity.setBranch_id(dto.getBranch_id());
		return entity;
	}
}