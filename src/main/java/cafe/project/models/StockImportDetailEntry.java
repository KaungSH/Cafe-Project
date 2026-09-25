package cafe.project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockImportDetailEntry {
	private String import_detail_id;
	private String import_id;
	private String ingredient_type_id;
	private double quantity_ordered;
	private double unit_cost;
	private LocalDate expireDate;

	public StockImportDetailEntry() {
	}

	public StockImportDetailEntry(String import_detail_id, String import_id, String ingredient_type_id,
			double quantity_ordered, double unit_cost) {
		this.import_detail_id = import_detail_id;
		this.import_id = import_id;
		this.ingredient_type_id = ingredient_type_id;
		this.quantity_ordered = quantity_ordered;
		this.unit_cost = unit_cost;
	}

	public String getImport_detail_id() {
		return import_detail_id;
	}

	public void setImport_detail_id(String import_detail_id) {
		this.import_detail_id = import_detail_id;
	}

	public String getImport_id() {
		return import_id;
	}

	public void setImport_id(String import_id) {
		this.import_id = import_id;
	}

	public String getIngredient_type_id() {
		return ingredient_type_id;
	}

	public void setIngredient_type_id(String ingredient_type_id) {
		this.ingredient_type_id = ingredient_type_id;
	}

	public double getQuantity_ordered() {
		return quantity_ordered;
	}

	public void setQuantity_ordered(double quantity_ordered) {
		this.quantity_ordered = quantity_ordered;
	}

	public double getUnit_cost() {
		return unit_cost;
	}

	public void setUnit_cost(double unit_cost) {
		this.unit_cost = unit_cost;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}
}