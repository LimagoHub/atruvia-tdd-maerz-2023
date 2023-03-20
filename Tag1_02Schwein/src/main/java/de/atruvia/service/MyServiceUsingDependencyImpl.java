package de.atruvia.service;

import de.atruvia.dependency.Dependency;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyServiceUsingDependencyImpl {

    private final Dependency dependency;
    public void abc() {

        dependency.foo("Mein String");
        dependency.foo("Mein anderer String");
    }

    public int bcd() {

        return dependency.bar() + dependency.bar() + 10;

    }

    public int cde(String message) {
        return dependency.foobar(message.toUpperCase()) * 100;
    }
}
