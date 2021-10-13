/*
 * Katie Klein
 * CSD 420
 * 2 October 2021
 * Assignment 10
 */

/*
This program accesses a database with a table called 'fans' which stores a user's
ID, first name, last name, and favorite team.

The user can enter a user ID and click 'Display' to display that user's information.
Then the user may edit any of the information displayed and click 'Update' to update
the database accordingly.
*/

import java.sql.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

public class Fans extends Application {
    Connection connection;
    Statement statement;
    
    @Override
    public void start(Stage primaryStage) {
        VBox allContentPane = new VBox(10);
        allContentPane.setAlignment(Pos.CENTER);
        
        // User ID input and Display button
        HBox selectUserPane = new HBox(10);
        Label instructionLabel = new Label("Enter the user ID of the record you want to display:");
        TextField userIDTextField = new TextField();
        userIDTextField.setPrefWidth(45);
        Button displayButton = new Button("Display");
        selectUserPane.getChildren().addAll(instructionLabel, userIDTextField, displayButton);
        selectUserPane.setAlignment(Pos.BASELINE_CENTER);

        // Results display area
        HBox displayResultsPane = new HBox(10);
        displayResultsPane.setAlignment(Pos.BASELINE_CENTER);

        Label userIDLabel = new Label("ID");
        TextField userIDDisplay = new TextField();
        userIDDisplay.setEditable(false);
        userIDDisplay.setPrefWidth(35);
        VBox displayField0 = new VBox(userIDLabel, userIDDisplay);
        
        Label firstNameLabel = new Label("First Name");
        TextField firstNameDisplay = new TextField();
        VBox displayField1 = new VBox(firstNameLabel, firstNameDisplay);

        Label lastNameLabel = new Label("Last Name");
        TextField lastNameDisplay = new TextField();
        VBox displayField2 = new VBox(lastNameLabel, lastNameDisplay);

        Label favTeamLabel = new Label("Favorite Team");
        TextField favTeamDisplay = new TextField();
        VBox displayField3 = new VBox(favTeamLabel, favTeamDisplay);

        // Create event handler for Display button
        displayButton.setOnAction(e -> {
            String id = userIDTextField.getText();
            connectToDatabase();
            String[] results = displayUserData(id);
            userIDDisplay.setText(id);
            firstNameDisplay.setText(results[0]);
            lastNameDisplay.setText(results[1]);
            favTeamDisplay.setText(results[2]);
        });

        // Create update button with event handler
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            connectToDatabase();
            updateUserData(userIDDisplay.getText(), firstNameDisplay.getText(), lastNameDisplay.getText(), favTeamDisplay.getText());
        });
        updateButton.setAlignment(Pos.CENTER);

        // Pane for header
        Text headerText = new Text("FAN DATABASE");
        StackPane topPane = new StackPane(headerText);

        // Panes for displayed results & Update button
        VBox bottomPane = new VBox(10);
        displayResultsPane.getChildren().addAll(displayField0, displayField1, displayField2, displayField3);
        bottomPane.getChildren().addAll(displayResultsPane, updateButton);
        bottomPane.setAlignment(Pos.CENTER);

        // Pane for main content
        VBox mainContentPane = new VBox(10);
        mainContentPane.getChildren().addAll(selectUserPane, bottomPane);

        // Place all main elements in pane
        allContentPane.getChildren().addAll(topPane, mainContentPane);
        Scene scene = new Scene(allContentPane, 600, 300);
        scene.getStylesheets().add("fans-styles.css");

        // Style/format elements
        topPane.getStyleClass().add("top-pane");
        headerText.getStyleClass().add("header-text");
        instructionLabel.getStyleClass().addAll("small-padding", "white-text");
        userIDLabel.getStyleClass().add("small-padding");
        firstNameLabel.getStyleClass().add("small-padding");
        lastNameLabel.getStyleClass().add("small-padding");
        favTeamLabel.getStyleClass().add("small-padding");
        displayButton.getStyleClass().addAll("small-padding", "button");
        updateButton.getStyleClass().addAll("small-padding", "button", "update-button");
        userIDTextField.getStyleClass().add("small-padding");
        userIDDisplay.getStyleClass().addAll("small-padding", "id-display");
        firstNameDisplay.getStyleClass().add("small-padding");
        lastNameDisplay.getStyleClass().add("small-padding");
        favTeamDisplay.getStyleClass().add("small-padding");

        selectUserPane.getStyleClass().addAll("main-element");
        bottomPane.getStyleClass().addAll("main-element", "bottom-pane");
        displayResultsPane.getStyleClass().add("main-element");
        mainContentPane.getStyleClass().add("main-content");
        allContentPane.getStyleClass().add("all-content");

        // Set scene in stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fan Database");
        primaryStage.show();
    }
    


    /* FUNCTIONS */

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/databasedb?"; 
            connection = DriverManager.getConnection(url + "user=student1&password=pass");  
        }
        catch(Exception e) {
            System.out.println("Error connecting to database");
            System.exit(0);
        }
    }

    private String[] displayUserData(String id) {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT firstname, lastname, favoriteteam FROM fans WHERE ID = '" + id + "'");
            String[] results = new String[3];
            while (resultSet.next()) {
                results[0] = resultSet.getString("firstname");
                results[1] = resultSet.getString("lastname");
                results[2] = resultSet.getString("favoriteteam");
            }
            return results;
        } catch (Exception e){
            System.out.println("Exception: " + e);
            System.exit(0);
            return new String[3];
        }
    }

    private void updateUserData(String id, String firstname, String lastname, String favoriteteam) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE fans SET firstname = '" + firstname + "', lastname = '" + lastname + "', favoriteteam = '" + favoriteteam + "' WHERE ID = " + id);
            
            // Information alert to confirm when data is successfully updated
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("");
            successAlert.setHeaderText("");
            successAlert.setContentText("Record successfully updated.");
            successAlert.show();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            
            // Error alert to show when data could not be updated
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("");
            errorAlert.setHeaderText("");
            errorAlert.setContentText("Record could not be updated.");
            errorAlert.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
