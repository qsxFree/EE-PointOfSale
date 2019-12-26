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
import main.java.data.entity.Transactions;
import main.java.misc.BackgroundProcesses;
import main.java.misc.SceneManipulator;

import java.net.URL;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class POSTransactionLogs implements Initializable {

    protected static SceneManipulator sceneManipulator = new SceneManipulator();
    protected static MiscInstances misc = new MiscInstances();
    protected static ObservableList<Transactions> logs = FXCollections.observableArrayList();
    private static ArrayList allItem = new ArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TextField tfTransac;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private ComboBox<String> cbUser;

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
    private JFXTreeTableView<Transactions> ttvLogTable;

    @FXML
    private TreeTableColumn<Transactions, Integer> chTransactionID;

    @FXML
    private TreeTableColumn<Transactions, String>  chType;

    @FXML
    private TreeTableColumn<Transactions, String>  chUser;

    @FXML
    private TreeTableColumn<Transactions, String>  chCustomer;

    @FXML
    private TreeTableColumn<Transactions, String>  chDate;

    @FXML
    private TreeTableColumn<Transactions, Integer> chSourceId;

    @FXML
    private TreeTableColumn<Transactions, JFXButton> chAction;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundProcesses.populateComboFromFile("load-sl-users", cbUser);
        BackgroundProcesses.populateComboFromFile("load-tl-type",cbType);
        queryAllItems();
        loadTable();
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        sceneManipulator.changeScene(rootPane, "POSDashboard", "POS | Dashboard");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchAllOnAction(ActionEvent event){
        logs.clear();
        logs.addAll(allItem);
        lblResult.setText(logs.size()+"");
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        logs.clear();
        logs.addAll(allItem);
        if (dpDate.getValue()!=null){
            searchFilter(e->
                    e.getDate()
                            .equals(dpDate.getValue().format(DateTimeFormatter.ofPattern(BackgroundProcesses.DATE_FORMAT))));
        }
        if (comboHasSelected(cbUser)){
            searchFilter(e->
                    e.getUser().split(" : ")[0]
                            .contains(
                                    cbUser.getSelectionModel().getSelectedItem().split(",")[0]));
        }
        if (comboHasSelected(cbType)){
            searchFilter(e->e.getType().equals(cbType.getSelectionModel().getSelectedItem()));
        }
        if (!tfTransac.getText().equals("")){
            searchFilter(e->
                    String.valueOf(e.getTransactionID()).contains(tfTransac.getText())
                    || e.getCustomer().contains(tfTransac.getText()));
        }
        lblResult.setText(logs.size()+"");
    }


    protected void queryAllItems() {
        logs.clear();
        String sql = "Select * from Transaction";
        misc.dbHandler.startConnection();
        ResultSet result = misc.dbHandler.execQuery(sql);
        try {
            Transactions log;
            while (result.next()) {
                log = new Transactions(result.getInt("transactionID")
                        ,result.getString("type")
                        ,result.getString("userID")
                        ,result.getString("customerID")
                        ,result.getInt("typeID")
                        ,result.getString("date")
                        ,result.getString("time")
                        ,new JFXButton("View"),misc.dbHandler);
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

    private void loadTable() {

        chTransactionID.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, Integer>("transactionID"));
        chSourceId.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, Integer>("typeID"));
        chType.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, String>("type"));
        chUser.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, String>("user"));
        chCustomer.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, String>("customer"));
        chDate.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, String>("date"));
        chAction.setCellValueFactory(new TreeItemPropertyValueFactory<Transactions, JFXButton>("btnView"));
        TreeItem<Transactions> dataItem = new RecursiveTreeItem<Transactions>(logs, RecursiveTreeObject::getChildren);
        ttvLogTable.setRoot(dataItem);
        ttvLogTable.setShowRoot(false);
    }

    private void createListButton(RecursiveTreeObject src) {
        Transactions log = (Transactions) src;
        log.getBtnView().setStyle("-fx-background-color:#1ca8d6;" +
                "-fx-border-radius: 5px;" +
                "-fx-border-color:#1994bd;" +
                "-fx-text-fill:#ffffff;");

        log.getBtnView().setOnAction(e -> {
            //log.getManipulator().openDialog((StackPane) BackgroundProcesses.getRoot(log.getBtnView()), "POSRestock");
        });
    }

    private boolean comboHasSelected(ComboBox box){
        return !(box.getSelectionModel().getSelectedIndex()== -1 || box.getSelectionModel().getSelectedItem().equals("---"));
    }

    private void searchFilter(Predicate<Transactions> d){
        ArrayList result = new ArrayList() ;
        logs.stream()
                .filter(d).forEach(e->
                result.add(e)
        );
        logs.clear();
        logs.addAll(result);
    }


}
