package de.atruvia.dependency;

import de.atruvia.service.Event;

public class DependencyImpl implements Dependency {

    @Override
    public void foo(String message) {
        System.out.println(message);
    }



    @Override
    public int bar() {
        return 42;
    }



    @Override
    public int foobar(String message) {
        return message.length();
    }

    public void send(Event event) {
        System.out.println(event);
    }
}
