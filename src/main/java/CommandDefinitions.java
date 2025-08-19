import java.util.function.Consumer;
import java.util.Map;
import java.util.HashMap;

public class CommandDefinitions {
    private static Map<String, Command> commandMap = Map.of(
        "hello", new Command("hello", CommandDefinitions::Hello, "hello"),
        "bye", new Command("bye", CommandDefinitions::Bye, "bye"),
        "list", new Command("list", CommandDefinitions::List, "list"),
        "mark", new Command("mark", CommandDefinitions::Mark, "mark <index>"),
        "unmark", new Command("unmark", CommandDefinitions::Unmark, "unmark <index>"),
        "todo", new Command("todo", CommandDefinitions::Todo, "todo <task>"),
        "deadline", new Command("deadline", CommandDefinitions::Deadline, "deadline <task> /by <time>"),
        "event", new Command("event", CommandDefinitions::Event, "event <task> /from <time> /to <time>")
    );

    private static void Hello(String arg) {
        MikuBot.formattedOutput("Hello sekai!");
    }

    private static void Bye(String arg) {
        MikuBot.formattedOutput("Bye! See you in the next sekai!");
        MikuBot.ToTerminate();
    }

    private static void Todo(String arg) throws IllegalCommandException {
        if (arg.equals("")) {
            throw new IllegalCommandException("Miku cannot add an empty task!");
        }

        TodoTask task = new TodoTask(arg);
        MikuBot.AddTask(task);

        int len = MikuBot.GetTaskLength();

        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }

    private static void Deadline(String arg) throws IllegalCommandException {
        String[] parts = arg.split("\\s+");

        String value = "";
        String deadline = "";
        boolean deadlineProvided = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("/by")) {
                value = sb.toString().trim();
                sb = new StringBuilder();
                deadlineProvided = true;
            } else {
                sb.append(parts[i]).append(" ");
            }
        }

        deadline = sb.toString().trim();

        if (!deadlineProvided) {
            throw new IllegalCommandException("No deadline provided :(");
        }

        if (value.isEmpty() || deadline.isEmpty()) {
            throw new IllegalCommandException("Task description or deadline cannot be empty :(");
        }

        DeadlineTask task = new DeadlineTask(value, deadline);
        MikuBot.AddTask(task);

        int len = MikuBot.GetTaskLength();

        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }

    private static void Event(String arg) throws IllegalCommandException {
        String[] parts = arg.split("\\s+");

        String value = "";
        String from = "";
        String to = "";
        boolean fromProvided = false;
        boolean toProvided = false;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("/from")) {
                value = sb.toString().trim();
                sb = new StringBuilder();
                fromProvided = true;
            } else if (parts[i].equals("/to")) {
                from = sb.toString().trim();
                sb = new StringBuilder();
                toProvided = true;
            } else {
                sb.append(parts[i]).append(" ");
            }
        }

        to = sb.toString().trim();

        if (!fromProvided || !toProvided) {
            throw new IllegalCommandException("Time range not provided :(");
        }

        if (value.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalCommandException("Task description and time range cannot be empty :(");
        }

        EventTask task = new EventTask(value, from, to);
        MikuBot.AddTask(task);

        int len = MikuBot.GetTaskLength();

        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }

    private static void List(String arg) throws IllegalCommandException {
        // print list here
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MikuBot.GetTaskLength(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, MikuBot.GetTask(i)));
        }

        MikuBot.formattedOutput(sb.toString());
    }

    private static void Mark(String arg) throws IllegalCommandException {
        // not error handling here pains me but we'll save that for a future commit
        int index = Integer.parseInt(arg) - 1;
        MikuBot.MarkTask(index);

        String output = "Omedetou! You've finished a task:\n       " + MikuBot.GetTask(index);
        MikuBot.formattedOutput(output);
    }

    private static void Unmark(String arg) throws IllegalCommandException {
        int index = Integer.parseInt(arg) - 1;
        MikuBot.UnmarkTask(index);

        String output = "Aw man! You still haven't finished the task:\n       " + MikuBot.GetTask(index);
        MikuBot.formattedOutput(output);
    }

    static Map<String, Command> getCommands() {
        return commandMap;
    }

    static String getUsage(String key) {
        Command c = commandMap.get(key);

        // too lazy to throw an error i dgaf
        if (c == null) {
            return "";
        }

        return c.usage;
    }
}
