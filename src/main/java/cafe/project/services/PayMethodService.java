package cafe.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.PayMethodDto;
import cafe.project.repositories.PayMethodRepository;
import cafe.project.repositories.entities.PayMethod;

@Service
public class PayMethodService {

	private final PayMethodRepository payMethodRepository;

	public PayMethodService(PayMethodRepository payMethodRepository) {
		this.payMethodRepository = payMethodRepository;
	}

	// ---------- Read ----------

	public List<PayMethod> getAllPayMethods() {
		return payMethodRepository.findAll();
	}

	public List<PayMethod> getAllActivePayMethods() {
		return payMethodRepository.findAllActive();
	}

	public List<PayMethodDto> getAllPayMethodsWithRelations() {
		return payMethodRepository.findAllWithRelations();
	}

	public PayMethod getPayMethodById(String methodId) {
		PayMethod entity = payMethodRepository.findById(methodId);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method");
		}
		return entity;
	}

	public PayMethodDto getPayMethodByIdWithRelations(String methodId) {
		PayMethodDto dto = payMethodRepository.findByIdWithRelations(methodId);
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method");
		}
		return dto;
	}

	public void changeIsActive(String methodId) {
		PayMethod entity = payMethodRepository.findById(methodId);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method");
		}
		payMethodRepository.changeIsActive(methodId, !entity.isActive());
	}

	public void createPayMethod(PayMethod pm) {
		if (pm == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Method data is required");
		}
		System.out.println("Service - " + pm.getLogoPath());
		if (pm.getMethodId() == null || pm.getMethodId().trim().isEmpty()) {
			pm.setMethodId(UUID.randomUUID().toString());
		}
		if (pm.getCreatedAt() == null) {
			pm.setCreatedAt(LocalDateTime.now());
		}
		payMethodRepository.add(pm);
	}

	public void updatePayMethod(PayMethod pm) {
		if (pm == null || pm.getMethodId() == null || pm.getMethodId().trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Method data and ID are required");
		}
		PayMethod entity = payMethodRepository.findById(pm.getMethodId());
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method");
		}
		payMethodRepository.update(pm);
	}

	public void deletePayMethod(String methodId) {
		PayMethod entity = payMethodRepository.findById(methodId);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method");
		}
		payMethodRepository.softDelete(methodId);
	}

	public List<PayMethodDto> findDeleted() {
		return this.payMethodRepository.deletedList();
	}

	public int restore(String methodId) {
		PayMethod entity = payMethodRepository.findById(methodId);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method");
		}
		return this.payMethodRepository.restore(methodId);
	}
}