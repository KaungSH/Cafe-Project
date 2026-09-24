package cafe.project.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import cafe.project.models.BranchEntryDto;
import cafe.project.services.BranchService;

@Controller
@RequestMapping("/manager/branches")
public class BranchController {

	private final BranchService branchService;
	public BranchController(BranchService branchService) {
		this.branchService = branchService;
	}
	@GetMapping
	public String list(Model model) {
		model.addAttribute("branches", branchService.findAll());
		return "branches/list";
	}
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("branchDto", new BranchEntryDto());
		model.addAttribute("statuses", branchService.findAllStatuses());
		return "branches/create";
	}
	@PostMapping("/create")
	public String save(@Valid @ModelAttribute("branchDto") BranchEntryDto dto, BindingResult result, Model model) {
		validateTimeRange(dto, result);
		if (result.hasErrors()) {
			model.addAttribute("statuses", branchService.findAllStatuses());
			return "branches/create";
		}
		branchService.add(dto);
		return "redirect:/manager/branches";
	}
	@GetMapping("/edit/{branch_id}")
	public String showEditForm(@PathVariable("branch_id") String branch_id, Model model) {
		BranchEntryDto dto = branchService.findById(branch_id);
		if (dto == null) {
			return "redirect:/manager/branches";
		}
		model.addAttribute("branchDto", dto);
		model.addAttribute("statuses", branchService.findAllStatuses());
		return "branches/edit";
	}
	@PostMapping("/edit/{branch_id}")
	public String update(@PathVariable("branch_id") String branch_id, @Valid @ModelAttribute("branchDto") BranchEntryDto dto,
			BindingResult result, Model model) {
			validateTimeRange(dto, result);
		if (result.hasErrors()) {
			model.addAttribute("statuses", branchService.findAllStatuses());
			return "branches/edit";
		}
		branchService.edit(branch_id, dto);
		return "redirect:/manager/branches";
	}
	@PostMapping("/delete/{branch_id}")
	public String delete(@PathVariable("branch_id") String branch_id) {
		branchService.delete(branch_id);
		return "redirect:/manager/branches";
	}
	private void validateTimeRange(BranchEntryDto dto, BindingResult result) {
		if (dto.getOpening_time() != null && dto.getClosing_time() != null) {
			if (dto.getClosing_time().isBefore(dto.getOpening_time())) {
				result.rejectValue("closing_time", "error.branchDto", 
						"The cl3osing time should not be later than the opening time.");
			}
			if (dto.getClosing_time().equals(dto.getOpening_time())) {
				result.rejectValue("closing_time", "error.branchDto",
						"The opening and closing times must not be the same.");
			}
		}
	}
}
