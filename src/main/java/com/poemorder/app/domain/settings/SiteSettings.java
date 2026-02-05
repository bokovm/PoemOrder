package com.poemorder.app.domain.settings;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "site_settings")
public class SiteSettings {

    @Id
    private Short id;

    @Column(name = "hero_title", nullable = false, length = 120)
    private String heroTitle;

    @Column(name = "hero_subtitle", nullable = false, length = 500)
    private String heroSubtitle;

    @Column(name = "telegram", length = 80)
    private String telegram;

    @Column(name = "phone", length = 40)
    private String phone;

    @Column(name = "social", length = 120)
    private String social;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public Short getId() { return id; }
    public void setId(Short id) { this.id = id; }

    public String getHeroTitle() { return heroTitle; }
    public void setHeroTitle(String heroTitle) { this.heroTitle = heroTitle; }

    public String getHeroSubtitle() { return heroSubtitle; }
    public void setHeroSubtitle(String heroSubtitle) { this.heroSubtitle = heroSubtitle; }

    public String getTelegram() { return telegram; }
    public void setTelegram(String telegram) { this.telegram = telegram; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSocial() { return social; }
    public void setSocial(String social) { this.social = social; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
