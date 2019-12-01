package main.java.data.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import main.java.misc.SceneManipulator;

public class Customer extends RecursiveTreeObject<Customer> {

    private Integer customerID;
    private String fullName,firstName,middleInitial,lastName,sex,address,phoneNumber,email;
    private JFXButton btnViewCard;
    private StackPane rootPane;
    private SceneManipulator manipulator;


    public Customer(int customerID, String firstName, String middleInitial, String lastName, String sex, String address, String phoneNumber, String email, JFXButton btnViewCard) {
        this.customerID = new Integer(customerID);
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        fullName = (firstName+" "+middleInitial+". "+lastName);
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        buildViewButton(btnViewCard);
        this.btnViewCard = btnViewCard;

    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        fullName = (firstName+" "+middleInitial+". "+lastName);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JFXButton getBtnViewCard() {
        return btnViewCard;
    }

    public void setBtnViewCard(JFXButton btnViewCard) {
        this.btnViewCard = btnViewCard;
    }

    public void setRootPane(StackPane rootPane) {
        this.rootPane = rootPane;
    }

    public void setManipulator(SceneManipulator manipulator) {
        this.manipulator = manipulator;
    }

    private void buildViewButton(JFXButton button){
        button.setStyle("-fx-background-color:#1ca8d6;" +
                "-fx-border-radius: 5px;" +
                "-fx-border-color:#1994bd;" +
                "-fx-text-fill:#ffffff;");

        button.setOnAction(e->{
            Node node = button;
          while (true){
              node = node.getParent();
              if (node.getId()!=null && node.getId().equals("rootPane"))break;
          }


          manipulator.openDialog((StackPane) node,"POSSelectedCardInfo");
        });
    }

}
