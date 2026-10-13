package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.UnitDto;
import cafe.project.repositories.entities.Unit;
import cafe.project.services.UnitService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UnitController {

	private final UnitService unitService;

	public UnitController(UnitService unitService) {
		this.unitService = unitService;
	}

	@GetMapping("/units")
	public String unitList(Model model) {

		model.addAttribute("units", this.unitService.findAll());

		return "units/list";
	}

	@GetMapping("/units/add")
	public String addUnit(Model model) {

		model.addAttribute("unit", new Unit());

		return "units/add";
	}

	@PostMapping("/units/add")
	public String addUnit(@ModelAttribute("unit") UnitDto unit, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		unit.setEmployee_name(ldto.getEmployee_id());
		this.unitService.add(unit);
		return "redirect:/units";
	}

	@GetMapping("/units/edit/{id}")
	public String editUnit(@PathVariable String id, Model model) {

		UnitDto existingUnit = this.unitService.findById(id);

		if (existingUnit != null) {

			model.addAttribute("unit", existingUnit);

			return "units/edit";
		}

		return "redirect:/notfound";
	}

	@PostMapping("/units/edit/{id}")
	public String editUnit(@ModelAttribute("unit") UnitDto unit, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		unit.setEmployee_name(ldto.getEmployee_id());
		this.unitService.edit(unit.getUnit_id(), unit);
		return "redirect:/units";
	}

	@GetMapping("/units/delete/{id}")
	public String deleteUnit(@PathVariable String id, Model model) {

		UnitDto existingUnit = this.unitService.findById(id);

		if (existingUnit != null) {

			model.addAttribute("unit", existingUnit);

			return "units/delete";
		}

		return "redirect:/notfound";
	}

	@PostMapping("/units/delete")
	public String deleteUnit(@ModelAttribute("unit") UnitDto unit) {

		this.unitService.delete(unit.getUnit_id());

		return "redirect:/units";
	}
	
	@GetMapping("/units/deleted-list")
	public String unitDeleteList(Model model) {

		model.addAttribute("units", this.unitService.deletedList());

		return "units/deleted-list";
	}
	
	@PostMapping("/units/restore")
	public String restoreUnit(@RequestParam String id) {
		
		unitService.restore(id);
		
		return "redirect:/units/deleted-list";
	}
	
	@PostMapping("/units/real-delete")
	public String realDeleteUnit(@RequestParam String id) {
		
		unitService.hardDelete(id);
		
		return "redirect:/units/deleted-list";
	}
}
