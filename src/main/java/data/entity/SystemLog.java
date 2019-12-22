package main.java.data.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import main.java.misc.SceneManipulator;

public class SystemLog extends RecursiveTreeObject<SystemLog> {
    private int logID;
    private String type, eventAction, date, userID, referencedID;
    private JFXButton btnView;
    private SceneManipulator manipulator;

    public SystemLog(int logID, String type, String eventAction, String date, String userID, String referencedID, JFXButton btnView) {
        this.logID = logID;
        this.type = type;
        this.eventAction = eventAction;
        this.date = date;
        this.userID = userID;
        this.referencedID = referencedID;
        this.btnView = btnView;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventAction() {
        return eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReferencedID() {
        return referencedID;
    }

    public void setReferencedID(String referencedID) {
        this.referencedID = referencedID;
    }

    public JFXButton getBtnView() {
        return btnView;
    }

    public void setBtnView(JFXButton btnView) {
        this.btnView = btnView;
    }

    public SceneManipulator getManipulator() {
        return manipulator;
    }

    public void setManipulator(SceneManipulator manipulator) {
        this.manipulator = manipulator;
    }


}
