import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class CommandHandler {
    private final Map<String, Command> commandMap = CommandDefinitions.getCommands();

    public void ParseCommand(String input) throws UnrecognizedCommandException, IllegalCommandException {
        String[] parts = input.split("\\s+", 2);
        String key = parts[0].toLowerCase();
        String argsPart = parts.length > 1 ? parts[1] : "";

        Command action = commandMap.get(key);
        if (action != null) {
            try {
                action.Run(argsPart);
            } catch (Exception e) {
                throw new IllegalCommandException(e.getMessage(), action.usage);
            }
        } else {
            throw new UnrecognizedCommandException();
        }
    }



}
