package com.pao.project.model;

import java.util.Objects;

public final class ISBN {
    private final String cod;

    public ISBN(String cod) {
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return cod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;
        ISBN isbn = (ISBN) o;
        return Objects.equals(cod, isbn.cod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod);
    }
}