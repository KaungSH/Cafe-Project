package cafe.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.DiscountProductDto;
import cafe.project.repositories.DiscountProductRepository;
import cafe.project.repositories.entities.DiscountProduct;

@Service
public class DiscountProductService {
	private final DiscountProductRepository repo;

	public DiscountProductService(DiscountProductRepository repo) {

		this.repo = repo;
	}

	public List<DiscountProductDto> findAll() {

		List<DiscountProduct> entities = repo.findAll();

		return entities.stream().map(this::toDto).toList();

	}

	public void add(DiscountProductDto dto) {
		if (dto == null || dto.getProduct_ids() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discount Product data is required");
		}

		for (String product_id : dto.getProduct_ids()) {
			boolean exists = repo.exists(dto.getDiscount_id(), product_id);
			if (!exists) {
				DiscountProduct dp = new DiscountProduct();
				dp.setDiscount_id(dto.getDiscount_id());
				dp.setProduct_id(product_id);
				repo.save(dp);
			}
		}
	}

	public int remove(String discount_id, String product_id) {
		if (discount_id == null || discount_id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discount ID is required");
		}
		if (product_id == null || product_id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}
		return repo.remove(discount_id, product_id);
	}

	private DiscountProductDto toDto(DiscountProduct entity) {
		DiscountProductDto dto = new DiscountProductDto();
		dto.setDiscount_id(entity.getDiscount_id());
		dto.setDiscount_name(entity.getDiscount_name());
		dto.setProduct_id(entity.getProduct_id());
		dto.setProduct_name(entity.getProduct_name());
		return dto;
	}
}
