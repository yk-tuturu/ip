package miku.storage;

import miku.exceptions.IllegalSaveException;
import miku.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class SaveDataManager {
    private File dir;
    private File file;

    private boolean isInit = false;

    public void Init() throws IllegalSaveException {
        this.dir = new File("data");
        this.file = new File("data/save.txt");

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("save dir doesnt exist");

            if (dir.mkdirs()) {  // mkdirs() also creates parent directories
                System.out.println("Directory created: " + dir.getAbsolutePath());
            } else {
                throw new IllegalSaveException();
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
                throw new IllegalSaveException();
            }
        }

        isInit = true;
    }

    public void Write(Task task) throws IllegalSaveException {
        String taskString = task.getSaveString();

        try (FileWriter writer = new FileWriter(file.getPath(), true)) {
            writer.write(taskString + "\n");
        } catch (IOException e) {
            throw new IllegalSaveException("Something went wrong in file write :(");
        }
    }



}
