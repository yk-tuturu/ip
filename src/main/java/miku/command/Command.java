package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.IllegalSaveException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

public abstract class Command {
    public String key;
    public String usage;

    public Command(String key, String usage) {
        this.key = key;
        this.usage = usage;
    }

    public String toString() {
        return usage;
    }

    public abstract void Run(String arg, TaskList tasks, SaveDataManager saveData, UIHandler ui)
            throws IllegalCommandException, IllegalSaveException;
}
