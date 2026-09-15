package cafe.project.HeinMinHtet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.HeinMinHtet.models.UnitDto;
import cafe.project.HeinMinHtet.services.UnitService;

@Controller
public class UnitController {

	private final UnitService unitService;

	public UnitController(UnitService unitService) {
		this.unitService = unitService;
	}

	@GetMapping("/units")
	public String unitList(Model model) {

		model.addAttribute("units", this.unitService.findAll());

		return "HeinMinHtet/units/list";
	}

	@GetMapping("/units/add")
	public String addUnit(Model model) {

		model.addAttribute("unit", new UnitDto());

		return "HeinMinHtet/units/add";
	}

	@PostMapping("/units/add")
	public String addUnit(@ModelAttribute("unit") UnitDto unit, Model model) {

		this.unitService.add(unit);

		return "redirect:/units";
	}

	@GetMapping("/units/edit/{id}")
	public String editUnit(@PathVariable String id, Model model) {

		UnitDto existingUnit = this.unitService.findById(id);

		if (existingUnit != null) {

			model.addAttribute("unit", existingUnit);

			return "HeinMinHtet/units/edit";
		}

		return "redirect:/notfound";
	}

	@PostMapping("/units/edit")
	public String editUnit(@ModelAttribute("unit") UnitDto unit, Model model) {

		this.unitService.edit(unit.getUnit_id(), unit);

		return "redirect:/units";
	}

	@GetMapping("/units/delete/{id}")
	public String deleteUnit(@PathVariable String id, Model model) {

		UnitDto existingUnit = this.unitService.findById(id);

		if (existingUnit != null) {

			model.addAttribute("unit", existingUnit);

			return "HeinMinHtet/units/delete";
		}

		return "redirect:/notfound";
	}

	@PostMapping("/units/delete")
	public String deleteUnit(@ModelAttribute("unit") UnitDto unit) {

		this.unitService.delete(unit.getUnit_id());

		return "redirect:/units";
	}
}
