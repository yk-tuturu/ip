package miku.command;

import java.util.Map;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;

public class MikuCommand extends Command {
    public MikuCommand() {
        super("miku", "miku");
    }

    /**
     * Easter egg
     * @param arg
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui) {
        return "Miku miku oooeeeooooo~";
    }
}
