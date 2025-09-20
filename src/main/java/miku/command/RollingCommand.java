package miku.command;

import java.util.Map;

import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;

public class RollingCommand extends Command {
    public RollingCommand() {
        super("rolling", "rolling");
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
        return "Rolling girl wa itsumademo~\nTodokanai yume wo mite~";
    }
}
