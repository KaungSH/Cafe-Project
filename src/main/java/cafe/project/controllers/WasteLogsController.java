package cafe.project.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.models.IngredientBatchDto;
import cafe.project.models.WasteLogsEntryDto;
import cafe.project.models.WasteLogsEntryListDto;
import cafe.project.repositories.WasteReasonRepository;
import cafe.project.services.EmployeeService;
import cafe.project.services.IngredientBatchService;
import cafe.project.services.WasteLogsService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/manager/WasteLogs")
public class WasteLogsController {

	private final WasteLogsService wasteLogsService;
	private final WasteReasonRepository wasteReasonRepository;
	private final IngredientBatchService ingredientBatchService;
	private final EmployeeService employeeService;

	public WasteLogsController(WasteLogsService wasteLogsService, WasteReasonRepository wasteReasonRepository,
			IngredientBatchService ingredientBatchService, EmployeeService employeeService) {

		this.wasteLogsService = wasteLogsService;
		this.wasteReasonRepository = wasteReasonRepository;
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
		model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
		model.addAttribute("batches", ingredientBatchService.findAll());
		model.addAttribute("employees", employeeService.getAllEmployeeListDto());
		return "YatiWinLatt/manager/WasteLogs/create1";
	}
	
	@PostMapping("/finCal")
	public String showFinCal(@ModelAttribute("wasteLog") WasteLogsEntryDto entryDto, Model model) {
		entryDto.setFinancial_loss(autoCalFinanceLoss(entryDto.getQuantity_lost(), ingredientBatchService.findByBatchId(entryDto.getBatch_id()).getUnitCost()));
		model.addAttribute("wasteLog", entryDto);
		model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
		model.addAttribute("batches", ingredientBatchService.findAll());
		model.addAttribute("employees", employeeService.getAllEmployeeListDto());
		return "YatiWinLatt/manager/WasteLogs/create";
	}

	@PostMapping("/create")
	public String createWasteLog(@Valid @ModelAttribute("wasteLog") WasteLogsEntryDto entryDto,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
			model.addAttribute("batches", ingredientBatchService.findAll());
			model.addAttribute("employees", employeeService.getAllEmployeeListDto());
			return "YatiWinLatt/manager/WasteLogs/create";
		}

		wasteLogsService.createWasteLog(entryDto);
		return "redirect:/manager/WasteLogs";
	}

	@GetMapping("/edit/{waste_id}")
	public String showEditForm(@PathVariable("waste_id") String waste_id, Model model) {
		model.addAttribute("wasteLog", wasteLogsService.getWasteLogsEntryById(waste_id));
		model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
		model.addAttribute("batches", ingredientBatchService.findAll());
		model.addAttribute("employees", employeeService.getAllEmployeeListDto());
		return "YatiWinLatt/manager/WasteLogs/edit";
	}

	@PostMapping("/edit")
	public String updateWasteLog(@Valid @ModelAttribute("wasteLog") WasteLogsEntryDto entryDto,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
			model.addAttribute("batches", ingredientBatchService.findAll());
			model.addAttribute("employees", employeeService.getAllEmployeeListDto());
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
	
	@GetMapping("/today-expired")
	public String showTodayExpired(Model model) {
		List<WasteLogsEntryDto> list = new ArrayList<>();
		for(IngredientBatchDto batchDto : wasteLogsService.getTodayExpired()) {
			WasteLogsEntryDto dto = new WasteLogsEntryDto();
			dto.setBatch_id(batchDto.getBatchId());
			dto.setWaste_reason_id(wasteReasonRepository.findReasonByName("Expired").getWaste_reason_id());
			dto.setQuantity_lost(batchDto.getRemainingQuantity());
			//to be replaced with HTTPSession
			dto.setEmployee_id("1");
			dto.setFinancial_loss(autoCalFinanceLoss(batchDto.getRemainingQuantity(), batchDto.getUnitCost()));
			list.add(dto);
		}
	    WasteLogsEntryListDto dtoList = new WasteLogsEntryListDto(list);
		
		model.addAttribute("wasteLogs", dtoList);
		return "YatiWinLatt/manager/WasteLogs/today-expired";
	}
	
	@PostMapping("/today-expired")
	public String createWasteLogsShowTodayExpired(@ModelAttribute("wasteLogs")  WasteLogsEntryListDto dtoList,  Model model) {
		for(WasteLogsEntryDto entryDto : dtoList.getDtoList()) {
			wasteLogsService.createWasteLog(entryDto);
		}
		return "redirect:/manager/daily-registers";
	}
	
	private BigDecimal autoCalFinanceLoss(BigDecimal amountLoss, BigDecimal unitCost) {
		if (amountLoss == null || unitCost == null || unitCost.compareTo(BigDecimal.ZERO) == 0) {
	        return BigDecimal.ZERO;
	    }
	    
	    // Match the database scale of 2 decimal places
	    return amountLoss.multiply(unitCost);
	}
}