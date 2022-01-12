package WorldCupTimeline.src.gui;

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

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static WorldCupTimeline.src.database.DBManager.DBConnector;
import static WorldCupTimeline.src.database.DBManager.getUserSalt;
import static WorldCupTimeline.src.hashForPassword.PassHash.getSecurePassword;

public class Login extends Stage{
	protected User currentUser;
	
	public void login() throws Exception{
		
		this.setTitle("Timeline Login");
		GridPane gp = createLoginForm();
		addUIControls(gp);
		Scene scene = new Scene(gp);
		gp.prefHeightProperty().bind(scene.heightProperty());
		gp.prefWidthProperty().bind(scene.widthProperty());
		this.setScene(scene);
		this.show();
	}
	
	private GridPane createLoginForm() {
		GridPane gp = new GridPane();
		gp.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(40, 40, 40, 40));
		gp.setHgap(10);
		gp.setVgap(10);
		
		ColumnConstraints columnOne = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOne.setHalignment(HPos.RIGHT);
		ColumnConstraints columnTwo = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwo.setHgrow(Priority.ALWAYS);
		
		gp.getColumnConstraints().addAll(columnOne, columnTwo);
		
		return gp;
	}
	
	private void addUIControls(GridPane gp) {
		Label headerLabel = new Label("Timeline Login");
		headerLabel.setFont(Font.font("Serif", FontWeight.BOLD, 24));
		headerLabel.setTextFill(Color.web("#0076a3"));
		gp.add(headerLabel, 0,  0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
		
		Label nameLabel = new Label("Username:");
		nameLabel.setFont(Font.font("Serif", FontWeight.BOLD, 16));
		gp.add(nameLabel, 0, 1);
		
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);
		gp.add(nameField, 1, 1);
		
		Label passwordLabel = new Label("Password:");
		passwordLabel.setFont(Font.font("Serif", FontWeight.BOLD, 16));
		gp.add(passwordLabel, 0, 3);
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		gp.add(passwordField, 1, 3);
		
		Button loginButton = new Button("Login");
		loginButton.setFont(Font.font("Serif", FontWeight.BOLD, 16));
		loginButton.setPrefHeight(50);
		loginButton.setPrefWidth(80);
		loginButton.setDefaultButton(true);
		gp.add(loginButton, 0, 4, 2, 1);
		GridPane.setHalignment(loginButton, HPos.CENTER);
		GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));
		
		loginButton.setOnAction(e -> {
			try {
				currentUser = new User(nameField.getText(), passwordField.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			byte[] salt = getUserSalt(nameField.getText(), DBConnector());
			String tempHash = "";
			try {
				tempHash = getSecurePassword(passwordField.getText(), salt);
			}
			catch (NoSuchAlgorithmException | NoSuchProviderException e2) {
				e2.printStackTrace();
			}
			
			if (currentUser.getHash().equals(tempHash)) {
				this.close();
				try {
					new TimelineGui().gui(currentUser);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else {
				showAlert(Alert.AlertType.ERROR, gp.getScene().getWindow(), "Login Error!",
						"The password or username is not correct, please try again.");
			}
		});
		
		Button signUpButton = new Button("Go to Signup");
		signUpButton.setFont(Font.font("Serif", FontWeight.BOLD, 16));
		signUpButton.setPrefHeight(50);
		signUpButton.setDefaultButton(true);
		signUpButton.setPrefWidth(115);
        gp.add(signUpButton, 0, 4, 3, 1);
        GridPane.setHalignment(signUpButton, HPos.RIGHT);
        GridPane.setMargin(signUpButton, new Insets(20, 0, 20, 0));
        signUpButton.setOnAction(e->{
        	try {
        		this.close();
				new SignUpGui().signUp();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
		
		Button exitButton = new Button("Exit");
		exitButton.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        exitButton.setPrefHeight(50);
        exitButton.setDefaultButton(true);
        exitButton.setPrefWidth(100);
        gp.add(exitButton, 0, 4, 3, 1);
        GridPane.setHalignment(exitButton, HPos.LEFT);
        GridPane.setMargin(exitButton, new Insets(20, 0, 20, 0));
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
