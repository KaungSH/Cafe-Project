package cafe.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cafe.project.models.StockImportDetailEntry;
import cafe.project.models.StockImportDetailList;
import cafe.project.repositories.IngredientTypeRepository;
import cafe.project.repositories.StockImportDetailRepository;
import cafe.project.repositories.UnitRepository;
import cafe.project.repositories.entities.IngredientType;
import cafe.project.repositories.entities.StockImportDetail;
import cafe.project.repositories.entities.Unit;

@Service
public class StockImportDetailService {
	private final StockImportDetailRepository sdr;
	private final IngredientTypeRepository itr; // Ingredient Name
	private final UnitRepository ur;

	public StockImportDetailService(StockImportDetailRepository sdr, IngredientTypeRepository itr, UnitRepository ur) {
		this.sdr = sdr;
		this.itr = itr;
		this.ur = ur;
	}

	public List<StockImportDetailList> findDetailsByImportId(String importId) {
		List<StockImportDetailList> list = new ArrayList<>();
		for (StockImportDetail item : sdr.findByImportId(importId)) {
			// 1.Fetch Ingredient name
			IngredientType ingredient = itr.findById(item.getIngredientTypeId());
			String ingredientName = (ingredient != null) ? ingredient.getName() : "Unknown";
			// 2. Fetch Unit Abbreviation manually using the UnitRepository
			String unitAbbreviation = "";
			if (ingredient != null && ingredient.getUnitId() != null) {
				try {
					Unit unit = ur.findById(ingredient.getUnitId());
					unitAbbreviation = (unit != null) ? unit.getAbbreviation() : "Unknown";
				} catch (Exception e) {
					unitAbbreviation = "Unknown";
				}
			}
			list.add(toListModel(item, ingredientName, unitAbbreviation));
		}

		return list;
	}

	public StockImportDetailEntry findById(String id) {
		return toEntryModel(sdr.findById(id));
	}

	public int add(StockImportDetailEntry se) {
		se.setImport_detail_id(UUID.randomUUID().toString()); // Generate unique ID
		return sdr.add(toEntity(se));
	}

	public int edit(StockImportDetailEntry se) {
		return sdr.edit(toEntity(se));
	}

	public int delete(String id) {
		return sdr.delete(id);
	}

	private StockImportDetailList toListModel(StockImportDetail sd, String ingredient_name, String unit_abbreviation) {
		return new StockImportDetailList(sd.getImport_detail_id(), sd.getImport_id(), ingredient_name,
				unit_abbreviation, sd.getQuantity_ordered(), sd.getUnit_cost(), sd.getLine_total());
	}

	private StockImportDetailEntry toEntryModel(StockImportDetail sd) {
		StockImportDetailEntry model = new StockImportDetailEntry();
		model.setImport_detail_id(sd.getImport_detail_id());
		model.setImport_id(sd.getImport_id());
		model.setIngredient_type_id(sd.getIngredient_type_id());
		model.setQuantity_ordered(sd.getQuantity_ordered());
		model.setUnit_cost(sd.getUnit_cost());
		return model;
	}

	private StockImportDetail toEntity(StockImportDetailEntry se) {
		// Fixed: subTotal is now Quantity * Unit Cost
		double subTotal = se.getQuantity_ordered() * se.getUnit_cost();
		return new StockImportDetail(se.getImport_detail_id(), se.getImport_id(), se.getIngredient_type_id(),
				se.getQuantity_ordered(), se.getUnit_cost(), subTotal, false);
	}
}