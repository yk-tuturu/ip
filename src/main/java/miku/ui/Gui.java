package miku.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import miku.MikuBot;

/**
 * Class that handles loading the gui
 */
public class Gui extends Application {
    private MikuBot mikuBot = new MikuBot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMiku(mikuBot); // inject the Duke instance
            stage.setTitle("MikuBot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
