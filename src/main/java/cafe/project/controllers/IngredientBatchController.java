package cafe.project.controllers;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.repositories.entities.IngredientBatch;
import cafe.project.services.IngredientBatchService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager-only/ingredient-batches")
public class IngredientBatchController {
	private final IngredientBatchService service;

	public IngredientBatchController(IngredientBatchService service) {
		this.service = service;
	}

	@GetMapping("")
	public String listBatches(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("batches", service.getAllBatches(ldto.getBranch_id()));
		return "ingredient-batch/list";
	}
	
	@GetMapping("/clear")
	public String clear(HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		for(IngredientBatch ing : service.getAllBatches(ldto.getBranch_id())) {
			if(ing.getRemaining_quantity().compareTo(BigDecimal.ZERO) <= 0) {
				service.softDeleteBatch(ing.getBatch_id());
			}
		}
		return "redirect:/manager-onlyingredient-batches";
	}

	@GetMapping("/delete/{id}")
	public String softDelete(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		var batch = service.getBatchById(id);
		if (batch != null && !batch.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.softDeleteBatch(id);
		return "redirect:/manager-onlyingredient-batches";
	}

	@GetMapping("/deleted-list")
	public String showDeletedList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("deletedBatches", service.getDeletedBatches(ldto.getBranch_id()));
		return "ingredient-batch/deleted-list";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		var batch = service.getBatchById(id);
		if (batch != null && !batch.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.recoverBatch(id);
		return "redirect:/manager-onlyingredient-batches/deleted-list";
	}

	@GetMapping("/hard-delete/{id}")
	public String hardDelete(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		var batch = service.getBatchById(id);
		if (batch != null && !batch.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.hardDeleteBatch(id);
		return "redirect:/manager-onlyingredient-batches/deleted-list";
	}
}