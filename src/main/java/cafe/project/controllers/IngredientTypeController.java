package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cafe.project.repositories.UnitRepository;
import cafe.project.services.IngredientTypeService;
import cafe.project.models.IngredientTypeDto;

@Controller
@RequestMapping("/ingredient-types")
public class IngredientTypeController {

	private final IngredientTypeService service;
	private final UnitRepository repo;

	public IngredientTypeController(IngredientTypeService service, UnitRepository repo) {
		this.service = service;
		this.repo = repo;
	}

	@GetMapping
    public String list(Model model) {
        model.addAttribute("ingredientTypes", service.getAllActiveIngredientTypes());
        return "ingredient-type/list";
    }

    @GetMapping("/deleted")
    public String listDeleted(Model model) {
        model.addAttribute("ingredientTypes", service.getAllDeletedIngredientTypes());
        return "ingredient-type/deleted-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ingredientType", new IngredientTypeDto());
        model.addAttribute("units", service.getAllUnits());
        return "ingredient-type/add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("ingredientType") IngredientTypeDto dto) {
    	System.out.println("name = " + dto.getName());
    	service.addIngredientType(dto);
        return "redirect:/ingredient-types";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("ingredientType", service.getById(id));
        model.addAttribute("units", service.getAllUnits());
        return "ingredient-type/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("ingredientType") IngredientTypeDto dto) {
        service.updateIngredientType(dto);
        return "redirect:/ingredient-types";
    }

    @GetMapping("/soft-delete/{id}")
    public String softDelete(@PathVariable("id") String id) {
        service.softDelete(id);
        return "redirect:/ingredient-types";
    }

    @GetMapping("/recover/{id}")
    public String recover(@PathVariable("id") String id) {
        service.recover(id);
        return "redirect:/ingredient-types/deleted";
    }

    @GetMapping("/hard-delete/{id}")
    public String showHardDeleteConfirmation(@PathVariable("id") String id, Model model) {
        model.addAttribute("typeId", id);
        return "ingredient-type/hard-deleted";
    }

    @PostMapping("/hard-delete")
    public String hardDelete(@RequestParam("typeId") String ingredientTypeId) {
        service.hardDelete(ingredientTypeId);
        return "redirect:/ingredient-types/deleted";
    }
}