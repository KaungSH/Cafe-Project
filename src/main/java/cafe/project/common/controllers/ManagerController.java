package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.repositories.AtypeAndPtypeRepository;
import cafe.project.services.ExpenseCategoryService;
import cafe.project.services.IngredientTypeService;
import cafe.project.services.PayMethodService;
import cafe.project.services.SizeService;
import cafe.project.services.SupplierService;
import cafe.project.services.UnitService;

@Controller
@RequestMapping("/manager-only")
public class ManagerController {
	
	private AtypeAndPtypeRepository typeRepo;
	private final ExpenseCategoryService expenseCategoryService;
	private final SizeService sizeService;
	private final PayMethodService payMethodService;
	private final UnitService unitService;
	private final IngredientTypeService ingredientTypeService;
	private final SupplierService supplierService;
	
	public ManagerController(AtypeAndPtypeRepository typeRepo, ExpenseCategoryService expenseCategoryService, SizeService sizeService, PayMethodService payMethodService, UnitService unitService, IngredientTypeService ingredientTypeService, SupplierService supplierService) {
		this.typeRepo = typeRepo;
		this.expenseCategoryService = expenseCategoryService;
		this.sizeService = sizeService;
		this.payMethodService = payMethodService;
		this.unitService = unitService;
		this.ingredientTypeService = ingredientTypeService;
		this.supplierService = supplierService;
	}
	
	@GetMapping("/types/audience")
	public String getAudienceTypes(Model model) {
		model.addAttribute("types", typeRepo.findTypesAll(false));
		model.addAttribute("name", "audience");
		model.addAttribute("dname", "Audience");
		return "types/list-manager";
	}
	
	@GetMapping("/types/promo")
	public String getPromoTypes(Model model) {
		model.addAttribute("types", typeRepo.findTypesAll(true));
		model.addAttribute("name", "promo");
		model.addAttribute("dname", "Promo");
		return "types/list-manager";
	}
	
	@GetMapping("/expensesCategories")
	public String ExpenseCategoryList(Model model) {
		model.addAttribute("expense", this.expenseCategoryService.findAll());
		return "expensecategory/list-manager";
	}
	
	@GetMapping("/sizes")
	public String listSize(Model model) {
		model.addAttribute("sizes", sizeService.getAllSizes());
		return "sizes/list-manager";
	}
	
	@GetMapping("/paymethod")
	public String listPayMethod(Model model) {
		model.addAttribute("payMethods", payMethodService.getAllPayMethodsWithRelations());
		return "paymethod/list-manager";
	}
	
	@GetMapping("/units")
	public String listUnits(Model model) {
		model.addAttribute("units", this.unitService.findAll());
		return "units/list-manager";
	}
	
	@GetMapping("/ingredient_types")
    public String list(Model model) {
        model.addAttribute("ingredientTypes", ingredientTypeService.getAllActiveIngredientTypes());
        return "ingredient-type/list-manager";
    }
	
	@GetMapping("/suppliers")
	public String supplierList(Model model) {
		model.addAttribute("suppliers", this.supplierService.findAll());
		return "supplier/list-manager";
	}

}
