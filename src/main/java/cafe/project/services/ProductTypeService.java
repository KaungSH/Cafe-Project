package cafe.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.ProductListModel;
import cafe.project.models.ProductTypeEntryModel;
import cafe.project.models.ProductTypeListModel;
import cafe.project.repositories.CategoryRepository;
import cafe.project.repositories.EmployeeRepository;
import cafe.project.repositories.ProductTypeRepository;
import cafe.project.repositories.entities.ProductType;

@Service
public class ProductTypeService {

	private final ProductTypeRepository pt;
	private final CategoryRepository cr;
	private final EmployeeRepository er;
	private final ProductService ps;

	public ProductTypeService(ProductTypeRepository pt, CategoryRepository cr, EmployeeRepository er,
			ProductService ps) {
		this.pt = pt;
		this.cr = cr;
		this.er = er;
		this.ps = ps;
	}

	public List<ProductTypeListModel> findAll() {
		List<ProductTypeListModel> list = new ArrayList<>();

		for (ProductType item : pt.findAll()) {
			String categoryName = cr.findById(item.getCategory_id()).getName();
			String employeeName = er.findByIdAdmin(item.getEmployee_id()).getName();

			list.add(toListModel2(item, categoryName, employeeName));
		}

		return list;
	}

	public List<ProductTypeListModel> findAllForOrder(String branchId) {

		if (branchId == null || branchId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Branch ID is required");
		}

		List<ProductTypeListModel> list = new ArrayList<>();

		for (ProductType item : pt.findAll()) {

			String categoryName = cr.findById(item.getCategory_id()).getName();

			String employeeName = er.findByIdAdmin(item.getEmployee_id()).getName();

			ProductTypeListModel model = new ProductTypeListModel(item.getType_id(), item.getName(),
					item.getDescription(), item.getCoverimgpath(), item.getPrice(), item.getCreated_at(),
					item.isIsdeleted(), item.isIsedited(), categoryName, employeeName,
					ps.findListAllByTypeIdForOrder(item.getType_id(), branchId));

			List<ProductListModel> products = ps.findListAllByTypeIdForOrder(item.getType_id(), branchId);

			list.add(model);
		}

		return list;
	}

	public List<ProductTypeListModel> searchByNameForOrder(String keyword, String branchId) {

		if (keyword == null || keyword.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search keyword is required");
		}

		if (branchId == null || branchId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Branch ID is required");
		}

		List<ProductTypeListModel> list = new ArrayList<>();

		for (ProductType item : pt.searchByName(keyword)) {

			String categoryName = cr.findById(item.getCategory_id()).getName();

			String employeeName = er.findByIdAdmin(item.getEmployee_id()).getName();

			ProductTypeListModel model = new ProductTypeListModel(item.getType_id(), item.getName(),
					item.getDescription(), item.getCoverimgpath(), item.getPrice(), item.getCreated_at(),
					item.isIsdeleted(), item.isIsedited(), categoryName, employeeName,
					ps.findListAllByTypeIdForOrder(item.getType_id(), branchId));

			// Remove product types that have no available product
			boolean hasAvailableProduct = false;

			if (model.getProducts() != null) {

				for (var product : model.getProducts()) {

					if (product.isAvailable() && product.getRemainingServings() > 0) {

						hasAvailableProduct = true;
						break;
					}
				}
			}

			if (hasAvailableProduct) {
				list.add(model);
			}
		}

		return list;
	}

	public List<ProductTypeListModel> searchByName(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search keyword is required");
		}
		List<ProductTypeListModel> list = new ArrayList<>();

		for (ProductType item : pt.searchByName(keyword)) {
			String categoryName = cr.findById(item.getCategory_id()).getName();
			String employeeName = er.findById(item.getEmployee_id()).getName();

			list.add(toListModel2(item, categoryName, employeeName));
		}

		return list;
	}

	public List<ProductTypeListModel> searchDeletedByName(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search keyword is required");
		}

		List<ProductTypeListModel> list = new ArrayList<>();

		for (ProductType item : pt.searchDeletedByName(keyword)) {
			String categoryName = cr.findById(item.getCategory_id()).getName();
			String employeeName = er.findById(item.getEmployee_id()).getName();

			list.add(toListModel3(item, categoryName, employeeName));
		}

		return list;
	}

	public List<ProductTypeListModel> findDeletedAll() {
		List<ProductTypeListModel> list = new ArrayList<>();

		for (ProductType item : pt.findDeletedAll()) {
			String categoryName = cr.findById(item.getCategory_id()).getName();
			String employeeName = er.findById(item.getEmployee_id()).getName();

			list.add(toListModel3(item, categoryName, employeeName));
		}

		return list;
	}

//	public ProductTypeEntryModel findById(String id) {
//		ProductType entity = pt.findById(id);
//
//		if (entity == null) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found");
//		}
//
//		return toEntryModel(entity);
//	}

	public ProductTypeEntryModel findById(String id) {
		try {
			ProductType entity = pt.findById(id);

			if (entity == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found");
			}

			return toEntryModel(entity);

		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found");
		}
	}

	public ProductTypeEntryModel findById3(String id) {
		ProductType entity = pt.findById2(id);

		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found");
		}

		return toEntryModel(entity);
	}

	public ProductTypeEntryModel findById2(String id) {
		ProductType entity = pt.findById2(id);

		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found");
		}

		return toEntryModel(entity);
	}

	public ProductTypeListModel findDetailById(String id) {
		ProductType entity = pt.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type");
		}
		return toListModel(entity, cr.findById(entity.getCategory_id()).getName(),
				er.findById(entity.getEmployee_id()).getName());
	}

	public ProductTypeListModel findDeletedById(String id) {
		ProductType entity = pt.findDeletedById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type");
		}
		return toListModel(entity, cr.findById(entity.getCategory_id()).getName(),
				er.findById(entity.getEmployee_id()).getName());
	}

	public int add(ProductTypeEntryModel pe) {
		if (pe == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Type data is required");
		}
		return pt.add(toEntity1(pe));
	}

	public int edit(ProductTypeEntryModel pe) {
		if (pe == null || pe.getType_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Type data is required");
		}
		ProductType entity = pt.findById(pe.getType_id());
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type");
		}
		return pt.edit(toEntity1(pe));
	}

	public int delete(String id) {
		ProductType entity = pt.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type");
		}
		return pt.deleted(id);
	}

	public int deletePerm(String id) {
		ProductType entity = pt.findDeletedById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type");
		}
		return pt.deletedPerm(id);
	}

	public int recover(String id) {
		ProductType entity = pt.findDeletedById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type");
		}
		return pt.recover(id);
	}

	private ProductTypeListModel toListModel(ProductType pt, String category_name, String employee_name) {
		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(),
				pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name);
	}

	private ProductTypeListModel toListModel2(ProductType pt, String category_name, String employee_name) {

		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(),
				pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name,
				ps.findListAllByTypeId(pt.getType_id()));
	}

	private ProductTypeListModel toListModel3(ProductType pt, String category_name, String employee_name) {
		return new ProductTypeListModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(),
				pt.getPrice(), pt.getCreated_at(), pt.isIsdeleted(), pt.isIsedited(), category_name, employee_name,
				ps.findListAllByTypeId2(pt.getType_id()));
	}

	private ProductTypeEntryModel toEntryModel(ProductType pt) {
		return new ProductTypeEntryModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(),
				pt.getPrice(), pt.getCategory_id(), pt.getEmployee_id());
	}

	private ProductTypeEntryModel toEntryModel2(ProductType pt) {
		return new ProductTypeEntryModel(pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(),
				pt.getPrice(), pt.getCategory_id(), pt.getEmployee_id(), getCoverImageFileName(pt.getCoverimgpath()),
				ps.findByTypeId(pt.getType_id()));
	}

//	private ProductType toEntity(ProductTypeEntryModel pe) {
//		return new ProductType(pe.getType_id(), pe.getName(), pe.getDescription(), pe.getCoverimgpath(), pe.getPrice(), pe.getCategory_id(), pe.getEmployee_id());
//	}

	private ProductType toEntity1(ProductTypeEntryModel pe) {
		return new ProductType(pe.getType_id(), pe.getName(), pe.getDescription(), pe.getCoverimgpath(), pe.getPrice(),
				pe.getCategory_id(), pe.getEmployee_id(), pe.getProduct());
	}

	private String getCoverImageFileName(String cover_img_path) {
		if (cover_img_path == null || cover_img_path.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cover image");
		}
		// Extracts just the file name from the full path string
		return java.nio.file.Paths.get(cover_img_path).getFileName().toString();
	}

}
