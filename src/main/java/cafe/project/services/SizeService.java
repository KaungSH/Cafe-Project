package cafe.project.services;

import cafe.project.models.SizeEntryDto;
import cafe.project.repositories.SizeRepository;
import cafe.project.repositories.entities.Size;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

//	public Size getSizeById(String size_id) {
//		Size entity = sizeRepository.findById(size_id);
//		if (entity == null) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size");
//		}
//		return entity;
//	}

	public Size getSizeById(String id) {
		try {
			Size size = sizeRepository.findById(id);

			if (size == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size not found");
			}

			return size;

		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size not found");
		}
	}

	public void createSize(SizeEntryDto dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size data is required");
		}
		sizeRepository.save(dto);
	}

	public void updateSize(SizeEntryDto dto) {
		if (dto == null || dto.getSize_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size data is required");
		}
		Size entity = sizeRepository.findById(dto.getSize_id());
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size");
		}
		sizeRepository.update(dto);
	}

	public void deleteSize(String size_id) {
		Size entity = sizeRepository.findById(size_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size");
		}
		sizeRepository.deleteById(size_id);
	}

	public void restoreSize(String size_id) {
		Size entity = sizeRepository.findById(size_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size");
		}
		sizeRepository.restoreById(size_id);
	}

	public void hardDeleteSize(String size_id) {
		Size entity = sizeRepository.findById(size_id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Size");
		}
		sizeRepository.hardDeleteById(size_id);
	}

}