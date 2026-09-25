package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cafe.project.repositories.StatusRepository;

@Controller
@RequestMapping("/admin/statuses")
public class StatusController {

	private final StatusRepository statusRepository;

	public StatusController(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	@GetMapping
	public String showStatusList(@RequestParam(defaultValue = "branch") String type, Model model) {
		model.addAttribute("selectedType", type);
		model.addAttribute("statusList", statusRepository.findAll(type));
		return "statuses/list";
	}

}

