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
            "bye", MikuBot::Exit,
            "list", MikuBot::List
    );
    private static boolean terminate = false;

    private static void Exit(String arg) {
        formattedOutput("Bye! See you in the next sekai!");
        terminate = true;
    }

    private static void Add(String arg) {
        Task task = new Task(arg);
        taskList.add(task);
        formattedOutput(String.format("Miku has added '%s' to the list!", arg));
    }

    private static void List(String arg) {
        // print list here
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(String.format("%d. %s\n", i, taskList.get(i)));
        }

        formattedOutput(sb.toString());
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
                "--------------------------------------------------------------------------\n";

        System.out.println(logo);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            Consumer<String> action = functions.get(input);
            if (action != null) {
                action.accept(input);
            } else {
                Add(input);
            }

            if (terminate) {
                break;
            }
        }
    }
}
