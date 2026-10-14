package cafe.project.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.RandomString;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.repositories.entities.PayMethod;
import cafe.project.services.PayMethodService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@Controller
@RequestMapping("/admin/paymethod")
public class PayMethodController {

	private final PayMethodService payMethodService;

	public PayMethodController(PayMethodService payMethodService) {
		this.payMethodService = payMethodService;
	}

	// ---------- List ----------

	@GetMapping
	public String listPayMethods(Model model) {
		model.addAttribute("payMethods", payMethodService.getAllPayMethodsWithRelations());
		return "paymethod/list";
	}

	// ---------- Add ----------

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("payMethod", new PayMethod());
		return "paymethod/add";
	}

	@PostMapping("/add")
	public String addPayMethod(@ModelAttribute("payMethod") PayMethod pm, HttpSession session, @RequestParam(value ="coverImgPart", required = false) Part imgPart) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		pm.setEmployeeId(ldto.getEmployee_id());
		pm.setLogoPath(saveImgFile(imgPart));
		System.out.println("Controller - " + pm.getLogoPath());
		payMethodService.createPayMethod(pm);
		return "redirect:/admin/paymethod";
	}

	// ---------- Edit ----------

	@GetMapping("/edit/{method_id}")
	public String showEditForm(@PathVariable("method_id") String methodId, Model model) {
		PayMethod pm = payMethodService.getPayMethodById(methodId);
		model.addAttribute("payMethod", pm);
		return "paymethod/edit";
	}

	@PostMapping("/edit/{method_id}")
	public String editPayMethod(@PathVariable("method_id") String methodId, @ModelAttribute("payMethod") PayMethod pm, HttpSession session, @RequestParam(value ="coverImgPart", required = false) Part imgPart) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		pm.setEmployeeId(ldto.getEmployee_id());
		pm.setMethodId(methodId);
		String file = saveImgFile(imgPart);
		if (file == null) {
			payMethodService.updatePayMethod(pm);
			return "redirect:/admin/paymethod";
		}
		pm.setLogoPath(saveImgFile(imgPart));
		payMethodService.updatePayMethod(pm);
		return "redirect:/admin/paymethod";
	}

	// ---------- Change Is Active ----------

	@GetMapping("/isActive/{method_id}")
	public String chaangeIsActive(@PathVariable("method_id") String methodId, Model model) {
		payMethodService.changeIsActive(methodId);
		return "redirect:/admin/paymethod";
	}

	// ---------- Delete (Soft) ----------

	@GetMapping("/delete/{method_id}")
	public String deletePayMethod(@PathVariable("method_id") String methodId) {
		payMethodService.deletePayMethod(methodId);
		return "redirect:/admin/paymethod";
	}

	@GetMapping("/deleted")
	public String deletedList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (ldto != null) {
			model.addAttribute("employeeName", ldto.getEmployee_name());
		}
		model.addAttribute("payMethods", payMethodService.findDeleted());
		return "paymethod/deletedList";
	}

	@PostMapping("/restore")
	public String restore(@RequestParam String methodId) {
		payMethodService.restore(methodId);
		return "redirect:/admin/paymethod/deleted";
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