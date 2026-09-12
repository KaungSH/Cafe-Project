package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

	@GetMapping("/")
	public String starter() {
		return "shop";
	}
}
