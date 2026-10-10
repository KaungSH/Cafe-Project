package cafe.project.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.DiscountEntryModel;
import cafe.project.models.DiscountListModel;
import cafe.project.repositories.DiscountRepository;
import cafe.project.repositories.entities.Discount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DiscountService {

	private final DiscountRepository discountRepository;
	private final ProductService productService;

	public DiscountService(DiscountRepository discountRepository, ProductService productService) {
		this.discountRepository = discountRepository;
		this.productService = productService;
	}

	public List<DiscountListModel> getAllDiscounts(String branch_id) {
		List<DiscountListModel> modelList = discountRepository.findAllForList(branch_id);
//		if (modelList == null || modelList.isEmpty())
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");

		for (DiscountListModel m : modelList) {
			setStatus(m.getDiscount_id(), m.getStartdate(), m.getEnddate());
			m.setProducts(productService.findListAllByDiscountId(m.getDiscount_id()));
		}
		return modelList;
	}

	public List<DiscountListModel> getAllDiscountsInactive(String branch_id) {
		List<DiscountListModel> modelList = discountRepository.findAllForListInactive(branch_id);

		for (DiscountListModel m : modelList) {
			setStatus(m.getDiscount_id(), m.getStartdate(), m.getEnddate());
			m.setProducts(productService.findListAllByDiscountId(m.getDiscount_id()));
		}

		return modelList;
	}

	public List<DiscountListModel> getAllDiscountsActive(String branch_id) {
		List<DiscountListModel> modelList = discountRepository.findAllForListActive(branch_id);
		if (modelList == null || modelList.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");

		for (DiscountListModel m : modelList) {
			setStatus(m.getDiscount_id(), m.getStartdate(), m.getEnddate());
			m.setProducts(productService.findListAllByDiscountId(m.getDiscount_id()));
		}
		return modelList;
	}

	public List<DiscountListModel> getAllDiscountsAdmin() {
		List<DiscountListModel> modelList = discountRepository.findAllForListAdmin();
		if (modelList == null || modelList.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");

		for (DiscountListModel m : modelList) {
			setStatus(m.getDiscount_id(), m.getStartdate(), m.getEnddate());
			m.setProducts(productService.findListAllByDiscountId(m.getDiscount_id()));
		}
		return modelList;
	}

	public DiscountListModel getAllDiscountsById(String id) {
		DiscountListModel listModel = discountRepository.findAllForListById(id);
		if (listModel == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");
		listModel.setProducts(productService.findListAllByDiscountId(listModel.getDiscount_id()));
		return listModel;
	}

	public List<DiscountListModel> findActiveDiscountByProductId(String productId) {
		List<DiscountListModel> list = discountRepository.findActiveDiscountByProductId(productId);
		if (list == null || list.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");
		return list;
	}

	public DiscountEntryModel getDiscountById(String id) {
		Discount discount = discountRepository.findById(id).orElse(null);

		if (discount == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");
		}

		if (discount.getBranches_branch_id() == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Discount has no branch assigned");
		}

		DiscountEntryModel dto = new DiscountEntryModel();
		dto.setDiscount_id(discount.getDiscount_id());
		dto.setEmployee_id(discount.getEmployee_id());
		dto.setName(discount.getName());
		dto.setDescription(discount.getDescription());
		dto.setDiscount_value(discount.getDiscount_value());
		dto.setStartdate(discount.getStartdate());
		dto.setEnddate(discount.getEnddate());
		dto.setIs_active(discount.getIs_active());
		dto.setPromo_type_id(discount.getPromo_type_id());
		dto.setAudience_type_id(discount.getAudience_type_id());
		dto.setBranches_branch_id(discount.getBranches_branch_id());
		return dto;
	}

	public void createDiscount(DiscountEntryModel dto) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discount data is required");
		}

		Discount discount = new Discount();
		discount.setDiscount_id(UUID.randomUUID().toString());
		discount.setEmployee_id(dto.getEmployee_id());
		discount.setName(dto.getName());
		discount.setDescription(dto.getDescription());
		discount.setDiscount_value(dto.getDiscount_value());
		discount.setStartdate(dto.getStartdate());
		discount.setEnddate(dto.getEnddate());
		discount.setIs_active(dto.isIs_active());
		discount.setPromo_type_id(dto.getPromo_type_id());
		discount.setAudience_type_id(dto.getAudience_type_id());
		discount.setBranches_branch_id(dto.getBranches_branch_id());

		discountRepository.save(discount);
	}

	public void updateDiscount(DiscountEntryModel dto) {
		if (dto == null || dto.getDiscount_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discount data is required");
		}

		Discount discount = discountRepository.findById(dto.getDiscount_id()).orElse(null);
		if (discount == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");
		}

		discount.setEmployee_id(dto.getEmployee_id());
		discount.setName(dto.getName());
		discount.setDescription(dto.getDescription());
		discount.setDiscount_value(dto.getDiscount_value());
		discount.setStartdate(dto.getStartdate());
		discount.setEnddate(dto.getEnddate());
		discount.setIs_active(dto.isIs_active());
		discount.setPromo_type_id(dto.getPromo_type_id());
		discount.setAudience_type_id(dto.getAudience_type_id());
		discount.setBranches_branch_id(dto.getBranches_branch_id());

		discountRepository.update(discount);
	}

	public void deleteDiscount(String id) {
		getAllDiscountsById(id);
		discountRepository.softDelete(id);
	}

	public List<DiscountListModel> DeletedList(String branch_id) {
		List<DiscountListModel> list = discountRepository.findActiveDiscountByProductId(branch_id);
		if (list == null || list.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount");

		return list;
	}

	public int restore(String id) {
		getAllDiscountsById(id);
		return discountRepository.restore(id);
	}

	public int hardDelete(String id) {
		getAllDiscountsById(id);
		return discountRepository.hardDelete(id);
	}

	private int setStatus(String discount_id, LocalDate startDate, LocalDate endDate) {
		boolean isActive = !LocalDate.now().isBefore(startDate) && !LocalDate.now().isAfter(endDate);

		if (isActive) {
			return discountRepository.setStatus(discount_id, true);
		} else {
			return discountRepository.setStatus(discount_id, false);
		}
	}
}