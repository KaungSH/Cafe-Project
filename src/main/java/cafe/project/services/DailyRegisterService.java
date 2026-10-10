package cafe.project.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.repositories.entities.DailyRegister;
import cafe.project.models.DailyRegisterEntryDto;
import cafe.project.models.DailyRegisterListDto;
import cafe.project.repositories.DailyRegisterRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DailyRegisterService {

	private final DailyRegisterRepository repository;

	public DailyRegisterService(DailyRegisterRepository repository) {
		this.repository = repository;
	}

	public List<DailyRegisterListDto> getAllRegisters(String branch_id) {
		return repository.findAllDto(branch_id);
	}

	public List<DailyRegisterListDto> getAllRegistersAdmin() {
		return repository.findAllDtoAdmin();
	}

	public List<DailyRegisterListDto> getAllOpened(String branch_id) {
		return repository.findAllOpened(branch_id);
	}

	public List<DailyRegisterListDto> getAllClosed(String branch_id) {
		return repository.findAllClosed(branch_id);
	}

	public List<DailyRegisterListDto> getAllDeleted(String branch_id) {
		return repository.findAllDeleted(branch_id);
	}

	public DailyRegisterEntryDto getRegisterEntryDtoById(String register_id) {
		DailyRegister entity = repository.findById(register_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Daily Register"));

		DailyRegisterEntryDto dto = new DailyRegisterEntryDto();
		dto.setRegister_id(entity.getRegister_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setRegister_status_id(entity.getRegister_status_id());
		dto.setDate(entity.getDate());
		dto.setOpened_at(entity.getOpened_at());
		dto.setClosed_at(entity.getClosed_at());

		return dto;
	}

	public DailyRegisterEntryDto getRegisterEntryDtoByDate(LocalDate date, String employee_id) {
		DailyRegister entity = repository.findByDate(date, employee_id);

		if (entity == null) {
			return null;
		}

		DailyRegisterEntryDto dto = new DailyRegisterEntryDto();
		dto.setRegister_id(entity.getRegister_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setRegister_status_id(entity.getRegister_status_id());
		dto.setDate(entity.getDate());
		dto.setOpened_at(entity.getOpened_at());
		dto.setClosed_at(entity.getClosed_at());

		return dto;
	}

	public DailyRegisterEntryDto getRegisterEntryDtoByDateOpened(LocalDate date, String employee_id) {
		DailyRegister entity = repository.findByDateOpened(date, employee_id);

		if (entity == null) {
			return null;
		}

		DailyRegisterEntryDto dto = new DailyRegisterEntryDto();
		dto.setRegister_id(entity.getRegister_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setRegister_status_id(entity.getRegister_status_id());
		dto.setDate(entity.getDate());
		dto.setOpened_at(entity.getOpened_at());
		dto.setClosed_at(entity.getClosed_at());

		return dto;
	}

	public DailyRegisterEntryDto getRegisterEntryDtoByDateClosed(LocalDate date, String employee_id) {
		DailyRegister entity = repository.findByDateClosed(date, employee_id);

		if (entity == null) {
			return null;
		}

		DailyRegisterEntryDto dto = new DailyRegisterEntryDto();
		dto.setRegister_id(entity.getRegister_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setRegister_status_id(entity.getRegister_status_id());
		dto.setDate(entity.getDate());
		dto.setOpened_at(entity.getOpened_at());
		dto.setClosed_at(entity.getClosed_at());

		return dto;
	}

	public void saveRegister(DailyRegisterEntryDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Daily Register data is required");
		}

		DailyRegister entity = new DailyRegister();
		entity.setRegister_id(UUID.randomUUID().toString());
		entity.setBranch_id(dto.getBranch_id());
		entity.setEmployee_id(dto.getEmployee_id());
		entity.setRegister_status_id(dto.getRegister_status_id());
		entity.setDate(dto.getDate());
		entity.setOpened_at(dto.getOpened_at());
		entity.setClosed_at(dto.getClosed_at());
		entity.setIsedited(false);
		entity.setIsdeleted(false);

		repository.save(entity);
	}

	public void updateRegister(DailyRegisterEntryDto dto) {
		if (dto == null || dto.getRegister_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Daily Register data is required");
		}

		DailyRegister entity = repository.findById(dto.getRegister_id())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Daily Register"));

		entity.setBranch_id(dto.getBranch_id());
		entity.setEmployee_id(dto.getEmployee_id());
		entity.setRegister_status_id(dto.getRegister_status_id());
		entity.setDate(dto.getDate());
		entity.setOpened_at(dto.getOpened_at());
		entity.setClosed_at(dto.getClosed_at());
		entity.setIsedited(true);

		repository.edit(entity);
	}

	public void deleteRegister(String register_id) {
		DailyRegister entity = repository.findById(register_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Daily Register"));
		repository.delete(register_id);
	}

	public void recoverRegister(String register_id) {
		DailyRegister entity = repository.findById(register_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Daily Register"));
		repository.recover(register_id);
	}

	public void hardDeleteRegister(String register_id) {
		DailyRegister entity = repository.findById(register_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Daily Register"));
		repository.hardDelete(register_id);
	}
}