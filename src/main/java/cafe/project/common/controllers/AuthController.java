package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

	public class AuthController {
		private final LoginService userService;
		
		public AuthController(LoginService loginService) {
			this.loginService=loginService;
		}
		@GetMapping("/login")
	    public String loginPage(Model model) {
			model.addAttribute("user",new LoginModel());
	        return "login/login";
	    }
		@PostMapping("/login")
	    public String login(@Valid@ModelAttribute("user")LoginModel user,
	    		BindingResult bindingResult,
	            HttpSession session,
	            HttpServletRequest request,
	            Model model) {
			if(bindingResult.hasErrors()) {						
				return "login/login";
			}
			
	        loginDto loginUser = LoginService.login(login);

	        if (loginUser != null) {
	        	session.invalidate(); // prevent session fixation
	        	HttpSession newSession = request.getSession(true);
	        	newSession.setAttribute("loggedInUser", loginUser);
	        	return "redirect:/";
	        }

	        model.addAttribute("error","Invalid email or password");
	        return "login/login";
	    }
		@GetMapping("/register")
	    public String registerPage(Model model) {
			model.addAttribute("user",new RegisterModel());
	        return "login/register";
	    }
		@PostMapping("/register")
	    public String register(@Valid @ModelAttribute("login")RegisterModel registerUser,
	    		BindingResult bindingResult,
	            Model model) {
			if(bindingResult.hasErrors()) {						
				return "login/register";
			}
			int isSuccess=LoginService.register(registerUser);
			if(isSuccess!=1) {
				model.addAttribute("error", "Email already exists");
	            return "login/register";
			}
	        return "redirect:/login";
	    }
		@GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/";
	    }
		
	}

}
