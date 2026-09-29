package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.repositories.WasteReasonRepository;
import cafe.project.repositories.entities.WasteReason;

@Controller
@RequestMapping("/manager/wastereasons")
public class WasteReasonsController {

	private WasteReasonRepository repo;

	public WasteReasonsController(WasteReasonRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public String getReason(Model model) {
		model.addAttribute("reasons", repo.findReasonsAll());
		return "/wastereasons/list";
	}

	@GetMapping("/add")
	public String addReason(Model model) {
		model.addAttribute("reason", new WasteReason());
		return "/wastereasons/add";
	}

	@PostMapping("/add")
	public String addReason(@ModelAttribute WasteReason reason, Model model) {
		repo.addReason(reason.getReason_name());
		return "redirect:/manager/wastereasons";
	}

	@GetMapping("/edit/{id}")
	public String editReason(@PathVariable String id, Model model) {
		System.out.println(repo.findReasonById(id).getReason_name());
		System.out.println(id);
		model.addAttribute("reason", repo.findReasonById(id));
		return "/wastereasons/edit";
	}

	@PostMapping("/edit")
	public String editReason(@ModelAttribute WasteReason reason, Model model) {
		System.out.println(reason.getReason_name());
		System.out.println(reason.getWaste_reason_id());
		repo.editReason(reason.getWaste_reason_id(), reason.getReason_name());
		return "redirect:/manager/wastereasons";
	}

	@GetMapping("/delete/{id}")
	public String deleteReason(@PathVariable String id) {
		repo.deleteReason(id);
		return "redirect:/manager/wastereasons";
	}

}
