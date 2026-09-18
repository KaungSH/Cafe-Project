package cafe.project.NayZarLinn.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.models.SupplierDto;
import cafe.project.NayZarLinn.repositories.SupplierRepository;
import cafe.project.NayZarLinn.repositories.entities.Supplier;

@Service
public class SupplierService {

	private final SupplierRepository repo;

	public SupplierService(SupplierRepository repo) {
		this.repo = repo;
	}

	public List<SupplierDto> findAll() {
		List<Supplier> entities = this.repo.findAll();
		List<SupplierDto> suppliers = entities.stream().map(this::toDto).toList();
		return suppliers;
	}

	public SupplierDto findById(String supplier_id) {
		Supplier entity = this.repo.findById(supplier_id);
		if (entity == null)
			return null;
		return toDto(entity);
	}

	public int add(SupplierDto dto) {
		Supplier entity = toEntity(dto);
		return this.repo.save(entity);
	}

	public int edit(String supplier_id, SupplierDto dto) {
		Supplier entity = toEntity(dto);
		return this.repo.edit(supplier_id, entity);
	}

	public int delete(String supplier_id) {
		return this.repo.delete(supplier_id);
	}

	public List<SupplierDto> findDeleted() {
		return this.repo.findDeleted().stream().map(this::toDto).toList();
	}

	public int restore(String supplier_id) {
		return this.repo.restore(supplier_id);
	}

	public int realDelete(String supplier_id) {
		return this.repo.realDelete(supplier_id);
	}

	private SupplierDto toDto(Supplier entity) {
		SupplierDto dto = new SupplierDto();
		dto.setSupplier_id(entity.getSupplier_id());
		dto.setName(entity.getName());
		dto.setContact_info(entity.getContact_info());
		dto.setIsdeleted(entity.isIsdeleted());
		dto.setCreated_at(entity.getCreated_at());
		return dto;
	}

	private Supplier toEntity(SupplierDto dto) {
		Supplier entity = new Supplier();
		entity.setSupplier_id(dto.getSupplier_id());
		entity.setName(dto.getName());
		entity.setContact_info(dto.getContact_info());
		entity.setIsdeleted(dto.isIsdeleted());
		entity.setCreated_at(dto.getCreated_at());
		return entity;

	}
}
