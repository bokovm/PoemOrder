package com.poemorder.app.web.public_;

import com.poemorder.app.dto.OrderForm;
import com.poemorder.app.dto.ReviewForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicPageController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("activePage", "home");

        // чтобы index.html не падал, если ты там используешь списки
        model.addAttribute("featuredPoems", List.of());
        model.addAttribute("featuredReviews", List.of());

        return "public/index";
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("activePage", "order");
        model.addAttribute("orderForm", new OrderForm());
        return "public/order";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("activePage", "reviews");
        model.addAttribute("reviewForm", new ReviewForm());

        // если шаблон выводит список отзывов
        model.addAttribute("reviews", List.of());

        return "public/reviews";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("activePage", "contacts");
        return "public/contacts";
    }
}
