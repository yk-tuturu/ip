package miku;

import miku.command.Command;
import miku.command.CommandHandler;
import miku.exceptions.FileIOError;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;

import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the MikuBot application.
 * <p>
 * Manages tasks, user input, commands, and save data. Provides an interactive console interface.
 * </p>
 */
public class MikuBot {
    private TaskList taskList;
    private CommandHandler commandHandler;
    private SaveDataManager saveData;
    private UiHandler ui;

    private boolean toTerminate = false;

    /**
     * Constructs a new MikuBot instance and initializes save data.
     */
    public MikuBot() {
        taskList = new TaskList();
        commandHandler = new CommandHandler();
        saveData = new SaveDataManager();
        ui = new UiHandler();

        try {
            saveData.init();
        } catch (FileIOError e) {
            ui.print(e.getMessage());
        }
    }

    /**
     * Clears all saved tasks from the save file. Mostly for running tests.
     */
    public void clearSave() {
        saveData.clearSave();
    }

    /**
     * Executes a single command (used for unit testing or programmatic input).
     *
     * @param input the command string (required)
     * @return the output message or error message
     */
    public String runCommand(String input) {
        try {
            Command command = commandHandler.parseCommand(input);
            Map<String, String> args = commandHandler.getArgs(input);
            String output = command.run(args, taskList, saveData, ui);

            if (command.checkToExit()) {
                toTerminate = true;
            }

            return output;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean getToTerminate() {
        return toTerminate;
    }

    /**
     * Runs the interactive console loop.
     * <p>
     * Prints the intro, populates tasks from save file, and repeatedly reads user input
     * to execute commands until an exit command is received.
     * </p>
     */
    public void run() {

        ui.printIntro();
        Scanner scanner = new Scanner(System.in);

        try {
            saveData.init();
            saveData.populateTasks(taskList);
        } catch (FileIOError e) {
            ui.print("Miku can't initialize the save file!");
        }

        while (true) {
            String input = scanner.nextLine();
            try {
                Command command = commandHandler.parseCommand(input);
                Map<String, String> args = commandHandler.getArgs(input);
                String output = command.run(args, taskList, saveData, ui);

                ui.print(output);

                if (command.checkToExit()) {
                    break;
                }

            } catch (Exception e) {
                ui.print(e.getMessage());
            }
        }
    }
}
