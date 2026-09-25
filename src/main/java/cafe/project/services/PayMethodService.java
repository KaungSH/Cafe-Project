package cafe.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
		return payMethodRepository.findById(methodId);
	}

	public PayMethodDto getPayMethodByIdWithRelations(String methodId) {
		return payMethodRepository.findByIdWithRelations(methodId);
	}

	public void createPayMethod(PayMethod pm) {
		if (pm.getMethodId() == null || pm.getMethodId().trim().isEmpty()) {
			pm.setMethodId(UUID.randomUUID().toString());
		}
		if (pm.getCreatedAt() == null) {
			pm.setCreatedAt(LocalDateTime.now());
		}
		payMethodRepository.add(pm);
	}

	public void updatePayMethod(PayMethod pm) {
		payMethodRepository.update(pm);
	}

	public void deletePayMethod(String methodId) {
		payMethodRepository.softDelete(methodId);
	}
}