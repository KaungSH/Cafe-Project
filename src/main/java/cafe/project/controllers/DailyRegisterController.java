package cafe.project.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpSession;
import cafe.project.services.DailyRegisterService;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.DailyRegisterEntryDto;
import cafe.project.models.DailyRegisterListDto;

@Controller
@RequestMapping("/manager-only/daily-registers")
public class DailyRegisterController {

	private final DailyRegisterService service;

	public DailyRegisterController(DailyRegisterService service) {
		this.service = service;
	}

	@GetMapping("/{sort}")
	public String showListPageAll(@PathVariable("sort") String sort, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (sort.equals("closed")) {
			model.addAttribute("registers", service.getAllClosed(ldto.getBranch_id()));
			model.addAttribute("sort", sort);
			return "daily_registers/list-all";
		}
		if (sort.equals("opened")) {
			model.addAttribute("registers", service.getAllOpened(ldto.getBranch_id()));
			model.addAttribute("sort", sort);
			return "daily_registers/list-all";
		}
		model.addAttribute("registers", service.getAllRegisters(ldto.getBranch_id()));
		model.addAttribute("sort", sort);
		return "daily_registers/list-all";
	}

	@GetMapping("/deleted")
	public String showListPageDeleted(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("registers", service.getAllDeleted(ldto.getBranch_id()));
		return "daily_registers/list-deleted";
	}

	@GetMapping("/delete/{register_id}")
	public String deleteRegister(@PathVariable("register_id") String register_id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		DailyRegisterEntryDto existingRegister = service.getRegisterEntryDtoById(register_id); 

		if (!existingRegister.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.deleteRegister(register_id);
		return "redirect:/manager-only/daily-registers/all";
	}

	@GetMapping("/recover/{register_id}")
	public String recoverRegister(@PathVariable("register_id") String register_id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		DailyRegisterEntryDto existingRegister = service.getRegisterEntryDtoById(register_id);

		if (!existingRegister.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.recoverRegister(register_id);
		return "redirect:/manager-only/daily-registers/deleted";
	}

	@GetMapping("/hardDelete/{register_id}")
	public String realDeleteRegister(@PathVariable("register_id") String register_id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		DailyRegisterEntryDto existingRegister = service.getRegisterEntryDtoById(register_id);

		if (!existingRegister.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.hardDeleteRegister(register_id);
		return "redirect:/manager-only/daily-registers/deleted";
	}

	@GetMapping("/timedSoftDelete")
	public String timedSoftDelete(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		int i = 0;
		for (DailyRegisterListDto listDto : service.getAllRegisters(ldto.getBranch_id())) {
			if (ChronoUnit.DAYS.between(listDto.getDate(), LocalDate.now()) >= 30) {
				service.deleteRegister(listDto.getRegister_id());
				i++;
			}
		}
		if (i < 1) {
			model.addAttribute("error", "");
		}
		return "redirect:/manager-only/daily-registers/all";
	}

	@GetMapping("/timedHardDelete")
	public String timedHardDelete(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		int i = 0;
		for (DailyRegisterListDto listDto : service.getAllDeleted(ldto.getBranch_id())) {
			if (ChronoUnit.DAYS.between(listDto.getDate(), LocalDate.now()) >= 90) {
				service.hardDeleteRegister(listDto.getRegister_id());
				i++;
			}
		}
		if (i < 1) {
			model.addAttribute("error", "");
		}
		return "redirect:/manager-only/daily-registers/deleted";
	}

}