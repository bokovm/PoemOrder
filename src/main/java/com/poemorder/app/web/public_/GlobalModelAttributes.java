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

    /**
     * Новый единый источник контента/контактов для public-страниц.
     * В шаблонах используешь ${settings.heroTitle}, ${settings.telegram}, и т.д.
     */
    @ModelAttribute("settings")
    public SiteSettings settings() {
        return settingsService.get();
    }

    /**
     * Совместимость со старым layout (чтобы не править всё сразу).
     * Если ты потом полностью перейдёшь на settings — эти методы можно удалить.
     */
    @ModelAttribute("siteName")
    public String siteName(@ModelAttribute("settings") SiteSettings s) {
        // можно вернуть отдельное поле, но пока берём heroTitle
        return s.getHeroTitle();
    }

    @ModelAttribute("authorName")
    public String authorName() {
        // если хочешь тоже в БД — добавим поле в SiteSettings и миграцию
        return "Алексей Боков";
    }

    @ModelAttribute("footerNote")
    public String footerNote(@ModelAttribute("settings") SiteSettings s) {
        return s.getHeroTitle();
    }
}
