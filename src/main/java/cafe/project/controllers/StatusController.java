package cafe.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cafe.project.repositories.StatusRepository;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin/statuses")
public class StatusController {

    private final StatusRepository statusRepository;

    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }


    @GetMapping
    public String showStatusList(@RequestParam(defaultValue = "branch") String type, Model model) {
        model.addAttribute("selectedType", type);
        model.addAttribute("statusList", statusRepository.findAll(type));
        return "YatiWinLatt/admin/statuses/list";
    }

  
    @GetMapping("/create")
    public String showCreateForm(@RequestParam(defaultValue = "branch") String type, Model model) {
        model.addAttribute("statusType", type);
        model.addAttribute("statusId", "");
        model.addAttribute("statusName", "");
        return "YatiWinLatt/admin/statuses/create";
    }

   
    @GetMapping("/edit")
    public String showEditForm(@RequestParam String type, @RequestParam String id, Model model) {
        Map<String, Object> status = statusRepository.findById(type, id);
        model.addAttribute("statusType", type);
        model.addAttribute("statusId", status.get("id"));
        model.addAttribute("statusName", status.get("name"));
        return "YatiWinLatt/admin/statuses/create";
    }

    
    @PostMapping("/save")
    public String saveStatus(@RequestParam String statusType,
                             @RequestParam(required = false) String id,
                             @RequestParam String name) {

        if (id == null || id.trim().isEmpty()) {
            String generatedId = UUID.randomUUID().toString();
            statusRepository.save(statusType, generatedId, name);
        } else {
            statusRepository.update(statusType, id, name);
        }

        return "redirect:/admin/statuses?type=" + statusType;
    }


   /* @GetMapping("/delete")
    public String showDeleteConfirm(@RequestParam String type, 
                                    @RequestParam String id, 
                                    @RequestParam(required = false) String name, 
                                    Model model) {
        model.addAttribute("statusType", type);
        model.addAttribute("statusId", id);
        model.addAttribute("statusName", name != null ? name : id);
        return "YatiWinLatt/admin/statuses/delete";
    }*/
    
    @GetMapping("/delete")
    public String deleteStatus(@RequestParam String type, @RequestParam String id) {
        statusRepository.delete(type, id);
        return "redirect:/admin/statuses?type=" + type;
    }

   
  /*  @PostMapping("/delete-confirm")
    public String executeDelete(@RequestParam String type, @RequestParam String id) {
        statusRepository.delete(type, id);
        return "redirect:/admin/statuses?type=" + type;
    }*/
}