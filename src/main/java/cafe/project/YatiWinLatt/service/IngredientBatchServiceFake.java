package cafe.project.YatiWinLatt.service;



	import cafe.project.YatiWinLatt.models.IngredientBatchDto;
	import org.springframework.stereotype.Service;

	import java.math.BigDecimal;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.List;

	@Service
	public class IngredientBatchServiceFake {

	  
	    public List<IngredientBatchDto> getAllBatches() {
	        List<IngredientBatchDto> list = new ArrayList<>();

	        list.add(new IngredientBatchDto(
	            "BATCH-001", 
	            new BigDecimal("50.000"), 
	            LocalDate.of(2026, 1, 10), 
	            LocalDate.of(2026, 12, 31), 
	            "BR-001", 
	            "ING-001", 
	            new BigDecimal("12000.00")
	        ));

	        list.add(new IngredientBatchDto(
	            "BATCH-002", 
	            new BigDecimal("20.500"), 
	            LocalDate.of(2026, 2, 15), 
	            LocalDate.of(2026, 8, 20), 
	            "BR-001", 
	            "ING-002", 
	            new BigDecimal("45000.00")
	        ));

	        list.add(new IngredientBatchDto(
	            "BATCH-003", 
	            new BigDecimal("100.000"), 
	            LocalDate.of(2026, 3, 1), 
	            LocalDate.of(2027, 3, 1), 
	            "BR-002", 
	            "ING-003", 
	            new BigDecimal("8500.00")
	        ));

	        return list;
	    }
	}

