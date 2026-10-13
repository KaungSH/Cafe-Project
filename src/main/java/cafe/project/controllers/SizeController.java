package cafe.project.controllers;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.SizeEntryDto;
import cafe.project.repositories.entities.Size;
import cafe.project.services.SizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sizes")
public class SizeController {

	private final SizeService sizeService;

	public SizeController(SizeService sizeService) {
		this.sizeService = sizeService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("sizes", sizeService.getAllSizes());
		return "sizes/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("sizeDto", new SizeEntryDto());
		return "sizes/create";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("sizeDto") SizeEntryDto sizeDto, BindingResult result, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (result.hasErrors()) {
			return "sizes/create";
		}
		sizeDto.setEmployee_id(ldto.getEmployee_id());
		sizeService.createSize(sizeDto);
		return "redirect:/admin/sizes";
	}

	@GetMapping("/edit/{size_id}")
	public String showEditForm(@PathVariable("size_id") String size_id, Model model) {
		Size size = sizeService.getSizeById(size_id);
		model.addAttribute("sizeDto", size);
		return "sizes/edit";
	}

	@PostMapping("/edit/{size_id}")
	public String update(@PathVariable("size_id") String size_id, @Valid @ModelAttribute("sizeDto") SizeEntryDto sizeDto, BindingResult result, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (result.hasFieldErrors("name") || result.hasFieldErrors("size_code")) {
			return "sizes/edit";
		}
		sizeDto.setEmployee_id(ldto.getEmployee_id());
		sizeDto.setSize_id(size_id);
		sizeService.updateSize(sizeDto);
		return "redirect:/admin/sizes";
	}

	@GetMapping("/delete/{size_id}")
	public String delete(@PathVariable("size_id") String size_id) {
		sizeService.deleteSize(size_id);
		return "redirect:/admin/sizes";

	}

	@GetMapping("/deleted")
	public String listDeleted(Model model) {
		model.addAttribute("sizes", sizeService.getDeletedSizes());
		return "sizes/deleted-list";
	}

	@GetMapping("/recover/{size_id}")
	public String recover(@PathVariable("size_id") String size_id) {
		sizeService.restoreSize(size_id);
		return "redirect:/admin/sizes/deleted";
	}

	@GetMapping("/hard-delete/{size_id}")
	public String showHardDeleteConfirm(@PathVariable("size_id") String size_id, Model model) {
		Size size = sizeService.getSizeById(size_id);
		model.addAttribute("size", size);
		return "sizes/hard-delete";
	}

	@PostMapping("/hard-delete/{size_id}")
	public String hardDelete(@PathVariable("size_id") String size_id) {
		sizeService.hardDeleteSize(size_id);
		return "redirect:/admin/sizes/deleted";
	}
}
