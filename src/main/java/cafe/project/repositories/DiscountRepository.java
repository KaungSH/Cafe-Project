package cafe.project.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.models.DiscountListModel;
import cafe.project.repositories.entities.Discount;
import cafe.project.repositories.mappers.resultsetextractors.DiscountListModelResultSetExtractor;
import cafe.project.repositories.mappers.resultsetextractors.DiscountResultSetExtractor;

@Repository
public class DiscountRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DeleteRecordRepository deleteRecordRepo;

	public DiscountRepository(JdbcTemplate jdbcTemplate, DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	public int save(Discount discount) {
		String sql = "INSERT INTO discounts (discount_id, employee_id, name, description, "
				+ "discount_value, startdate, enddate, is_active, promo_type_id, audience_type_id,branches_branch_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, discount.getDiscount_id(), discount.getEmployee_id(), discount.getName(),
				discount.getDescription(), discount.getDiscount_value(), discount.getStartdate(), discount.getEnddate(),
				false, discount.getPromo_type_id(), discount.getAudience_type_id(), discount.getBranches_branch_id());
	}

	public int update(Discount discount) {
		String sql = "UPDATE discounts SET employee_id = ?, name = ?, description = ?, "
				+ "discount_value = ?, startdate = ?, enddate = ?, "
				+ "isedited = 1, promo_type_id = ?, audience_type_id = ? " + "WHERE discount_id = ? AND isdeleted = 0";
		return jdbcTemplate.update(sql, discount.getEmployee_id(), discount.getName(), discount.getDescription(),
				discount.getDiscount_value(), discount.getStartdate(), discount.getEnddate(),
				discount.getPromo_type_id(), discount.getAudience_type_id(), discount.getDiscount_id());
	}

	

	public List<DiscountListModel> DeletedList(String branch_id) {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, \r\n"
				+ "d.startdate, d.enddate, d.is_active, pt.type_name AS promo_type_name, \r\n"
				+ "at.type_name AS audience_type_name, e.name AS employee_name, b.name AS branch_name FROM discounts d \r\n"
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id \r\n"
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id \r\n"
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON  d.branches_branch_id = b.branch_id WHERE d.isdeleted = 1 AND d.branches_branch_id = ? \r\n"
				+ "ORDER BY d.created_at DESC";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor(), branch_id);
	}
	
	

	public Optional<Discount> findById(String id) {
		String sql = "SELECT * FROM discounts WHERE discount_id = ? AND isdeleted = 0";
		List<Discount> results = jdbcTemplate.query(sql, new DiscountResultSetExtractor(), id);
		return (results != null && !results.isEmpty()) ? Optional.of(results.get(0)) : Optional.empty();
	}

	public List<DiscountListModel> findAllForList(String branch_id) {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, \r\n"
				+ "d.startdate, d.enddate, d.is_active, pt.type_name AS promo_type_name, \r\n"
				+ "at.type_name AS audience_type_name, e.name AS employee_name, b.name AS branch_name FROM discounts d \r\n"
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id \r\n"
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id \r\n"
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON  d.branches_branch_id = b.branch_id WHERE d.isdeleted = 0 AND d.branches_branch_id = ? \r\n"
				+ "ORDER BY d.created_at DESC";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor(), branch_id);
	}
	
	public List<DiscountListModel> findAllForListActive(String branch_id) {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, \r\n"
				+ "d.startdate, d.enddate, d.is_active, pt.type_name AS promo_type_name, \r\n"
				+ "at.type_name AS audience_type_name, e.name AS employee_name, b.name AS branch_name FROM discounts d \r\n"
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id \r\n"
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id \r\n"
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON  d.branches_branch_id = b.branch_id WHERE d.isdeleted = 0 AND d.branches_branch_id = ? AND d.is_active = true \r\n"
				+ "ORDER BY d.created_at DESC";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor(), branch_id);
	}
	
	public List<DiscountListModel> findAllForListInactive(String branch_id) {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, \r\n"
				+ "d.startdate, d.enddate, d.is_active, pt.type_name AS promo_type_name, \r\n"
				+ "at.type_name AS audience_type_name, e.name AS employee_name, b.name AS branch_name FROM discounts d \r\n"
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id \r\n"
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id \r\n"
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON  d.branches_branch_id = b.branch_id WHERE d.isdeleted = 0 AND d.branches_branch_id = ? AND d.is_active = false\r\n"
				+ "ORDER BY d.created_at DESC";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor(), branch_id);
	}
	
	public List<DiscountListModel> findAllForListAdmin() {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, \r\n"
				+ "d.startdate, d.enddate, d.is_active, pt.type_name AS promo_type_name, \r\n"
				+ "at.type_name AS audience_type_name, e.name AS employee_name, b.name AS branch_name FROM discounts d \r\n"
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id \r\n"
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id \r\n"
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON  d.branches_branch_id = b.branch_id WHERE d.isdeleted = 0 AND d.discount_id != 'deleted'\r\n"
				+ "ORDER BY d.created_at DESC";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor());
	}
	
	public DiscountListModel findAllForListById(String id) {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, \r\n"
				+ "d.startdate, d.enddate, d.is_active, pt.type_name AS promo_type_name, \r\n"
				+ "at.type_name AS audience_type_name, e.name AS employee_name, b.name AS branch_name FROM discounts d \r\n"
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id \r\n"
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id \r\n"
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON  d.branches_branch_id = b.branch_id WHERE d.isdeleted = 0 AND d.discount_id != 'deleted' AND d.discount_id = ? \r\n"
				+ "ORDER BY d.created_at DESC";
		List<DiscountListModel> entities = jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public int setStatus(String discount_id, boolean active) {
		return jdbcTemplate.update("UPDATE discounts SET is_active = ? WHERE discount_id = ?", active, discount_id);
	}
	
	public int softDelete(String discount_id) {
		if (!"deleted".equals(discount_id)) {
			recordDelete(discount_id);
			setDiscounts_products(discount_id);
			String sql = "UPDATE discounts SET isdeleted = 1 WHERE discount_id = ?";
			return jdbcTemplate.update(sql, discount_id);
		}
		return 0;
	}

	public int restore(String discount_id) {
		for(DeleteRecord dr : deleteRecordRepo.getByParentId(discount_id)) {
			jdbcTemplate.update("UPDATE discounts_products SET discount_id = ? WHERE discount_id = 'deleted' AND product_id = ?", dr.getParent_id(), dr.getChild_id());
		}
		deleteRecordRepo.deleteByParentId(discount_id);
		String sql = "UPDATE discounts SET isdeleted = 0 WHERE discount_id = ?";
		return jdbcTemplate.update(sql, discount_id);
	}

	public int hardDelete(String discount_id) {
		if (!"deleted".equals(discount_id)) {
			deleteRecordRepo.deleteByParentId(discount_id);
			String sql = "DELETE FROM discounts WHERE discount_id=?";
			return jdbcTemplate.update(sql, discount_id);
		}
		return 0;
	}
	
	private int setDiscounts_products(String discount_id) {
		return jdbcTemplate.update("UPDATE discounts_products SET discount_id = 'deleted' WHERE discount_id = ?", discount_id);
	}
	
	private int recordDelete(String register_id) {
		int i = 0;
		for (String childId : getChildIds(register_id)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(register_id); dr.setParent_table_name("discounts"); dr.setChild_id(childId); dr.setChild_table_name("discounts_products");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}
	
	private List<String> getChildIds(String discount_id) {
		List<String> child_ids = new ArrayList<String>();
		for(DeleteRecord dr : deleteRecordRepo.getChildIds(discount_id, "discount_id", "discounts_products", "product_id")) {
			for(String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		
		return child_ids;
	}
}
