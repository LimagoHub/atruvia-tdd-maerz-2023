package de.limago.webapp.repository.entities;

import java.util.Objects;


public final class TinyPerson {

    private final String id;
    private final String nachname;

    public TinyPerson(String id, String nachname) {
        this.id = id;
        this.nachname = nachname;
    }

    public String getId() {
        return id;
    }

    public String getNachname() {
        return nachname;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TinyPerson{");
        sb.append("id=").append(id);
        sb.append(", nachname='").append(nachname).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TinyPerson that = (TinyPerson) o;
        return id.equals(that.id) && nachname.equals(that.nachname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nachname);
    }
}
