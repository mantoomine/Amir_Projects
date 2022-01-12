package JavaFXSimpleGames;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

public class Yahtzee extends Application {
    public static void main(String[] args) {
    	launch(args);
    }

    private int count = 0;
    private int first = 0;
    private int second = 0;
    private int third = 0;
    private int fourth = 0;
    private int fifth = 0;

    public void getnumbers(int x, int y) {
        if (x == 1)
            first = x;
        else if (x == 2)
            second = x;
        else if (x == 3)
            third = x;
        else if (x == 4)
            fourth = x;
        else if (x == 5)
            fifth = x;
    }

    public String getimage(int x) {
        Random rand = new Random();
        int picType = rand.nextInt(6) + 1;
        String pic = "";
        if (picType == 1) {
            File file = new File("./src/as225rr_assign2/Dice1.png");
            pic = file.toURI().toString();
            getnumbers(x, 1);
        } else if (picType == 2) {
            File file1 = new File("./src/as225rr_assign2/Dice2.png");
            pic = file1.toURI().toString();
            getnumbers(x, 2);
        } else if (picType == 3) {
            File file2 = new File("./src/as225rr_assign2/Dice3.png");
            pic = file2.toURI().toString();
            getnumbers(x, 3);
        } else if (picType == 4) {
            File file3 = new File("./src/as225rr_assign2/Dice4.png");
            pic = file3.toURI().toString();
            getnumbers(x, 4);
        } else if (picType == 5) {
            File file4 = new File("./src/as225rr_assign2/Dice5.png");
            pic = file4.toURI().toString();
            getnumbers(x, 5);
        } else if (picType == 6) {
            File file5 = new File("./src/as225rr_assign2/Dice6.png");
            pic = file5.toURI().toString();
            getnumbers(x, 6);
        }
        return pic;
    }

    public void start(Stage oneRoll) throws MalformedURLException {
        oneRoll.setTitle("Yahtzee");
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_LEFT);
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.setPadding(new Insets(20, 20, 20, 20));
        Label head = new Label("Yahtzee");
        head.setFont(new Font("Serif", 53));
        Image pic1 = new Image(getimage(1));
        Image pic2 = new Image(getimage(2));
        Image pic3 = new Image(getimage(3));
        Image pic4 = new Image(getimage(4));
        Image pic5 = new Image(getimage(5));
        ImageView imageShow1 = new ImageView();
        imageShow1.setImage(pic1);
        imageShow1.setFitHeight(55);
        imageShow1.setFitWidth(55);
        ImageView imageShow2 = new ImageView();
        imageShow2.setImage(pic2);
        imageShow2.setFitHeight(55);
        imageShow2.setFitWidth(55);
        ImageView imageShow3 = new ImageView();
        imageShow3.setImage(pic3);
        imageShow3.setFitHeight(55);
        imageShow3.setFitWidth(55);
        ImageView imageShow4 = new ImageView();
        imageShow4.setImage(pic4);
        imageShow4.setFitHeight(55);
        imageShow4.setFitWidth(55);
        ImageView imageShow5 = new ImageView();
        imageShow5.setImage(pic5);
        imageShow5.setFitHeight(55);
        imageShow5.setFitWidth(55);

        CheckBox checkBox1 = new CheckBox();
        checkBox1.setSelected(false);
        checkBox1.setDisable(true);
        CheckBox checkBox2 = new CheckBox();
        checkBox2.setSelected(false);
        checkBox2.setDisable(true);
        CheckBox checkBox3 = new CheckBox();
        checkBox3.setSelected(false);
        checkBox3.setDisable(true);
        CheckBox checkBox4 = new CheckBox();
        checkBox4.setSelected(false);
        checkBox4.setDisable(true);
        CheckBox checkBox5 = new CheckBox();
        checkBox5.setSelected(false);
        checkBox5.setDisable(true);

        gridpane.add(head, 0, 0, 3, 1);
        gridpane.add(imageShow1, 0, 1);
        gridpane.add(imageShow2, 1, 1);
        gridpane.add(imageShow3, 2, 1);
        gridpane.add(imageShow4, 3, 1);
        gridpane.add(imageShow5, 4, 1);
        gridpane.add(checkBox1, 0, 2);
        GridPane.setHalignment(checkBox1, HPos.CENTER);
        gridpane.add(checkBox2, 1, 2);
        GridPane.setHalignment(checkBox2, HPos.CENTER);
        gridpane.add(checkBox3, 2, 2);
        GridPane.setHalignment(checkBox3, HPos.CENTER);
        gridpane.add(checkBox4, 3, 2);
        GridPane.setHalignment(checkBox4, HPos.CENTER);
        gridpane.add(checkBox5, 4, 2);
        GridPane.setHalignment(checkBox5, HPos.CENTER);

        Label rollNumber = new Label("You have 3 rolls left!");
        Button button = new Button("Roll the Dice!");
        gridpane.add(button, 0, 3, 2, 1);
        GridPane.setHalignment(button, HPos.LEFT);
        gridpane.add(rollNumber, 2, 3, 3, 1);
        GridPane.setHalignment(rollNumber, HPos.LEFT);

        button.setOnAction(e -> {
            if (count == 0) {
                Image picOne = new Image(getimage(1));
                Image picTwo = new Image(getimage(2));
                Image picThree = new Image(getimage(3));
                Image picFour = new Image(getimage(4));
                Image picFive = new Image(getimage(5));
                imageShow1.setImage(picOne);
                imageShow2.setImage(picTwo);
                imageShow3.setImage(picThree);
                imageShow4.setImage(picFour);
                imageShow5.setImage(picFive);
                rollNumber.setText("You have 2 Rolls left!");
                checkBox5.setDisable(false);
                checkBox4.setDisable(false);
                checkBox3.setDisable(false);
                checkBox2.setDisable(false);
                checkBox1.setDisable(false);
            }
            if (count == 1) {
                rollNumber.setText("You have only 1 Roll left!!!");

                if (!checkBox1.isSelected()) {
                    Image picA = new Image(getimage(1));
                    imageShow1.setImage(picA);
                }
                if (!checkBox2.isSelected()) {
                    Image picB = new Image(getimage(2));
                    imageShow2.setImage(picB);
                }
                if (!checkBox3.isSelected()) {
                    Image picC = new Image(getimage(3));
                    imageShow3.setImage(picC);
                }
                if (!checkBox4.isSelected()) {
                    Image picD = new Image(getimage(4));
                    imageShow4.setImage(picD);
                }
                if (!checkBox5.isSelected()) {
                    Image picE = new Image(getimage(5));
                    imageShow5.setImage(picE);
                }

            }
            if (count == 2) {

                if (!checkBox1.isSelected()) {
                    Image picF = new Image(getimage(1));
                    imageShow1.setImage(picF);
                }
                if (!checkBox2.isSelected()) {
                    Image picG = new Image(getimage(2));
                    imageShow2.setImage(picG);
                }
                if (!checkBox3.isSelected()) {
                    Image picH = new Image(getimage(3));
                    imageShow3.setImage(picH);
                }
                if (!checkBox4.isSelected()) {
                    Image picI = new Image(getimage(4));
                    imageShow4.setImage(picI);
                }
                if (!checkBox5.isSelected()) {
                    Image picJ = new Image(getimage(5));
                    imageShow5.setImage(picJ);
                }
                rollNumber.setText(finalstring());
                checkBox5.setDisable(true);
                checkBox4.setDisable(true);
                checkBox3.setDisable(true);
                checkBox2.setDisable(true);
                checkBox1.setDisable(true);
                button.setVisible(false);
            }
            count++;
        });

        Scene window = new Scene(gridpane, 350, 250);
        oneRoll.setScene(window);
        oneRoll.show();
    }

    public String finalstring() {
        String string = "";
        Boolean straight = true;
        Boolean straightS = false;
        int[] arr = {first, second, third, fourth, fifth};
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i + 1] < arr[i]) {
                    int min = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = min;
                }
            }
        }
        int count = 1;
        int x = 1;
        int y = 1;
        int value = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i + 1] == arr[i] + 1) {
                count++;
            }
            if (count == 4) {
                straightS = true;
            }
            if (arr[i + 1] != arr[i] + 1) {
                straight = false;
                if (arr[i + 1] != arr[i]) {
                    count = 1;
                }
            }
            if (arr[i] == arr[i + 1] && y == 1) {
                if (value != 0) {
                    if (value == arr[i]) {
                        x++;
                    }
                } else {
                    x++;
                    value = arr[i];
                }
                string = Integer.toString(x) + " " + Integer.toString(y);
            }
            if (arr[i] == arr[i + 1] && value != arr[i]) {
                y++;
            }
        }
        if (x == 5) {
            string = "Yahtzee";
        }
        if (x == 4) {
            string = "Four of a Kind!";
        }
        if (x + y == 5 && x != 4) {
            string = "Fullhouse!";
        }
        if (x == 3 && y == 1) {
            string = "Three of a kind";
        }
        if (x == 2 && y < 3) {
            string = "A pair.";
        }
        if (straightS == true) {
            string = "Small Straight";
        }
        if (straight == true) {
            string = "Large Straight";
        }
        return string;
    }
}