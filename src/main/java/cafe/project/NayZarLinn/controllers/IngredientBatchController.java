package cafe.project.NayZarLinn.controllers;

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

import cafe.project.NayZarLinn.models.IngredientBatchDto;
import cafe.project.NayZarLinn.repositories.IngredientTypeRepository;
import cafe.project.NayZarLinn.services.IngredientBatchService;
import cafe.project.YatiWinLatt.repositories.BranchRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager/ingredientBatch")
public class IngredientBatchController {

	private final IngredientBatchService ingredientBatchService;
	private final BranchRepository branchRepository;
	private final IngredientTypeRepository ingredientTypeRepository;

	public IngredientBatchController(IngredientBatchService ingredientBatchService, BranchRepository branchRepository,
			IngredientTypeRepository ingredientTypeRepository) {
		this.ingredientBatchService = ingredientBatchService;
		this.branchRepository = branchRepository;
		this.ingredientTypeRepository = ingredientTypeRepository;
	}

	@GetMapping("/batches-expiry")
	public String batchesAndExpiry(Model model) {
		model.addAttribute(ingredientBatchService.getBatchesAndExpiry());
		return "NayZarLinn/ingredientBatch/batchesAndExpiry";

	}

	@GetMapping
	public String ingredientBatchList(Model model) {
		model.addAttribute("ingredientBatch", this.ingredientBatchService.findAll());
		return "NayZarLinn/ingredientBatch/list";
	}

	@GetMapping("/add")
	public String addIngredientBatch(Model model) {
		model.addAttribute("ingredientBatch", new IngredientBatchDto());
		model.addAttribute("branches", branchRepository.findAll());
		model.addAttribute("ingredientTypes", ingredientTypeRepository.findAll());
		return "NayZarLinn/ingredientBatch/add";
	}

	@PostMapping("/add")
	public String addIngredientBatch(@Valid @ModelAttribute("ingredientBatch") IngredientBatchDto ingredientBatch,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("branches", branchRepository.findAll());
			model.addAttribute("ingredientTypes", ingredientTypeRepository.findAll());
			return "NayZarLinn/ingredientBatch/add";
		}
		ingredientBatch.setCreatedAt(LocalDateTime.now());
		this.ingredientBatchService.add(ingredientBatch);
		return "redirect:/manager/ingredientBatch";
	}

	@GetMapping("/expired")
	public String expiredIngredientBatchList(Model model) {
		model.addAttribute("ingredientBatch", ingredientBatchService.findExpired());
		return "NayZarLinn/ingredientBatch/expiredList";
	}

	@PostMapping("/expired/delete")
	public String deleteExpiredIngredientBatch(@RequestParam String batchId) {

		ingredientBatchService.softDeleteExpired(batchId);

		return "redirect:/manager/ingredientBatch/expired";
	}

	@GetMapping("/edit/{batchId}")
	public String editIngredientBatch(@PathVariable String batchId, Model model) {
		IngredientBatchDto existingIb = this.ingredientBatchService.findByBatchId(batchId);
		if (existingIb != null) {
			model.addAttribute("ingredientBatch", existingIb);
			model.addAttribute("branches", branchRepository.findAll());
			model.addAttribute("ingredientTypes", ingredientTypeRepository.findAll());
			return "NayZarLinn/ingredientBatch/edit";
		}
		return "redirect:/error/404";
	}

	@PostMapping("/edit")
	public String editIngredientBatch(@Valid @ModelAttribute("ingredientBatch") IngredientBatchDto ingredientBatch,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("branches", branchRepository.findAll());
			model.addAttribute("ingredientTypes", ingredientTypeRepository.findAll());
			return "NayZarLinn/ingredientBatch/edit";
		}
		ingredientBatch.setCreatedAt(LocalDateTime.now());
		this.ingredientBatchService.edit(ingredientBatch.getBatchId(), ingredientBatch);
		return "redirect:/error/404";
	}

	@GetMapping("/delete/{batchId}")
	public String deleteIngredientBatch(@PathVariable String batchId, Model model) {
		IngredientBatchDto existingIb = this.ingredientBatchService.findByBatchId(batchId);
		if (existingIb != null) {
			model.addAttribute("ingredientBatch", existingIb);
			return "NayZarLinn/ingredientBatch/delete";
		}
		model.addAttribute("ingredientBatch", existingIb);
		return "redirect:/error/404";
	}

	@PostMapping("/delete")
	public String deletedIngredientBatch(@ModelAttribute("ingredientBatch") IngredientBatchDto ingredientBatch) {
		this.ingredientBatchService.delete(ingredientBatch.getBatchId());
		return "redirect:/manager/ingredientBatch";
	}

	@GetMapping("/deleted")
	public String deletedingredientBatchList(Model model) {
		model.addAttribute("ingredientBatch", ingredientBatchService.findDeleted());
		return "NayZarLinn/ingredientBatch/deletedList";
	}

	@PostMapping("/restore")
	public String restoreSupplier(@RequestParam String batchId) {
		ingredientBatchService.restore(batchId);
		return "redirect:/manager/ingredientBatch/deleted";
	}

	@PostMapping("real-delete")
	public String realDeleteingredientBatch(@ModelAttribute("ingredientBatch") IngredientBatchDto ingredientBatch) {
		ingredientBatchService.hardDelete(ingredientBatch.getBatchId());
		return "redirect:/manager/ingredientBatch/deleted";
	}

}
