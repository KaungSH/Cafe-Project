package cafe.project.KaungSattHein.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.RandomString;
import cafe.project.HeinMinHtet.services.CategoryService;
import cafe.project.KaungSattHein.models.ProductTypeEntryModel;
import cafe.project.KaungSattHein.models.fakes.IngredientTypesListModelFake;
import cafe.project.KaungSattHein.repositories.entities.ProductsAndQuantities;
import cafe.project.KaungSattHein.services.ProductService;
import cafe.project.KaungSattHein.services.ProductTypeService;
import cafe.project.YatiWinLatt.service.SizeService;
import jakarta.servlet.http.Part;

@Controller
public class ProductController {
	
	private final ProductService pservice;
	private final ProductTypeService ptservice;
	private final CategoryService cservice;
	private final SizeService sservice;
	
	public ProductController (ProductService pservice, ProductTypeService ptservice, CategoryService cservice, SizeService sservice) {
		this.pservice = pservice;
		this.ptservice = ptservice;
		this.cservice = cservice;
		this.sservice = sservice;
	}
	
	@GetMapping("/manager/products")
	public String productTypeList(Model model) {
		model.addAttribute("product_types", ptservice.findAll());
		return "KaungSattHein/products/list";
	}
	
	@GetMapping("/staff/products")
	public String productTypeCardList(Model model) {
		model.addAttribute("product_types", ptservice.findAll());
		model.addAttribute("product_quantities", new ProductsAndQuantities());
		return "KaungSattHein/products/card_list";
	}
	
	@PostMapping("/staff/products")
	public String productTypeCardList(@ModelAttribute("product_quantities") ProductsAndQuantities productsNo, Model model) {
		System.out.println(productsNo.getQuantities().get(1));
		model.addAttribute("product_quantities", productsNo);
		return "KaungSattHein/products/card_list";
	}
	
	@GetMapping("/manager/products/deleted")
	public String productTypeDeleted(Model model) {
		model.addAttribute("product_types", ptservice.findDeletedAll());
		return "KaungSattHein/products/list_deleted";
	}
	
	@GetMapping("/manager/products/add")
	public String productTypeAdd(Model model) {
		model.addAttribute("product_type", new ProductTypeEntryModel());
		bindAvialableData(model);
		return "KaungSattHein/products/add";
	}
	
	@PostMapping("/manager/products/add")
	public String productTypeAdd(@ModelAttribute("product_type") ProductTypeEntryModel tentry,  @RequestParam(value ="coverImgPart", required = false) Part imgPart, Model model) {
		tentry.setCoverimgpath(saveImgFile(imgPart));
		tentry.setType_id(UUID.randomUUID().toString());
		//Fake Employee Id Saving later to be replaced with HttpSession Method
		tentry.setEmployee_id("1");
		
		ptservice.add(tentry);
		return "redirect:/manager/products";
	}
	
	@GetMapping("/manager/products/edit/{id}")
	public String productTypeEdit(@PathVariable String id, Model model) {
		if(ptservice.findById(id) != null) {
			model.addAttribute("product_type", ptservice.findById2(id));
			bindAvialableData(model);
			return "KaungSattHein/products/edit";
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
		
	}
	
	@PostMapping("/manager/products/edit")
	public String productTypeEdit(@ModelAttribute("product_type") ProductTypeEntryModel tentry, @RequestParam(value ="coverImgPart", required = false) Part imgPart, Model model) {
		String file = saveImgFile(imgPart);
		if (file == null || file.isEmpty()) {
			ptservice.edit(tentry);
			return "redirect:/manager/products";
		}
		tentry.setCoverimgpath(file);
		System.out.println(tentry.getProduct().get(1).isIs_active());
		ptservice.edit(tentry);
		return "redirect:/manager/products";
	}
	
	@GetMapping("/manager/products/delete/type/{id}")
	public String productTypeDelete(@PathVariable String id, Model model) {
		if(ptservice.findById(id) != null) {
			ptservice.delete(id);
			return "redirect:/manager/products";
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
		
	}
	
	@GetMapping("/manager/products/delete_perm/type/{id}")
	public String productTypeDeletePerm(@PathVariable String id, Model model) {
		if(ptservice.findById3(id) != null) {
			ptservice.deletePerm(id);
			return "redirect:/manager/products/deleted";
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
		
	}
	
	@GetMapping("/manager/products/delete/{id}")
	public String productTypeDeletePro(@PathVariable String id, Model model) {
		if(pservice.findById(id) != null) {
			pservice.delete(id);
			return "redirect:/manager/products";
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
		
	}
	
	@GetMapping("/manager/products/recover/type/{id}")
	public String productTypeRecover(@PathVariable String id, Model model) {
		if(ptservice.findById3(id) != null) {
			ptservice.recover(id);
			return "redirect:/manager/products/deleted";
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
		
	}
	
	private List<IngredientTypesListModelFake> ingredientgetAllFake() {
		List<IngredientTypesListModelFake> list = new ArrayList<IngredientTypesListModelFake>();
		list.add(new IngredientTypesListModelFake("1", "milk", "cow's milk", "gl"));
		list.add(new IngredientTypesListModelFake("2", "water", "distilled water", "gl"));
		list.add(new IngredientTypesListModelFake("3", "cake", "cake", "c"));
		list.add(new IngredientTypesListModelFake("4", "coffee beans", "java", "kg"));
		list.add(new IngredientTypesListModelFake("5", "sugar", "sugar", "lb"));
		list.add(new IngredientTypesListModelFake("6", "orange juice", "orangey", "l"));
		return list;
	}
	
	
	private void bindAvialableData(Model model) {
		model.addAttribute("categories", cservice.findAll());
		model.addAttribute("sizes", sservice.getAllSizes());
		model.addAttribute("ingredients", ingredientgetAllFake());
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
