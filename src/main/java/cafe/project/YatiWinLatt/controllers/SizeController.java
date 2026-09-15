package cafe.project.YatiWinLatt.controllers;

import cafe.project.YatiWinLatt.models.SizeEntryDto;
import cafe.project.YatiWinLatt.repositories.entities.Size;
import cafe.project.YatiWinLatt.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sizes")
public class SizeController {

    private final SizeService sizeService;
    SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }
    @GetMapping
    public String list(Model model) {
        model.addAttribute("sizes", sizeService.getAllSizes());
        return "YatiWinLatt/sizes/list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("sizeDto", new SizeEntryDto());
        model.addAttribute("employees", sizeService.getAllEmployees());
        return "YatiWinLatt/sizes/create";
    }
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("sizeDto") SizeEntryDto sizeDto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", sizeService.getAllEmployees());
            return "YatiWinLatt/sizes/create";
        }
        sizeService.createSize(sizeDto);
        return "redirect:/sizes";
    }
    @GetMapping("/edit/{size_id}")
    public String showEditForm(@PathVariable("size_id") String size_id, Model model) {
    	Size size = sizeService.getSizeById(size_id);
        model.addAttribute("sizeDto", size);
        return "YatiWinLatt/sizes/edit";
    }
    @PostMapping("/edit/{size_id}")
    public String update(@PathVariable("size_id") String size_id,
                         @Valid @ModelAttribute("sizeDto") SizeEntryDto sizeDto,
                         BindingResult result) {
        if (result.hasFieldErrors("name") || result.hasFieldErrors("size_code")) {
            return "YatiWinLatt/sizes/edit";
        }
        sizeDto.setSize_id(size_id);
        sizeService.updateSize(sizeDto);
        return "redirect:/sizes";
    }
    @GetMapping("/delete/{size_id}")
    public String delete(@PathVariable("size_id") String size_id) {
        sizeService.deleteSize(size_id);
        return "redirect:/sizes";
    }
}