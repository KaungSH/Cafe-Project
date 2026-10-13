package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    // ================= MANAGER PROFILE =================
    @GetMapping("/manager/profile")
    public String showManagerProfile() {
        return "admin/profile"; // admin/profile.html ကိုပဲ သုံးပါသည်
    }

    @PostMapping("/manager/profile")
    public String saveManagerProfile(@RequestParam(value = "name", required = false) String name, 
                                     HttpSession session) {
        if (name != null && !name.trim().isEmpty()) {
            session.setAttribute("employeeName", name.trim());
        }
        return "redirect:/manager/profile";
    }

    // ================= ADMIN PROFILE =================
    @GetMapping("/admin/profile")
    public String showAdminProfile() {
        return "admin/profile";
    }

    @PostMapping("/admin/profile")
    public String saveAdminProfile(@RequestParam(value = "name", required = false) String name, 
                                   HttpSession session) {
        if (name != null && !name.trim().isEmpty()) {
            session.setAttribute("employeeName", name.trim());
        }
        return "redirect:/admin/profile";
    }
}