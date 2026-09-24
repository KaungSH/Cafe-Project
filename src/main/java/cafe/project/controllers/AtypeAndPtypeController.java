package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.repositories.AtypeAndPtypeRepository;
import cafe.project.repositories.entities.AtypeAndPtype;

@Controller
@RequestMapping("/manager/types")
public class AtypeAndPtypeController {
	
	private AtypeAndPtypeRepository repo;
	
	public AtypeAndPtypeController(AtypeAndPtypeRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping("/audience")
	public String getAudienceTypes(Model model) {
		model.addAttribute("types", repo.findTypesAll(false));
		model.addAttribute("name", "audience");
		model.addAttribute("dname", "Audience");
		return "types/list";
	}
	
	@GetMapping("/promo")
	public String getPromoTypes(Model model) {
		model.addAttribute("types", repo.findTypesAll(true));
		model.addAttribute("name", "promo");
		model.addAttribute("dname", "Promo");
		return "types/list";
	}
	
	@GetMapping("/add/{name}")
	public String addTypes(@PathVariable String name, Model model) {
		model.addAttribute("type", new AtypeAndPtype());
		model.addAttribute("name", name);
		if(name.equals("promo")) {
			model.addAttribute("dname", "Promo");
		} else {
			model.addAttribute("dname", "Audience");
		}
		return "types/add";
	}
	
	@PostMapping("/add")
	public String addTypes(@ModelAttribute AtypeAndPtype type, @RequestParam String name,  Model model) {
		boolean isPromo = false;
		if (name.equals("promo")) {
			isPromo = true;
			repo.addType(type.getPromo_type_name(), isPromo);
			return "redirect:/manager/types/promo";
		}
		
		repo.addType(type.getAudience_type_name(), isPromo);
		return "redirect:/manager/types/audience";
	}
	
	@GetMapping("/promo/edit/{id}")
	public String editTypes(@PathVariable String id, Model model) {
		model.addAttribute("type", repo.findTypeById(id, true));
		model.addAttribute("name", "promo");
		model.addAttribute("dname", "Promo");
		return "types/edit";
	}
	
	@PostMapping("/promo/edit")
	public String editTypes(@ModelAttribute AtypeAndPtype type, Model model) {
		repo.editType(type.getPromo_type_id(), type.getPromo_type_name(), true);
		return "redirect:/manager/types/promo";
	}
	
	@GetMapping("/audience/edit/{id}")
	public String editTypes2(@PathVariable String id, Model model) {
		model.addAttribute("type", repo.findTypeById(id, false));
		model.addAttribute("name", "audience");
		model.addAttribute("dname", "Audience");
		return "types/edit";
	}
	
	@PostMapping("/audience/edit")
	public String editTypes2(@ModelAttribute AtypeAndPtype type, Model model) {
		repo.editType(type.getAudience_type_id(), type.getAudience_type_name(), false);
		return "redirect:/manager/types/audience";
	}
	
	@GetMapping("/promo/delete/{id}")
	public String deleteTypes(@PathVariable String id, Model model) {
		repo.deleteType(id, true);
		return "redirect:/manager/types/promo";
	}
	
	@GetMapping("/audience/delete/{id}")
	public String deleteTypes2(@PathVariable String id, Model model) {
		repo.deleteType(id, false);
		return "redirect:/manager/types/audience";
	}
}
