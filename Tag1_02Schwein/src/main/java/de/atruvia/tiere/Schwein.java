package de.atruvia.tiere;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Objects;


@Data
public class Schwein {

    private String name;
    @Setter(AccessLevel.PRIVATE)
    private int gewicht;

    public Schwein() {
        this("Nobody");
    }

    public Schwein(String name) {
        setName(name);
        setGewicht(10);
    }



    public final void setName(String name) {
        if("elsa".equalsIgnoreCase(name)) throw new IllegalArgumentException("Häh");
        this.name = name;
    }



    public void fuettern() {
        setGewicht(getGewicht() + 1);
    }


}
