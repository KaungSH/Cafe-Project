package cafe.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.BranchesStatsDto;
import cafe.project.repositories.BranchesStatsRepository;
import cafe.project.repositories.entities.BranchesStats;

@Service
public class BranchesStatsService {

	private final BranchesStatsRepository bsrepo;

	public BranchesStatsService(BranchesStatsRepository bsrepo) {
		this.bsrepo = bsrepo;
	}

	public List<BranchesStatsDto> findAll(String branch_id) {
		List<BranchesStats> entities = this.bsrepo.findAll(branch_id);
		List<BranchesStatsDto> branchesStats = entities.stream().map(this::toDto).toList();
		return branchesStats;
	}

	public List<BranchesStatsDto> findAllAdmin() {
		List<BranchesStats> entities = this.bsrepo.findAllAdmin();
		List<BranchesStatsDto> branchesStats = entities.stream().map(this::toDto).toList();
		return branchesStats;
	}

	public BranchesStatsDto findById(String branch_stats_id, String branch_id) {
		BranchesStats entity = this.bsrepo.findById(branch_stats_id, branch_id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return toDto(entity);
	}

	public BranchesStatsDto findByIdAdmin(String branch_stats_id) {
		BranchesStats entity = this.bsrepo.findByIdAdmin(branch_stats_id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return toDto(entity);
	}

	public BranchesStatsDto findByMonth(String month) {
		BranchesStats entity = this.bsrepo.findByMonth(month);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return toDto(entity);
	}

	public int add(BranchesStatsDto dto) {
		BranchesStats entity = toEntity(dto);
		return this.bsrepo.add(entity);
	}

	public int edit(String branch_stats_id, BranchesStatsDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Branch Stats data is required");
		}
		BranchesStats entity = toEntity(dto);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return this.bsrepo.edit(branch_stats_id, entity);
	}

	public int delete(String branch_stats_id) {
		BranchesStats entity = this.bsrepo.findByIdAdmin(branch_stats_id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return this.bsrepo.delete(branch_stats_id);
	}

	public List<BranchesStatsDto> findDeleted() {
		return this.bsrepo.findDeleted().stream().map(this::toDto).toList();
	}

	public int restore(String branch_stats_id) {
		BranchesStats entity = this.bsrepo.findByIdAdmin(branch_stats_id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return this.bsrepo.restore(branch_stats_id);
	}

	public int hardDelete(String branch_stats_id) {
		BranchesStats entity = this.bsrepo.findByIdAdmin(branch_stats_id);
		if (entity == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch Stats");
		return this.bsrepo.hardDelte(branch_stats_id);
	}

	private BranchesStatsDto toDto(BranchesStats entity) {
		BranchesStatsDto dto = new BranchesStatsDto();
		dto.setBranch_stats_id(entity.getBranch_stats_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setSalecount(entity.getSalecount());
		dto.setSaleamount(entity.getSaleamount());
		dto.setMonth(entity.getMonth());
		dto.setIsedited(entity.isIsedited());
		dto.setIsdeleted(entity.isIsdeleted());
		dto.setCreated_at(entity.getCreated_at());
		dto.setEmployee_cost(entity.getEmployee_cost());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setBranch_name(entity.getBranch_name());
		dto.setEmployee_name(entity.getEmployee_name());
		return dto;
	}

	private BranchesStats toEntity(BranchesStatsDto dto) {
		BranchesStats entity = new BranchesStats();
		entity.setBranch_stats_id(dto.getBranch_stats_id());
		entity.setBranch_id(dto.getBranch_id());
		entity.setSalecount(dto.getSalecount());
		entity.setSaleamount(dto.getSaleamount());
		entity.setMonth(dto.getMonth());
		entity.setIsedited(dto.isIsedited());
		entity.setIsdeleted(dto.isIsdeleted());
		entity.setCreated_at(dto.getCreated_at());
		entity.setEmployee_cost(dto.getEmployee_cost());
		entity.setEmployee_id(dto.getEmployee_id());
		return entity;
	}

}
