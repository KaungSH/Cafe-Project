package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(HttpSession session) {
		loginDto login = (loginDto) session.getAttribute("loggedInUser");
		if(login==null) 	return "home";
		
		Role role=login.getRole();
		if(role==Role.SUPERADMIN ||role==Role.ADMIN) {
			return "admin_home";
		}else {
			return "redirect:/branches";
		}
	
	}
	
}

