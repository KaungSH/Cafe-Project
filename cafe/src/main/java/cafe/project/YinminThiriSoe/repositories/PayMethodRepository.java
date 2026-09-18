package cafe.project.YinminThiriSoe.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YinminThiriSoe.repositories.entities.PayMethod;
import cafe.project.YinminThiriSoe.repositories.mappers.PayMethodMapper;

@Repository
public class PayMethodRepository {

	private final JdbcTemplate jdbcTemplate;

	public PayMethodRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<PayMethod> findAll() {
		String sql = "SELECT * FROM pay_methods WHERE isdeleted = 0";
		return jdbcTemplate.query(sql, new PayMethodMapper());
	}

	public List<PayMethod> findAllActive() {
		String sql = "SELECT * FROM pay_methods WHERE isdeleted = 0 AND is_active = 1";
		return jdbcTemplate.query(sql, new PayMethodMapper());
	}

//	public List<PayMethod> findAllWithRelations() {
//		String sql=
//		return null;
//	}

//	public PayMethod findById(String methodId) {
//		String sql = "SELECT * FROM pay_methods WHERE method_id = ? AND isdeleted = 0";
//		List<PayMethod> payMethods = jdbcTemplate.query(sql, new PayMethodMapper(), methodId);
//		return 
//	}

}
