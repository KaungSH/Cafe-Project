package cafe.project.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Discount {
	    private String discount_id;
	    private String employee_id;
	    private String name;
	    private String description;
	    private BigDecimal discount_value;
	    private LocalDate startdate;
	    private LocalDate enddate;
	    private Boolean isedited;
	    private Boolean is_active;
	    private Boolean isdeleted;
	    private LocalDateTime created_at;
	    private String promo_type_id;
	    private String audience_type_id;

	    public Discount() {}
	    public Discount(String discount_id,String employee_id, String name,String description,
	    		 BigDecimal discount_value,LocalDate startdate,LocalDate enddate,Boolean isedited,
	    		 Boolean is_active,Boolean isdeleted,LocalDateTime created_at,String promo_type_id,
	    		 String audience_type_id) {
	    	this.discount_id=discount_id;
	    	this.employee_id=employee_id;
	    	this.name=name;
	    	this.description=description;
	    	this.discount_value=discount_value;
	    	this.startdate=startdate;
	    	this.enddate=enddate;
	    	this.isedited=isedited;
	    	this.is_active=is_active;
	    	this.isdeleted=isdeleted;
	    	this.created_at=created_at;
	    	this.promo_type_id=promo_type_id;
	    	this.audience_type_id=audience_type_id;
	    	}
		public String getDiscount_id() {
			return discount_id;
		}
		public String getEmployee_id() {
			return employee_id;
		}
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public BigDecimal getDiscount_value() {
			return discount_value;
		}
		public LocalDate getStartdate() {
			return startdate;
		}
		public LocalDate getEnddate() {
			return enddate;
		}
		public Boolean getIsedited() {
			return isedited;
		}
		public Boolean getIs_active() {
			return is_active;
		}
		public Boolean getIsdeleted() {
			return isdeleted;
		}
		public LocalDateTime getCreated_at() {
			return created_at;
		}
		public String getPromo_type_id() {
			return promo_type_id;
		}
		public String getAudience_type_id() {
			return audience_type_id;
		}
		public void setDiscount_id(String discount_id) {
			this.discount_id = discount_id;
		}
		public void setEmployee_id(String employee_id) {
			this.employee_id = employee_id;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public void setDiscount_value(BigDecimal discount_value) {
			this.discount_value = discount_value;
		}
		public void setStartdate(LocalDate startdate) {
			this.startdate = startdate;
		}
		public void setEnddate(LocalDate enddate) {
			this.enddate = enddate;
		}
		public void setIsedited(Boolean isedited) {
			this.isedited = isedited;
		}
		public void setIs_active(Boolean is_active) {
			this.is_active = is_active;
		}
		public void setIsdeleted(Boolean isdeleted) {
			this.isdeleted = isdeleted;
		}
		public void setCreated_at(LocalDateTime created_at) {
			this.created_at = created_at;
		}
		public void setPromo_type_id(String promo_type_id) {
			this.promo_type_id = promo_type_id;
		}
		public void setAudience_type_id(String audience_type_id) {
			this.audience_type_id = audience_type_id;
		}
	    
	    
}
	
	
	
	
