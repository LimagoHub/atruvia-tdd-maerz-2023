package de.limago.webapp.service.model;

import lombok.*;

@Data
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class Schwein {


    private String id;

    private String name;

    private int gewicht;

    public void fuettern() {
        setGewicht(getGewicht() + 1);
    }

    public void taufen(String name) {
        setName(name);
    }
}
