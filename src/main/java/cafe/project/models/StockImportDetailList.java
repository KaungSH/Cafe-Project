package cafe.project.YinminThiriSoe.models;

public class StockImportDetailList {
	private String import_detail_id;
	private String import_id;
	private String ingredient_type_name;
	private String unit_abbreviation;
	private double quantity_ordered;
	private double unit_cost;
	private double line_total;

	public StockImportDetailList() {
	}

	public StockImportDetailList(String import_detail_id, String import_id, String ingredient_type_name,
			String unit_abbreviation, double quantity_ordered, double unit_cost, double line_total) {
		this.import_detail_id = import_detail_id;
		this.import_id = import_id;
		this.ingredient_type_name = ingredient_type_name;
		this.unit_abbreviation = unit_abbreviation;
		this.quantity_ordered = quantity_ordered;
		this.unit_cost = unit_cost;
		this.line_total = line_total;

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

	public String getIngredient_type_name() {
		return ingredient_type_name;
	}

	public void setIngredient_type_name(String ingredient_type_name) {
		this.ingredient_type_name = ingredient_type_name;
	}

	public String getUnit_abbreviation() {
		return unit_abbreviation;
	}

	public void setUnit_abbreviation(String unit_abbreviation) {
		this.unit_abbreviation = unit_abbreviation;
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

	public double getLine_total() {
		return line_total;
	}

	public void setLine_total(double line_total) {
		this.line_total = line_total;
	}

}
