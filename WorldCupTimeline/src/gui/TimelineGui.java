package WorldCupTimeline.src.gui;

import WorldCupTimeline.src.database.DBManager;
import WorldCupTimeline.src.object_classes.Event;
import WorldCupTimeline.src.object_classes.Timeline;
import WorldCupTimeline.src.object_classes.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.util.ArrayList;

public class TimelineGui extends Stage {
	private static ArrayList<String> timelineNames;
	private static ArrayList<Timeline> allTimelines;
	protected static User currentUser;
	private int eventCount = 0;

	ListView<String> eventList = new ListView<>();
	public static ComboBox<String> CB1 = new ComboBox<String>();

	Label lblEventRange = new Label();
	Image img;

	public void gui(User currentUser) throws Exception {
		// Setting the user to the user that logged in
		TimelineGui.currentUser = currentUser;
		CB1.getItems().clear();
		// Pull all timelines with all their events from database
		timelineNames = new ArrayList<>();
		allTimelines = new ArrayList<>();
		DBManager.selectTimelineNames(timelineNames, DBManager.DBConnector());
		for (int i = 0; i < timelineNames.size(); i++) {
			allTimelines.add(new Timeline(timelineNames.get(i)));
		}

		// Main box
		BorderPane root = new BorderPane();
		double sizer = Screen.getPrimary().getVisualBounds().getWidth();
		root.setStyle("-fx-background-color: #0068a8");

		// Box with text content
		VBox content = new VBox();
		content.setStyle("-fx-background-color: whitesmoke");
		// content.setSpacing(2);
		// content.setPadding(new Insets(10));

		img = allTimelines.get(0).getContent().get(0).getImg();
		ImageView imgView = new ImageView(img);
		content.getChildren().addAll(imgView);
		imgView.setSmooth(true);
		imgView.setFitHeight(328);
		imgView.setPreserveRatio(true);
		imgView.setY(300);
		imgView.setX(5);

		// Create ListView with Years
		// Set width
		eventList.setPrefWidth(200);
		eventList.setStyle("-fx-font: 16 Serif ; -fx-control-inner-background: #323739; -fx-padding: 0;");

		// Create the necessary labels for content
		Label timelineName = new Label();
		timelineName.setFont(Font.font("Serif", FontWeight.BOLD, 40));
		Label eventName = new Label();
		eventName.setFont(Font.font("Serif", FontWeight.BOLD, 40));
		Label shortDesc = new Label();
		shortDesc.setFont(Font.font("Serif", FontPosture.ITALIC, 28));
		Label description = new Label();
		description.setFont(Font.font("Serif", FontPosture.REGULAR, 20));
		description.setPadding(new Insets(10, 0, 0, 0)); // Extra padding on top of main text
		description.setWrapText(true);
		description.setTextAlignment(TextAlignment.LEFT); // Have each line fill out horizontally

		// Rating
		Label ratingLable = new Label();
		ratingLable.setFont(Font.font("Serif", FontPosture.ITALIC, 20));
		ratingLable.setTextAlignment(TextAlignment.JUSTIFY);

		Rating rating = new Rating();

		rating.setMax(5); // set max number of stars
		rating.setPartialRating(false); // set true if we want the user to be able to rate with Doubles
		rating.setUpdateOnHover(false);
		rating.setRating(DBManager.getAverageRating(
				DBManager.selectTimelineId(getSelectedName(), DBManager.DBConnector()), DBManager.DBConnector()));

		rating.ratingProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
				ratingLable.setText("Average rating: " + t1.toString());
				rating.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						boolean timelienOwner = DBManager
								.selectTimelineUsername(getSelectedName(), DBManager.DBConnector())
								.equals(currentUser.getUserName());
						boolean ratedBefore = DBManager.getRating(
								DBManager.selectTimelineId(getSelectedName(), DBManager.DBConnector()),
								currentUser.getUserName(), DBManager.DBConnector());

						if (timelienOwner) {
							Alert ownerAlert = new Alert(Alert.AlertType.ERROR);
							ownerAlert.setTitle("ERROR!");
							ownerAlert.setContentText("You can not vote on your own timeline.");
							ownerAlert.showAndWait();
						}

						else if (ratedBefore) {
							Alert doubleRating = new Alert(Alert.AlertType.ERROR);
							doubleRating.setTitle("ERROR!");
							doubleRating.setContentText("You can not vote twice.");
							doubleRating.showAndWait();
						}

						else {

							DBManager.insertRating(
									DBManager.selectTimelineId(getSelectedName(), DBManager.DBConnector()),
									currentUser.getUserName(), t1.intValue(), DBManager.DBConnector());
									
									Alert succrating = new Alert(Alert.AlertType.INFORMATION);
								succrating.setTitle("Confirmation");
								succrating.setContentText("You voted with " + t1 + " stars on this timeline.");
								succrating.show();

						}
						rating.setRating(DBManager.getAverageRating(
								DBManager.selectTimelineId(getSelectedName(), DBManager.DBConnector()),
								DBManager.DBConnector()));
					}
				});
			}
		});

		HBox titleWithRating = new HBox();
		titleWithRating.getChildren().addAll(timelineName, rating, ratingLable);

		// Add content text to content node and put content in a scroll pane to allow
		// scrolling
		content.getChildren().addAll(titleWithRating, eventName, shortDesc, description);

		ScrollPane scroll = new ScrollPane(content);
		scroll.setFitToWidth(true); // Lock width to window size
		// Add list and content to pane
		VBox v = new VBox();
		v.setStyle("-fx-border-color: #323739");
		v.getChildren().addAll(eventList);

		VBox.setVgrow(eventList, Priority.ALWAYS);

		String dateRange = "";
		String defaultTimelinename = "";
		ObservableList<String> nameList = FXCollections.observableArrayList();

		// Loop to get Timeline name, Date year range from List of timelineNamesAndYears
		// to show on Interface
		for (int i = 0; i < allTimelines.size(); i++) {
			Timeline timeLine = allTimelines.get(i);
			nameList.add(timeLine.getTitle());// Getting name from Timeline list and stor into nameList

			// Getting first index date year range and stor into String variable i.e
			// dateRange
			dateRange = allTimelines.get(0).getStart().getYear() + " to " + allTimelines.get(0).getStop().getYear();
			// Getting first index timeline name and stor into String variable i.e
			// defaultTimelinename
			defaultTimelinename = allTimelines.get(0).getTitle();
		}

		// Put all timeline names in combobox
		CB1.getItems().addAll(nameList);
		// Add and show default timeline name in combobox i.e first timeline name
		CB1.setValue(defaultTimelinename);
		CB1.setBackground(
				new Background(new BackgroundFill(Paint.valueOf("#0068a8"), new CornerRadii(0), new Insets(0))));
		CB1.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: green;");

		// Adding action on Combobox, i.e when user change timeline name from combox,
		// then show data range of selected timeline in Label
		CB1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int index = CB1.getSelectionModel().getSelectedIndex();
				if (index > -1) {
					ArrayList<Event> temp = getEventsOfTimeline(CB1.getSelectionModel().getSelectedIndex());
					eventCount = temp.size();

					String dateRange = allTimelines.get(index).getStart().getYear() + " to "
							+ allTimelines.get(index).getStop().getYear();
					lblEventRange.setText(dateRange + ": " + eventCount + " Events.");
					
					rating.setRating(DBManager.getAverageRating(
				DBManager.selectTimelineId(getSelectedName(), DBManager.DBConnector()), DBManager.DBConnector()));
					
					if (temp.isEmpty()) {
						eventList.getItems().clear();
						eventList.getItems().add("No events");
					} else {
						eventList.getItems().clear();
						for (Event e : temp) {
							eventList.getItems().add(e.getName());
						}
					}
				}
			}
		});

		if (CB1.getSelectionModel().getSelectedIndex() == 0) {
			ArrayList<Event> tmp = allTimelines.get(0).getContent();
			eventCount = tmp.size();

			dateRange = allTimelines.get(0).getStart().getYear() + " to " + allTimelines.get(0).getStop().getYear();
			lblEventRange.setText(dateRange + ": " + eventCount + " Events.");
			
			rating.setRating(DBManager.getAverageRating(
				DBManager.selectTimelineId(getSelectedName(), DBManager.DBConnector()), DBManager.DBConnector()));

			if (tmp.isEmpty()) {
				eventList.getItems().clear();
				eventList.getItems().add("No events");
				imgView.setVisible(false);
			} else {
				eventList.getItems().clear();
				for (Event e : tmp) {
					eventList.getItems().add(e.getName());
				}
				ArrayList<String> eventContent = tmp.get(0).getContent();
				eventName.setText(tmp.get(0).getName());
				shortDesc.setText(eventContent.get(0));
				description.setText(eventContent.get(1));
				imgView.setVisible(true);
				img = tmp.get(0).getImg();
				imgView.setImage(img);
			}
		}

		Button getDashboard = new Button();
		getDashboard.setText("Dashboard");
		getDashboard.setPrefWidth(100);
		getDashboard.setBackground(
				new Background(new BackgroundFill(Paint.valueOf("#0081d0"), new CornerRadii(7), new Insets(0))));
		getDashboard.setTextFill(Paint.valueOf("white"));
		getDashboard.setFont(new Font("Arial", 14));

		// Add action on Edit Button to Open new window of edit timeline
		getDashboard.setOnMouseClicked(i -> {
			try {
				this.close();
				new DashBoard().getDashBoard(currentUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		getDashboard.setVisible(false);

		if (currentUser.hasRights() == true) {
			getDashboard.setVisible(true);
		}

		// Defining and adding HBox for Timeline title combobox, Edit Button and Create
		// Button
		HBox menuBars = new HBox();
		menuBars.getChildren().addAll(CB1);
		menuBars.setAlignment(Pos.CENTER_LEFT);
		menuBars.setMinHeight(30);
		menuBars.setSpacing(20);

		AnchorPane aaap = new AnchorPane();

		// Setting Date range and Events on label
		lblEventRange.setText(dateRange + ": " + eventCount + " Events");
		lblEventRange.setPrefWidth(280);
		lblEventRange.setPadding(new Insets(0, 0, 10, 10));
		lblEventRange.setTextFill(Paint.valueOf("white"));
		lblEventRange.setFont(new Font("Arial", 18));
		lblEventRange.setStyle("-fx-font-weight: bold");

		// Defining and adding HBox for Date Range and Event Count Label
		HBox menuBars1 = new HBox(lblEventRange);
		menuBars1.setAlignment(Pos.BOTTOM_LEFT);
		menuBars1.setMinHeight(20);
		menuBars1.setSpacing(20);

		// Adding Timeline title HBox and Date Range with Event count HBox in VBox.
		VBox vBox = new VBox(menuBars, menuBars1);
		vBox.setMinHeight(50);

		// Adding VBox at Top left in AnchorPane
		aaap.getChildren().add(vBox);

		Label ll = new Label("TimeLine Manager");
		ll.setFont(new Font("Arial", 28));
		ll.setTextFill(Paint.valueOf("white"));
		// l1.setTranslateY(50);

		// Add nodes to root
		AnchorPane.setTopAnchor(getDashboard, 10.00);
		AnchorPane.setRightAnchor(getDashboard, 10.00);

		AnchorPane.setTopAnchor(ll, 10.00);
		AnchorPane.setLeftAnchor(ll, (sizer / 2) - 150);
		aaap.getChildren().add(getDashboard);
		aaap.getChildren().add(ll);

		root.setTop(aaap);
		root.setLeft(v);
		root.setCenter(scroll);

		// On selection of list item, update nodes with data from list of timelines
		eventList.setOnMouseClicked(e -> {
			int index = eventList.getSelectionModel().getSelectedIndex();
			ArrayList<Event> tmp = getEventsOfTimeline(CB1.getSelectionModel().getSelectedIndex());
			if (tmp.isEmpty()) {
				eventName.setText("No events to display!");
				shortDesc.setText("The creator will likely add events at any given time.");
				description.setText("");
				imgView.setVisible(false);
			}
			else {
				ArrayList<String> eventContent = tmp.get(index).getContent();
				eventName.setText(tmp.get(index).getName());
				shortDesc.setText(eventContent.get(0));
				description.setText(eventContent.get(1));
				imgView.setVisible(true);
				img = tmp.get(index).getImg();
				imgView.setImage(img);
			}
		});

		// Pre-select first item
		eventList.getSelectionModel().select(0);

		// Create scene
		Scene scene = new Scene(root, 1200, 800);
		this.setScene(scene);
		this.setTitle("Timeline Manager");
		this.show();

		Node n2 = eventList.lookup(".scroll-bar");
		if (n2 instanceof ScrollBar) {
			ScrollBar sb = (ScrollBar) n2;
			sb.setMaxWidth(10);
			sb.setPrefWidth(10);
		}
	}

	protected static String getSelectedName() {
		String selectedTimelineName = CB1.getSelectionModel().getSelectedItem();

		return selectedTimelineName;
	}

	private ArrayList<Event> getEventsOfTimeline(int index) {
		Timeline tmp = allTimelines.get(index);
		if (tmp.getContent().isEmpty()) {
			return new ArrayList<Event>();
		}
		return tmp.getContent();
	}
}