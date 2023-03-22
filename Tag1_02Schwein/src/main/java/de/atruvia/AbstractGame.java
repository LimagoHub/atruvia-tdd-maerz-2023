package de.atruvia;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractGame {

    private final Writer writer;

    public void gameloop() {
        while(! gameover()) {
            writer.write("Ich mache einen Zug");
        }
    }

    public abstract boolean gameover() ;
}
