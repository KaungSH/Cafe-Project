package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.HeinMinHtet.models.CategoryDto;
import cafe.project.HeinMinHtet.services.CategoryService;


@Controller
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/categories")
	public String categoryList(Model model) {

		model.addAttribute("categories", this.categoryService.findAll());

		return "HeinMinHtet/categories/list";
	}

	@GetMapping("/categories/add")
	public String addCategory(Model model) {

		model.addAttribute("category", new CategoryDto());

		return "HeinMinHtet/categories/add";
	}

	@PostMapping("/categories/add")
	public String addCategory(@ModelAttribute("category") CategoryDto category, Model model) {

		this.categoryService.add(category);

		return "redirect:/categories";
	}

	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable String id, Model model) {

		CategoryDto existingCategory = this.categoryService.findById(id);

		if (existingCategory != null) {

			model.addAttribute("category", existingCategory);

			return "HeinMinHtet/categories/edit";
		}

		return "redirect:/notfound";
	}

	@PostMapping("/categories/edit")
	public String editCategory(@ModelAttribute("category") CategoryDto category, Model model) {

		this.categoryService.edit(category.getCategory_id(), category);

		return "redirect:/categories";
	}

	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable String id, Model model) {

		CategoryDto existingCategory = this.categoryService.findById(id);

		if (existingCategory != null) {

			model.addAttribute("category", existingCategory);

			return "HeinMinHtet/categories/delete";
		}

		return "redirect:/notfound";
	}

	@PostMapping("/categories/delete")
	public String deleteCategory(@ModelAttribute("category") CategoryDto category) {

		this.categoryService.delete(category.getCategory_id());

		return "redirect:/categories";
	}
}
