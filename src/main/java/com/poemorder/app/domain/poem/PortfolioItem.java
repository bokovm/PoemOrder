package com.poemorder.app.domain.poem;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "portfolio_item")
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 400)
    private String excerpt;

    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PortfolioKind kind;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PoemStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public PortfolioKind getKind() { return kind; }
    public void setKind(PortfolioKind kind) { this.kind = kind; }

    public PoemStatus getStatus() { return status; }
    public void setStatus(PoemStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
