public class Command {
    public String key;
    public CustomConsumer<String> function;
    public String usage;

    public Command(String key, CustomConsumer<String> function, String usage) {
        this.key = key;
        this.function = function;
        this.usage = usage;
    }

    public String toString() {
        return usage;
    }

    public void Run(String arg) throws Exception{
        function.accept(arg);
    }
}
