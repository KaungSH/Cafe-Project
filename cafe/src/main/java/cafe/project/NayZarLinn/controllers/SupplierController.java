package cafe.project.NayZarLinn.controllers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.NayZarLinn.models.SupplierDto;
import cafe.project.NayZarLinn.services.SupplierService;
import jakarta.validation.Valid;

@Controller
public class SupplierController {
	private final SupplierService supplierService;

	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@GetMapping("/supplier")
	public String supplierList(Model model) {
		model.addAttribute("supplier", this.supplierService.findAll());
		return "NayZarLinn/supplier/list";
	}

	@GetMapping("/supplier/add")
	public String addSupplier(Model model) {
		model.addAttribute("supplier", new SupplierDto());
		return "NayZarLinn/supplier/add";
	}

	@PostMapping("/supplier/add")
	public String addSupplier(@Valid @ModelAttribute("supplier") SupplierDto supplier, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "NayZarLinn/supplier/add";
		}
		supplier.setCreated_at(LocalDateTime.now());
		this.supplierService.add(supplier);
		return "redirect:/supplier";
	}

	@GetMapping("/supplier/edit/{supplier_id}")
	public String editSupplier(@PathVariable String supplier_id, Model model) {
		SupplierDto existingSup = this.supplierService.findById(supplier_id);
		if (existingSup != null) {
			model.addAttribute("supplier", existingSup);
			return "NayZarLinn/supplier/edit";
		}
		return "redirect:/notfound";
	}

	@PostMapping("/supplier/edit")
	public String editSupplier(@Valid @ModelAttribute("supplier") SupplierDto supplier, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "NayZarLinn/supplier/edit";
		}
		this.supplierService.edit(supplier.getSupplier_id(), supplier);
		return "redirect:/supplier";
	}

	@GetMapping("/supplier/delete/{supplier_id}")
	public String deleteSupplier(@PathVariable String supplier_id, Model model) {
		SupplierDto existingSup = this.supplierService.findById(supplier_id);
		if (existingSup != null) {
			model.addAttribute("supplier", existingSup);
			return "NayZarLinn/supplier/delete";
		}
		return "redirect:/notfound";
	}

	@PostMapping("/supplier/delete")
	public String deletedSupplier(@ModelAttribute("supplier") SupplierDto supplier) {
		this.supplierService.delete(supplier.getSupplier_id());
		return "redirect:/supplier";
	}

	@GetMapping("/supplier/deleted")
	public String deletedSupplierList(Model model) {
		model.addAttribute("supplier", supplierService.findDeleted());
		return "NayZarLinn/supplier/deleted";
	}

	@PostMapping("/supplier/restore")
	public String restoreSupplier(@RequestParam String supplier_id) {
		supplierService.restore(supplier_id);
		return "redirect:/supplier/deleted";
	}

	@PostMapping("/supplier/real-delete")
	public String realDeleteSupplier(@ModelAttribute("supplier") SupplierDto supplier) {
		supplierService.realDelete(supplier.getSupplier_id());
		return "redirect:/supplier/deleted";
	}

}
