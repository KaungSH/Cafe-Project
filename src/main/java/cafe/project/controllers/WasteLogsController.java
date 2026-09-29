package cafe.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cafe.project.models.WasteLogsEntryDto;
import cafe.project.services.WasteLogsService;



@Controller
@RequestMapping("/manager/WasteLogs")
public class WasteLogsController {

    private final WasteLogsService wasteLogsService;

    public WasteLogsController(WasteLogsService wasteLogsService) {
        this.wasteLogsService = wasteLogsService;
    }

  
    @GetMapping
    public String listWasteLogs(Model model) {
        model.addAttribute("wasteLogs", wasteLogsService.getAllWasteLogs());
        return "/WasteLogs/list";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("wasteLog", new WasteLogsEntryDto());
        return "/WasteLogs/create";
    }

    @PostMapping("/create")
    public String createWasteLog(@ModelAttribute("wasteLog") WasteLogsEntryDto dto) {
        wasteLogsService.createWasteLog(dto);
        return "redirect:/manager/WasteLogs";
    }

   
    @GetMapping("/edit/{waste_id}")
    public String showEditForm(@PathVariable("waste_id") String waste_id, Model model) {
        model.addAttribute("wasteLog", wasteLogsService.getWasteLogById(waste_id));
        return "/WasteLogs/edit";
    }

    
    @PostMapping("/edit")
    public String updateWasteLog(@ModelAttribute("wasteLog") WasteLogsEntryDto dto) {
        wasteLogsService.updateWasteLog(dto);
        return "redirect:/manager/WasteLogs";
    }

  
    @GetMapping("/delete/{waste_id}")
    public String softDeleteWasteLog(@PathVariable("waste_id") String waste_id) {
        wasteLogsService.softDeleteWasteLog(waste_id);
        return "redirect:/manager/WasteLogs";
    }

 
    @GetMapping("/trash")
    public String listDeletedWasteLogs(Model model) {
        model.addAttribute("deletedLogs", wasteLogsService.getDeletedWasteLogs());
        return "/WasteLogs/deleted-list";
    }

 
    @GetMapping("/recover/{waste_id}")
    public String recoverWasteLog(@PathVariable("waste_id") String waste_id) {
        wasteLogsService.recoverWasteLog(waste_id);
        return "redirect:/manager/WasteLogs/trash";
    }

   
    @GetMapping("/confirm-hard-delete/{waste_id}")
    public String showHardDeleteConfirmation(@PathVariable("waste_id") String waste_id, Model model) {
        model.addAttribute("wasteLog", wasteLogsService.getWasteLogByIdAny(waste_id));
        return "/WasteLogs/hard-delete";
    }

   
    @PostMapping("/hard-delete/{waste_id}")
    public String executeHardDelete(@PathVariable("waste_id") String waste_id) {
        wasteLogsService.hardDeleteWasteLog(waste_id);
        return "redirect:/manager/WasteLogs/trash";
    }
}