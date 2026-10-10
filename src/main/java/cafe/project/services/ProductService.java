package cafe.project.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.ProductDiscountModel;
import cafe.project.models.ProductEntryModel;
import cafe.project.models.ProductListModel;
import cafe.project.models.ProductQuantityRequiredModel;
import cafe.project.repositories.ProductRepository;
import cafe.project.repositories.entities.Product;

@Service
public class ProductService {

	private final ProductRepository prepo;
	private final IngredientBatchService ingredientBatchService;

	public ProductService(ProductRepository prepo, IngredientBatchService ingredientBatchService) {
		this.prepo = prepo;
		this.ingredientBatchService = ingredientBatchService;
	}

	public List<ProductListModel> findListAll() {
		return prepo.findListAll().stream().map(this::toListModel2).toList();
	}

	private ProductListModel calculateAvailability(Product product, ProductListModel productModel, String branchId) {

		List<String> ingredientIds = product.getIngredient_ids();
		List<Double> quantityRequired = product.getQuantity_required();

		// Product has no ingredients
		if (ingredientIds == null || ingredientIds.isEmpty() || quantityRequired == null
				|| quantityRequired.isEmpty()) {

			productModel.setAvailable(false);
			productModel.setRemainingServings(0);
			productModel.setAvailabilityStatus("OUT OF STOCK");

			return productModel;
		}

		int remainingServings = Integer.MAX_VALUE;

		for (int i = 0; i < ingredientIds.size(); i++) {

			String ingredientTypeId = ingredientIds.get(i);
			double requiredQuantity = quantityRequired.get(i);

			if (requiredQuantity <= 0) {
				continue;
			}

			BigDecimal availableQuantity = ingredientBatchService.getAvailableQuantity(ingredientTypeId, branchId);

			int servings = availableQuantity
					.divide(BigDecimal.valueOf(requiredQuantity), 0, java.math.RoundingMode.FLOOR).intValue();

			if (servings < remainingServings) {
				remainingServings = servings;
			}
		}

		if (remainingServings == Integer.MAX_VALUE) {
			remainingServings = 0;
		}

		productModel.setRemainingServings(remainingServings);

		if (remainingServings <= 0) {

			productModel.setAvailable(false);
			productModel.setAvailabilityStatus("OUT OF STOCK");

		} else if (remainingServings <= 5) {

			productModel.setAvailable(true);
			productModel.setAvailabilityStatus("LOW STOCK");

		} else {

			productModel.setAvailable(true);
			productModel.setAvailabilityStatus("AVAILABLE");
		}

		return productModel;
	}

	public List<ProductListModel> findAllForOrder(String branchId) {

		List<Product> allProducts = new ArrayList<>();
		allProducts.addAll(prepo.findListAllForOrder());

		List<ProductListModel> products = allProducts.stream().map(this::toListModel2).toList();

		for (int i = 0; i < products.size(); i++) {

			Product product = allProducts.get(i);
			ProductListModel productModel = products.get(i);

			calculateAvailability(product, productModel, branchId);
		}

		return products;
	}

	public List<ProductListModel> findListAllByTypeIdForOrder(String typeId, String branchId) {

		if (typeId == null || typeId.isBlank() || branchId == null || branchId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product type ID and Branch ID are required");
		}

		List<Product> products = prepo.findListAllByTypeId(typeId);

		List<ProductListModel> result = new ArrayList<>();

		for (Product product : products) {

			ProductListModel productModel = toListModel2(product);

			calculateAvailability(product, productModel, branchId);

			result.add(productModel);
		}

		return result;
	}

	public List<ProductListModel> findListAllByTypeId(String type_id) {
		if (type_id == null || type_id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product type ID is required");
		}
		return prepo.findListAllByTypeId(type_id).stream().map(this::toListModel2).toList();
	}

	public List<ProductListModel> findListAllByTypeId2(String type_id) {
		if (type_id == null || type_id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product type ID is required");
		}
		return prepo.findListAllByTypeId3(type_id).stream().map(this::toListModel2).toList();
	}

	public List<ProductListModel> findListAllByDiscountId(String discountId) {
		if (discountId == null || discountId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discount ID is required");
		}
		return prepo.findListAllByDiscount(discountId).stream().map(this::toListModel2).toList();
	}

	public List<ProductListModel> findDeletedAll() {
		return prepo.findDeletedAll().stream().map(this::toListModel).toList();
	}

	public List<ProductListModel> findInactiveAll() {
		return prepo.findInactiveAll().stream().map(this::toListModel).toList();
	}

	public List<ProductListModel> findAll() {
		List<Product> allProducts = new ArrayList<>();
		allProducts.addAll(prepo.findListAll());
		allProducts.addAll(prepo.findDeletedAll());
		allProducts.addAll(prepo.findInactiveAll());

		return allProducts.stream().map(this::toListModel).toList();
	}

	public ProductEntryModel findById(String id) {
		if (id == null || id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findDetailById(id);

		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return toEntryModel(product);
	}

	public List<ProductEntryModel> findByTypeId(String type_id) {
		if (type_id == null || type_id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product type ID is required");
		}

		List<ProductEntryModel> list = new ArrayList<ProductEntryModel>();
		for (Product product : prepo.findDetailByTypeId(type_id)) {
			list.add(toEntryModel(product));
		}
		return list;
	}

	public ProductListModel findListById(String id) {
		if (id == null || id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findListById(id);

		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return toListModel(product);
	}

	public ProductDiscountModel getDiscountById(String pid) {
		if (pid == null || pid.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findDiscountById(pid);

		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return getDiscount(product);
	}

	public ProductQuantityRequiredModel getQuantityById(String pid) {
		if (pid == null || pid.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findQuantityRequiredById(pid);

		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return getQuantity(product);
	}

	public int add(ProductEntryModel em, String id, String type_id) {
		if (em == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product data is required");
		}
		em.setProduct_id(id);
		em.setType_id(type_id);
		em.setEmployee_id("1");
		return prepo.add(toEntity(em));
	}

	public int add2(ProductEntryModel em, String id, String type_id) {
		if (em == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product data is required");
		}
		em.setProduct_id(id);
		em.setType_id(type_id);
		em.setEmployee_id("1");
		return prepo.add2(toEntity(em));
	}

	public int edit(ProductEntryModel em) {
		if (em == null || em.getProduct_id() == null || em.getProduct_id().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product data and Product ID are required");
		}

		Product product = prepo.findDetailById(em.getProduct_id());
		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return prepo.edit(toEntity(em));
	}

	public int delete(String id) {
		if (id == null || id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findDetailById(id);
		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return prepo.delete(id);
	}

	public int recover(String id) {
		if (id == null || id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findDetailById(id);
		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return prepo.recover(id);
	}

	public int permDelete(String id) {
		if (id == null || id.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required");
		}

		Product product = prepo.findDetailById(id);
		if (product == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
		}

		return prepo.permDelete(id);
	}

	private ProductListModel toListModel(Product ep) {
		return new ProductListModel(ep.getProduct_id(), ep.getEmployee_name(), ep.getType_name(), ep.getSize_code(),
				ep.getPrice(), ep.isIsedited(), ep.isIsdeleted(), ep.isIs_active(), ep.getCreated_at(),
				ep.getIngredient_ids(), ep.getQuantity_required(), ep.getDiscount_names(), ep.getUnit_code(),
				ep.getIngredient_names());
	}

	private ProductListModel toListModel2(Product ep) {
		return new ProductListModel(ep.getProduct_id(), ep.getEmployee_name(), ep.getType_name(), ep.getSize_code(),
				ep.getPrice(), ep.isIsedited(), ep.isIsdeleted(), ep.isIs_active(), ep.getCreated_at(),
				ep.getIngredient_ids(), ep.getQuantity_required(), ep.getDiscount_names(), ep.getUnit_code(),
				ep.getIngredient_names(), ep.getDiscount_values());
	}

	private ProductEntryModel toEntryModel(Product ep) {
		return new ProductEntryModel(ep.getProduct_id(), ep.getEmployee_id(), ep.getType_id(), ep.getSize_id(),
				ep.getPrice(), ep.isIs_active(), ep.getDiscount_ids(), ep.getIngredient_ids(),
				ep.getQuantity_required());
	}

	private Product toEntity(ProductEntryModel em) {
		return new Product(em.getProduct_id(), em.getEmployee_id(), em.getType_id(), em.getSize_id(), em.getPrice(),
				em.isIs_active(), em.getDiscount_ids(), em.getIngredient_ids(), em.getQuantity_required());
	}

	private ProductDiscountModel getDiscount(Product ep) {
		return new ProductDiscountModel(ep.getProduct_id(), ep.getDiscount_names(), ep.getDiscount_values(),
				ep.getDiscount_ids());
	}

	private ProductQuantityRequiredModel getQuantity(Product ep) {
		return new ProductQuantityRequiredModel(ep.getProduct_id(), ep.getIngredient_names(),
				ep.getQuantity_required());
	}

}