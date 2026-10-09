package cafe.project.employeemanagement.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.RandomString;
import cafe.project.employeemanagement.models.ChangePasswordDto;
import cafe.project.employeemanagement.models.ChangeProfileDto;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.employeemanagement.services.EmployeeManagementService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;

@Controller
public class EmployeeSelfChangeController {
	
	private final EmployeeManagementService emservice;
	
	public EmployeeSelfChangeController(EmployeeManagementService emservice) {
		this.emservice = emservice;
	}
	
	@GetMapping("/changepassword")
	public String changePassword(Model model) {
		model.addAttribute("changepassword", new ChangePasswordDto());
		return "common/user/changepassword";
	}
	
	@PostMapping("/changepassword")
	public String changePassword(@Valid @ModelAttribute("changepassword") ChangePasswordDto cpdto, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			return "common/user/changepassword";
		}
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		cpdto.setEmployee_id(ldto.getEmployee_id());
		int i = emservice.changePassword(cpdto);
		
		if (i == 0) {
			model.addAttribute("error", "Wrong Credentials.");
			return "common/user/changepassword";
		}
		return "redirect:/logout";
	}
	
	@GetMapping("/changeprofile")
	public String changeProfile(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("changeprofile", emservice.getChangeProfileDtoById(ldto.getEmployee_id()));
		return "common/user/changeprofile";
	}
	
	@PostMapping("/changeprofile")
	public String changeProfile(@Valid @ModelAttribute("changeprofile") ChangeProfileDto cfdto, BindingResult result, Model model, HttpSession session, @RequestParam(value ="coverImgPart", required = false) Part imgPart) {
		if (result.hasErrors()) {
			return "common/user/changeprofile";
		}
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		cfdto.setEmployee_id(ldto.getEmployee_id());
		
		String file = saveImgFile(imgPart);
		if (file == null) {
			int i = emservice.changeProfile(cfdto);
			if (i == 0) {
				model.addAttribute("error", "Wrong Credentials.");
				return "common/user/changepassword";
			}
			return "redirect:/";
		}

		cfdto.setPhotopath(file);
		int i = emservice.changeProfile(cfdto);
		if (i == 0) {
			model.addAttribute("error", "Wrong Credentials.");
			return "common/user/changepassword";
		}
		return "redirect:/";
	}
	
	private String saveImgFile(Part imgPart) {
	    try {
	        if (imgPart != null && imgPart.getSize() > 0) {
	            
	            String userHome = System.getProperty("user.home");
	            File uploadDir = new File(userHome, "Downloads" + File.separator + "Cafe Project Images");
	            
	            if (!uploadDir.exists()) {
	                uploadDir.mkdirs();
	            }
	            
	            String fileName = RandomString.generate() + " - " + imgPart.getSubmittedFileName();
	            File fileToSave = new File(uploadDir, fileName);
	            
	            imgPart.write(fileToSave.getAbsolutePath());
	            
	            // Return ONLY the file name so Thymeleaf can append it to /images/ correctly
	            return fileName; 
	        }
	        return null;
	        
	    } catch (IOException e) {
	        System.out.println("Saving Img Failed - " + e);
	    }
	    return null;
	}

}
