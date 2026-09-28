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

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.BranchesStatsDto;
import cafe.project.services.BranchService;
import cafe.project.services.BranchesStatsService;
import cafe.project.services.EmployeeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager/branchesStats")
public class BranchesStatsController {

	private final BranchesStatsService bsService;
	private final BranchService branchService;
	private final EmployeeService employeeService;

	public BranchesStatsController(BranchesStatsService bsService, BranchService branchService,
			EmployeeService employeeService) {
		this.bsService = bsService;
		this.branchService = branchService;
		this.employeeService = employeeService;
	}

	@GetMapping
	public String BranchesStatsList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("branchesStatses", this.bsService.findAll());
		return "branchesStats/list";
	}

	@GetMapping("/add")
	public String addBranchesStats(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		BranchesStatsDto branchesStats = new BranchesStatsDto();

		branchesStats.setEmployee_id(ldto.getEmployee_id());
		branchesStats.setBranch_id(ldto.getBranch_id());

		model.addAttribute("branchesStats", branchesStats);
		return "branchesStats/add";
	}

	@PostMapping("/add")
	public String addBranchesStats(@ModelAttribute("branchesStats") BranchesStatsDto branchesStats,
			BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "branchesStats/add";
		}
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		branchesStats.setEmployee_id(ldto.getEmployee_id());
		branchesStats.setBranch_id(ldto.getBranch_id());

		branchesStats.setCreated_at(LocalDateTime.now());

		System.out.println("Sale Count = " + branchesStats.getSalecount());
		System.out.println("Sale Amount = " + branchesStats.getSaleamount());
		System.out.println("Employee Cost = " + branchesStats.getEmployee_cost());

		this.bsService.add(branchesStats);
		return "redirect:/manager/branchesStats";
	}

	@GetMapping("edit/{branch_stats_id}")
	public String editBranchesStats(@PathVariable String branch_stats_id, Model model) {
		BranchesStatsDto existingBs = this.bsService.findById(branch_stats_id);
		if (existingBs != null) {
			model.addAttribute("branchesStats", existingBs);
			return "branchesStats/edit";
		}
		return "redirect:/manager/branchesStats";

	}

	@PostMapping("/edit")
	public String editBranchesStats(@ModelAttribute("branchesStats") BranchesStatsDto branchesStats,
			BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "branchesStats/edit";
		}
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		branchesStats.setBranch_id(ldto.getBranch_id());
		branchesStats.setEmployee_id(ldto.getEmployee_id());

		branchesStats.setIsedited(true);
		branchesStats.setCreated_at(LocalDateTime.now());
		this.bsService.edit(branchesStats.getBranch_stats_id(), branchesStats);
		return "redirect:/manager/branchesStats";
	}

	@GetMapping("/delete/{branch_stats_id}")
	public String deleteBranchesStats(@PathVariable String branch_stats_id) {

		bsService.delete(branch_stats_id);

		return "redirect:/manager/branchesStats";
	}

	@PostMapping("/delete")
	public String deleteBranchesStats(@ModelAttribute("branchesStats") BranchesStatsDto branchesStats) {
		this.bsService.delete(branchesStats.getBranch_stats_id());
		return "redirect:/manager/branchesStats";
	}

	@GetMapping("/deleted")
	public String deletedBranchesStatsList(Model model) {
		model.addAttribute("branchseStats", bsService.findDeleted());
		return "branchesStats/deletedList";
	}

	@GetMapping("/restore")
	public String restoreBranchesStatsList(@RequestParam String branch_stats_id) {
		bsService.restore(branch_stats_id);
		return "redirect:/manager/branchesStats/deleted";
	}

	@GetMapping("/real-delete")
	public String realDeletedBranchesStatsList(@ModelAttribute("banchesStats") BranchesStatsDto branchesStats) {
		bsService.hardDelete(branchesStats.getBranch_stats_id());
		return "redirect:/manager/branchesStats/deleted";
	}
}
