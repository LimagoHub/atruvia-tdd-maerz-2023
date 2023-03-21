package appender;

public class ConsolenAppender implements Appender{
    @Override
    public void write(String message) {
        System.out.println(message); // Java API Call -> Nix zu testen
    }
}
