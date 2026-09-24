package cafe.project.controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.services.BranchService;
import cafe.project.services.EmployeeService;
import cafe.project.services.IngredientTypeService;
import cafe.project.services.StockImportService;
import cafe.project.services.SupplierService;
import cafe.project.models.StockImportEntryModel;

@Controller
@RequestMapping("/manager/stock_imports")
public class StockImportController {

	private final StockImportService stockImportService;
	private final SupplierService supplierService;
	private final EmployeeService employeeService;
	private final BranchService branchService;
	private final IngredientTypeService ingredientTypeService;

	public StockImportController(StockImportService stockImportService, SupplierService supplierService,
			EmployeeService employeeService, BranchService branchService,IngredientTypeService ingredientTypeService) {
		this.stockImportService = stockImportService;
		this.supplierService = supplierService;
		this.employeeService = employeeService;
		this.branchService = branchService;
		this.ingredientTypeService=ingredientTypeService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("imports", stockImportService.findAll());
		return "stock_imports/list";
	}

	@GetMapping("/deleted")
	public String deletedList(Model model) {
		model.addAttribute("imports", stockImportService.findDeletedAll());
		return "stock_imports/delete";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("stockImport", new StockImportEntryModel());
		model.addAttribute("suppliers", supplierService.findAll());
		model.addAttribute("employees", employeeService.getAllEmployeeListDto());
		model.addAttribute("branches", branchService.findAll());
		model.addAttribute("ingredients", ingredientTypeService.getAllActive());
		return "stock_imports/form";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute("stockImport") StockImportEntryModel model) {
		if (model.getImport_id() == null || model.getImport_id().isEmpty()) {
			model.setImport_id(UUID.randomUUID().toString());
		}
		if (model.getImported_at() == null) {
			model.setImported_at(LocalDateTime.now());
		}

		System.out.println("=== ADD STOCK IMPORT DEBUG ===");
		if (model.getDetails() != null) {
			System.out.println("Total Details Count: " + model.getDetails().size());
			for (var detail : model.getDetails()) {
				System.out.println(" - Ingredient Type ID: " + detail.getIngredient_type_id() + ", Quantity: "
						+ detail.getQuantity_ordered() + ", Expiry: " + detail.getExpireDate());
			}
		} else {
			System.out.println(" - Details list is NULL!");
		}
		stockImportService.add(model);
		return "redirect:/manager/stock_imports";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		StockImportEntryModel existing = stockImportService.findById(id);
		if (existing == null) {
			return "redirect:/manager/stock_imports";
		}
		model.addAttribute("stockImport", existing);
		model.addAttribute("suppliers", supplierService.findAll());
		model.addAttribute("employees", employeeService.getAllEmployeeListDto());
		model.addAttribute("branches", branchService.findAll());
		return "stock_imports/form";
	}

	@PostMapping("/edit")
	public String edit(@ModelAttribute("stockImport") StockImportEntryModel entryModel) {
		System.out.println("=== EDIT STOCK IMPORT DEBUG ===");
		if (entryModel.getDetails() != null) {
			System.out.println("Editing Details Count: " + entryModel.getDetails().size());
		}
		stockImportService.edit(entryModel);
		return "redirect:/manager/stock_imports";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		stockImportService.delete(id);
		return "redirect:/manager/stock_imports";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id) {
		stockImportService.recover(id);
		return "redirect:/manager/stock_imports/deleted";
	}

}