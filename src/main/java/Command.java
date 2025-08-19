import java.util.function.Consumer;

public class Command {
    public String key;
    public Consumer<String> function;
    public String usage;

    public Command(String key, Consumer<String> function, String usage) {
        this.key = key;
        this.function = function;
        this.usage = usage;
    }

    public String toString() {
        return usage;
    }

    public void Run(String arg) {
        function.accept(arg);
    }
}
