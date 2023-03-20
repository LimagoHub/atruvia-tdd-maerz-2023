package de.atruvia.dependency;

public class DependencyImpl implements Dependency {

    @Override
    public void foo(String message) {
        System.out.println(message);
    }

//    public void foo(String message) {
//
//    }

    @Override
    public int bar() {
        return 42;
    }

//    public int bar() {
//        return 0;
//    }

    @Override
    public int foobar(String message) {
        return message.length();
    }

//    public int foobar(String message) {
//        return 0;
//    }
}
