package cafe.project.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

import cafe.project.services.WasteLogsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.IngredientBatchDto;
import cafe.project.services.IngredientBatchService;
import cafe.project.models.WasteLogsEntryDto;
import cafe.project.models.WasteLogsEntryListDto;
import cafe.project.models.WasteLogsListDto;
import cafe.project.repositories.WasteReasonRepository;


@Controller
@RequestMapping("/manager-only/WasteLogs")
public class WasteLogsController {

	private final WasteLogsService wasteLogsService;
	private final WasteReasonRepository wasteReasonRepository;
	private final IngredientBatchService ingredientBatchService;

	public WasteLogsController(WasteLogsService wasteLogsService, WasteReasonRepository wasteReasonRepository, IngredientBatchService ingredientBatchService) {
		this.wasteLogsService = wasteLogsService;
		this.wasteReasonRepository = wasteReasonRepository;
		this.ingredientBatchService = ingredientBatchService;
	}

	@GetMapping
	public String listWasteLogs(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("wasteLogs", wasteLogsService.getAllWasteLogs(ldto.getBranch_id()));
		return "WasteLogs/list";
	}
	
	@GetMapping("/deleted")
	public String listDeletedWasteLogs(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("wasteLogs", wasteLogsService.getAllWasteLogsDeleted(ldto.getBranch_id()));
		return "WasteLogs/list-deleted";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		System.out.println("batch id = " + ingredientBatchService.getAllBatches().get(0).getBatch_id());
		model.addAttribute("wasteLog", new WasteLogsEntryDto());
		model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
		model.addAttribute("batches", ingredientBatchService.getAllBatches());
		return "WasteLogs/create";
	}

	@PostMapping("/create")
	public String createWasteLog(@Valid @ModelAttribute("wasteLog") WasteLogsEntryDto entryDto, BindingResult bindingResult, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (bindingResult.hasErrors()) {
			model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
			model.addAttribute("batches", ingredientBatchService.getAllBatches());
			return "WasteLogs/create";
		}
		entryDto.setBranch_id(ldto.getBranch_id());
		entryDto.setEmployee_id(ldto.getEmployee_id());
		deductFromIngredientBatches(entryDto.getBatch_id(), entryDto.getQuantity_lost());
		wasteLogsService.createWasteLog(entryDto);
		return "redirect:/manager-only/WasteLogs";
	}

	@GetMapping("/edit/{waste_id}")
	public String showEditForm(@PathVariable("waste_id") String waste_id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("wasteLog", wasteLogsService.getWasteLogsEntryById(waste_id, ldto.getBranch_id()));
		model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
		model.addAttribute("batches", ingredientBatchService.getAllBatches());
		return "WasteLogs/edit";
	}

	@PostMapping("/edit")
	public String updateWasteLog(@Valid @ModelAttribute("wasteLog") WasteLogsEntryDto entryDto, BindingResult bindingResult, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (bindingResult.hasErrors()) {
			model.addAttribute("reasons", wasteReasonRepository.findReasonsAll());
			model.addAttribute("batches", ingredientBatchService.getAllBatches());
			return "WasteLogs/edit";
		}
		BigDecimal actualQuantityLost = wasteLogsService.getWasteLogsEntryById(entryDto.getWaste_id(), ldto.getBranch_id()).getQuantity_lost().subtract(entryDto.getQuantity_lost());
		deductFromIngredientBatches(entryDto.getBatch_id(), actualQuantityLost);
		entryDto.setEmployee_id(ldto.getEmployee_id());
		wasteLogsService.updateWasteLog(entryDto);
		return "redirect:/manager-only/WasteLogs";
	}

	@GetMapping("/delete/{waste_id}")
	public String deleteWasteLog(@PathVariable("waste_id") String waste_id) {
		wasteLogsService.deleteWasteLog(waste_id);
		return "redirect:/manager-only/WasteLogs";
	}
	
	@GetMapping("/recover/{waste_id}")
	public String recoverWasteLog(@PathVariable("waste_id") String waste_id) {
		wasteLogsService.recoverWasteLog(waste_id);
		return "redirect:/manager-only/WasteLogs";
	}
	
	@GetMapping("/harddelete/{waste_id}")
	public String harddeleteWasteLog(@PathVariable("waste_id") String waste_id) {
		wasteLogsService.hardDeleteWasteLog(waste_id);
		return "redirect:/manager-only/WasteLogs";
	}
	
	@GetMapping("/timedSoftDelete")
	public String timedSoftDelete(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		int i = 0;
		for (WasteLogsListDto listDto : wasteLogsService.getAllWasteLogs(ldto.getBranch_id())){
			if (ChronoUnit.DAYS.between(listDto.getLogged_at(), LocalDateTime.now()) >= 30) {
				wasteLogsService.deleteWasteLog(listDto.getWaste_id());
				i++;
			}
		}
		if (i < 1) {
			model.addAttribute("error", "");
		}
		return "redirect:/manager-only/WasteLogs";
	}
	
	@GetMapping("/timedHardDelete")
	public String timedHardDelete(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		int i = 0;
		for (WasteLogsListDto listDto : wasteLogsService.getAllWasteLogsDeleted(ldto.getBranch_id())){
			if (ChronoUnit.DAYS.between(listDto.getLogged_at(), LocalDateTime.now()) >= 90) {
				wasteLogsService.hardDeleteWasteLog(listDto.getWaste_id());
				i++;
			}
		}
		if (i < 1) {
			model.addAttribute("error", "");
		}
		return "redirect:/manager-only/WasteLogs/deleted";
	}
	
	@GetMapping("/today-expired")
	public String showTodayExpired(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		List<WasteLogsEntryDto> list = new ArrayList<>();
		for(IngredientBatchDto batchDto : wasteLogsService.getTodayExpired()) {
			WasteLogsEntryDto dto = new WasteLogsEntryDto();
			dto.setBatch_id(batchDto.getBatch_id());
			dto.setWaste_reason_id(wasteReasonRepository.findReasonByName("Expired").getWaste_reason_id());
			dto.setQuantity_lost(batchDto.getRemaining_quantity());
			dto.setEmployee_id(ldto.getEmployee_id());
			dto.setFinancial_loss(autoCalFinanceLoss(batchDto.getRemaining_quantity(), batchDto.getUnit_cost()));
			list.add(dto);
		}
	    WasteLogsEntryListDto dtoList = new WasteLogsEntryListDto(list);
		
		model.addAttribute("wasteLogs", dtoList);
		return "WasteLogs/today-expired";
	}
	
	@PostMapping("/today-expired")
	public String createWasteLogsShowTodayExpired(@ModelAttribute("wasteLogs") WasteLogsEntryListDto dtoList, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		for(WasteLogsEntryDto entryDto : dtoList.getDtoList()) {
			entryDto.setBranch_id(ldto.getBranch_id());
			deductFromIngredientBatches(entryDto.getBatch_id(), entryDto.getQuantity_lost());
			wasteLogsService.createWasteLog(entryDto);
		}
		return "redirect:/manager-only/WasteLogs";
	}
	
	private BigDecimal autoCalFinanceLoss(BigDecimal amountLoss, BigDecimal unitCost) {
		if (amountLoss == null || unitCost == null || unitCost.compareTo(BigDecimal.ZERO) == 0) {
	        return BigDecimal.ZERO;
	    }
	    
	    // Match the database scale of 2 decimal places
	    return amountLoss.multiply(unitCost);
	}
	
	private int deductFromIngredientBatches(String batch_id, BigDecimal quantity_lost) {
		BigDecimal original_amount = ingredientBatchService.getBatchById(batch_id).getRemaining_quantity();
		BigDecimal newQuantity = original_amount.subtract(quantity_lost);
		return wasteLogsService.deductFromIngredientBatches(batch_id, newQuantity);
	}
}