package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static database.DBManager.*;
import java.util.ArrayList;

public class Users extends Stage {
    private CheckBox[] cheBox;

    public void CheckBoxes() throws Exception{

        this.setTitle("Users and Admins");

         VBox vbox = new VBox();
         vbox.setSpacing(5);
         vbox.setPadding(new Insets(15));
         vbox.setAlignment(Pos.TOP_LEFT);

        Label l = new Label("Admin rights:");
        l.setFont(Font.font ("Verdana", 18));
        l.setTextFill(Color.BLUE);
        l.setLineSpacing(5);
        l.setPadding(new Insets(5));
        l.setAlignment(Pos.TOP_LEFT);

        ArrayList<String> UsersList = new ArrayList<String>();
        ArrayList<Integer> admins = new ArrayList<Integer>();
        selectUsersAndStatus(UsersList, admins, DBConnector());


        vbox.getChildren().add(l);

        cheBox = new CheckBox[UsersList.size()];

        for (int i = 0; i < UsersList.size(); i++) {
            Label l1 = new Label();

            cheBox[i] = new CheckBox(UsersList.get(i));
            vbox.getChildren().add(cheBox[i]);
            vbox.getChildren().add(l1);
        }
        
        for(int i = 0; i < admins.size(); i++) {
        	if(admins.get(i).equals(1)) {
        		cheBox[i].setSelected(true);
        	}
        }

        Button setAdmin = new Button("Set as admin");
        setAdmin.setAlignment(Pos.BOTTOM_LEFT);

        setAdmin.setOnAction(e -> {
            for (int i = 0 ; i < cheBox.length ; i++) {
                if (cheBox[i].isSelected()) {
                        updateUserAdmin(cheBox[i].getText(), true, DBConnector());
                }
                else {
                	updateUserAdmin(cheBox[i].getText(), false, DBConnector());
                }
            }
        });

        vbox.getChildren().add(setAdmin);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.show();
        Scene sc = new Scene(scrollPane, 300, 600);

        this.setScene(sc);

        this.show();
    }
}
