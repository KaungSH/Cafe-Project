package cafe.project.services;

import org.springframework.stereotype.Service;

import cafe.project.models.DiscountEntryModel;
import cafe.project.models.DiscountListModel;
import cafe.project.repositories.DiscountRepository;
import cafe.project.repositories.entities.Discount;

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
		
		for (DiscountListModel m : modelList) {
			m.setProducts(productService.findListAllByDiscountId(m.getDiscount_id()));
		}
		return modelList;
	}
	
	public List<DiscountListModel> getAllDiscountsAdmin() {
		List<DiscountListModel> modelList = discountRepository.findAllForListAdmin();
		
		for (DiscountListModel m : modelList) {
			m.setProducts(productService.findListAllByDiscountId(m.getDiscount_id()));
		}
		return modelList;
	}
	
	public DiscountListModel getAllDiscountsById(String id) {
		DiscountListModel listModel = discountRepository.findAllForListById(id);
		listModel.setProducts(productService.findListAllByDiscountId(listModel.getDiscount_id()));
		return listModel;
	}

	public DiscountEntryModel getDiscountById(String id) {
		Discount discount = discountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));

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
		Discount discount = discountRepository.findById(dto.getDiscount_id())
				.orElseThrow(() -> new RuntimeException("Discount not found with ID: " + dto.getDiscount_id()));

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
		discountRepository.softDelete(id);
	}

	public List<DiscountListModel> DeletedList(String branch_id) {
		return discountRepository.DeletedList(branch_id);
	}

	public int restore(String id) {
		return discountRepository.restore(id);
	}

	public int hardDelete(String id) {
		return discountRepository.hardDelete(id);
	}
}
