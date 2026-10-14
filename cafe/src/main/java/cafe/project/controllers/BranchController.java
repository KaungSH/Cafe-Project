package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.BranchEntryDto;
import cafe.project.services.BranchService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager-only/branches")
public class BranchController {

	private final BranchService branchService;
	public BranchController(BranchService branchService) {
		this.branchService = branchService;
	}
	
	@GetMapping("/status")
	public String changeStatus(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("branchDto", branchService.findById(ldto.getBranch_id()));
		model.addAttribute("statuses", branchService.findAllStatuses());
		return "branches/changeStatus";
	}
	
	@PostMapping("/status")
	public String changeStatus(@ModelAttribute("branchDto") BranchEntryDto dto, Model model) {
		branchService.changeStatus(dto.getBranch_id(), dto.getBranch_status_id());
		return "redirect:/manager-only/branchstats";
	}
	
	@GetMapping("/finance")
	public String changeFinance(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		BranchEntryDto dto = branchService.findById(ldto.getBranch_id());
		model.addAttribute("branchDto", dto);
		return "branches/changeFinance";
	}
	
	@PostMapping("/finance")
	public String changeFinance(@ModelAttribute("branchDto") BranchEntryDto dto, Model model) {
		branchService.updateFinance(dto.getBranch_id(),dto.getBranch_finance());
		return "redirect:/manager-only/branchstats";
	}
	
}
