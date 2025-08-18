import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;

public class MikuBot {
    private static List<Task> taskList = new ArrayList<>();
    private static Map<String, Consumer<String>> functions = Map.of(
            "hello", MikuBot::Hello,
            "bye", MikuBot::Exit,
            "list", MikuBot::List,
            "mark", MikuBot::Mark,
            "unmark", MikuBot::Unmark,
            "todo", MikuBot::Todo,
            "deadline", MikuBot::Deadline,
            "event", MikuBot::Event
    );
    private static boolean terminate = false;

    private static void Hello(String arg) {
        formattedOutput("Hello sekai!");
    }

    private static void Exit(String arg) {
        formattedOutput("Bye! See you in the next sekai!");
        terminate = true;
    }

    private static void Todo(String arg) {
        TodoTask task = new TodoTask(arg);
        taskList.add(task);

        formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, taskList.size(), taskList.size() > 1 ? "s" : ""));
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
        taskList.add(task);

        formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, taskList.size(), taskList.size() > 1 ? "s" : ""));
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
        taskList.add(task);

        formattedOutput(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, taskList.size(), taskList.size() > 1 ? "s" : ""));
    }

    private static void List(String arg) {
        // print list here
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, taskList.get(i)));
        }

        formattedOutput(sb.toString());
    }

    private static void Mark(String arg) {
        // not error handling here pains me but we'll save that for a future commit
        int index = Integer.parseInt(arg) - 1;
        taskList.get(index).mark();

        String output = "Omedetou! You've finished a task: \n       " + taskList.get(index);
        formattedOutput(output);
    }

    private static void Unmark(String arg) {
        int index = Integer.parseInt(arg) - 1;
        taskList.get(index).unmark();

        String output = "Aw man! You still haven't finished the task: \n       " + taskList.get(index);
        formattedOutput(output);
    }


    private static void formattedOutput(String arg) {
        String indent = "     ";
        StringBuilder sb = new StringBuilder();

        sb.append(indent).append("---------------------------------------\n");

        String[] lines = arg.split("\\R");
        for (String line : lines) {
            sb.append(indent).append(line).append("\n");
        }

        sb.append(indent).append("---------------------------------------");

        System.out.println(sb);
    }

    public static void main(String[] args) {
        String logo = "-------------------------------------------------------------\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣤⣤⣦⣶⣴⣤⣤⣖⣒⣲⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣾⠿⠛⠋⠉⠁⠀⠀⠉⠛⠋⠁⠀⠀⠈⠙⠻⢷⣦⣀⠀⠀⠀⢀⡤⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣶⠟⣉⡤⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠙⠲⢤⣀⠀⠀⠉⠳⣷⣤⣴⡯⠚⠉⠊⠳⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⢟⡴⠚⠁⠀⢀⠀⠀⠀⠀⠀⠐⠦⣄⠀⠀⠀⠀⠀⠈⠳⣄⠀⠀⠘⢿⣏⢀⡤⠚⠛⠛⠻⣿⢦⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣖⣒⣒⣲⣤⣾⣾⡵⠋⠀⠀⠀⢀⡞⠀⠀⠀⠀⠀⠀⠀⠈⠳⡄⠀⠀⠀⠀⠀⠈⠳⡀⠀⣼⡷⣽⡆⠀⠀⠀⠀⣈⢳⣧⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⣠⠾⠟⢋⣁⣀⣀⡉⠻⣾⠟⠀⠀⠀⠀⠀⣼⠀⡴⠀⠀⠀⠀⠀⠀⠀⠀⠘⣆⠀⠀⠀⠀⠀⠀⠹⣄⠏⡇⠈⣷⡀⠀⠀⠀⠈⢾⣿⠄⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⢰⡃⢊⣼⣿⡿⠛⠉⢉⣿⠏⠀⠀⠀⠀⠀⢠⡇⢠⠇⠀⠀⠀⠀⠀⠀⢄⠀⠀⠈⡆⠀⠀⠀⠀⢄⠀⠘⣯⡇⠀⢸⣿⡄⠀⠀⠀⠀⢯⣧⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠈⠛⣻⣿⠋⠀⠠⠞⢩⠏⠀⠀⠀⢠⠀⢀⣾⡇⢸⠀⠀⠀⠀⠀⠀⠀⠘⣇⠀⠀⢸⡄⠀⠀⠀⠈⣆⠀⠹⣇⠀⠀⡟⣿⡀⠀⠀⠀⠘⣿⣤⢀⡤⢤⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⣠⢿⠃⠀⢀⠅⠀⡿⠀⣠⠀⠀⡾⠀⣾⡟⢻⣾⠀⠀⠀⠀⠀⠀⠀⠀⠘⣆⠀⠀⢧⠀⠀⠀⠀⠘⣆⠀⣻⠖⢺⡗⣿⣧⠀⠈⢦⡀⠈⠻⠯⢺⣽⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⣿⣟⠀⠀⢸⡆⣰⡇⠀⡏⠀⠀⡇⠀⡟⠀⠈⢿⡀⠀⠀⠀⠀⠀⠀⣆⠀⠘⣦⡀⠘⡆⠀⠀⠀⠀⢻⡀⠸⡇⠈⡇⣿⢻⠀⠀⠀⠙⢷⣶⣾⠿⠋⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⣿⡯⠀⠀⠀⣷⡿⡇⢰⡇⠀⠀⡇⣾⢿⣿⣶⣾⣧⡀⠀⠀⠀⠀⠀⢸⠀⠀⠹⡟⢦⣹⡀⠀⠀⠀⠸⡇⠀⣿⠀⣿⡏⣾⠀⠀⠀⠀⠈⢳⣵⡀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⣴⣿⠃⠀⠀⠀⠘⣿⣷⢰⣇⠀⠀⢣⡇⢸⣿⣿⣿⣿⡷⡄⢰⡀⠀⠀⢸⡇⠀⠀⠹⣾⣿⡇⠀⠀⠀⢀⡇⢀⡿⠀⣿⢡⡏⠀⠀⠀⠀⠀⠀⢻⡳⡄⠀⠀⠀\n" +
                "⠀⠀⠀⢀⣼⡽⠁⡄⠀⠀⠀⠀⢹⣿⣧⡹⣄⠀⠀⢷⠈⠿⣅⣉⡿⠁⣹⠾⢷⠀⢀⡞⢷⣦⣀⣀⣈⣻⣷⡎⠀⠀⣸⡁⣼⡟⣻⠇⣸⠁⠀⠀⠀⠀⠀⠀⠀⢷⢿⠀⠀⠀\n" +
                "⠀⠀⢠⣾⠟⠀⠀⠀⠀⠀⡄⠀⢨⡇⠈⣷⣼⣷⣤⣈⣳⣄⡈⠁⠀⠀⠀⠀⠸⠷⠋⠀⠈⠷⣬⡽⠏⢹⠟⠁⠀⣰⣿⣿⣿⣀⡿⢀⡟⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⡄⠀⠀\n" +
                "⠀⣰⣿⠃⠆⠀⠀⢀⣠⡞⣁⡴⢻⡇⠀⢹⣟⣯⠙⢿⣧⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠴⣏⣀⣤⢾⣿⣿⣽⠻⣼⣇⢸⠁⠀⠀⠀⠀⠀⡀⠀⠀⢠⣿⠀⠀⠀\n" +
                "⢠⣿⠇⡜⠀⠀⠀⠈⠉⠉⠉⡖⠠⡇⠀⢸⣿⠸⣦⠈⣯⡏⢓⣶⢤⣀⣀⡀⡒⠆⠀⠀⠀⠀⣀⣀⣤⣶⣿⣿⣼⣿⣿⡏⠀⠈⢻⣿⠀⠀⠀⠀⠀⠀⣷⠀⠀⣾⣿⠁⠀⠀\n" +
                "⢸⣼⠀⡇⠀⠀⠀⠀⠀⠀⢸⠇⠐⣧⠀⠘⣿⣴⠟⢀⣿⣿⠰⣾⣿⢿⣿⠉⢻⡿⠛⢙⡿⢿⣿⣅⣛⡿⠋⠙⠛⠻⣿⡆⢀⣴⠛⢯⠀⠀⠀⠀⠀⠀⢹⣇⠀⠸⢿⣟⡩⣱\n" +
                "⠘⣿⡌⡇⠀⠀⠀⠀⠀⠀⠈⣧⠀⠈⠓⣶⢛⣵⣶⣿⣟⣷⣾⡟⠁⡼⠩⣿⣿⣷⡔⢻⡇⠀⠙⣿⣿⠃⠀⠀⠀⠀⢿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠸⠞⢷⣤⣶⣖⡿⠃\n" +
                "⠀⠹⣿⣝⣆⠀⠀⠀⠀⠀⠀⠘⢦⡀⠀⠘⠛⠾⣿⣿⣿⡿⠋⠀⢀⣧⠀⣼⢻⡇⠀⢸⣷⠀⠀⠘⢟⣳⠀⠀⠀⠀⢸⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡏⡏⠁⠀⠀\n" +
                "⠀⠀⠙⢬⣳⡓⠀⢰⠀⠀⠀⠀⠀⠙⢦⣀⠀⠀⣸⡿⠏⠀⠀⠀⣸⡿⣷⣿⣸⣧⣴⣻⣿⣇⠀⠀⠈⢷⣷⡀⠀⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⡇⡇⠀⠀⠀\n" +
                "⠀⠀⠀⠀⢹⢳⠀⠸⣇⠀⠀⠀⠀⠀⠀⠉⠳⣦⣿⡀⠀⠐⢤⣴⠋⠀⠀⡄⠀⠉⠉⠉⠉⠸⡆⠀⠀⠀⠳⣽⣄⣴⡿⠃⠀⠀⠀⠀⠀⠀⡄⠀⠀⠀⠀⠠⣹⣷⠇⠀⠀⠀\n" +
                "⠀⠀⣀⣀⣼⡟⢀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⡉⣳⣤⡞⠁⠀⠀⢰⡇⠀⠀⠀⠀⠀⠀⢹⡀⡠⠀⠀⢹⣶⣋⣀⣠⡤⠀⠀⠀⠀⠀⣿⠀⠀⢀⠄⣳⣿⣿⠀⠀⠀⠀\n" +
                "⠀⠀⢗⡾⣁⣰⣀⣾⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠈⢉⣿⠛⢦⡀⠀⠀⡰⢷⡄⠀⠀⠀⠀⠀⢸⣇⣀⣤⠔⢻⣿⠒⢿⣻⠠⡀⠀⠀⠀⠀⠸⣧⣐⣬⢢⠹⣾⡿⠤⡄⠀⠀\n" +
                "⠀⠀⠀⠙⠛⠻⣷⣾⢿⠁⠀⠀⠀⠀⠀⠀⢀⠀⠀⣈⣿⡦⣀⡉⠳⠾⠁⠀⢳⣄⣠⣤⠴⠚⠋⠈⠉⠩⣷⣺⠇⠀⠘⣿⢦⡱⣀⠀⠀⠀⠀⠙⣿⣿⣢⠷⣜⣿⠴⠃⠀⠀\n" +
                "⠀⠀⠀⠀⢰⣚⡾⡻⢋⢀⠀⠀⠀⠀⠀⠀⣼⠃⣐⣶⢷⠛⠛⠯⣿⡗⠶⠲⢦⣬⡤⣤⠶⠤⢶⣖⣯⠖⠋⠀⠀⠀⠀⠀⠛⠯⣗⣻⣖⠢⡄⢀⠺⣿⠉⠉⠉⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠘⣮⡳⠵⢼⣾⡆⠀⠀⠀⠠⢾⠷⢾⡥⠚⠃⠀⠀⠀⠸⣿⣿⣿⣿⣿⡇⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣄⣽⣽⡗⡘⢦⣹⣼⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠈⠉⠚⠳⡿⣄⡀⠀⠀⢊⠼⡧⣧⣤⣀⡀⠀⠀⠀⣿⣿⣿⣿⣿⣻⠀⠀⠀⢹⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠳⣔⠢⣵⣬⡵⣚⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⢿⣮⣵⣮⣶⣝⣷⠭⠚⠃⠀⠀⠀⢸⣿⣿⣿⣿⣿⡆⠀⠀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠿⠿⠿⠋⠸⣇⣠⣴⡿⠀⠀⠀\n" +
                "--------------------------------------------------------------------------\n" +
                "Hello, I'm MikuBot\n" +
                "Welcome to our sekai!\n" +
                "--------------------------------------------------------------------------";

        System.out.println(logo);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argsPart = parts.length > 1 ? parts[1] : "";

            Consumer<String> action = functions.get(command);
            if (action != null) {
                action.accept(argsPart);
            } else {
                formattedOutput("Miku is lost! Miku doesn't know what to do! Miku can't see the end of the horizon!");
            }

            if (terminate) {
                break;
            }
        }
    }
}
