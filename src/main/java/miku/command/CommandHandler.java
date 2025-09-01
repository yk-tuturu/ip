package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.UnrecognizedCommandException;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that parses commands from a given input string. contains also the classes for all available commands
 */
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
        commandMap.put("find", new FindCommand());
    }

    /**
     * Parses a command from a given string, and returns the corresponding command class.
     * Only looks at the keyword, argument parsing is in the {@link #getArgs(String)} function
     * @param input the input string from the user
     * @return a Command object of the corresponding command, to be run by the main bot program
     * @throws UnrecognizedCommandException for wrong command keywords
     */

    public Command parseCommand(String input) throws UnrecognizedCommandException, IllegalCommandException {
        String[] parts = input.split("\\s+", 2);
        String key = parts[0].toLowerCase();
        String argsPart = parts.length > 1 ? parts[1] : "";

        Command command = commandMap.get(key);
        if (command == null) {
            throw new UnrecognizedCommandException("Miku doesn't recognize this command :(");
        }

        return command;
    }

    /**
     * Parses a command to get its arguments
     * @param input the input string
     * @return Map<String, String> where the key is the identifier of the argument, and the value is the value of the argument
     */
    public Map<String, String> getArgs(String input) {
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
