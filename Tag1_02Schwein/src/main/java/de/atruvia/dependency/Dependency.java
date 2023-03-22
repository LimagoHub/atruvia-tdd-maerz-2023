package de.atruvia.dependency;

import de.atruvia.service.Event;

public interface Dependency {
    void foo(String message);

    int bar();

    int foobar(String message);

    public void send(Event event);
}
