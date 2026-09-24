package cafe.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.models.ProductDiscountModel;
import cafe.project.models.ProductEntryModel;
import cafe.project.models.ProductListModel;
import cafe.project.models.ProductQuantityRequiredModel;
import cafe.project.repositories.ProductRepository;
import cafe.project.repositories.entities.Product;

@Service
public class ProductService {
	
	private final ProductRepository prepo;
	
	public ProductService(ProductRepository prepo) {this.prepo = prepo;}
	
	public List<ProductListModel> findListAll() {
		return prepo.findListAll().stream().map(this::toListModel2).toList();
	}
	
	public List<ProductListModel> findListAllByTypeId(String type_id) {
		return prepo.findListAllByTypeId(type_id).stream().map(this::toListModel2).toList();
	}
	
	public List<ProductListModel> findListAllByTypeId2(String type_id) {
		return prepo.findListAllByTypeId3(type_id).stream().map(this::toListModel2).toList();
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
		return toEntryModel(prepo.findDetailById(id));
	}
	
	public List<ProductEntryModel> findByTypeId(String type_id) {
		List<ProductEntryModel> list = new ArrayList<ProductEntryModel>();
		for (Product product : prepo.findDetailByTypeId(type_id)) {
			list.add(toEntryModel(product));
		}
		return list;
	}
	
	public ProductListModel findListById(String id) {
		return toListModel(prepo.findListById(id));
	}
	
	public ProductDiscountModel getDiscountById(String pid) {
		return getDiscount(prepo.findDiscountById(pid));
	}
	
	public ProductQuantityRequiredModel getQuantityById(String pid) {
		return getQuantity(prepo.findQuantityRequiredById(pid));
	}
	
	public int add(ProductEntryModel em, String id, String type_id) {
		//to be replaced by http session
		em.setProduct_id(id); em.setType_id(type_id); em.setEmployee_id("1");
		return prepo.add(toEntity(em));
	}
	
	public int add2(ProductEntryModel em, String id, String type_id) {
		//to be replaced by http session
		em.setProduct_id(id); em.setType_id(type_id); em.setEmployee_id("1");
		return prepo.add2(toEntity(em));
	}
	
	public int edit(ProductEntryModel em) {
		return prepo.edit(toEntity(em));
	}
	
	public int delete(String id) {
		return prepo.delete(id);
	}
	
	public int recover(String id) {
		return prepo.recover(id);
	}
	
	public int permDelete(String id) {
		return prepo.permDelete(id);
	}
	
	private ProductListModel toListModel(Product ep) {
		return new ProductListModel(ep.getProduct_id(), ep.getEmployee_name(), ep.getType_name(), ep.getSize_code(), ep.getPrice(), ep.isIsedited(), ep.isIsdeleted(), ep.isIs_active(), ep.getCreated_at(), ep.getQuantity_required(), ep.getDiscount_names(), ep.getUnit_code(), ep.getIngredient_names());
	}
	
	private ProductListModel toListModel2(Product ep) {
		return new ProductListModel(ep.getProduct_id(), ep.getEmployee_name(), ep.getType_name(), ep.getSize_code(), ep.getPrice(), ep.isIsedited(), ep.isIsdeleted(), ep.isIs_active(), ep.getCreated_at(), ep.getQuantity_required(), ep.getDiscount_names(), ep.getUnit_code(), ep.getIngredient_names(), ep.getDiscount_values());
	}
	
	private ProductEntryModel toEntryModel(Product ep) {
		return new ProductEntryModel(ep.getProduct_id(), ep.getEmployee_id(), ep.getType_id(), ep.getSize_id(), ep.getPrice(), ep.isIs_active(), ep.getDiscount_ids(), ep.getIngredient_ids(),ep.getQuantity_required());
	}
	
	private Product toEntity (ProductEntryModel em) {
		return new Product(em.getProduct_id(), em.getEmployee_id(), em.getType_id(), em.getSize_id(), em.getPrice(), em.isIs_active(), em.getDiscount_ids(), em.getIngredient_ids(), em.getQuantity_required());
	}
	
	private ProductDiscountModel getDiscount(Product ep) {
		return new ProductDiscountModel(ep.getProduct_id(), ep.getDiscount_names(), ep.getDiscount_values(), ep.getDiscount_ids());
	}
	
	private ProductQuantityRequiredModel getQuantity(Product ep) {
		return new ProductQuantityRequiredModel(ep.getProduct_id(), ep.getIngredient_names(), ep.getQuantity_required());
	}

}
