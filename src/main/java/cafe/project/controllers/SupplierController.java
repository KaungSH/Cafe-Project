package cafe.project.controllers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.models.SupplierDto;
import cafe.project.services.SupplierService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager")
public class SupplierController {
	private final SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@GetMapping("/suppliers")
	public String supplierList(Model model) {
		model.addAttribute("supplier", this.supplierService.findAll());
		return "NayZarLinn/supplier/list";
	}

	@GetMapping("/suppliers/add")
	public String addSupplier(Model model) {
		model.addAttribute("supplier", new SupplierDto());
		return "NayZarLinn/supplier/add";
	}

	@PostMapping("/suppliers/add")
	public String addSupplier(@Valid @ModelAttribute("supplier") SupplierDto supplier, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "NayZarLinn/supplier/add";
		}
		supplier.setCreated_at(LocalDateTime.now());
		this.supplierService.add(supplier);
		return "redirect:/manager/suppliers";
	}

	@GetMapping("/suppliers/edit/{supplier_id}")
	public String editSupplier(@PathVariable String supplier_id, Model model) {
		SupplierDto existingSup = this.supplierService.findById(supplier_id);
		if (existingSup != null) {
			model.addAttribute("supplier", existingSup);
			return "NayZarLinn/supplier/edit";
		}
		return "";
	}

	@PostMapping("/suppliers/edit")
	public String editSupplier(@Valid @ModelAttribute("supplier") SupplierDto supplier, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "NayZarLinn/supplier/edit";
		}
		supplier.setCreated_at(LocalDateTime.now());
		this.supplierService.edit(supplier.getSupplier_id(), supplier);
		return "redirect:/manager/suppliers";
	}

	@GetMapping("/suppliers/delete/{supplier_id}")
	public String deleteSupplier(@PathVariable String supplier_id, Model model) {
		SupplierDto existingSup = this.supplierService.findById(supplier_id);
		if (existingSup != null) {
			model.addAttribute("supplier", existingSup);
			return "NayZarLinn/supplier/delete";
		}
		return "";
	}

	@PostMapping("/suppliers/delete")
	public String deletedSupplier(@ModelAttribute("supplier") SupplierDto supplier) {
		this.supplierService.delete(supplier.getSupplier_id());
		return "redirect:/manager/suppliers";
	}

	@GetMapping("/suppliers/deleted")
	public String deletedSupplierList(Model model) {
		model.addAttribute("supplier", supplierService.findDeleted());
		return "NayZarLinn/supplier/deletedList";
	}

	@PostMapping("/suppliers/restore")
	public String restoreSupplier(@RequestParam String supplier_id) {
		supplierService.restore(supplier_id);
		return "redirect:/manager/suppliers/deleted";
	}

	@PostMapping("/suppliers/real-delete")
	public String realDeleteSupplier(@ModelAttribute("supplier") SupplierDto supplier) {
		supplierService.realDelete(supplier.getSupplier_id());
		return "redirect:/manager/suppliers/deleted";
	}

}
