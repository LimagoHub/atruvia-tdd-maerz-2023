package de.atruvia.tiere;

import java.util.Objects;

public class Schwein {

    private String name;
    private int gewicht;

    public Schwein() {
        this("Nobody");
    }

    public Schwein(String name) {
        this.name = name;
        this.gewicht = 10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if("elsa".equalsIgnoreCase(name)) throw new IllegalArgumentException("Häh");
        this.name = name;
    }

    public int getGewicht() {
        return gewicht;
    }

    private void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public void fuettern() {
        setGewicht(getGewicht() + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schwein schwein = (Schwein) o;
        return gewicht == schwein.gewicht && Objects.equals(name, schwein.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gewicht);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Schwein{");
        sb.append("name='").append(name).append('\'');
        sb.append(", gewicht=").append(gewicht);
        sb.append('}');
        return sb.toString();
    }
}
