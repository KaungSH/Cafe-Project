package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.models.PayMethodDto;
import cafe.project.repositories.entities.PayMethod;
import cafe.project.services.PayMethodService;

@Controller
@RequestMapping("/manager/paymethod")
public class PayMethodController {

	private final PayMethodService payMethodService;

	public PayMethodController(PayMethodService payMethodService) {
		this.payMethodService = payMethodService;
	}

	// ---------- List ----------

	@GetMapping
	public String listPayMethods(Model model) {
		model.addAttribute("payMethods", payMethodService.getAllPayMethodsWithRelations());
		return "YinminThiriSoe/manager/paymethod/list";
	}

	// ---------- Add ----------

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("payMethod", new PayMethod());
		return "YinminThiriSoe/manager/paymethod/add";
	}

	@PostMapping("/add")
	public String addPayMethod(@ModelAttribute("payMethod") PayMethod pm) {
		payMethodService.createPayMethod(pm);
		return "redirect:/manager/paymethod";
	}

	// ---------- Edit ----------

	@GetMapping("/edit/{method_id}")
	public String showEditForm(@PathVariable("method_id") String methodId, Model model) {
		PayMethod pm = payMethodService.getPayMethodById(methodId);
		model.addAttribute("payMethod", pm);
		return "YinminThiriSoe/manager/paymethod/edit";
	}

	@PostMapping("/edit/{method_id}")
	public String editPayMethod(@PathVariable("method_id") String methodId, @ModelAttribute("payMethod") PayMethod pm) {
		pm.setMethodId(methodId);
		payMethodService.updatePayMethod(pm);
		return "redirect:/manager/paymethod";
	}

	// ---------- View ----------

	@GetMapping("/view/{method_id}")
	public String viewPayMethod(@PathVariable("method_id") String methodId, Model model) {
		PayMethodDto dto = payMethodService.getPayMethodByIdWithRelations(methodId);
		model.addAttribute("payMethod", dto);
		return "YinminThiriSoe/manager/paymethod/view";
	}

	// ---------- Delete (Soft) ----------

	@GetMapping("/delete/{method_id}")
	public String deletePayMethod(@PathVariable("method_id") String methodId) {
		payMethodService.deletePayMethod(methodId);
		return "redirect:/manager/paymethod";
	}
}