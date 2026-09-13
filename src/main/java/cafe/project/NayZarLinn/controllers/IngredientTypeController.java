package cafe.project.NayZarLinn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.NayZarLinn.models.IngredientTypeDto;
import cafe.project.NayZarLinn.services.IngredientTypeService;

@Controller
public class IngredientTypeController {
	private final IngredientTypeService ingredientTypeService;

	public IngredientTypeController(IngredientTypeService ingredientTypeService) {
		this.ingredientTypeService = ingredientTypeService;
	}

	@GetMapping("/ingredientType")
	public String supplierList(Model model) {
		model.addAttribute("supplier", this.ingredientTypeService.findAll());
		return "NayZarLinn/ingredient_type/list";
	}

	@GetMapping("/ingredientType/add")
	public String addIngredientType(Model model) {
		model.addAttribute("ingredientType", new IngredientTypeDto());
		return "NayZarLinn/ingredient_type/add";
	}

	@PostMapping("/ingredientType/add")
	public String addIngredientType(@ModelAttribute("ingredientType") IngredientTypeDto ingredientType) {
		this.ingredientTypeService.add(ingredientType);
		return "redirect:/ingredient_type";
	}

	@GetMapping("/ingredientType/edit/{ingredient_type_id}")
	public String editIngredientType(@PathVariable String ingredient_type_id, Model model) {
		IngredientTypeDto existingIngredientType = this.ingredientTypeService.findById(ingredient_type_id);
		if (existingIngredientType != null) {
			model.addAttribute("ingredientType", existingIngredientType);
			return "NayZarLinn/ingredient_type/edit";
		}
		return "redirect:/notfound";
	}

	@PostMapping("/ingredientType/edit")
	public String editIngredientType(@ModelAttribute("ingredientType") IngredientTypeDto ingredientType,
			Model model) {
		this.ingredientTypeService.edit(ingredientType.getIngredient_type_id(), ingredientType);
		return "redirect:/ingredient_type";
	}

	@GetMapping("/ingredientType/delete/{ingredient_type_id}")
	public String deleteIngredientType(@PathVariable String ingredient_type_id, Model model) {
		IngredientTypeDto existingIngredientType = this.ingredientTypeService.findById(ingredient_type_id);
		if (existingIngredientType != null) {
			model.addAttribute("ingredientType", existingIngredientType);
			return "NayZarLinn/ingredient_type/delete";
		}
		return "redirect:/notfound";
	}

	@PostMapping("/ingredientType/delete")
	public String deletedIngredientType(@ModelAttribute("ingredientType") IngredientTypeDto ingredientType) {
		this.ingredientTypeService.delete(ingredientType.getIngredient_type_id());
		return "redirect:/ingredient_type";
	}

}
