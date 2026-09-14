package cafe.project.NayZarLinn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.NayZarLinn.models.IngredientTypeEntryDto;
import cafe.project.NayZarLinn.models.IngredientTypeListDto;
import cafe.project.NayZarLinn.models.SupplierDto;
import cafe.project.NayZarLinn.services.IngredientTypeService;

@Controller
public class IngredientTypeController {
	private final IngredientTypeService ingredientTypeService;

	public IngredientTypeController(IngredientTypeService ingredientTypeService) {
		this.ingredientTypeService = ingredientTypeService;
	}

	@GetMapping("/ingredientType")
	public String ingredientTyoeList(Model model) {
		model.addAttribute("ingredientType", this.ingredientTypeService.findAll());
		return "NayZarLinn/ingredient_type/list";
	}

	@GetMapping("/ingredientType/add")
	public String addIngredientType(Model model) {
		model.addAttribute("ingredientType", new IngredientTypeListDto());
		return "NayZarLinn/ingredient_type/add";
	}

	@PostMapping("/ingredientType/add")
	public String addIngredientType(@ModelAttribute("ingredientType") IngredientTypeEntryDto ingredientType) {
		this.ingredientTypeService.add(ingredientType);
		return "redirect:/ingredientType";
	}

	@GetMapping("/ingredientType/edit/{ingredient_type_id}")
	public String editIngredientType(@PathVariable String ingredient_type_id, Model model) {
		IngredientTypeEntryDto existingIngredientType = this.ingredientTypeService.findById(ingredient_type_id);
		if (existingIngredientType != null) {
			model.addAttribute("ingredientType", existingIngredientType);
			return "NayZarLinn/ingredient_type/edit";
		}
		return "";
	}

	@PostMapping("/ingredientType/edit")
	public String editIngredientType(@ModelAttribute("ingredientType") IngredientTypeEntryDto ingredientType, Model model) {
		this.ingredientTypeService.edit(ingredientType);
		return "redirect:/NayZarLinn/ingredient_type";
	}

	@GetMapping("/ingredientType/delete/{ingredient_type_id}")
	public String deleteIngredientType(@PathVariable String ingredient_type_id, Model model) {
		IngredientTypeEntryDto existingIngredientType = this.ingredientTypeService.findById(ingredient_type_id);
		if (existingIngredientType != null) {
			model.addAttribute("ingredientType", existingIngredientType);
			return "NayZarLinn/ingredient_type/delete";
		}
		return "";
	}

	@PostMapping("/ingredientType/delete")
	public String deletedIngredientType(@ModelAttribute("ingredientType") IngredientTypeListDto ingredientType) {
		this.ingredientTypeService.delete(ingredientType.getIngredient_type_id());
		return "redirect:/ingredient_type";
	}

	@GetMapping("/ingredientType/deleted")
	public String deletedIngredientType(Model model) {
		model.addAttribute("supplier", ingredientTypeService.findDeleted());
		return "NayZarLinn/supplieringredient_type/deleted";
	}

	@PostMapping("/ingredientType/restore")
	public String restoreIngredientType(@RequestParam String ingredient_type_id) {
		ingredientTypeService.restore(ingredient_type_id);
		return "redirect:/ingredient_type/deleted";
	}

	@PostMapping("/ingredientType/real-delete")
	public String realDeleteIngredientType(@ModelAttribute("ingredientType") IngredientTypeListDto ingredientType) {
		ingredientTypeService.realDelete(ingredientType.getIngredient_type_id());
		return "redirect:/ingredient_type/deleted";
	}
}