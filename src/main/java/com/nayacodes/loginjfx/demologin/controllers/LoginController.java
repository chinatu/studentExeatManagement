package com.nayacodes.loginjfx.demologin.controllers;

import com.nayacodes.loginjfx.demologin.DatabaseConnection;
import com.nayacodes.loginjfx.demologin.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField matricNoTxtField;

    @FXML
    private PasswordField passwordPwdField;

    @FXML
    public String userFirstnameLastname;

    private Stage stage;

    // Method to set the stage object
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to close the scene
    public void closeScene() {
        stage.close();
    }

    public void cancelButtonOnAction(ActionEvent a){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void loginButtonOnAction(ActionEvent e){
        if(matricNoTxtField.getText().isBlank() == false && passwordPwdField.getText().isBlank() == false){
//            loginMessageLabel.setText("You tried to Login!");
            validateLogin();
        }else{
            loginMessageLabel.setText("Please enter Matric no and password");
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = " Select count(1) from pocbase.useraccounts where matric_no = '"+ matricNoTxtField.getText() +"' AND password = '"+ passwordPwdField.getText() +"'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if (queryResult.getInt(1)==1) {
                    //Retrieve user's firstname and Lastname from DB
                    String retrievals =  "select firstname, lastname from pocbase.useraccounts where matric_no = ?";
                    PreparedStatement stmt = connectDB.prepareStatement(retrievals);
                    stmt.setString(1, matricNoTxtField.getText());

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        String firstName = rs.getString("firstname");
                        String lastName = rs.getString("lastname");

                        // Update the UI with the retrieved data
                        userFirstnameLastname = firstName + " " + lastName;
                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard.fxml"));
                    fxmlLoader.setController(new DashboardController(matricNoTxtField.getText()));
                    Scene scene = new Scene(fxmlLoader.load(), 800, 700);

                    //Set the new stage to be displayed
                    Stage dashboardStage = new Stage();
                    dashboardStage.initStyle(StageStyle.DECORATED);
                    dashboardStage.setTitle("BEMS - Babcock Exeat Management System");
                    dashboardStage.setScene(scene);
                    dashboardStage.show();

                    //close Login Stage
                    closeScene();

                    // Get the controller for the new screen and pass data to it
                    DashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.setStage(dashboardStage);
                    dashboardController.setUser(userFirstnameLastname);

                    //Close Connection to DB
//                    rs.close();
//                    stmt.close();
//                    connectDB.close();

                } else {
                    loginMessageLabel.setText("Invalid Login! Please try again");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}