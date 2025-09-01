package miku.storage;

import miku.exceptions.FileIOError;
import miku.exceptions.IllegalCommandException;
import miku.tasks.*;
import miku.util.DateTimeParser;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

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
     * @throws FileIOError if the directory or file cannot be created
     */
    public void Init() throws FileIOError {
        this.dir = new File("data");
        this.file = new File("data/save.txt");

        if (!dir.exists() || !dir.isDirectory()) {
            if (dir.mkdirs()) {
                System.out.println("Directory created: " + dir.getAbsolutePath());
            } else {
                throw new FileIOError();
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
                throw new FileIOError();
            }
        }

        isInit = true;
    }

    /**
     * Writes a single task to the save file.
     *
     * @param task the task to write
     * @throws FileIOError if writing fails
     */
    public void Write(Task task) throws FileIOError {
        String taskString = task.getSaveString();

        try (FileWriter writer = new FileWriter(file.getPath(), true)) {
            writer.write(taskString + "\n");
        } catch (IOException e) {
            throw new FileIOError("Something went wrong in file write :(");
        }
    }

    /**
     * Populates a task list by reading tasks from the save file.
     * <p>
     * Invalid lines are ignored, and the file is cleaned if illegal entries are found.
     * </p>
     *
     * @param list the task list to populate
     * @throws FileIOError if reading the file fails
     */
    public void PopulateTasks(TaskList list) throws FileIOError {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new FileIOError();
        }

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
                        task = new DeadlineTask(parts[2], DateTimeParser.parse(parts[3]), parts[1].charAt(0)=='1');
                        break;
                    case 'E':
                        task = new EventTask(parts[2], DateTimeParser.parse(parts[3]),
                                DateTimeParser.parse(parts[4]), parts[1].charAt(0)=='1');
                        break;
                    default:
                        needClean = true;
                }

                if (task != null) {
                    list.Add(task);
                }

            } catch (IndexOutOfBoundsException | IllegalCommandException e) {
                needClean = true;
            }
        }

        if (needClean) {
            WriteListToFile(list);
        }
    }

    /**
     * Overwrites the save file with all tasks from the given list.
     *
     * @param list the task list to save
     */
    public void WriteListToFile(TaskList list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.GetLength(); i++) {
            Task task = list.Get(i);
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
    public void ClearSave() {
        String path = file.getPath();
        try (FileWriter fw = new FileWriter(path, false)) { // false = overwrite
            // truncates the file
        } catch (IOException e) {
            return;
        }
    }
}