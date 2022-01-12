package WorldCupTimeline.src.gui;

import javafx.stage.Stage;
import javafx.application.Application;

public class Start extends Application{
	
	public void start(Stage primaryStage) throws Exception {
        new Login().login();
	}
	
    public static void main(String[] args) {
        launch(args);
    }
}