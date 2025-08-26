package miku;

import miku.command.Command;
import miku.command.CommandHandler;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Scanner;
import java.lang.StringBuilder;

public class MikuBot {
    private TaskList taskList;
    private CommandHandler commandHandler;
    private SaveDataManager saveData;
    private UIHandler ui;

    private boolean terminate = false;

    public MikuBot() {
        taskList = new TaskList();
        commandHandler = new CommandHandler();
        saveData = new SaveDataManager();
        ui = new UIHandler();
    }

    public void ToTerminate() {
        terminate = true;
    }

    public void formattedOutput(String arg) {
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

    public void Run() {
        ui.PrintIntro();
        Scanner scanner = new Scanner(System.in);

        try {
            saveData.Init();
        } catch (Exception e) {
            ui.Print("Miku can't initialize the save file!");
            return;
        }

        while (true) {
            String input = scanner.nextLine();
            try {
                Command command = commandHandler.ParseCommand(input);
                String args = commandHandler.GetArgs(input);
                command.Run(args, taskList, saveData, ui);

            } catch (Exception e) {
                formattedOutput(e.getMessage());
            }

            if (terminate) {
                break;
            }
        }
    }



    public static void main(String[] args) {
        MikuBot bot = new MikuBot();
        bot.Run();
    }
}
