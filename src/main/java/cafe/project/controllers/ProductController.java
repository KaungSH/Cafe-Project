package cafe.project.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.IngredientTypesListModelFake;
import cafe.project.models.OrderDetailsDto;
import cafe.project.models.ProductTypeEntryModel;
import cafe.project.models.SearchKeyword;
import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.entities.Orders;
import cafe.project.repositories.entities.ProductsAndQuantities;
import cafe.project.services.CategoryService;
import cafe.project.services.OrderDetailsService;
import cafe.project.services.OrdersService;
import cafe.project.services.ProductService;
import cafe.project.services.ProductTypeService;
import cafe.project.services.SizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@Controller
public class ProductController {

	private final ProductService pservice;
	private final ProductTypeService ptservice;
	private final CategoryService cservice;
	private final SizeService sservice;
	private final OrdersService ordersService;
	private final OrderDetailsService orderDetailsService;

	public ProductController(ProductService pservice, ProductTypeService ptservice, CategoryService cservice,
			SizeService sservice, OrdersService ordersService, OrderDetailsService orderDetailsService) {

		this.pservice = pservice;
		this.ptservice = ptservice;
		this.cservice = cservice;
		this.sservice = sservice;
		this.ordersService = ordersService;
		this.orderDetailsService = orderDetailsService;
	}

	@GetMapping("/manager/products")
	public String productTypeList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("keywordmodel", new SearchKeyword());
		model.addAttribute("product_types", ptservice.findAll());
		return "products/list";
	}

	@PostMapping("/manager/products/search")
	public String searchProductTypeList(@ModelAttribute("keywordmodel") SearchKeyword key, Model model,
			HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("product_types", ptservice.searchByName(key.getKeyword()));
		return "products/list";
	}

	@GetMapping("/staff/products")
	public String productTypeCardList(Model model, HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		model.addAttribute("keywordmodel", new SearchKeyword());

		model.addAttribute("product_types", ptservice.findAllForOrder(ldto.getBranch_id()));

		model.addAttribute("product_quantities", new ProductsAndQuantities());

		return "products/card_list";
	}

	@PostMapping("/staff/products/search")
	public String searchProductTypeCardList(@ModelAttribute("keywordmodel") SearchKeyword key, Model model,
			HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		model.addAttribute("product_types", ptservice.searchByNameForOrder(key.getKeyword(), ldto.getBranch_id()));

		model.addAttribute("product_quantities", new ProductsAndQuantities());

		return "products/card_list";
	}

	@PostMapping("/staff/products")
	public String productTypeCardList(@ModelAttribute("product_quantities") ProductsAndQuantities productsNo,
			HttpSession session, Model model) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		if (productsNo.getProduct_ids() == null || productsNo.getProduct_ids().isEmpty()) {

			model.addAttribute("error", "Please select at least one product.");

			model.addAttribute("keywordmodel", new SearchKeyword());
			model.addAttribute("product_types", ptservice.findAllForOrder(ldto.getBranch_id()));
			model.addAttribute("product_quantities", productsNo);

			return "products/card_list";
		}

		List<OrderDetails> details = new ArrayList<>();

		for (int i = 0; i < productsNo.getProduct_ids().size(); i++) {

			String productId = productsNo.getProduct_ids().get(i);
			Integer quantity = productsNo.getQuantities().get(i);

			OrderDetails detail = new OrderDetails();

			detail.setProduct_id(productId);
			detail.setQuantity(quantity);

			details.add(detail);
		}

		OrderDetailsDto dto = new OrderDetailsDto(details);

		try {
			// Check stock without saving the order
			orderDetailsService.checkStock(dto, ldto.getBranch_id());

			// Send selected products to the Add Order page
			session.setAttribute("selectedOrderDetails", details);

			return "redirect:/order-with-details/add";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());
			model.addAttribute("keywordmodel", new SearchKeyword());
			model.addAttribute("product_types", ptservice.findAllForOrder(ldto.getBranch_id()));
			model.addAttribute("product_quantities", productsNo);

			return "products/card_list";
		}
	}

	@GetMapping("/add")
	public String add(Model model, HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		Orders order = new Orders();
		order.setEmployee_id(ldto.getEmployee_id());
		order.setBranch_id(ldto.getBranch_id());

		@SuppressWarnings("unchecked")
		List<OrderDetails> selectedDetails = (List<OrderDetails>) session.getAttribute("selectedOrderDetails");

		OrderDetailsDto dto;

		if (selectedDetails != null && !selectedDetails.isEmpty()) {
			dto = new OrderDetailsDto(selectedDetails);
		} else {
			dto = new OrderDetailsDto();
		}

		model.addAttribute("order", order);
		model.addAttribute("orderDetailsDto", dto);
		model.addAttribute("products", pservice.findAllForOrder(ldto.getBranch_id()));

		return "orderwithdetails/add";
	}

	@GetMapping("/manager/products/deleted")
	public String productTypeDeleted(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("keywordmodel", new SearchKeyword());
		model.addAttribute("product_types", ptservice.findDeletedAll());
		return "products/list_deleted";
	}

	@PostMapping("/manager/products/deleted/search")
	public String searchProductTypeDeleted(@ModelAttribute("keywordmodel") SearchKeyword key, Model model,
			HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("product_types", ptservice.searchDeletedByName(key.getKeyword()));
		return "products/list_deleted";
	}

	@GetMapping("/manager/products/add")
	public String productTypeAdd(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("product_type", new ProductTypeEntryModel());
		bindAvialableData(model);
		return "products/add";
	}

	@PostMapping("/manager/products/add")
	public String productTypeAdd(@ModelAttribute("product_type") ProductTypeEntryModel tentry,
			@RequestParam(value = "coverImgPart", required = false) Part imgPart, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		tentry.setCoverimgpath(saveImgFile(imgPart));
		tentry.setType_id(UUID.randomUUID().toString());
		tentry.setEmployee_id(ldto.getEmployee_id());

		ptservice.add(tentry);
		return "redirect:/manager/products";
	}

	@GetMapping("/manager/products/edit/{id}")
	public String productTypeEdit(@PathVariable String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ProductTypeEntryModel existingProduct = ptservice.findById(id);

		if (existingProduct != null) {
			if (!existingProduct.getType_id().equals(ldto.getBranch_id())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
			}
			model.addAttribute("product_type", ptservice.findById2(id));
			bindAvialableData(model);
			return "products/edit";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
	}

	@PostMapping("/manager/products/edit")
	public String productTypeEdit(@ModelAttribute("product_type") ProductTypeEntryModel tentry,
			@RequestParam(value = "coverImgPart", required = false) Part imgPart, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ProductTypeEntryModel existingProduct = ptservice.findById(tentry.getType_id());

		if (existingProduct != null && !existingProduct.getType_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
		}

		String file = saveImgFile(imgPart);
		if (file == null || file.isEmpty()) {
			ptservice.edit(tentry);
			return "redirect:/manager/products";
		}
		tentry.setCoverimgpath(file);
		ptservice.edit(tentry);
		return "redirect:/manager/products";
	}

	@GetMapping("/manager/products/delete/type/{id}")
	public String productTypeDelete(@PathVariable String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ProductTypeEntryModel existingProduct = ptservice.findById(id);

		if (existingProduct != null) {
			if (!existingProduct.getType_id().equals(ldto.getBranch_id())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
			}
			ptservice.delete(id);
			return "redirect:/manager/products";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
	}

	@GetMapping("/manager/products/delete_perm/type/{id}")
	public String productTypeDeletePerm(@PathVariable String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ProductTypeEntryModel existingProduct = ptservice.findById3(id);

		if (existingProduct != null) {
			if (!existingProduct.getType_id().equals(ldto.getBranch_id())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
			}
			ptservice.deletePerm(id);
			return "redirect:/manager/products/deleted";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
	}

	@GetMapping("/manager/products/delete/{id}")
	public String productTypeDeletePro(@PathVariable String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		var existingPro = pservice.findById(id);

		if (existingPro != null) {
			if (!existingPro.getType_id().equals(ldto.getBranch_id())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
			}
			pservice.delete(id);
			return "redirect:/manager/products";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product_type");
		}
	}

	@GetMapping("/manager/products/recover/type/{id}")
	public String productTypeRecover(@PathVariable String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ProductTypeEntryModel existingProduct = ptservice.findById3(id);

		if (existingProduct != null) {
			if (!existingProduct.getType_id().equals(ldto.getBranch_id())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
			}
			ptservice.recover(id);
			return "redirect:/manager/products/deleted";
		} else {
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

				return fileName;
			}
			return null;

		} catch (IOException e) {
			System.out.println("Saving Img Failed - " + e);
		}
		return null;
	}

}