package JavaFXSimpleGames;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompoundInterest extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) { //declare nodes
		int labelSize = 16;
		Text title = new Text(0, 0, "Compound Interest");
		title.setTextAlignment(TextAlignment.LEFT);
		title.setFont(Font.font("Serif", 35));
		Label labelA = new Label("Start amount:");
		labelA.setFont(Font.font("Serif", labelSize));
		Label labelI = new Label("Interest:");
		labelI.setFont(Font.font("Serif", labelSize));
		Label labelY = new Label("Number of years:");
		labelY.setFont(Font.font("Serif", labelSize));
		Label labelR = new Label("");
		labelR.setFont(Font.font("Serif", labelSize));
		TextField fieldA = new TextField();
		TextField fieldI = new TextField();
		TextField fieldY = new TextField();
		Button button = new Button("Calculate");
		button.setOnAction(calc -> {
			String amount = fieldA.getText();
			String interest = fieldI.getText();
			String year = fieldY.getText();
			if (isNumber(amount) && isNumber(interest) && isNumber(year)) {
				Long result = calculate(Double.parseDouble(amount), Double.parseDouble(interest),
						Double.parseDouble(year));
				labelR.setText("Result: " + result);
			} else if (amount.isEmpty() || interest.isEmpty() || year.isEmpty()) {
				labelR.setText("Enter Number please!");
			} else {
				labelR.setText("Enter only Number!");
			}
		});
		HBox top = new HBox();  //Declare boxes
		top.setSpacing(15);
		top.setAlignment(Pos.CENTER);
		top.getChildren().add(title);
		VBox window = new VBox();
		window.setPadding(new Insets(14, 5, 5, 5)); // top,right,bottom,left
		window.setSpacing(20);
		window.getChildren().addAll(labelA, labelI, labelY);
		VBox up = new VBox();
		up.setPadding(new Insets(20, 5, 5, 3));
		up.setSpacing(15);
		up.getChildren().addAll(fieldA, fieldI, fieldY);
		HBox middle = new HBox();
		middle.setPadding(new Insets(0, 0, 5, 0));
		middle.getChildren().addAll(window, up);
		VBox down = new VBox();
		down.setPadding(new Insets(5, 0, 5, 10));
		down.setSpacing(4);
		down.getChildren().addAll(button, labelR);
		VBox base = new VBox();
		base.getChildren().addAll(top, middle, down);
		Scene scene = new Scene(base);
		primaryStage.setTitle("Compound Interest");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public long calculate(double x, double y, double z) { //method to calculate the rate
		return Math.round(x * Math.pow((1 + y / 100.0), z));
	}
	public boolean isNumber(String s) { //check input for numbers
		return s.matches("^[\\d\\-\\.]+$");
	}
}