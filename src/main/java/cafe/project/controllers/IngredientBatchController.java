package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cafe.project.models.IngredientBatchEntryDto;
import cafe.project.models.StockImportEntryDto;
import cafe.project.services.IngredientBatchService;
import cafe.project.services.StockImportService;

@Controller
@RequestMapping("/ingredient-batches")
public class IngredientBatchController {
	private final IngredientBatchService service;
	private final StockImportService stockImportService;

	public IngredientBatchController(IngredientBatchService service, StockImportService stockImportService) {
		this.service = service;
		this.stockImportService = stockImportService;
	}

	@GetMapping("/all")
	public String listBatches(Model model) {
		model.addAttribute("batches", service.getAllBatches());
		return "ingredient-batch/list";
	}

	@GetMapping("/add")
	public String showAddBatchForm(@RequestParam("import_id") String import_id, Model model) {
		IngredientBatchEntryDto dto = new IngredientBatchEntryDto();
		dto.setImport_id(import_id);
		dto.setItems(stockImportService.getAllIngredientTypes());

		model.addAttribute("batchEntry", dto);
		return "ingredient-batch/add";
	}

	@PostMapping("/add")
	public String saveIngredientBatches(@ModelAttribute("batchEntry") IngredientBatchEntryDto dto) {
		String mockBranchId = "BR-001"; // Session/User Context မှ ယူနိုင်သည်
		stockImportService.addIngredientBatchesToImport(dto, mockBranchId);
		return "redirect:/stock-imports";
	}

	@GetMapping("/edit/{import_id}")
	public String editIngredientBatches(@PathVariable("import_id") String import_id, Model model) {
		StockImportEntryDto dto = stockImportService.getImportById(import_id);
		model.addAttribute("stockImport", dto);
		model.addAttribute("suppliers", stockImportService.getAllSuppliers());
		return "stock-import/edit";
	}

	@GetMapping("/delete/{id}")
	public String softDelete(@PathVariable("id") String id) {
		service.softDeleteBatch(id);
		return "redirect:/ingredient-batches";
	}

	@GetMapping("/deleted-list")
	public String showDeletedList(Model model) {
		model.addAttribute("deletedBatches", service.getDeletedBatches());
		return "ingredient-batch/deleted-list";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id) {
		service.recoverBatch(id);
		return "redirect:/ingredient-batches/deleted-list";
	}

	@GetMapping("/hard-delete/{id}")
	public String hardDelete(@PathVariable("id") String id) {
		service.hardDeleteBatch(id);
		return "redirect:/ingredient-batches/deleted-list";
	}
}