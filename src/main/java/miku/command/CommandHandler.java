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
        commandMap.put("hello", new HelloCommand());
        commandMap.put("bye", new ByeCommand());
        commandMap.put("deadline", new DeadlineTaskCommand());
        commandMap.put("event", new EventTaskCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("mark", new MarkCommand());
        commandMap.put("unmark", new UnmarkCommand());
        commandMap.put("delete", new DeleteTaskCommand());
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

    public Map<String, String> GetArgs(String input) {
        String[] parts = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        String keyword = "";
        Map<String, String> result = new HashMap<>();

        if (parts.length == 1) {
            return result;
        }

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];

            if (part.charAt(0) == '/') {
                result.put(keyword.isEmpty() ? "default" : keyword, sb.toString().trim());
                sb = new StringBuilder();
                keyword = part.substring(1);
            } else {
                sb.append(part).append(" ");
            }
        }

        result.put(keyword.isEmpty() ? "default" : keyword, sb.toString().trim());
        return result;


    }



}
