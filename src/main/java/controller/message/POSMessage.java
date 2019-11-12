package main.java.controller.message;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import main.java.misc.BackgroundProcesses;
import main.java.misc.DirectoryHandler;
import main.java.misc.SceneManipulator;

import java.io.*;
import java.util.Scanner;

public class POSMessage  {
    protected static SceneManipulator sceneManipulator = new SceneManipulator();

    public enum MessageType{
        ERROR,
        INFORM,
        CONFIRM
    }

    private static void writeToCache(String message,String title,String directory) {
        String cacheData = title+"\n"+message+"\n"+directory;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(BackgroundProcesses.getFile("etc\\cache-message.file")));
            writer.write(cacheData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getIconDirectory(MessageType messageType){
        String directory = "";
        switch (messageType){
            case ERROR:
                directory = DirectoryHandler.IMG+"pos-message-error.png";
                break;
            case INFORM:
                directory = DirectoryHandler.IMG+"pos-message-inform.png";
                break;
            case CONFIRM:
                directory = DirectoryHandler.IMG+"pos-message-confirm.png";
                break;
        }
        return directory;
    }

    public static void showMessage(StackPane parent,String message,String title,MessageType messageType){
        BackgroundProcesses.createCacheDir("etc\\cache-message.file");
        writeToCache(message,title,getIconDirectory(messageType));
        sceneManipulator.openDialog(parent,"POSSimpleMessage");

    }

    public static void showConfirmationMessage(StackPane parent,String message,String title,MessageType messageType){
        BackgroundProcesses.createCacheDir("etc\\cache-message.file");
        writeToCache(message,title,getIconDirectory(messageType));
        sceneManipulator.openDialog(parent,"POSConfirmation");
    }

    public static int getConfirmationStatus(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Scanner scan=null;
        try {
            scan = new Scanner(new FileInputStream(BackgroundProcesses.getFile("etc\\cache-confirm-return.file")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(scan.nextLine());
    }

}
