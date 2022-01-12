package JavaFXSimpleGames;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Snowman extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Snowman");
		Rectangle sky = new Rectangle(0, 0, 600, 300);
		sky.setFill(Color.DEEPSKYBLUE);
		Circle head = new Circle(300, 150, 20, Color.WHITE);
		Circle middle = new Circle(300, 190, 30, Color.WHITE);
		Circle lower = new Circle(300, 255, 50, Color.WHITE);
		Circle sun = new Circle(450, 70, 50, Color.YELLOW);
		Circle lEye = new Circle(290, 145, 3, Color.BLACK);
		Circle rEye = new Circle(310, 145, 3, Color.BLACK);
		Circle b1 = new Circle(300, 180, 3, Color.BLACK);
		Circle b2 = new Circle(300, 195, 3, Color.BLACK);
		Circle b3 = new Circle(300, 210, 3, Color.BLACK);
		Line line = new Line(290, 158, 310, 158);
		line.setStroke(Color.BLACK);
		Group snow = new Group();
		snow.getChildren().addAll(sky, head, middle, lower, sun, lEye, rEye, b1, b2, b3, line);
		Scene scene = new Scene(snow, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}