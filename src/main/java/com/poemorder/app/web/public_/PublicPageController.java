package com.poemorder.app.web.public_;

import com.poemorder.app.dto.OrderForm;
import com.poemorder.app.service.PortfolioService;
import com.poemorder.app.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicPageController {

    private final ReviewService reviewService;
    private final PortfolioService portfolioService;

    public PublicPageController(ReviewService reviewService, PortfolioService portfolioService) {
        this.reviewService = reviewService;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("featuredReviews", reviewService.findApprovedForHomepage(3));
        model.addAttribute("featuredWorks", portfolioService.publishedForHomepage(3));
        return "public/index";
    }

    /*@GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("reviews", reviewService.getApproved());
        return "public/reviews";
    }*/

    @GetMapping("/contacts")
    public String contacts() {
        return "public/contacts";
    }

    @GetMapping("/portfolio")
    public String portfolio(Model model) {
        model.addAttribute("items", portfolioService.publishedAll());
        return "public/portfolio";
    }

    /*@GetMapping("/order")
    public String orderPage(Model model) {
        model.addAttribute("activePage", "order");
        if (!model.containsAttribute("orderForm")) {
            model.addAttribute("orderForm", new OrderForm());
        }
        return "public/order";
    }*/
}
