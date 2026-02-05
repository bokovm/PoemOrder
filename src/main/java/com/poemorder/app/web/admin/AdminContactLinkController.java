package com.poemorder.app.web.admin;

import com.poemorder.app.domain.settings.ContactLink;
import com.poemorder.app.service.ContactLinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/contacts")
public class AdminContactLinkController {

    private final ContactLinkService service;

    public AdminContactLinkController(ContactLinkService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", service.adminList());
        model.addAttribute("newItem", new ContactLink());
        return "admin/contacts-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ContactLink item) {
        service.save(item);
        return "redirect:/admin/contacts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/contacts";
    }
}
