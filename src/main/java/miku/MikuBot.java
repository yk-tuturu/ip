package miku;

import miku.command.Command;
import miku.command.CommandHandler;
import miku.exceptions.FileIOError;
import miku.exceptions.IllegalSaveException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;
import java.util.Scanner;

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
        } catch (FileIOError e) {
            ui.Print(e.getMessage());
        }
    }

    public void ClearSave() {
        saveData.ClearSave();
    }

    // for running junit tests
    public String RunCommand(String input) {
        try {
            Command command = commandHandler.ParseCommand(input);
            Map<String, String> args = commandHandler.GetArgs(input);
            String output = command.Run(args, taskList, saveData, ui);

            return output;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void Run() {
        ui.PrintIntro();
        Scanner scanner = new Scanner(System.in);

        try {
            saveData.Init();
            saveData.PopulateTasks(taskList);
        } catch (FileIOError e) {
            ui.Print("Miku can't initialize the save file!");
        }

        while (true) {
            String input = scanner.nextLine();
            try {
                Command command = commandHandler.ParseCommand(input);
                Map<String, String> args = commandHandler.GetArgs(input);
                String output = command.Run(args, taskList, saveData, ui);

                ui.Print(output);

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
