//package miku.command;
//
//import miku.*;
//import miku.exceptions.IllegalCommandException;
//import miku.tasks.*;
//
//import java.util.Map;
//
//public class CommandDefinitions {
//    private static Map<String, Command> commandMap = Map.ofEntries(
//            Map.entry("hello", new Command("hello", CommandDefinitions::Hello, "hello")),
//            Map.entry("bye", new Command("bye", CommandDefinitions::Bye, "bye")),
//            Map.entry("list", new Command("list", CommandDefinitions::List, "list")),
//            Map.entry("mark", new Command("mark", CommandDefinitions::Mark, "mark <index>")),
//            Map.entry("unmark", new Command("unmark", CommandDefinitions::Unmark, "unmark <index>")),
//            Map.entry("todo", new Command("todo", CommandDefinitions::Todo, "todo <task>")),
//            Map.entry("deadline", new Command("deadline", CommandDefinitions::Deadline, "deadline <task> /by <time>")),
//            Map.entry("event", new Command("event", CommandDefinitions::Event, "event <task> /from <time> /to <time>")),
//            Map.entry("delete", new Command("delete", CommandDefinitions::Delete, "delete <index>")),
//            Map.entry("help", new Command("help", CommandDefinitions::Help, "help")),
//
//            // easter egg commands
//            Map.entry("sekai", new Command("sekai", CommandDefinitions::Sekai, "sekai")),
//            Map.entry("miku", new Command("miku", CommandDefinitions::Miku, "miku")),
//            Map.entry("rolling", new Command("rolling", CommandDefinitions::Rolling, "rolling")),
//            Map.entry("mesmerizer", new Command("mesmerizer", CommandDefinitions::Mesmerize, "mesmerizer")),
//            Map.entry("ghost", new Command("ghost", CommandDefinitions::Ghost, "ghost")),
//            Map.entry("disappear", new Command("disappear", CommandDefinitions::Disappear, "disappear"))
//    );
//
//    private static void Hello(String arg) {
//        MikuBot.formattedOutput("Hello sekai!");
//    }
//
//    private static void Bye(String arg) {
//        MikuBot.formattedOutput("Bye! See you in the next sekai!");
//        MikuBot.ToTerminate();
//    }
//
//    private static void Help(String arg) {
//        String output = "hello: say hello to miku.MikuBot!\n" +
//                "bye: say goodbye to miku.MikuBot :(\n" +
//                "help: shows this command\n" +
//                "todo <task>: adds a todo task to your task list\n" +
//                "deadline <task> /by <time>: adds a deadline task to your task list\n" +
//                "event <task> /from <time> /to <time>: adds an event to your task list\n" +
//                "mark <index>: mark a task as done\n" +
//                "unmark <index>: mark a task as not done\n" +
//                "delete <index>: delete a task from the list\n" +
//                "list: shows all tasks\n";
//        MikuBot.formattedOutput(output);
//    }
//
//    private static void Todo(String arg) throws IllegalCommandException {
//        if (arg.equals("")) {
//            throw new IllegalCommandException("Miku cannot add an empty task!");
//        }
//
//        TodoTask task = new TodoTask(arg);
//        MikuBot.GetTaskList().Add(task);
//
//        int len = MikuBot.GetTaskList().GetLength();
//
//        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
//                task, len, len > 1 ? "s" : ""));
//    }
//
//    private static void Deadline(String arg) throws IllegalCommandException {
//        String[] parts = arg.split("\\s+");
//
//        String value = "";
//        String deadline = "";
//        boolean deadlineProvided = false;
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < parts.length; i++) {
//            if (parts[i].equals("/by")) {
//                value = sb.toString().trim();
//                sb = new StringBuilder();
//                deadlineProvided = true;
//            } else {
//                sb.append(parts[i]).append(" ");
//            }
//        }
//
//        deadline = sb.toString().trim();
//
//        if (!deadlineProvided) {
//            throw new IllegalCommandException("No deadline provided :(");
//        }
//
//        if (value.isEmpty() || deadline.isEmpty()) {
//            throw new IllegalCommandException("miku.tasks.Task description or deadline cannot be empty :(");
//        }
//
//        DeadlineTask task = new DeadlineTask(value, deadline);
//        TaskList tl = MikuBot.GetTaskList();
//        tl.Add(task);
//
//        int len = tl.GetLength();
//
//        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
//                task, len, len > 1 ? "s" : ""));
//    }
//
//    private static void Event(String arg) throws IllegalCommandException {
//        String[] parts = arg.split("\\s+");
//
//        String value = "";
//        String from = "";
//        String to = "";
//        boolean fromProvided = false;
//        boolean toProvided = false;
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < parts.length; i++) {
//            if (parts[i].equals("/from")) {
//                value = sb.toString().trim();
//                sb = new StringBuilder();
//                fromProvided = true;
//            } else if (parts[i].equals("/to")) {
//                from = sb.toString().trim();
//                sb = new StringBuilder();
//                toProvided = true;
//            } else {
//                sb.append(parts[i]).append(" ");
//            }
//        }
//
//        to = sb.toString().trim();
//
//        if (!fromProvided || !toProvided) {
//            throw new IllegalCommandException("Time range not provided :(");
//        }
//
//        if (value.isEmpty() || from.isEmpty() || to.isEmpty()) {
//            throw new IllegalCommandException("miku.tasks.Task description and time range cannot be empty :(");
//        }
//
//        EventTask task = new EventTask(value, from, to);
//        TaskList tl = MikuBot.GetTaskList();
//
//        tl.Add(task);
//
//        int len = tl.GetLength();
//
//        MikuBot.formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
//                task, len, len != 1 ? "s" : ""));
//    }
//
//    private static void List(String arg) throws IllegalCommandException {
//        // print list here
//        StringBuilder sb = new StringBuilder();
//        TaskList tl = MikuBot.GetTaskList();
//
//        for (int i = 0; i < tl.GetLength(); i++) {
//            sb.append(String.format("%d. %s\n", i + 1, tl.Get(i)));
//        }
//
//        MikuBot.formattedOutput(sb.toString());
//    }
//
//    private static void Mark(String arg) throws IllegalCommandException {
//        // not error handling here pains me but we'll save that for a future commit
//        int index;
//        try {
//            index = Integer.parseInt(arg) - 1;
//        } catch (Exception e) {
//            throw new IllegalCommandException("Parsing index number failed :(");
//        }
//
//        TaskList tl = MikuBot.GetTaskList();
//
//        if (index < 0 || index >= tl.GetLength()) {
//            throw new IllegalCommandException("Index provided is out of bounds :(");
//        }
//
//        tl.MarkTask(index);
//
//        String output = "Omedetou! You've finished a task:\n       " + tl.Get(index);
//        MikuBot.formattedOutput(output);
//    }
//
//    private static void Unmark(String arg) throws IllegalCommandException {
//        int index;
//        try {
//            index = Integer.parseInt(arg) - 1;
//        } catch (Exception e) {
//            throw new IllegalCommandException("Parsing index number failed :(");
//        }
//
//        TaskList tl = MikuBot.GetTaskList();
//
//        if (index < 0 || index >= tl.GetLength()) {
//            throw new IllegalCommandException("Index provided is out of bounds :(");
//        }
//
//        tl.UnmarkTask(index);
//
//        String output = "Aw man! You still haven't finished the task:\n       " + tl.Get(index);
//        MikuBot.formattedOutput(output);
//    }
//
//    private static void Delete(String arg) throws IllegalCommandException{
//        int index;
//        try {
//            index = Integer.parseInt(arg) - 1;
//        } catch (Exception e) {
//            throw new IllegalCommandException("Parsing index number failed :(");
//        }
//
//        TaskList tl = MikuBot.GetTaskList();
//
//        if (index < 0 || index >= tl.GetLength()) {
//            throw new IllegalCommandException("Index provided is out of bounds :(");
//        }
//
//        Task task = tl.Delete(index);
//        int len = tl.GetLength();
//        String output = String.format("Miku has deleted the task:\n    %s\nNow you have %d task%s remaining",
//                task, len, len != 1 ? "s" : "");
//        MikuBot.formattedOutput(output);
//
//    }
//
//    static Map<String, Command> getCommands() {
//        return commandMap;
//    }
//
//    static String getUsage(String key) {
//        Command c = commandMap.get(key);
//
//        // too lazy to throw an error i dgaf
//        if (c == null) {
//            return "";
//        }
//
//        return c.usage;
//    }
//
//    // waow easter eggs
//    private static void Sekai(String arg) {
//        MikuBot.formattedOutput("Sekai de ichiban no hime sama~");
//    }
//
//    private static void Miku(String arg) {
//        MikuBot.formattedOutput("Miku miku ooooeeeoooo~");
//    }
//
//    private static void Rolling(String arg) {
//        MikuBot.formattedOutput("Mou ikkai~ mou ikkai~");
//    }
//
//    private static void Mesmerize(String arg) {
//        MikuBot.formattedOutput("FOCUS！→ ✷ ← FOCUS!");
//    }
//
//    private static void Ghost(String arg) {
//        MikuBot.formattedOutput("Mayday! Boku wo wakatte mo~ Mou dakishimenakutte ii nda yo~");
//    }
//
//    private static void Disappear(String arg) {
//        MikuBot.formattedOutput("--A critical error has occurred--\n--404 not found--");
//        MikuBot.ToTerminate();
//    }
//}
