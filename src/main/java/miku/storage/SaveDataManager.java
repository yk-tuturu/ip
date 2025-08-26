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

    public void Init() throws IOException {
        this.dir = new File("data");
        this.file = new File("data/save.txt");

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("save dir doesnt exist");
            if (dir.mkdirs()) {  // mkdirs() also creates parent directories
                System.out.println("Directory created: " + dir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory"); // should throw error here
            }
        }

        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            } else {
                System.out.println("Failed to create file");
            }
        }

        isInit = true;
    }

    public void Write(Task task) throws IllegalSaveException {
        String taskString = task.getSaveString();

        try (FileWriter writer = new FileWriter("save.txt", true)) {
            writer.write(taskString + "\n");
        } catch (IOException e) {
            throw new IllegalSaveException("Something went wrong in file write :(");
        }
    }



}
