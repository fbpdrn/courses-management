package it.unibg.studenti.data;

public enum Color {
    ERROR("red"), WARNING("orange"), OK("green"), UNKNOWN("gray");

    private final String colorValue;

    Color(String colorValue) {
        this.colorValue = colorValue;
    }

    public String getColorValue() {
        return colorValue;
    }
}
