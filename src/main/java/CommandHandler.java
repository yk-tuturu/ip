import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class CommandHandler {
    private final Map<String, Command> commandMap = CommandDefinitions.getCommands();

    public void ParseCommand(String input) throws UnrecognizedCommandException {
        String[] parts = input.split("\\s+", 2);
        String key = parts[0].toLowerCase();
        String argsPart = parts.length > 1 ? parts[1] : "";

        Command action = commandMap.get(key);
        if (action != null) {
            action.Run(argsPart);
        } else {
            throw new UnrecognizedCommandException();
        }
    }



}
