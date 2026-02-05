package com.poemorder.app.service;

import com.poemorder.app.domain.settings.SiteSettings;
import com.poemorder.app.repo.SiteSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class SiteSettingsService {

    public static final short SINGLETON_ID = 1;

    private final SiteSettingsRepository repo;

    public SiteSettingsService(SiteSettingsRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public SiteSettings get() {
        return repo.findById(SINGLETON_ID)
                .orElseThrow(() -> new IllegalStateException("site_settings row not found (id=1). Check Flyway migration."));
    }

    @Transactional
    public void update(SiteSettings updated) {
        SiteSettings s = get();

        s.setHeroTitle(updated.getHeroTitle());
        s.setHeroSubtitle(updated.getHeroSubtitle());

        s.setTelegram(emptyToNull(updated.getTelegram()));
        s.setPhone(emptyToNull(updated.getPhone()));
        s.setEmail(emptyToNull(updated.getEmail()));
        s.setSocial(emptyToNull(updated.getSocial()));

        // pricing / terms
        s.setPricingTitle(emptyToNull(updated.getPricingTitle()));
        s.setPricingPayment(emptyToNull(updated.getPricingPayment()));
        s.setPricingDelivery(emptyToNull(updated.getPricingDelivery()));
        s.setPricingRefund(emptyToNull(updated.getPricingRefund()));

        s.setUpdatedAt(Instant.now());
        repo.save(s);
    }

    private String emptyToNull(String v) {
        if (v == null) return null;
        String t = v.trim();
        return t.isEmpty() ? null : t;
    }
}
