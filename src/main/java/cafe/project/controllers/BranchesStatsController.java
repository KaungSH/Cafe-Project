package cafe.project.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
import cafe.project.models.EmployeeListDto;
import cafe.project.repositories.entities.Orders;
import cafe.project.services.BranchService;
import cafe.project.services.BranchesStatsService;
import cafe.project.services.EmployeeService;
import cafe.project.services.OrdersService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager-only/branchstats")
public class BranchesStatsController {

	private final BranchesStatsService bsService;
	private final BranchService branchService;
	private final EmployeeService employeeService;
	private final OrdersService orderService;

	public BranchesStatsController(BranchesStatsService bsService, BranchService branchService, EmployeeService employeeService, OrdersService orderService) {
		this.bsService = bsService;
		this.branchService = branchService;
		this.employeeService = employeeService;
		this.orderService = orderService;
	}

	@GetMapping
	public String BranchesStatsList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("branchesStatses", this.bsService.findAll(ldto.getBranch_id()));
		return "branchesStats/list";
	}

	@GetMapping("/add")
	public String addBranchesStats(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		
		BranchesStatsDto branchesStats = new BranchesStatsDto();
		model.addAttribute("branch_name", branchService.findById(ldto.getBranch_id()).getName());
		model.addAttribute("month_name", LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)));
		
		branchesStats.setSalecount(getTotalSaleCount());
		branchesStats.setSaleamount(getTotalSaleAmount().doubleValue());
		branchesStats.setEmployee_cost(getTotalSalary(ldto.getBranch_id()));

		model.addAttribute("branchesStats", branchesStats);
		return "branchesStats/add";
	}

	@PostMapping("/add")
	public String addBranchesStats(@ModelAttribute("branchesStats") BranchesStatsDto branchesStats, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "branchesStats/add";
		}
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		branchesStats.setEmployee_id(ldto.getEmployee_id());
		branchesStats.setBranch_id(ldto.getBranch_id());
		branchesStats.setCreated_at(LocalDateTime.now());
		branchesStats.setMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)));
		

		this.bsService.add(branchesStats);
		return "redirect:/manager-only/branchstats";
	}

	@GetMapping("edit/{branch_stats_id}")
	public String editBranchesStats(@PathVariable String branch_stats_id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		
		BranchesStatsDto existingBs = this.bsService.findById(branch_stats_id, ldto.getBranch_id());
		if (existingBs != null) {
			model.addAttribute("branchesStats", existingBs);
			return "branchesStats/edit";
		}
		return "redirect:/manager-only/branchstats";

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
		return "redirect:/manager-only/branchstats";
	}

	@GetMapping("/delete/{branch_stats_id}")
	public String deleteBranchesStats(@PathVariable String branch_stats_id) {

		bsService.delete(branch_stats_id);

		return "redirect:/manager-only/branchstats";
	}

	@PostMapping("/delete")
	public String deleteBranchesStats(@ModelAttribute("branchesStats") BranchesStatsDto branchesStats) {
		this.bsService.delete(branchesStats.getBranch_stats_id());
		return "redirect:/manager-only/branchstats";
	}

	@GetMapping("/deleted")
	public String deletedBranchesStatsList(Model model) {
		model.addAttribute("branchseStats", bsService.findDeleted());
		return "branchesStats/deletedList";
	}

	@GetMapping("/restore")
	public String restoreBranchesStatsList(@RequestParam String branch_stats_id) {
		bsService.restore(branch_stats_id);
		return "redirect:/manager-only/branchstats/deleted";
	}

	@GetMapping("/real-delete")
	public String realDeletedBranchesStatsList(@ModelAttribute("banchesStats") BranchesStatsDto branchesStats) {
		bsService.hardDelete(branchesStats.getBranch_stats_id());
		return "redirect:/manager-only/branchstats/deleted";
	}
	
	//Need to Add BranchValidtaions
	private int getTotalSaleCount() {
		int i = 0;
		for (Orders order : orderService.findReceivedAll()) {
			if((YearMonth.from(LocalDateTime.now()).minusMonths(1)).equals(YearMonth.from(order.getCreated_time()))) {
				i++;
			}
		}
		
		for (Orders order : orderService.findNotReceivedAll()) {
			if((YearMonth.from(LocalDateTime.now()).minusMonths(1)).equals(YearMonth.from(order.getCreated_time()))) {
				i++;
			}
		}
		return i;
	}
	
	//Need to Add BranchValidtaions
	private BigDecimal getTotalSaleAmount() {
		BigDecimal bd = new BigDecimal("0.00");
		for (Orders order : orderService.findReceivedAll()) {
			if((YearMonth.from(LocalDateTime.now()).minusMonths(1)).equals(YearMonth.from(order.getCreated_time()))) {
				bd.add((order.getTotal_amount() != null) ? order.getTotal_amount() : BigDecimal.ZERO);
			}
		}
		
		for (Orders order : orderService.findNotReceivedAll()) {
			if((YearMonth.from(LocalDateTime.now()).minusMonths(1)).equals(YearMonth.from(order.getCreated_time()))) {
				bd.add((order.getTotal_amount() != null) ? order.getTotal_amount() : BigDecimal.ZERO);
			}
		}
		
		return bd;
	}
	
	private double getTotalSalary(String branch_id) {
		double ts = 0.0;
		
		for (EmployeeListDto listDto : employeeService.getAllEmployees(branch_id)) {
			ts += listDto.getSalary();
		}
		
		return ts;
	}
}