import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;

public class MikuBot {
    private static TaskList taskList = new TaskList();
    private static CommandHandler commandHandler = new CommandHandler();
    private static boolean terminate = false;

    public static void AddTask(Task task) {
        taskList.Add(task);
    }

    public static Task GetTask(int index) throws IllegalCommandException {
        if (index < 0 || index >= taskList.GetLength()) {
            throw new IllegalCommandException("Index provided is out of bounds :(");
        }

        return taskList.Get(index);
    }

    public static void MarkTask(int index) throws IllegalCommandException {
        if (index < 0 || index >= taskList.GetLength()) {
            throw new IllegalCommandException("Index provided is out of bounds :(");
        }

        taskList.MarkTask(index);
    }

    public static void UnmarkTask(int index) throws IllegalCommandException {
        if (index < 0 || index >= taskList.GetLength()) {
            throw new IllegalCommandException("Index provided is out of bounds :(");
        }

        taskList.UnmarkTask(index);
    }

    public static int GetTaskLength() {
        return taskList.GetLength();
    }

    public static void ToTerminate() {
        terminate = true;
    }

    public static void formattedOutput(String arg) {
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
            try {
                commandHandler.ParseCommand(input);
            } catch (Exception e) {
                formattedOutput(e.getMessage());
            }

            if (terminate) {
                break;
            }
        }
    }
}
