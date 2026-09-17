package cafe.project.KaungSattHein.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.HeinMinHtet.repositories.CategoryRepository;
import cafe.project.KaungSattHein.models.ProductTypeEntryModel;
import cafe.project.KaungSattHein.models.ProductTypeListModel;
import cafe.project.KaungSattHein.repositories.ProductTypeRepository;
import cafe.project.KaungSattHein.repositories.entities.ProductType;
import cafe.project.YinminThiriSoe.repositories.EmployeeRepository;

@Service
public class ProductTypeService {
	
	private final ProductTypeRepository pt;
	private final CategoryRepository cr;
	private final EmployeeRepository er;
	
	public ProductTypeService (ProductTypeRepository pt, CategoryRepository cr, EmployeeRepository er) {
		this.pt = pt;
		this.cr = cr;
		this.er = er;
	}
	
	public List<ProductTypeListModel> findAll(){
		List<ProductTypeListModel> list = new ArrayList<>();
	    
	    for (ProductType item : pt.findAll()) {
	        String categoryName = cr.findById(item.getCategory_id()).getName();
	        String employeeName = er.findById(item.getEmployee_id()).getName();
	        
	        list.add(toListModel(item, categoryName, employeeName));
	    }
	    
	    return list;
	}
	
	public List<ProductTypeListModel> findDeletedAll(){
		List<ProductTypeListModel> list = new ArrayList<>();
	    
	    for (ProductType item : pt.findDeletedAll()) {
	        String categoryName = cr.findById(item.getCategory_id()).getName();
	        String employeeName = er.findById(item.getEmployee_id()).getName();
	        
	        list.add(toListModel(item, categoryName, employeeName));
	    }
	    
	    return list;
	}
	
	public ProductTypeEntryModel findById(String id) {
		return toEntryModel(pt.findById(id));
	}
	
	public ProductTypeListModel findDetailById(String id) {
		return toListModel(pt.findById(id), cr.findById(pt.findById(id).getCategory_id()).getName(), er.findById(pt.findById(id).getEmployee_id()).getName());
	}
	
	public ProductTypeListModel findDeletedById(String id) {
		return toListModel(pt.findDeletedById(id), cr.findById(pt.findById(id).getCategory_id()).getName(), er.findById(pt.findById(id).getEmployee_id()).getName());
	}
	
	public int add(ProductTypeEntryModel pe) {
		return pt.add(toEntity(pe));
	}
	
	public int edit(ProductTypeEntryModel pe) {
		return pt.edit(toEntity(pe));
	}
	
	public int delete(String id) {
		return pt.deleted(id);
	}
	
	public int recover(String id) {
		return pt.recover(id);
	}
	
	private ProductTypeListModel toListModel(ProductType pt, String category_name, String employee_name) {
		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name);
	}
	
	private ProductTypeEntryModel toEntryModel(ProductType pt) {
		return new ProductTypeEntryModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCategory_id(), pt.getEmployee_id());
	}
	
	private ProductType toEntity(ProductTypeEntryModel pe) {
		return new ProductType(pe.getType_id(), pe.getName(), pe.getDescription(), pe.getCoverimgpath(), pe.getPrice(), pe.getCategory_id(), pe.getEmployee_id());
	}
	
}
