package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.misc.DirectoryHandler;
import main.java.rfid.RFIDReaderInterface;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSLogin.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Point of Sales");
        stage.setMinHeight(679);
        stage.setMinWidth(1137);
        //stage.setFullScreen(true);
        stage.setOnCloseRequest(e->{
            System.exit(0);
        });
        stage.show();

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSSecondaryMain.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Customer View");
        stage.setMinHeight(679);
        stage.setMinWidth(1137);
        stage.setMaximized(true);
        stage.setOnCloseRequest(e->{
            System.exit(0);
        });
        stage.show();

        /* DISPLAYING UI TO THE SECOND SCREEN
        if ( Screen.getScreens().size()>1){
            Rectangle2D bounds = Screen.getScreens().get(1).getVisualBounds();
            stage.setX(bounds.getMinX() + 100);
            stage.setY(bounds.getMinY() + 100);
        }
        */
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static RFIDReaderInterface rfid = new RFIDReaderInterface();
}
