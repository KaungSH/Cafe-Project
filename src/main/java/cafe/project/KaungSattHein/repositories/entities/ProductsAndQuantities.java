package cafe.project.KaungSattHein.repositories.entities;

import java.util.List;

public class ProductsAndQuantities {
	
	private List<String> product_ids;
	private List<Integer> quantities;
	
	public ProductsAndQuantities() {}
	
	public ProductsAndQuantities(List<String> product_ids, List<Integer> quantities) {
		this.product_ids = product_ids;
		this.quantities = quantities;
	}

	public List<String> getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(List<String> product_ids) {
		this.product_ids = product_ids;
	}

	public List<Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}
	
	

}
