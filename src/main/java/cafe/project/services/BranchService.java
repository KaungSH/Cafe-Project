package cafe.project.services;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cafe.project.models.BranchEntryDto;
import cafe.project.models.BranchListDto;
import cafe.project.models.BranchStatusListDto;
import cafe.project.repositories.BranchRepository;
import cafe.project.repositories.BranchStatusRepository;
import cafe.project.repositories.entities.Branch;
import cafe.project.repositories.entities.BranchStatus;

@Service
public class BranchService {

	private final BranchRepository branchRepository;
	private final BranchStatusRepository branchStatusRepository;

	public BranchService(BranchRepository branchRepository, BranchStatusRepository branchStatusRepository) {
		this.branchRepository = branchRepository;
		this.branchStatusRepository = branchStatusRepository;
	}

	public List<BranchListDto> findAll() {
		List<Branch> entities = this.branchRepository.findAllWithRelation();
		return entities.stream().map(this::toListDto).toList();
	}

	public BranchEntryDto findById(String branch_id) {
		Branch entity = this.branchRepository.findByIdWithRelation(branch_id);
		if (entity == null) {
			return null;
		}
		return toEntryDto(entity);
	}

	public List<BranchStatusListDto> findAllStatuses() {
		List<BranchStatus> list = this.branchStatusRepository.findAll();
		return list.stream().map(s -> {
			BranchStatusListDto dto = new BranchStatusListDto();
			dto.setBranch_status_id(s.getBranch_status_id());
			dto.setName(s.getName());
			return dto;
		}).toList();
	}

	public boolean add(BranchEntryDto dto) {
		Branch entity = toEntity(dto);
		entity.setBranch_id(UUID.randomUUID().toString());
		entity.setCreated_at(LocalDateTime.now());
		entity.setIsdeleted(false);

		return this.branchRepository.save(entity) > 0;
	}

	public boolean edit(String branch_id, BranchEntryDto dto) {
		Branch existingBranch = this.branchRepository.findById(branch_id);
		if (existingBranch == null) {
			return false;
		}

		Branch entity = toEntity(dto);
		return this.branchRepository.edit(branch_id, entity) > 0;
	}

	public boolean delete(String branch_id) {
		Branch existingBranch = this.branchRepository.findById(branch_id);
		if (existingBranch == null) {
			return false;
		}
		return this.branchRepository.delete(branch_id) > 0;
	}

	private BranchListDto toListDto(Branch entity) {
		BranchListDto dto = new BranchListDto();
		dto.setBranch_id(entity.getBranch_id());
		dto.setName(entity.getName());
		dto.setLocation(entity.getLocation());
		dto.setDescription(entity.getDescription());
		dto.setOpening_time(entity.getOpening_time());
		dto.setClosing_time(entity.getClosing_time());
		dto.setIsdeleted(entity.Isdeleted());
		dto.setCreated_at(entity.getCreated_at());
		dto.setStatus_name(entity.getStatus_name());
		return dto;
	}

	private BranchEntryDto toEntryDto(Branch entity) {
		BranchEntryDto dto = new BranchEntryDto();
		dto.setBranch_id(entity.getBranch_id());
		dto.setName(entity.getName());
		dto.setLocation(entity.getLocation());
		dto.setDescription(entity.getDescription());
		dto.setBranch_status_id(entity.getBranch_status_id());

		if (entity.getOpening_time() != null) {
			dto.setOpening_time(entity.getOpening_time().toLocalTime());
		}
		if (entity.getClosing_time() != null) {
			dto.setClosing_time(entity.getClosing_time().toLocalTime());
		}

		dto.setIsdeleted(entity.Isdeleted());
		return dto;
	}

	private Branch toEntity(BranchEntryDto dto) {
		Branch entity = new Branch();
		entity.setName(dto.getName());
		entity.setLocation(dto.getLocation());
		entity.setDescription(dto.getDescription());
		entity.setBranch_status_id(dto.getBranch_status_id());

		if (dto.getOpening_time() != null) {
			entity.setOpening_time(Time.valueOf(dto.getOpening_time()));
		}
		if (dto.getClosing_time() != null) {
			entity.setClosing_time(Time.valueOf(dto.getClosing_time()));
		}

		return entity;
	}
}