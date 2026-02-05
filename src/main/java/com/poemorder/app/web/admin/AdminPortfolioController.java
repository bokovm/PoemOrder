package com.poemorder.app.web.admin;

import com.poemorder.app.domain.poem.PortfolioItem;
import com.poemorder.app.domain.poem.PortfolioKind;
import com.poemorder.app.domain.poem.PoemStatus;
import com.poemorder.app.service.PortfolioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/portfolio")
public class AdminPortfolioController {

    private final PortfolioService portfolioService;

    public AdminPortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", portfolioService.adminAll());
        return "admin/portfolio-list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        PortfolioItem item = new PortfolioItem();
        item.setKind(PortfolioKind.POEM);
        item.setStatus(PoemStatus.DRAFT);
        model.addAttribute("item", item);
        model.addAttribute("kinds", PortfolioKind.values());
        model.addAttribute("statuses", PoemStatus.values());
        return "admin/portfolio-form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("item") PortfolioItem item) {
        portfolioService.save(item);
        return "redirect:/admin/portfolio";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", portfolioService.get(id));
        model.addAttribute("kinds", PortfolioKind.values());
        model.addAttribute("statuses", PoemStatus.values());
        return "admin/portfolio-form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute("item") PortfolioItem item) {
        PortfolioItem existing = portfolioService.get(id);
        existing.setTitle(item.getTitle());
        existing.setExcerpt(item.getExcerpt());
        existing.setBody(item.getBody());
        existing.setKind(item.getKind());
        existing.setStatus(item.getStatus());
        portfolioService.save(existing);
        return "redirect:/admin/portfolio";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        portfolioService.delete(id);
        return "redirect:/admin/portfolio";
    }
}
