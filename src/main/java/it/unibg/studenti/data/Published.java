package it.unibg.studenti.data;

public enum Published {
    PUBLISHED(1), NOTPUBLISHED(0);

    private final int publishedValue;

    Published(int publishedValue) {
        this.publishedValue = publishedValue;
    }

    public int getPublishedValue() {
        return publishedValue;
    }
}
