package cafe.project.YatiWinLatt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cafe.project.YatiWinLatt.service.WasteLogsService;
import cafe.project.YatiWinLatt.service.WasteReasonService;
import cafe.project.YinminThiriSoe.services.EmployeeService;
import jakarta.validation.Valid;
import cafe.project.YatiWinLatt.models.WasteLogsEntryDto;
import cafe.project.YatiWinLatt.service.IngredientBatchServiceFake;

@Controller
@RequestMapping("/manager/WasteLogs")
public class WasteLogsController {

	private final WasteLogsService wasteLogsService;
	private final WasteReasonService wasteReasonService;
	private final IngredientBatchServiceFake ingredientBatchService;
	private final EmployeeService employeeService;

	public WasteLogsController(WasteLogsService wasteLogsService, WasteReasonService wasteReasonService,
			IngredientBatchServiceFake ingredientBatchService, EmployeeService employeeService) {

		this.wasteLogsService = wasteLogsService;
		this.wasteReasonService = wasteReasonService;
		this.ingredientBatchService = ingredientBatchService;
		this.employeeService = employeeService;
	}

	@GetMapping
	public String listWasteLogs(Model model) {
		model.addAttribute("wasteLogs", wasteLogsService.getAllWasteLogs());
		return "YatiWinLatt/manager/WasteLogs/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("wasteLog", new WasteLogsEntryDto());
		model.addAttribute("reasons", wasteReasonService.getAllWasteReasons());
		model.addAttribute("batches", ingredientBatchService.getAllBatches());
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "YatiWinLatt/manager/WasteLogs/create";
	}

	@PostMapping("/create")
	public String createWasteLog(@Valid @ModelAttribute("wasteLog") WasteLogsEntryDto entryDto,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("reasons", wasteReasonService.getAllWasteReasons());
			model.addAttribute("batches", ingredientBatchService.getAllBatches());
			model.addAttribute("employees", employeeService.getAllEmployees());
			return "YatiWinLatt/manager/WasteLogs/create";
		}

		wasteLogsService.createWasteLog(entryDto);
		return "redirect:/manager/waste-logs";
	}

	@GetMapping("/edit/{waste_id}")
	public String showEditForm(@PathVariable("waste_id") String waste_id, Model model) {
		model.addAttribute("wasteLog", wasteLogsService.getWasteLogsEntryById(waste_id));
		model.addAttribute("reasons", wasteReasonService.getAllWasteReasons());
		model.addAttribute("batches", ingredientBatchService.getAllBatches());
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "YatiWinLatt/manager/WasteLogs/edit";
	}

	@PostMapping("/edit")
	public String updateWasteLog(@Valid @ModelAttribute("wasteLog") WasteLogsEntryDto entryDto,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("reasons", wasteReasonService.getAllWasteReasons());
			model.addAttribute("batches", ingredientBatchService.getAllBatches());
			model.addAttribute("employees", employeeService.getAllEmployees());
			return "YatiWinLatt/manager/WasteLogs/edit";
		}

		wasteLogsService.updateWasteLog(entryDto);
		return "redirect:/manager/WasteLogs";
	}

	@GetMapping("/delete/{waste_id}")
	public String deleteWasteLog(@PathVariable("waste_id") String waste_id) {
		wasteLogsService.deleteWasteLog(waste_id);
		return "redirect:/manager/WasteLogs";
	}
}