package cafe.project.controllers;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cafe.project.models.StockImportEntryDto;
import cafe.project.services.StockImportService;

@Controller
@RequestMapping("/stock-imports")
public class StockImportController {
	private final StockImportService service;

	public StockImportController(StockImportService service) {
		this.service = service;
	}

	@GetMapping
	public String listActive(Model model) {
		model.addAttribute("imports", service.getAllActiveImports());
		return "stock-import/list";
	}

	@GetMapping("/deleted")
	public String listDeleted(Model model) {
		model.addAttribute("imports", service.getAllDeletedImports());
		return "stock-import/deleted-list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		StockImportEntryDto dto = new StockImportEntryDto();
		dto.setImport_id(service.generateNextImportId());
		dto.setImported_at(LocalDateTime.now());
		model.addAttribute("stockImport", dto);
		return "stock-import/add";
	}

	@PostMapping("/add")
	public String saveStockImport(@ModelAttribute("stockImport") StockImportEntryDto dto) {
		String mockemployee_id = "EMP-001";
		String mockbranch_id = "BR-001";

		service.addStockImport(dto, mockemployee_id, mockbranch_id);
		return "redirect:/stock-imports";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		StockImportEntryDto dto = service.getImportById(id);
		model.addAttribute("stockImport", dto);
		return "stock-import/edit";
	}

	@PostMapping("/edit")
	public String updateStockImport(@ModelAttribute("stockImport") StockImportEntryDto dto) {
		service.editStockImport(dto);
		return "redirect:/stock-imports";
	}

	@GetMapping("/soft-delete/{id}")
	public String softDelete(@PathVariable("id") String id) {
		service.softDelete(id);
		return "redirect:/stock-imports";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id) {
		service.recover(id);
		return "redirect:/stock-imports/deleted";
	}

	@GetMapping("/hard-delete/{id}")
	public String showHardDeleteConfirmation(@PathVariable("id") String id, Model model) {
		model.addAttribute("import_id", id);
		return "stock-import/hard-deleted";
	}

	@PostMapping("/hard-delete")
	public String hardDelete(@RequestParam("import_id") String import_id) {
		service.hardDelete(import_id);
		return "redirect:/stock-imports/deleted";
	}

	@GetMapping("/detail-batches/{id}")
	@ResponseBody
	public Object getBatchesDetail(@PathVariable("id") String id) {
		return service.getBatchesByImportId(id);
	}
}