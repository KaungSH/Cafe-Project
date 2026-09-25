package cafe.project.repositories.entities;

public class AtypeAndPtype {
	
	private String audience_type_id, promo_type_id, audience_type_name, promo_type_name;
	
	public AtypeAndPtype() {}
	
	public AtypeAndPtype(String id, String name, boolean isPromo) {
		if(isPromo) {
			this.promo_type_id = id;
			this.promo_type_name = name;
		} else {
			this.audience_type_id = id;
			this.audience_type_name = name;
		}
		
	}

	public String getAudience_type_id() {
		return audience_type_id;
	}

	public void setAudience_type_id(String audience_type_id) {
		this.audience_type_id = audience_type_id;
	}

	public String getPromo_type_id() {
		return promo_type_id;
	}

	public void setPromo_type_id(String promo_type_id) {
		this.promo_type_id = promo_type_id;
	}

	public String getAudience_type_name() {
		return audience_type_name;
	}

	public void setAudience_type_name(String audience_type_name) {
		this.audience_type_name = audience_type_name;
	}

	public String getPromo_type_name() {
		return promo_type_name;
	}

	public void setPromo_type_name(String promo_type_name) {
		this.promo_type_name = promo_type_name;
	}
	
	

}
