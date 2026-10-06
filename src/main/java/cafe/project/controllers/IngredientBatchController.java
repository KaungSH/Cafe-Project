package cafe.project.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cafe.project.models.IngredientBatchListDto;
import cafe.project.repositories.entities.IngredientBatch;
import cafe.project.services.IngredientBatchService;

@Controller
@RequestMapping("/ingredient-batches")
public class IngredientBatchController {
	private final IngredientBatchService service;

	public IngredientBatchController(IngredientBatchService service) {
		this.service = service;
	}

	@GetMapping
	public String listBatches(Model model) {
		model.addAttribute("batches", service.getAllBatches());
		return "ingredient-batch/list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		IngredientBatchListDto dto = new IngredientBatchListDto();
		List<IngredientBatch> initialList = new ArrayList<>();
		initialList.add(new IngredientBatch());
		dto.setBatchList(initialList);

		model.addAttribute("batchListDto", dto);
		return "ingredient-batch/add";
	}

	@PostMapping("/add")
	public String saveBatches(@ModelAttribute("batchListDto") IngredientBatchListDto batchListDto) {
		service.createBatch(batchListDto);
		return "redirect:/ingredient-batches";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("batch", service.getBatchById(id));
		return "ingredient-batch/edit";
	}

	@PostMapping("/edit")
	public String updateBatch(@ModelAttribute("batch") IngredientBatch batch) {
		service.updateBatch(batch);
		return "redirect:/ingredient-batches";
	}

	@GetMapping("/delete/{id}")
	public String softDelete(@PathVariable("id") String id) {
		service.softDeleteBatch(id);
		return "redirect:/ingredient-batches";
	}

	@GetMapping("/deleted-list")
	public String showDeletedList(Model model) {
		model.addAttribute("deletedBatches", service.getDeletedBatches());
		return "ingredient-batch/deleted-list";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id) {
		service.recoverBatch(id);
		return "redirect:/ingredient-batches/deleted-list";
	}

	@GetMapping("/hard-delete/{id}")
	public String hardDelete(@PathVariable("id") String id) {
		service.hardDeleteBatch(id);
		return "redirect:/ingredient-batches/deleted-list";
	}
}