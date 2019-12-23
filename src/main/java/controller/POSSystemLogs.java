package main.java.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.StackPane;
import main.java.MiscInstances;
import main.java.data.CacheWriter;
import main.java.data.entity.SystemLog;
import main.java.misc.BackgroundProcesses;
import main.java.misc.SceneManipulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class POSSystemLogs implements Initializable {

    protected static SceneManipulator sceneManipulator = new SceneManipulator();
    protected static MiscInstances misc = new MiscInstances();
    protected static ObservableList<SystemLog> logs = FXCollections.observableArrayList();
    private static ArrayList allItem = new ArrayList();
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXButton btnHome;
    @FXML
    private ComboBox<String> cbUser;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<String> cbAction;
    @FXML
    private DatePicker dpDate;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblAll;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXTreeTableView<SystemLog> ttvLogTable;
    @FXML
    private TreeTableColumn<SystemLog, Integer> chLogID;
    @FXML
    private TreeTableColumn<SystemLog, String> chType;
    @FXML
    private TreeTableColumn<SystemLog, String> chEvent;
    @FXML
    private TreeTableColumn<SystemLog, String> chDate;
    @FXML
    private TreeTableColumn<SystemLog, String> chUser;
    @FXML
    private TreeTableColumn<SystemLog, JFXButton> chAction;
    @FXML
    private TreeTableColumn<SystemLog, String> chReference;
    @FXML
    private TextField tfReferencedID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queryAllItems();
        loadTable();
        BackgroundProcesses.populateComboFromFile("load-sl-users", cbUser);
        BackgroundProcesses.populateComboFromFile("load-sl-type", cbType);
        BackgroundProcesses.populateComboFromFile("load-sl-all-action", cbAction);

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        sceneManipulator.changeScene(rootPane, "POSDashboard", "Dashboard");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        System.out.println(dpDate.getValue().format(DateTimeFormatter.ofPattern(BackgroundProcesses.DATE_FORMAT)));
    }


    private void loadTable() {

        chLogID.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, Integer>("logID"));
        chType.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, String>("type"));
        chEvent.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, String>("eventAction"));
        chDate.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, String>("date"));
        chUser.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, String>("userID"));
        chReference.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, String>("referencedID"));
        chAction.setCellValueFactory(new TreeItemPropertyValueFactory<SystemLog, JFXButton>("btnView"));
        TreeItem<SystemLog> dataItem = new RecursiveTreeItem<SystemLog>(logs, RecursiveTreeObject::getChildren);
        ttvLogTable.setRoot(dataItem);
        ttvLogTable.setShowRoot(false);
    }

    protected void queryAllItems() {
        logs.clear();
        String sql = "Select * from systemlogs";
        misc.dbHandler.startConnection();
        ResultSet result = misc.dbHandler.execQuery(sql);
        try {
            SystemLog log;
            while (result.next()) {
                log = new SystemLog(result.getInt("logID")
                        , result.getString("type")
                        , result.getString("eventAction")
                        , result.getString("date")
                        , result.getString("userID")
                        , result.getString("referencedID")
                        , new JFXButton("View"),misc.dbHandler);
                log.setManipulator(sceneManipulator);
                createListButton(log);
                logs.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            misc.dbHandler.closeConnection();
        }
        misc.dbHandler.closeConnection();
        allItem.clear();
        allItem.addAll(logs);
        lblAll.setText(String.valueOf(allItem.size()));
    }


    private void createListButton(RecursiveTreeObject src) {
        SystemLog log = (SystemLog) src;
        log.getBtnView().setStyle("-fx-background-color:#1ca8d6;" +
                "-fx-border-radius: 5px;" +
                "-fx-border-color:#1994bd;" +
                "-fx-text-fill:#ffffff;");

        log.getBtnView().setOnAction(e -> {
            //log.getManipulator().openDialog((StackPane) BackgroundProcesses.getRoot(log.getBtnView()), "POSRestock");
        });
    }



}
