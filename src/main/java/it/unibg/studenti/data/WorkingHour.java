package it.unibg.studenti.data;

public enum WorkingHour {
    TRAINING("training"),
    LESSON("lesson"),
    TUTORING("tutoring"),
    OTHER("other");

    private String type;

    private WorkingHour(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return type;
    }
}
