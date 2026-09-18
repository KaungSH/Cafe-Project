package cafe.project.KaungSattHein.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.KaungSattHein.repositories.entities.AtypeAndPtype;
import cafe.project.KaungSattHein.repositories.mappers.AudienceTypeMapper;
import cafe.project.KaungSattHein.repositories.mappers.PromoTypeMapper;

@Repository
public class AtypeAndPtypeRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	AtypeAndPtypeRepository(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<AtypeAndPtype> findTypesAll(boolean isPromo) {
		if(!isPromo) {
			return jdbcTemplate.query("SELECT * FROM audience_types", new AudienceTypeMapper());
		} else {
			return jdbcTemplate.query("SELECT * FROM promo_types", new PromoTypeMapper());
		}
		
	}
	
	public AtypeAndPtype findTypeById(String id, boolean isPromo) {
		if(isPromo) {
			return jdbcTemplate.queryForObject("SELECT * FROM promo_types WHERE promo_type_id = ?", new PromoTypeMapper(), id);
		} else {
			return jdbcTemplate.queryForObject("SELECT * FROM audience_types WHERE audience_type_id = ?", new AudienceTypeMapper(), id);
		}
	}
	
	public int addType(String type_name, boolean isPromo) {
		if(isPromo) {
			return jdbcTemplate.update("INSERT INTO promo_types(promo_type_id, type_name) VALUES(?, ?)", UUID.randomUUID().toString(), type_name);
		} else {
			return jdbcTemplate.update("INSERT INTO audience_types(audience_type_id, type_name) VALUES(?, ?)", UUID.randomUUID().toString(), type_name);
		}
	}
	
	public int editType(String id, String type_name, boolean isPromo) {
		if(isPromo) {
			return jdbcTemplate.update("UPDATE promo_types SET type_name = ? WHERE promo_type_id = ?", type_name, id);
		} else {
			return jdbcTemplate.update("UPDATE audience_types SET type_name = ? WHERE audience_type_id = ?", type_name, id);
		}
	}
	
	public int deleteType(String id, boolean isPromo) {
		setDiscounts(id, isPromo);
		if(isPromo) {
			return jdbcTemplate.update("DELETE FROM promo_types WHERE promo_type_id = ?", id);
		} else {
			return jdbcTemplate.update("DELETE FROM audience_types WHERE audience_type_id = ?", id);
		}
	}
	
	private int setDiscounts(String id, boolean isPromo) {
		if(isPromo) {
			return jdbcTemplate.update("UPDATE discounts SET promo_type_id = 'deleted' WHERE promo_type_id = ?", id);
		} else {
			return jdbcTemplate.update("UPDATE discounts SET audience_type_id = 'deleted' WHERE audience_type_id = ?", id);
		}
	}

}
