package it.unibg.studenti.data;

public enum Sex {
    MALE("M"), FEMALE("F"), OTHER("-");

    private final String sexName;

    Sex(String sexName) {
        this.sexName = sexName;
    }

    public String getSexName() {
        return sexName;
    }
}