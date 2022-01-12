package WorldCupTimeline.src.gui;

import WorldCupTimeline.src.database.DBManager;
import WorldCupTimeline.src.object_classes.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;

public class SignUpGui extends Stage {
	User newuser = new User();

    public void signUp() throws Exception {
        this.setTitle("TimeLine Registration Form ");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        Scene scene = new Scene(gridPane);
        gridPane.prefHeightProperty().bind(scene.heightProperty());
        gridPane.prefWidthProperty().bind(scene.widthProperty());
        this.setScene(scene);
        this.show();
        
    }
    
    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Timeline Sign Up");
        headerLabel.setFont(Font.font("Serif", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#0076a3"));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add Name Label
        Label nameLabel = new Label("Username:");
        nameLabel.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        gridPane.add(nameLabel, 0, 1);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1, 1);

        // Add Password Label
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        gridPane.add(passwordLabel, 0, 3);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 3);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        submitButton.setPrefHeight(50);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(80);
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        final int PASS_LENGTH = 6;
        submitButton.setOnAction(event -> {
            ArrayList<String> currentUsers = new ArrayList<String>();
            DBManager.selectAllUsers(currentUsers, DBManager.DBConnector());
            if(currentUsers.contains(nameField.getText())) {
            	showAlert(Alert.AlertType.WARNING, gridPane.getScene().getWindow(), "Form Error!", "A username like that already exists");
            }
            else {
                 if (nameField.getText().isEmpty()) {
                     showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                     return;
                 }
                 if (passwordField.getText().isEmpty()) {
                     showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
                     return;
                 }
                 if (passwordField.getText().length() < PASS_LENGTH) {
                     showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Password should be at least 6 characters");
                     return;
                 }

                 else {
                     try {
                         newuser = new User(nameField.getText(), passwordField.getText(), passwordField.getText());
                      } catch (Exception e1) {
                          e1.printStackTrace();
                      }
                     showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Welcome " + newuser.getUserName());
                     try {
                     	this.close();
         				new TimelineGui().gui(newuser);
         			}
                     catch (Exception e1) {
         				e1.printStackTrace();
         			}
                 }
            }

        });

        Button loginButton = new Button("Go to Log in");
        loginButton.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        loginButton.setPrefHeight(50);
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(115);
        gridPane.add(loginButton, 0, 4, 3, 1);
        GridPane.setHalignment(loginButton, HPos.RIGHT);
        GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));
        loginButton.setOnAction(e->{
        	try {
        		this.close();
				new Login().login();
			}
        	catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        //Login button event will be handled on the next sprint as we create the login page

        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        exitButton.setPrefHeight(50);
        exitButton.setDefaultButton(true);
        exitButton.setPrefWidth(100);
        gridPane.add(exitButton, 0, 4, 3, 1);
        GridPane.setHalignment(exitButton, HPos.LEFT);
        GridPane.setMargin(exitButton, new Insets(20, 0, 20, 0));
        // Handle Button event.
        exitButton.setOnAction((event) -> System.exit(0));
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
