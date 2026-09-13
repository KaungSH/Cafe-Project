package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StarterController {
	
	@GetMapping("/")
	public String starter() {
		return "starter";
	}

}
