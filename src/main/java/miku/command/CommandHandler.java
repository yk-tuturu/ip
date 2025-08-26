package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.UnrecognizedCommandException;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        commandMap.put("todo", new TodoTaskCommand());
        commandMap.put("list", new ListCommand());
    }

    public Command ParseCommand(String input) throws UnrecognizedCommandException, IllegalCommandException {
        String[] parts = input.split("\\s+", 2);
        String key = parts[0].toLowerCase();
        String argsPart = parts.length > 1 ? parts[1] : "";

        Command command = commandMap.get(key);
        if (command == null) {
            throw new UnrecognizedCommandException("Miku doesn't recognize this command :(");
        }

        return command;
    }

    // this will return a string later, but not today
    public String GetArgs(String input) {
        String[] parts = input.split("\\s+");
        String key = parts[0].toLowerCase();

        return parts.length > 1 ? parts[1] : "";


    }



}
