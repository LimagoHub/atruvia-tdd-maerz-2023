package org.example;

import java.time.LocalDateTime;

public class DateDemo {

    private final LocalDateTime time;

    public DateDemo(LocalDateTime time) {
        this.time = time;
    }

    public void foo() {
        System.out.println(time.getSecond());
        System.out.println(time.getHour());
    }
}
