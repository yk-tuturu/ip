package miku.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import miku.exceptions.BadDateException;
import miku.exceptions.FileIoError;
import miku.exceptions.IllegalCommandException;
import miku.tasks.DeadlineTask;
import miku.tasks.EventTask;
import miku.tasks.FixedDurationTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.util.DateTimeParser;

/**
 * Manages saving and loading tasks from persistent storage.
 */
public class SaveDataManager {
    private File dir;
    private File file;
    private boolean isInit = false;

    /**
     * Initializes the save directory and file.
     * <p>
     * Creates the "data" directory and "save.txt" file if they do not exist.
     * </p>
     *
     * @throws FileIoError if the directory or file cannot be created
     */
    public void init() throws FileIoError {
        this.dir = new File("data");
        this.file = new File("data/save.txt");

        if (!dir.exists() || !dir.isDirectory()) {
            if (dir.mkdirs()) {
                System.out.println("Directory created: " + dir.getAbsolutePath());
            } else {
                throw new FileIoError();
            }
        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file");
                }
            } catch (IOException e) {
                throw new FileIoError();
            }
        }

        isInit = true;
    }

    /**
     * Writes a single task to the save file.
     *
     * @param task the task to write
     * @throws FileIoError if writing fails
     */
    public void write(Task task) throws FileIoError {
        String taskString = task.getSaveString();

        try (FileWriter writer = new FileWriter(file.getPath(), true)) {
            writer.write(taskString + "\n");
        } catch (IOException e) {
            throw new FileIoError("Something went wrong in file write :(");
        }
    }

    /**
     * Populates a task list by reading tasks from the save file.
     * <p>
     * Invalid lines are ignored, and the file is cleaned if illegal entries are found.
     * </p>
     *
     * @param list the task list to populate
     * @throws FileIoError if reading the file fails
     */
    public void populateTasks(TaskList list) throws FileIoError {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new FileIoError();
        }

        // if illegal characters found in save file, later will rewrite the save file with only the valid inputs
        boolean needClean = false;

        for (String line : lines) {
            try {
                String[] parts = line.split("\\|");

                char type = parts[0].charAt(0);
                Task task = null;
                switch (type) {
                case 'T':
                    task = new TodoTask(parts[2], parts[1].charAt(0) == '1');
                    break;
                case 'D':
                    task = new DeadlineTask(parts[2], DateTimeParser.parseIsoDate(parts[3]), parts[1].charAt(0) == '1');
                    break;
                case 'E':
                    task = new EventTask(parts[2], DateTimeParser.parseIsoDate(parts[3]),
                            DateTimeParser.parseIsoDate(parts[4]), parts[1].charAt(0) == '1');
                    break;
                case 'F':
                    task = new FixedDurationTask(parts[2], parts[3], parts[1].charAt(0) == '1');
                    break;
                default:
                    // if we reach the default case, something is wrong, and we need to clean the file
                    System.out.println("error parsing task");
                    needClean = true;
                }

                if (task != null) {
                    list.add(task);
                }

            } catch (IndexOutOfBoundsException | BadDateException e) {
                System.out.println(e);
                needClean = true;
            }
        }

        // cleans by basically rewriting the save file to only contain valid saved tasks
        if (needClean) {
            writeListToFile(list);
            System.out.println("save file has been cleaned");
        }
    }


    /**
     * Overwrites the save file with all tasks from the given list.
     *
     * @param list the task list to save
     */
    public void writeListToFile(TaskList list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.getLength(); i++) {
            Task task = list.get(i);
            sb.append(task.getSaveString()).append("\n");
        }

        try (FileWriter writer = new FileWriter(file.getPath(), false)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("File cleaning failed");
        }
    }
    /**
     * Clears the save file completely.
     */
    public void clearSave() {
        String path = file.getPath();
        try (FileWriter fw = new FileWriter(path, false)) { // false = overwrite
            // do nothing, this truncates the file
        } catch (IOException e) {
            return;
        }
    }
}
