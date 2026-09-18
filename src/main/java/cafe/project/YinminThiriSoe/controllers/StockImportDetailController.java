package cafe.project.YinminThiriSoe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.NayZarLinn.services.IngredientTypeService;
import cafe.project.YinminThiriSoe.models.StockImportDetailEntry;
import cafe.project.YinminThiriSoe.services.StockImportDetailService;

@Controller
@RequestMapping({ "/manager/stock_import_details", "/manager/stock_import/details" })
public class StockImportDetailController {

	private final StockImportDetailService detailService;
	private final IngredientTypeService ingredientTypeService;

	public StockImportDetailController(StockImportDetailService detailService,
			IngredientTypeService ingredientTypeService) {
		this.detailService = detailService;
		this.ingredientTypeService = ingredientTypeService;
	}

	@GetMapping({ "", "/" })
	public String redirectToList() {
		return "redirect:/manager/stock_imports";
	}

	@GetMapping("/{importId}")
	public String listDetails(@PathVariable("importId") String importId, Model model) {
		model.addAttribute("details", detailService.findDetailsByImportId(importId));
		model.addAttribute("importId", importId);
		return "YinminThiriSoe/manager/stock_import_details/list";
	}

	@GetMapping("/add/{importId}")
	public String showAddForm(@PathVariable("importId") String importId, Model model) {
		StockImportDetailEntry form = new StockImportDetailEntry();
		form.setImport_id(importId);

		model.addAttribute("detailForm", form);
		model.addAttribute("ingredients", ingredientTypeService.getAllActive());
		return "YinminThiriSoe/manager/stock_import_details/add";
	}

	@PostMapping("/add")
	public String addDetail(@ModelAttribute("detailForm") StockImportDetailEntry form) {
		detailService.add(form);
		//StockImportDetailEntry.setCreatedAt(LocalDateTime.now());
		return "redirect:/manager/stock_import_details/" + form.getImport_id();
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		StockImportDetailEntry form = detailService.findById(id);

		model.addAttribute("detailForm", form);
		model.addAttribute("ingredients", ingredientTypeService.getAllActive());
		// ✅ FIXED: "stock_import_detail/edit" → "stock_import_details/edit"
		return "YinminThiriSoe/manager/stock_import_details/edit";
	}

	@PostMapping("/edit")
	public String editDetail(@ModelAttribute("detailForm") StockImportDetailEntry form) {
		detailService.edit(form);
		return "redirect:/manager/stock_import_details/" + form.getImport_id();
	}

	@GetMapping("/delete/{id}/{importId}")
	public String deleteDetail(@PathVariable("id") String id, @PathVariable("importId") String importId) {
		detailService.delete(id);
		return "redirect:/manager/stock_import_details/" + importId;
	}
}