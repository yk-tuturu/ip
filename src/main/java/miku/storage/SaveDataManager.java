package miku.storage;

import miku.exceptions.FileIOError;
import miku.exceptions.IllegalCommandException;
import miku.exceptions.IllegalSaveException;
import miku.tasks.*;
import miku.util.DateTimeParser;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

public class SaveDataManager {
    private File dir;
    private File file;

    private boolean isInit = false;

    public void Init() throws FileIOError {
        this.dir = new File("data");
        this.file = new File("data/save.txt");

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("save dir doesnt exist");

            if (dir.mkdirs()) {  // mkdirs() also creates parent directories
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

    public void Write(Task task) throws FileIOError {
        String taskString = task.getSaveString();

        try (FileWriter writer = new FileWriter(file.getPath(), true)) {
            writer.write(taskString + "\n");
        } catch (IOException e) {
            throw new FileIOError("Something went wrong in file write :(");
        }
    }

    public void PopulateTasks(TaskList list) throws FileIOError {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new FileIOError();
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
                        task = new DeadlineTask(parts[2], DateTimeParser.parse(parts[3]), parts[1].charAt(0)=='1');
                        break;
                    case 'E':
                        task = new EventTask(parts[2], DateTimeParser.parse(parts[3]),
                                DateTimeParser.parse(parts[4]), parts[1].charAt(0)=='1');
                        break;

                    default:
                        // if we reach the default case, something is wrong, and we need to clean the file
                        needClean = true;
                }

                if (task != null) {
                    list.Add(task);
                }

            } catch (IndexOutOfBoundsException e) {
                needClean = true;
            } catch (IllegalCommandException e) {
                needClean = true;
            }
        }

        // cleans by basically rewriting the save file to only contain valid saved tasks
        if (needClean) {
            WriteListToFile(list);
        }
    }

    public void WriteListToFile(TaskList list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.GetLength(); i++) {
            Task task = list.Get(i);
            String taskString = task.getSaveString();
            sb.append(taskString).append("\n");
        }

        try (FileWriter writer = new FileWriter(file.getPath(), false)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("File cleaning failed");
        }
    }



}
