package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.repositories.UnitRepository;
import cafe.project.models.IngredientTypeDto;
import cafe.project.repositories.entities.IngredientType;
import cafe.project.services.IngredientTypeService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/staff/ingredientType")
public class IngredientTypeController {

	private final IngredientTypeService service;
	private final UnitRepository unitRepository;

	public IngredientTypeController(IngredientTypeService service, UnitRepository unitRepository) {
		this.service = service;
		this.unitRepository = unitRepository;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("ingredients", service.getAllActive());
		return "NayZarLinn/ingredient_type/list";
	}

	@GetMapping("/deleted")
	public String deletedList(Model model) {
		model.addAttribute("deletedIngredients", service.getAllDeleted());
		return "NayZarLinn/ingredient_type/deletedlist";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("ingredientForm", new IngredientTypeDto());
		model.addAttribute("units", unitRepository.findAll());
		return "NayZarLinn/ingredient_type/add";
	}

	@PostMapping("/add")
	public String add(@Valid @ModelAttribute("ingredientForm") IngredientTypeDto form, BindingResult result,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("units", unitRepository.findAll());
			return "NayZarLinn/ingredient_type/add";
		}
		service.create(form);
		return "redirect:/staff/ingredientType";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable String id, Model model) {
		IngredientType item = service.getById(id);

		IngredientTypeDto form = new IngredientTypeDto();
		form.setIngredientTypeId(item.getIngredientTypeId());
		form.setName(item.getName());
		form.setDescription(item.getDescription());

		model.addAttribute("ingredientForm", form);

		model.addAttribute("units", unitRepository.findAll());
		return "NayZarLinn/ingredient_type/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable String id, @Valid @ModelAttribute("ingredientForm") IngredientTypeDto form,
			BindingResult result,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("units", unitRepository.findAll());
			return "NayZarLinn/ingredient_type/edit";
		}
		service.update(id, form);
		return "redirect:/staff/ingredientType";
	}

	@PostMapping("/soft-delete/{id}")
	public String softDelete(@PathVariable String id) {
		service.softDelete(id);
		return "redirect:/staff/ingredientType";
	}

	@PostMapping("/restore/{id}")
	public String restore(@PathVariable String id) {
		service.restore(id);
		return "redirect:/staff/ingredientType/deleted";
	}

	@PostMapping("/hard-delete/{id}")
	public String hardDelete(@PathVariable String id) {
		service.hardDelete(id);
		return "redirect:/staff/ingredientType/deleted";
	}
}