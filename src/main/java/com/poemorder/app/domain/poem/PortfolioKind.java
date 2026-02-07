package com.poemorder.app.domain.poem;

public enum PortfolioKind {
    POEM("Стих"),
    PROSE("Проза"),
    OTHER("Другое");

    private final String label;

    PortfolioKind(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
