package com.poemorder.app.dto;

public class OrderForm {
    private String name;
    private String phone;
    private String social;
    private String description;

    // honeypot (должен быть в форме name="website")
    private String website;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSocial() { return social; }
    public void setSocial(String social) { this.social = social; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
