package cafe.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.models.IngredientTypeDto;
import cafe.project.repositories.IngredientTypeRepository;
import cafe.project.repositories.entities.IngredientType;

@Service
public class IngredientTypeService {
	private final IngredientTypeRepository repository;

	public IngredientTypeService(IngredientTypeRepository repository) {
		this.repository = repository;
	}

	public List<IngredientType> getAllActive() {
		return repository.findAll();
	}

	public List<IngredientType> getAllDeleted() {
		return repository.findAllDeleted();
	}

	public IngredientType getById(String id) {
		return repository.findById(id);
				
	}

	public void create(IngredientTypeDto form) {
		IngredientType item = new IngredientType();
		item.setIngredientTypeId(form.getIngredientTypeId());
		item.setName(form.getName());
		item.setDescription(form.getDescription());
		item.setUnitId(form.getUnitId());
		repository.save(item);
	}

	public void update(String id, IngredientTypeDto form) {
		IngredientType item = getById(id);
		item.setName(form.getName());
		item.setDescription(form.getDescription());
		item.setUnitId(form.getUnitId());
		repository.update(item);
	}

	public void softDelete(String id) {
		repository.softDelete(id);
	}

	public void restore(String id) {
		repository.restore(id);
	}

	public void hardDelete(String id) {
		repository.hardDelete(id);
	}
}