package cafe.project.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repository.entities.Supplier;
import cafe.project.repository.mapper.SupplierMapper;

@Repository
public class SupplierRepository {

	private final JdbcTemplate jdbcTemplate;

	public SupplierRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Supplier> findAll() {
		String sql = "SELECT * FROM suppliers WHERE isdeleted=0";
		List<Supplier> entities = this.jdbcTemplate.query(sql, new SupplierMapper());
		return entities;
	}

	public Supplier findById(String supplier_id) {
		String sql = "SELECT * FROM suppliers WHERE supplier_id=? AND isdeleted=0";
		List<Supplier> entities = this.jdbcTemplate.query(sql, new SupplierMapper(), supplier_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int save(Supplier entity) {
		String sql = "INSERT INTO suppliers (supplier_id,name,contact_info,isdeleted,created_at) VALUES (?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, entity.getSupplier_id(), entity.getName(), entity.getContact_info(),
				entity.isIsdeleted(), entity.getCreated_at());

	}

	public int edit(String supplier_id, Supplier entity) {
		String sql = "UPDATE suppliers SET name=?, contact_info=?, isdeleted=?, created_at=? WHERE supplier_id=?";
		return this.jdbcTemplate.update(sql, entity.getName(), entity.getContact_info(), entity.isIsdeleted(),
				entity.getCreated_at(), supplier_id);

	}

	public int delete(String supplier_id) {
		String sql = "UPDATE suppliers SET isdeleted=1 WHERE supplier_id=?";
		return this.jdbcTemplate.update(sql, supplier_id);
	}

	public List<Supplier> findDeleted() {
		String sql = "SELECT * FROM suppliers WHERE isdeleted=TRUE";
		return jdbcTemplate.query(sql, new SupplierMapper());
	}

	public int restore(String supplier_id) {
		String sql = "UPDATE suppliers SET isdeleted = 0 WHERE supplier_id = ?";
		return jdbcTemplate.update(sql, supplier_id);
	}

	public int realDelete(String supplier_id) {
		String sql = "DELETE FROM suppliers WHERE supplier_id=?";
		return this.jdbcTemplate.update(sql, supplier_id);
	}
}
