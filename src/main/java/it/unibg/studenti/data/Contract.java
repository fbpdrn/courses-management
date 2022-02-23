package it.unibg.studenti.data;

public enum Contract {
    INTERNAL("internal"),
    EXTERNAL("external"),
    OTHER("other");

    private String type;

    private Contract(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return type;
    }
}
