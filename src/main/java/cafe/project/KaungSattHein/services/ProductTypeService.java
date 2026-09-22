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
	private final ProductService ps;
	
	public ProductTypeService (ProductTypeRepository pt, CategoryRepository cr, EmployeeRepository er, ProductService ps) {
		this.pt = pt;
		this.cr = cr;
		this.er = er;
		this.ps = ps;
	}
	
	public List<ProductTypeListModel> findAll(){
		List<ProductTypeListModel> list = new ArrayList<>();
	    
	    for (ProductType item : pt.findAll()) {
	        String categoryName = cr.findById(item.getCategory_id()).getName();
	        String employeeName = er.findById(item.getEmployee_id()).getName();
	        
	        list.add(toListModel2(item, categoryName, employeeName));
	    }
	    
	    return list;
	}
	
	public List<ProductTypeListModel> searchByName(String keyword){
		List<ProductTypeListModel> list = new ArrayList<>();
	    
	    for (ProductType item : pt.searchByName(keyword)) {
	        String categoryName = cr.findById(item.getCategory_id()).getName();
	        String employeeName = er.findById(item.getEmployee_id()).getName();
	        
	        list.add(toListModel2(item, categoryName, employeeName));
	    }
	    
	    return list;
	}
	
	public List<ProductTypeListModel> searchDeletedByName(String keyword){
		List<ProductTypeListModel> list = new ArrayList<>();
	    
	    for (ProductType item : pt.searchDeletedByName(keyword)) {
	        String categoryName = cr.findById(item.getCategory_id()).getName();
	        String employeeName = er.findById(item.getEmployee_id()).getName();
	        
	        list.add(toListModel3(item, categoryName, employeeName));
	    }
	    
	    return list;
	}
	
	public List<ProductTypeListModel> findDeletedAll(){
		List<ProductTypeListModel> list = new ArrayList<>();
	    
	    for (ProductType item : pt.findDeletedAll()) {
	        String categoryName = cr.findById(item.getCategory_id()).getName();
	        String employeeName = er.findById(item.getEmployee_id()).getName();
	        
	        list.add(toListModel3(item, categoryName, employeeName));
	    }
	    
	    return list;
	}
	
	public ProductTypeEntryModel findById(String id) {
		return toEntryModel(pt.findById(id));
	}
	
	public ProductTypeEntryModel findById3(String id) {
		return toEntryModel(pt.findById2(id));
	}
	
	public ProductTypeEntryModel findById2(String id) {
		return toEntryModel2(pt.findById(id));
	}
	
	public ProductTypeListModel findDetailById(String id) {
		return toListModel(pt.findById(id), cr.findById(pt.findById(id).getCategory_id()).getName(), er.findById(pt.findById(id).getEmployee_id()).getName());
	}
	
	public ProductTypeListModel findDeletedById(String id) {
		return toListModel(pt.findDeletedById(id), cr.findById(pt.findById(id).getCategory_id()).getName(), er.findById(pt.findById(id).getEmployee_id()).getName());
	}
	
	public int add(ProductTypeEntryModel pe) {
		return pt.add(toEntity1(pe));
	}
	
	public int edit(ProductTypeEntryModel pe) {
		return pt.edit(toEntity1(pe));
	}
	
	public int delete(String id) {
		return pt.deleted(id);
	}
	
	public int deletePerm(String id) {
		return pt.deletedPerm(id);
	}
	
	public int recover(String id) {
		return pt.recover(id);
	}
	
	private ProductTypeListModel toListModel(ProductType pt, String category_name, String employee_name) {
		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name);
	}
	
	private ProductTypeListModel toListModel2(ProductType pt, String category_name, String employee_name) {
		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name, ps.findListAllByTypeId(pt.getType_id()));
	}
	
	private ProductTypeListModel toListModel3(ProductType pt, String category_name, String employee_name) {
		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name, ps.findListAllByTypeId2(pt.getType_id()));
	}
	
	private ProductTypeEntryModel toEntryModel(ProductType pt) {
		return new ProductTypeEntryModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCategory_id(), pt.getEmployee_id());
	}
	
	private ProductTypeEntryModel toEntryModel2(ProductType pt) {
		return new ProductTypeEntryModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCategory_id(), pt.getEmployee_id(), getCoverImageFileName(pt.getCoverimgpath()), ps.findByTypeId(pt.getType_id()));
	}
	
//	private ProductType toEntity(ProductTypeEntryModel pe) {
//		return new ProductType(pe.getType_id(), pe.getName(), pe.getDescription(), pe.getCoverimgpath(), pe.getPrice(), pe.getCategory_id(), pe.getEmployee_id());
//	}
	
	private ProductType toEntity1(ProductTypeEntryModel pe) {
		return new ProductType(pe.getType_id(), pe.getName(), pe.getDescription(), pe.getCoverimgpath(), pe.getPrice(), pe.getCategory_id(), pe.getEmployee_id(), pe.getProduct());
	}
	
	private String getCoverImageFileName(String cover_img_path) {
	    if (cover_img_path == null || cover_img_path.isEmpty()) {
	        return null;
	    }
	    // Extracts just the file name from the full path string
	    return java.nio.file.Paths.get(cover_img_path).getFileName().toString();
	}
	
}
