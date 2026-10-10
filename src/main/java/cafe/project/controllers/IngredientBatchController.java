package cafe.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.IngredientBatchEntryDto;
import cafe.project.models.StockImportEntryDto;
import cafe.project.services.IngredientBatchService;
import cafe.project.services.StockImportService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ingredient-batches")
public class IngredientBatchController {
	private final IngredientBatchService service;
	private final StockImportService stockImportService;

	public IngredientBatchController(IngredientBatchService service, StockImportService stockImportService) {
		this.service = service;
		this.stockImportService = stockImportService;
	}

	@GetMapping("")
	public String listBatches(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("batches", service.getAllBatches());
		return "ingredient-batch/list";
	}

	@GetMapping("/add")
	public String showAddBatchForm(@RequestParam("import_id") String import_id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		IngredientBatchEntryDto dto = new IngredientBatchEntryDto();
		dto.setImport_id(import_id);
		dto.setItems(stockImportService.getAllIngredientTypes());

		model.addAttribute("batchEntry", dto);
		return "ingredient-batch/add";
	}

	@PostMapping("/add")
	public String saveIngredientBatches(@ModelAttribute("batchEntry") IngredientBatchEntryDto dto,
			HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		stockImportService.addIngredientBatchesToImport(dto, ldto.getBranch_id());
		return "redirect:/stock-imports";
	}

	@GetMapping("/edit/{import_id}")
	public String editIngredientBatches(@PathVariable("import_id") String import_id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = stockImportService.getImportById(import_id);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		model.addAttribute("stockImport", dto);
		model.addAttribute("suppliers", stockImportService.getAllSuppliers());
		return "stock-import/edit";
	}

	@GetMapping("/delete/{id}")
	public String softDelete(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		var batch = service.getBatchById(id);
		if (batch != null && !batch.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.softDeleteBatch(id);
		return "redirect:/ingredient-batches";
	}

	@GetMapping("/deleted-list")
	public String showDeletedList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("deletedBatches", service.getDeletedBatches());
		return "ingredient-batch/deleted-list";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		var batch = service.getBatchById(id);
		if (batch != null && !batch.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.recoverBatch(id);
		return "redirect:/ingredient-batches/deleted-list";
	}

	@GetMapping("/hard-delete/{id}")
	public String hardDelete(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		var batch = service.getBatchById(id);
		if (batch != null && !batch.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.hardDeleteBatch(id);
		return "redirect:/ingredient-batches/deleted-list";
	}
}