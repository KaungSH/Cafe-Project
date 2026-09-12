package cafe.project.service;

import cafe.project.model.SizeEntryDto;
import cafe.project.model.SizeListDto;
import cafe.project.repository.SizeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SizeService {

	private final SizeRepository sizeRepository;

	SizeService(SizeRepository sizeRepository) {
		this.sizeRepository = sizeRepository;
	}

	public List<SizeListDto> getAllSizes() {
		return sizeRepository.findAll();
	}

	public SizeEntryDto getSizeById(String id) {
		return sizeRepository.findById(id);
	}

	public void createSize(SizeEntryDto dto) {
		sizeRepository.save(dto);
	}

	public void updateSize(SizeEntryDto dto) {
		sizeRepository.update(dto);
	}

	public void deleteSize(String id) {
		sizeRepository.deleteById(id);
	}

	public List<Map<String, Object>> getAllEmployees() {
		return sizeRepository.findAllEmployees();
	}
}