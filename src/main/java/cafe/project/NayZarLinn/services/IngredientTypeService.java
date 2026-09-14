package cafe.project.NayZarLinn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.HeinMinHtet.repositories.UnitRepository;
import cafe.project.NayZarLinn.models.IngredientTypeEntryDto;
import cafe.project.NayZarLinn.models.IngredientTypeListDto;
import cafe.project.NayZarLinn.repositories.IngredientTypeRepository;
import cafe.project.NayZarLinn.repositories.entities.IngredientType;

@Service
public class IngredientTypeService {
	
	private final IngredientTypeRepository repo;
	private final UnitRepository ur;

	public IngredientTypeService(IngredientTypeRepository repo,UnitRepository ur) {
		this.repo = repo;
		this.ur=ur;
	}

	public List<IngredientTypeListDto> findAll() {
		List<IngredientTypeListDto> list = new ArrayList<>();
		
		for(IngredientType item : repo.findAll()){
			String abbreviation = ur.findById(item.getUnit_id()).getAbbreviation();
			
			list.add(toListModel(item,abbreviation));
		}
		return list;
	}
	
	public List<IngredientTypeListDto> findDeleted(){
		List<IngredientTypeListDto> list = new ArrayList<>();
	    
	    for (IngredientType item : repo.findAll()){
			String abbreviation = ur.findById(item.getUnit_id()).getAbbreviation();
	        
	        list.add(toListModel(item, abbreviation));
	    }
	    
	    return list;
	}

	public IngredientTypeEntryDto findById(String ingredient_type_id) {
		return toEntryDto(repo.findById(ingredient_type_id));
	}

	public int add(IngredientTypeEntryDto dto) {
		return this.repo.save(toEntity(dto));
	}

	public int edit(IngredientTypeEntryDto dto) {
		return this.repo.edit(toEntity(dto));
	}
	
	//soft delete
	public int delete(String ingredient_type_id) {
		return this.repo.delete(ingredient_type_id);
	}
	
	public int restore(String ingredient_type_id) {
		return repo.restore(ingredient_type_id);
	}
	//hard delete
	public int realDelete(String ingredient_type_id) {
		return this.repo.realDelete(ingredient_type_id);
	}
	
	public IngredientTypeListDto toListModel(IngredientType entity,String abbreviation) {
		return new IngredientTypeListDto(
				entity.getIngredient_type_id(),
				entity.getName(),
				entity.getDescription(),
				abbreviation,
				entity.isIsdeleted(),
				entity.getCreated_at()
				);
	}

	private IngredientTypeEntryDto toEntryDto(IngredientType entity) {
		return new IngredientTypeEntryDto(
				entity.getIngredient_type_id(),
				entity.getName(),
				entity.getDescription(),
				entity.getUnit_id(),
				entity.isIsdeleted(),
				entity.getCreated_at()
				);
	}

	private IngredientType toEntity(IngredientTypeEntryDto dto) {
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
