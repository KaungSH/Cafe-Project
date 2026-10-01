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
	
	@GetMapping("/status/{branch_id}")
	public String changeStatus(@PathVariable("branch_id") String branch_id, Model model) {
		BranchEntryDto dto = branchService.findById(branch_id);
		if (dto == null) {
			return "redirect:/manager/branches";
		}
		model.addAttribute("branchDto", dto);
		model.addAttribute("statuses", branchService.findAllStatuses());
		return "branches/changeStatus";
	}
	@PostMapping("/status")
	public String changeStatus(@ModelAttribute("branchDto") BranchEntryDto dto, Model model) {
		branchService.changeStatus(dto.getBranch_id(), dto.getBranch_status_id());
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
