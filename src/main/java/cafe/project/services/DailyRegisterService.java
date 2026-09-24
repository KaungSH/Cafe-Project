package cafe.project.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<DailyRegisterListDto> getAllRegisters() {
		return repository.findAllDto();
	}
	
	public List<DailyRegisterListDto> getAllOpened() {
		return repository.findAllOpened();
	}
	
	public List<DailyRegisterListDto> getAllClosed() {
		return repository.findAllClosed();
	}
	
	public List<DailyRegisterListDto> getAllDeleted() {
		return repository.findAllDeleted();
	}

	public DailyRegisterEntryDto getRegisterEntryDtoById(String register_id) {
		DailyRegister entity = repository.findById(register_id)
				.orElseThrow(() -> new IllegalArgumentException("Register not found."));

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
		DailyRegister entity = repository.findById(dto.getRegister_id())
				.orElseThrow(() -> new IllegalArgumentException("Register not found."));

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
		repository.delete(register_id);
	}
	
	public void recoverRegister(String register_id) {
		repository.recover(register_id);
	}
	
	public void hardDeleteRegister(String register_id) {
		repository.hardDelete(register_id);
	}
}