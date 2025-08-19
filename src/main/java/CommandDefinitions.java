import java.util.function.Consumer;
import java.util.Map;
import java.util.HashMap;

public class CommandDefinitions {
    private static void Hello(String arg) {
        MikuBot.formattedOutput("Hello sekai!");
    }

    private static void Bye(String arg) {
        MikuBot.formattedOutput("Bye! See you in the next sekai!");
        MikuBot.ToTerminate();
    }

    private static void Todo(String arg) {
        TodoTask task = new TodoTask(arg);
        MikuBot.AddTask(task);

        int len = MikuBot.GetTaskLength();

        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }

    private static void Deadline(String arg) {
        String[] parts = arg.split("\\s+");

        String value = "";
        String deadline = "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("/by")) {
                value = sb.toString().trim();
                sb = new StringBuilder();
            } else {
                sb.append(parts[i]).append(" ");
            }
        }

        deadline = sb.toString().trim();
        DeadlineTask task = new DeadlineTask(value, deadline);
        MikuBot.AddTask(task);

        int len = MikuBot.GetTaskLength();

        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }

    private static void Event(String arg) {
        String[] parts = arg.split("\\s+");

        String value = "";
        String from = "";
        String to = "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("/from")) {
                value = sb.toString().trim();
                sb = new StringBuilder();
            } else if (parts[i].equals("/to")) {
                from = sb.toString().trim();
                sb = new StringBuilder();
            } else {
                sb.append(parts[i]).append(" ");
            }
        }

        to = sb.toString().trim();

        EventTask task = new EventTask(value, from, to);
        MikuBot.AddTask(task);

        int len = MikuBot.GetTaskLength();

        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }

    private static void List(String arg) {
        // print list here
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MikuBot.GetTaskLength(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, MikuBot.GetTask(i)));
        }

        MikuBot.formattedOutput(sb.toString());
    }

    private static void Mark(String arg) {
        // not error handling here pains me but we'll save that for a future commit
        int index = Integer.parseInt(arg) - 1;
        MikuBot.MarkTask(index);

        String output = "Omedetou! You've finished a task:\n       " + MikuBot.GetTask(index);
        MikuBot.formattedOutput(output);
    }

    private static void Unmark(String arg) {
        int index = Integer.parseInt(arg) - 1;
        MikuBot.UnmarkTask(index);

        String output = "Aw man! You still haven't finished the task:\n       " + MikuBot.GetTask(index);
        MikuBot.formattedOutput(output);
    }

    static Map<String, Command> getCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("hello", new Command("hello", CommandDefinitions::Hello, "hello"));
        commands.put("bye", new Command("bye", CommandDefinitions::Bye, "bye"));
        commands.put("list", new Command("list", CommandDefinitions::List, "list"));
        commands.put("mark", new Command("mark", CommandDefinitions::Mark, "mark <index>"));
        commands.put("unmark", new Command("unmark", CommandDefinitions::Unmark, "unmark <index>"));
        commands.put("todo", new Command("todo", CommandDefinitions::Todo, "todo <task>"));
        commands.put("deadline", new Command("deadline", CommandDefinitions::Deadline, "deadline <task> /by <time>"));
        commands.put("event", new Command("event", CommandDefinitions::Event, "event <task> /from <time> /to <time>"));
        return commands;
    }
}
