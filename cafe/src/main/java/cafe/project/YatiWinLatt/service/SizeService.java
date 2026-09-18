package cafe.project.YatiWinLatt.service;

import cafe.project.YatiWinLatt.models.SizeEntryDto;

import cafe.project.YatiWinLatt.repositories.SizeRepository;
import cafe.project.YatiWinLatt.repositories.entities.Size;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SizeService {

	private final SizeRepository sizeRepository;

	SizeService(SizeRepository sizeRepository) {
		this.sizeRepository = sizeRepository;
	}

	public List<Size> getAllSizes() {
		return sizeRepository.findAll();
	}

	public Size getSizeById(String size_id) {
		return sizeRepository.findById(size_id);
	}

	public void createSize(SizeEntryDto dto) {
		sizeRepository.save(dto);
	}

	public void updateSize(SizeEntryDto dto) {
		sizeRepository.update(dto);
	}

	public void deleteSize(String size_id) {
		sizeRepository.deleteById(size_id);
	}

	public List<Map<String, Object>> getAllEmployees() {
		return sizeRepository.findAllEmployees();
	}
}