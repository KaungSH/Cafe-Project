package cafe.project.YinminThiriSoe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.repositories.IngredientTypeRepository;
import cafe.project.YinminThiriSoe.models.StockImportDetailList;
import cafe.project.YinminThiriSoe.repositories.StockImportDetailRepository;

@Service
public class StockImportDetailService {
	private final StockImportDetailRepository sdr;
	private final IngredientTypeRepository itr; // Ingredient Name လှမ်းယူရန်

	public StockImportDetailService(StockImportDetailRepository sdr, IngredientTypeRepository itr) {
		this.sdr = sdr;
		this.itr = itr;
	}
//
//	public List<StockImportDetailList> findDetailsByImportId(String importId) {
//		List<StockImportDetailList> list = new ArrayList<>();
//	}
}