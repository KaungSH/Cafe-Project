package cafe.project.YinminThiriSoe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.YinminThiriSoe.models.CartItem;
import cafe.project.YinminThiriSoe.models.CheckoutForm;

import java.util.Arrays;
import java.util.List;

@Controller
public class CheckoutController {

	// ၁။ Checkout Page ကို ပြသရန် (GET Method)
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model) {

		List<CartItem> cartItems = Arrays.asList(new CartItem("Coca Cola", 2, 1500),
				new CartItem("Fried Rice", 1, 5000));

		// UI ဖက်ကို ပို့ပေးမည့် Form Object
		CheckoutForm checkoutForm = new CheckoutForm();
		checkoutForm.setOrderDetailCode("ORD-987654321"); // ရှိပြီးသား Order Code ကို ထည့်ပေးလိုက်ပါ

		// Thymeleaf တွင် သုံးရန် Model ထဲသို့ ထည့်ခြင်း
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("checkoutForm", checkoutForm);

		return "checkout"; // checkout.html ကို ရည်ညွှန်းသည်
	}

	// ၂။ Checkout ခလုတ်နှိပ်ပါက Data လက်ခံရန် (POST Method)
	@PostMapping("/checkout/process")
	public String processCheckout(@ModelAttribute("checkoutForm") CheckoutForm form, Model model) {

		// UI ကနေ ပို့လိုက်တဲ့ Data တွေကို ဒီမှာ ရပါပြီ
		String code = form.getOrderDetailCode();
		String customer = form.getCustomerName();
		String remark = form.getRemark(); // ဤနေရာတွင် Remark ကို ဖမ်းယူရရှိပါပြီ

		// TODO: Database တွင် Order သိမ်းဆည်းသည့် လုပ်ငန်းစဉ် (Save to DB) ကို
		// ဤနေရာတွင် ရေးပါ
		System.out.println("Order Code: " + code);
		System.out.println("Customer: " + customer);
		System.out.println("Remark: " + remark);

		// အောင်မြင်ပါက Success Page သို့ သွားမည်
		return "success";
	}
}