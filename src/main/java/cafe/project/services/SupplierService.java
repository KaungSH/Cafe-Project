package cafe.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.SupplierDto;
import cafe.project.repositories.SupplierRepository;
import cafe.project.repositories.entities.Supplier;

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
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier");
		}
		return toDto(entity);
	}

	public int add(SupplierDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Supplier data is required");
		}
		Supplier entity = toEntity(dto);
		return this.repo.save(entity);
	}

	public int edit(String supplier_id, SupplierDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Supplier data is required");
		}
		Supplier entity = this.repo.findById(supplier_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier");
		}
		Supplier updatedEntity = toEntity(dto);
		return this.repo.edit(supplier_id, updatedEntity);
	}

	public int delete(String supplier_id) {
		Supplier entity = this.repo.findById(supplier_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier");
		}
		return this.repo.delete(supplier_id);
	}

	public List<SupplierDto> findDeleted() {
		return this.repo.findDeleted().stream().map(this::toDto).toList();
	}

	public int restore(String supplier_id) {
		Supplier entity = this.repo.findById(supplier_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier");
		}
		return this.repo.restore(supplier_id);
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
