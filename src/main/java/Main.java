package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.misc.BackgroundProcesses;
import main.java.misc.DirectoryHandler;
import main.java.rfid.RFIDReaderInterface;



public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        if (!BackgroundProcesses.getStoreName().equals("")){

            Parent root = FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSSecondaryMain.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle(BackgroundProcesses.getStoreName()+" | Customer View");
            stage.setMinHeight(679);
            stage.setMinWidth(1137);
            //stage.setMaximized(true);
            if ( Screen.getScreens().size()>1){
                Rectangle2D bounds = Screen.getScreens().get(1).getVisualBounds();
                stage.setX(bounds.getMinX() + 100);
                stage.setY(bounds.getMinY() + 100);
            }
            stage.setOnCloseRequest(e->{
                System.exit(0);
            });
            stage.setFullScreen(true);
            stage.show();


            stage = new Stage();
            root =  FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSLogin.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle(BackgroundProcesses.getStoreName()+" | Login");
            stage.setMinHeight(679);
            stage.setMinWidth(1137);
            stage.setMaximized(true);
            //stage.setFullScreen(true);
            stage.setOnCloseRequest(e->{
                System.exit(0);
            });
            stage.show();

        }else{
            Parent root = FXMLLoader.load(getClass().getResource("/"+ DirectoryHandler.FXML+"POSInitialSetup.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("POS | Initial Setup");
            stage.setResizable(false);
            stage.show();
        }



        //Main.rfid.gsmSendSMS("639955109622","Message Sample");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static RFIDReaderInterface rfid = new RFIDReaderInterface();
}
