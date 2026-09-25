package cafe.project.services;

import cafe.project.models.SizeEntryDto;
import cafe.project.repositories.SizeRepository;
import cafe.project.repositories.entities.Size;

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

	  public List<Size> getDeletedSizes() {
	    return sizeRepository.findDeletedAll();
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

	  public void restoreSize(String size_id) {
	    sizeRepository.restoreById(size_id);
	  }

	  public void hardDeleteSize(String size_id) {
	    sizeRepository.hardDeleteById(size_id);
	  }

	  public List<Map<String, Object>> getAllEmployees() {
	    return sizeRepository.findAllEmployees();
	  }
	
}