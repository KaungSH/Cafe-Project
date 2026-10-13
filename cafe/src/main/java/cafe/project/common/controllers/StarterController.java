package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cafe.project.employeemanagement.models.LoginDto;
import jakarta.servlet.http.HttpSession;

@Controller
public class StarterController {
	
	@GetMapping("/")
	public String starter(HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if(ldto.getEmployee_role().equals("STAFF")) {
			return "products/card_list";
		}
		return "common/starter";
	}
	
	@GetMapping("/notfound")
	public String notfound() {
		return "common/notfound";
	}

}
