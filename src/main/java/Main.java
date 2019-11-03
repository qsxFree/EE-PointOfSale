package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.misc.DirectoryHandler;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSCashier.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Point of Sales");
        stage.setFullScreen(true);
        //MiscInstances misc = new MiscInstances();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
