package com.poemorder.app.dto;

public class ReviewForm {
    private String name;
    private String text;
    private String telegramUsername;
    private boolean telegramPublic;

    // honeypot
    private String website;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTelegramUsername() { return telegramUsername; }
    public void setTelegramUsername(String telegramUsername) { this.telegramUsername = telegramUsername; }

    public boolean isTelegramPublic() { return telegramPublic; }
    public void setTelegramPublic(boolean telegramPublic) { this.telegramPublic = telegramPublic; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
