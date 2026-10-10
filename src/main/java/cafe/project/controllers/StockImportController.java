package cafe.project.controllers;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.IngredientBatchItemDto;
import cafe.project.models.StockImportEntryDto;
import cafe.project.services.StockImportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/stock-imports")
public class StockImportController {

	private final StockImportService service;

	public StockImportController(StockImportService service) {
		this.service = service;
	}

	@GetMapping
	public String listActive(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("imports", service.getAllActiveImports());
		return "stock-import/list";
	}

	@GetMapping("/deleted")
	public String listDeleted(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("imports", service.getAllDeletedImports());
		return "stock-import/deleted-list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = new StockImportEntryDto();
		dto.setImported_at(LocalDateTime.now());
		dto.setItems(service.getAllIngredientTypes());

		model.addAttribute("stockImport", dto);
		model.addAttribute("suppliers", service.getAllSuppliers());
		return "stock-import/add";
	}

	@PostMapping("/add")
	public String saveStockImport(@ModelAttribute("stockImport") StockImportEntryDto dto, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (ldto == null) {
			return "redirect:/login";
		}
		String employee_id = ldto.getEmployee_id();
		String branch_id = ldto.getBranch_id();
		service.addStockImport(dto, employee_id, branch_id);
		return "redirect:/stock-imports";
	}
	/*
	 * @GetMapping("/edit/{id}") public String showEditForm(@PathVariable("id")
	 * String id, Model model) { StockImportEntryDto dto =
	 * service.getImportById(id); model.addAttribute("stockImport", dto);
	 * model.addAttribute("suppliers", service.getAllSuppliers()); return
	 * "stock-import/edit"; }
	 */

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = service.getImportById(id);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not Your Branch's Data!!!");
		}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if (dto.getImported_at() == null) {
			dto.setImported_at(LocalDateTime.now());
		}

		if (dto.getItems() != null) {
			for (IngredientBatchItemDto item : dto.getItems()) {
				item.setSelected(true); // Auto Checkbox

				if (item.getManufactured_date() == null) {
					item.setManufactured_date(LocalDate.now());
				}
				if (item.getExpire_date() == null) {
					item.setExpire_date(LocalDate.now().plusYears(1));
				}
			}
		}

		model.addAttribute("stockImport", dto);
		model.addAttribute("suppliers", service.getAllSuppliers());
		return "stock-import/edit";
	}

	@PostMapping("/edit")
	public String updateStockImport(@ModelAttribute("stockImport") StockImportEntryDto dto, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto existingDto = service.getImportById(dto.getImport_id());

		if (!existingDto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.editStockImport(dto);
		return "redirect:/stock-imports";
	}

	@GetMapping("/soft-delete/{id}")
	public String softDelete(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = service.getImportById(id);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.softDelete(id);
		return "redirect:/stock-imports";
	}

	@GetMapping("/recover/{id}")
	public String recover(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = service.getImportById(id);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.recover(id);
		return "redirect:/stock-imports/deleted";
	}

	@GetMapping("/hard-delete/{id}")
	public String showHardDeleteConfirmation(@PathVariable("id") String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = service.getImportById(id);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not Your Branch's Data!!!");
		}

		model.addAttribute("importId", id);
		return "stock-import/hard-deleted";
	}

	@PostMapping("/hard-delete")
	public String hardDelete(@RequestParam("import_id") String importId, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = service.getImportById(importId);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		service.hardDelete(importId);
		return "redirect:/stock-imports/deleted";
	}

	@GetMapping("/detail-batches/{id}")
	@ResponseBody
	public Object getBatchesDetail(@PathVariable("id") String id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		StockImportEntryDto dto = service.getImportById(id);

		if (!dto.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		return service.getBatchesByImportId(id);
	}

//    //for ingradient batch
//
//    @GetMapping("/stock-imports/{id}/add-ingredient-batch")
//    public String showAddBatchForm(@PathVariable("id") Long id, Model model) {
//        
//        StockImport stockImport = StockImportService.findById(id);
//        
//   
//        if (stockImport.getIngredientBatches() == null || stockImport.getIngredientBatches().isEmpty()) {
//            stockImport.getIngredientBatches().add(new IngredientBatch());
//        }
//        
//        model.addAttribute("stockImport", stockImport);
//        return "stock-import-batch-form"; // Form စာမျက်နှာ
//    }
//
//   
//    @PostMapping("/stock-imports/save-batch")
//    public String saveIngredientBatch(@ModelAttribute("stockImport") StockImport stockImport, 
//                                      RedirectAttributes redirectAttributes) {
//        
//       
//        stockImport.setStatus("Edited");
//        
//        stockImportService.save(stockImport);
//        
//        redirectAttributes.addFlashAttribute("successMessage", "Stock Import Batch saved successfully!");
//        return "redirect:/stock-imports";
//    }
}