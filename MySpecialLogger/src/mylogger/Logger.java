package mylogger;

import appender.Appender;

public class Logger {

    private final Appender appender;

    public Logger(Appender appender) {
        this.appender = appender;
    }

    public void log(String message) {
        final String prefix = "Prefix: "; // Kompliziert ermittelt
        appender.write(prefix + message);
    }
}
