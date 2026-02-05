package com.poemorder.app.web.public_;

import com.poemorder.app.dto.OrderForm;
import com.poemorder.app.domain.order.Order;
import com.poemorder.app.domain.order.OrderStatus;
import com.poemorder.app.repo.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order")
    public String orderPage(Model model) {
        model.addAttribute("activePage", "order");
        if (!model.containsAttribute("orderForm")) {
            model.addAttribute("orderForm", new OrderForm());
        }
        return "public/order";
    }

    @PostMapping("/order")
    public String submitOrder(
            @Valid @ModelAttribute("orderForm") OrderForm form,
            BindingResult binding,
            RedirectAttributes ra
    ) {
        // honeypot
        if (form.getWebsite() != null && !form.getWebsite().isBlank()) {
            // делаем вид, что всё ок (ботов не палим)
            ra.addFlashAttribute("success", "Заявка отправлена!");
            return "redirect:/order";
        }

        if (binding.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.orderForm", binding);
            ra.addFlashAttribute("orderForm", form);
            return "redirect:/order";
        }

        Order order = new Order();
        order.setName(form.getName().trim());
        order.setPhone(form.getPhone().trim());
        order.setSocial(form.getSocial() == null ? null : form.getSocial().trim());
        order.setDescription(form.getDescription().trim());
        order.setStatus(OrderStatus.NEW);

        orderRepository.save(order);

        ra.addFlashAttribute("success", "Заявка отправлена! Я свяжусь с тобой.");
        return "redirect:/order";
    }
}
