package com.poemorder.app.web.public_;

import com.poemorder.app.domain.settings.SiteSettings;
import com.poemorder.app.service.SiteSettingsService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackages = "com.poemorder.app.web.public_")
public class GlobalModelAttributes {

    private final SiteSettingsService settingsService;

    public GlobalModelAttributes(SiteSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @ModelAttribute("settings")
    public SiteSettings settings() {
        return settingsService.get();
    }

    // совместимость со старым layout (если где-то ещё используется)
    @ModelAttribute("siteName")
    public String siteName(@ModelAttribute("settings") SiteSettings s) {
        return s.getHeroTitle();
    }

    @ModelAttribute("authorName")
    public String authorName() {
        return "Алексей Боков";
    }

    @ModelAttribute("footerNote")
    public String footerNote(@ModelAttribute("settings") SiteSettings s) {
        return s.getHeroTitle();
    }
}
