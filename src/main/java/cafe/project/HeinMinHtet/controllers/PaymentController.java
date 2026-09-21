package cafe.project.HeinMinHtet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.HeinMinHtet.repositories.entities.Payment;
import cafe.project.HeinMinHtet.services.PaymentService;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // LIST
    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "payments",
                paymentService.findAll()
        );

        return "HeinMinHtet/payments/index";
    }

    // ADD FORM
    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("payment", new Payment());

        return "HeinMinHtet/payments/add";
    }

    // SAVE
    @PostMapping("/add")
    public String save(
            @ModelAttribute("payment") Payment payment) {

        paymentService.save(payment);

        return "redirect:/payments";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable String id,
            Model model) {

        Payment existingPayment =
                paymentService.findById(id);

        if (existingPayment != null) {

            model.addAttribute(
                    "payment",
                    existingPayment
            );

            return "HeinMinHtet/payments/edit";
        }

        return "redirect:/payments";
    }

    // UPDATE
    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable String id,
            @ModelAttribute("payment") Payment payment) {

        paymentService.edit(id, payment);

        return "redirect:/payments";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {

        paymentService.delete(id);

        return "redirect:/payments";
    }
}