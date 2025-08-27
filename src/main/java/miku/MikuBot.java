package miku;

import miku.command.Command;
import miku.command.CommandHandler;
import miku.exceptions.IllegalSaveException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;
import java.util.Scanner;
import java.lang.StringBuilder;

public class MikuBot {
    private TaskList taskList;
    private CommandHandler commandHandler;
    private SaveDataManager saveData;
    private UIHandler ui;

    public MikuBot() {
        taskList = new TaskList();
        commandHandler = new CommandHandler();
        saveData = new SaveDataManager();
        ui = new UIHandler();

        try {
            saveData.Init();
        } catch (IllegalSaveException e) {
            ui.Print(e.getMessage());
        }
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
                Map<String, String> args = commandHandler.GetArgs(input);
                command.Run(args, taskList, saveData, ui);

                if (command.toExit) {
                    break;
                }

            } catch (Exception e) {
                ui.Print(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MikuBot bot = new MikuBot();
        bot.Run();
    }
}
