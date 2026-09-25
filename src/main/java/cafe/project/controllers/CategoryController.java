package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.CategoryDto;
import cafe.project.services.BranchService;
import cafe.project.services.CategoryService;
import jakarta.servlet.http.HttpSession;


@Controller
public class CategoryController {

	private final CategoryService categoryService;
	private final BranchService branchService;

	public CategoryController(CategoryService categoryService,BranchService branchService) {
		this.categoryService = categoryService;
		this.branchService=branchService;
	}

	@GetMapping("/categories")
	public String categoryList(Model model) {

		model.addAttribute("categories", this.categoryService.findAllByRelation());

		return "HeinMinHtet/categories/list";
	}

	@GetMapping("/categories/add")
	public String addCategory(Model model) {

		model.addAttribute("category", new CategoryDto());
		model.addAttribute("branches",branchService.findAll());
		
		return "HeinMinHtet/categories/add";
	}

	@PostMapping("/categories/add")
	public String addCategory(@ModelAttribute("category") CategoryDto category, Model model,HttpSession session) {
		
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		String employeeID = ldto.getEmployee_id();
		
		category.setEmployee_id(employeeID);
		
		this.categoryService.add(category);
		
		

		return "redirect:/categories";
	}

	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable String id, Model model) {

		CategoryDto existingCategory = this.categoryService.findById(id);

		if (existingCategory != null) {

			model.addAttribute("category", existingCategory);
			model.addAttribute("branches",branchService.findAll());

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
	
	@GetMapping("/categories/deleted-list")
	public String categoryDeletedList(Model model) {
		model.addAttribute("categories", this.categoryService.deletedList());
		return "HeinMinHtet/categories/deleted-list";
	}
	
	@PostMapping("/categories/restore")
	public String restoreCategory(@RequestParam String id) {
		categoryService.restore(id);
		return "redirect:/categories/deleted-list";
	}
	
	@PostMapping("/categories/real-delete")
	public String realDeleteCategory(@RequestParam String id) {
		categoryService.hardDelete(id);
		return "redirect:/categories/deleted-list";
	}
}
