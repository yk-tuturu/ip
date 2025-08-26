package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.IllegalSaveException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

public abstract class Command {
    public String key;
    public String usage;
    public boolean toExit;

    public Command(String key, String usage) {
        this.key = key;
        this.usage = usage;
    }

    public Command(String key, String usage, boolean toExit) {
        this.key = key;
        this.usage = usage;
        this.toExit = true;
    }

    public String toString() {
        return usage;
    }

    public abstract void Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui)
            throws IllegalCommandException, IllegalSaveException;
}
