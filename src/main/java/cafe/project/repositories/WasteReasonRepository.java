package cafe.project.YatiWinLatt.repositories;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import cafe.project.YatiWinLatt.repositories.entities.WasteReason;
import cafe.project.YatiWinLatt.repositories.mappers.WasteReasonMapper;

	@Repository
	public class WasteReasonRepository {

		private final JdbcTemplate jdbcTemplate;

		public WasteReasonRepository(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		public List<WasteReason> getAllWasteReasons() {
			String sql = "SELECT * FROM waste_reasons";
			return this.jdbcTemplate.query(sql, new WasteReasonMapper());
		}
}
