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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardController extends Main {

    @FXML
    private Label welcomeLabel;



    private String user;
    private String matricNo;
    @FXML
    private Label fullNameTextField;
    @FXML
    private Label matricNoTextField;
    @FXML
    private Label facultyTextField;
    @FXML
    private Label courseTextField;
    @FXML
    private Label levelTextField;
    @FXML
    private Label genderTextField;
    @FXML
    private Label homeAddressTextField;
    @FXML
    private Label phoneTextField;
    @FXML
    private Label studentEmailTextField;
    @FXML
    private Label nokTextField;
    @FXML
    private Label nokPhoneTextField;

    @FXML
    private Button logoutButton;

    private Stage dashboardStage;

    public DashboardController(String matricNo) {
        this.matricNo = matricNo;
    }

    // Method to set the stage object
    public void setStage(Stage dashboardStage) {
        this.dashboardStage = dashboardStage;
    }

    // Method to close the scene
    public void closeScene() {
        dashboardStage.close();
    }

    public void setUser(String user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user + "!");
    }

    public void setMatricNo(String number) {
        matricNo = number;
    }

    public void initialize() {
        try {
            loadStudentDetails();
        } catch (SQLException e) {
            e.printStackTrace();
            // handle the SQLException here
        }
    }
    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        // Load the login.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();

        // Create a new stage and set the scene to the login.fxml file
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root, 520, 400));

        // Show the login scene and close the dashboard scene
        stage.show();
    }



    public void loadStudentDetails() throws SQLException {
        //Connect to DB
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Create a prepared statement to retrieve the student details
        String retrieveDetails = "SELECT firstname, lastname, matric_no, faculty, course, level, gender, home_address, phone_no, student_email_address, nok_firstname, nok_lastname, nok_phone_no FROM pocbase.useraccounts WHERE matric_no = ?";
        PreparedStatement statement = connectDB.prepareStatement(retrieveDetails);
        statement.setString(1, matricNo);

        // Execute the prepared statement and retrieve the result set
        ResultSet result = statement.executeQuery();

        // Set the labels with the retrieved data
        if (result.next()) {
            fullNameTextField.setText("Name: "+result.getString("firstname") + " " + result.getString("lastname"));
            matricNoTextField.setText("Matric No: "+result.getString("matric_no"));
            facultyTextField.setText("Faculty: "+result.getString("faculty"));
            courseTextField.setText("Course: "+result.getString("course"));
            levelTextField.setText("Level: "+result.getString("level"));
            genderTextField.setText("Gender: "+result.getString("gender"));
            homeAddressTextField.setText("Address: "+result.getString("home_address"));
            phoneTextField.setText("Phone No: "+result.getString("phone_no"));
            studentEmailTextField.setText("Student Email: "+result.getString("student_email_address"));
            nokTextField.setText("Next of Kin: "+result.getString("nok_firstname") + " " + result.getString("nok_lastname"));
            nokPhoneTextField.setText("(Nok) Phone No: "+result.getString("nok_phone_no"));
        }

        statement.close();
        result.close();
        connectDB.close();

    }


}
