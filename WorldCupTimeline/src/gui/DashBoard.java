package gui;

import java.io.File;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import object_classes.*;
import  static database.DBManager.*;

public class DashBoard extends Stage {
	Scene home, edit, delete, create;
	VBox globalMenu;
	BorderPane layout;
	User currentUser;
	File picture = new File("");
	Image pic;
	
	private static ArrayList<String> timelineNames = new ArrayList<String>();
	private static ArrayList<Timeline> allTimelines = new ArrayList<Timeline>();
	private int indexOfNewTimeline = -1;
	
	ListView<String> events = new ListView<String>();
	ComboBox<String> timelines = new ComboBox<String>();
	Event selectedEventForEdit = new Event();
	Timeline selectedTimelineForEdit = new Timeline();
	ArrayList<Event> eventList  = new ArrayList<Event>();
	String keywordsForUpdate = "";
	
	public void getDashBoard(User currentUser) throws Exception {
		timelineNames.clear();
		allTimelines.clear();
		selectTimelineNames(timelineNames, DBConnector());
		for(int i = 0; i < timelineNames.size(); i++) {
			allTimelines.add(new Timeline(timelineNames.get(i)));
		}

		this.currentUser = currentUser;

		this.setResizable(true);
		this.setScene(getHome());
		this.show();

		//adding on close action call timeline gui window on closing of this window
		this.setOnCloseRequest(e->{
			try {
				new TimelineGui().gui(currentUser);
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	private Scene getHome(){
		this.setTitle("Timeline Dashboard");
		layout = getLayout();
		timelines = new ComboBox<String>();
		events = new ListView<String>();
		timelines.setVisibleRowCount(allTimelines.size() - 1);
		timelines.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		timelines.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");
		events.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		events.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");

		ObservableList<String> timelinesNameList = FXCollections.observableArrayList();
		ObservableList<String> eventsNameList = FXCollections.observableArrayList();

		for(int i = 0; i < allTimelines.size(); i++) {
			Timeline timeline = allTimelines.get(i);
			timelinesNameList.add(timeline.getTitle());
		}
		timelines.getItems().addAll(timelinesNameList);

		timelines.setValue(allTimelines.get(0).getTitle());

		ArrayList<Event> copy = allTimelines.get(0).getContent();
		eventsNameList.clear();
		events.getItems().clear();
		for(int j = 0; j < allTimelines.get(0).getContent().size(); j++) {
			Event event = copy.get(j);
			eventsNameList.add(event.getName());
		}
		events.getItems().addAll(eventsNameList);


		HBox tlOp = new HBox();
		HBox.setMargin(tlOp, new Insets(10));
		tlOp.setPrefWidth(1000);
		tlOp.setPrefHeight(400);
		tlOp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		BorderPane tlOpLayout = new BorderPane();
		tlOpLayout.setTop(timelines);
		BorderPane.setMargin(timelines, new Insets(5, 0, 0, 400));

		VBox timelineInfo = new VBox();
		timelineInfo.setPrefWidth(1000);
		timelineInfo.setPrefHeight(350);
		timelineInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		Label timelineName = new Label(allTimelines.get(0).getTitle());
		timelineName.setTextFill(Color.WHITE);
		timelineName.setFont(new Font("Arial", 20));

		Label timelineRange = new Label(allTimelines.get(0).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
				+ " to " + allTimelines.get(0).getStop().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		timelineRange.setTextFill(Color.WHITE);
		timelineRange.setFont(new Font("Arial", 20));

		Label timelineCreator = new Label(allTimelines.get(0).getCreator());
		timelineCreator.setTextFill(Color.WHITE);
		timelineCreator.setFont(new Font("Arial", 20));

		Label timelineDescription = new Label(allTimelines.get(0).getDescription());
		timelineDescription.setTextFill(Color.WHITE);
		timelineDescription.setFont(new Font("Arial", 20));

		Label timelineKeywords = new Label(allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).displayTags());
		timelineKeywords.setTextFill(Color.WHITE);
		timelineKeywords.setFont(new Font("Arial", 20));

		timelineInfo.getChildren().addAll(timelineName, timelineRange, timelineCreator, timelineDescription, timelineKeywords);
		tlOpLayout.setCenter(timelineInfo);
		tlOp.getChildren().add(tlOpLayout);

		HBox evOp = new HBox();
		HBox.setMargin(tlOp, new Insets(10));
		evOp.setPrefWidth(1000);
		evOp.setPrefHeight(400);
		evOp.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

		BorderPane evOpLayout = new BorderPane();
		evOpLayout.setLeft(events);

		VBox eventInfo = new VBox();
		eventInfo.setPrefWidth(1000);
		eventInfo.setPrefHeight(350);
		eventInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		ArrayList<String> eventContent = copy.get(0).getContent();

		Label eventName = new Label(copy.get(0).getName());
		eventName.setTextFill(Color.WHITE);
		eventName.setFont(new Font("Arial", 20));

		Label eventRange = new Label(copy.get(0).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " to "
				+ copy.get(0).getStop().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		eventRange.setTextFill(Color.WHITE);
		eventRange.setFont(new Font("Arial", 20));

		Label eventHead_desc = new Label(eventContent.get(0));
		eventHead_desc.setTextFill(Color.WHITE);
		eventHead_desc.setFont(new Font("Arial", 20));

		Label eventBody_desc = new Label(eventContent.get(1));
		eventBody_desc.setTextFill(Color.WHITE);
		eventBody_desc.setFont(new Font("Arial", 20));
		
		ImageView eventPicture = new ImageView();
		eventPicture.setSmooth(true);
		eventPicture.setFitHeight(328);
		eventPicture.setPreserveRatio(true);

		eventInfo.getChildren().addAll(eventName, eventRange, eventHead_desc, eventBody_desc, eventPicture);
		evOpLayout.setCenter(eventInfo);
		evOp.getChildren().add(evOpLayout);

		VBox options = new VBox();
		options.getChildren().addAll(tlOp, evOp);
		layout.setCenter(options);

		timelines.setOnAction(e->{
			timelineName.setText(allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getTitle());
			timelineRange.setText(allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					+ " to " + allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getStop().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			timelineCreator.setText(allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getCreator());
			timelineDescription.setText(allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getDescription());
			timelineKeywords.setText(allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).displayTags());

			ArrayList<Event> eventCopy = allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getContent();
			ArrayList<String> contentOfEvent;
			eventsNameList.clear();
			events.getItems().clear();

			if(eventCopy.isEmpty()) {
				events.getItems().add("No events!");
				eventName.setText("No events to display!");
				eventHead_desc.setText("The creator will likely add events at any given time.");
				eventBody_desc.setText("");
				eventRange.setText("");
				eventPicture.setVisible(false);
			}
			else {
				for(int j = 0; j < allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getContent().size(); j++) {
					Event event = eventCopy.get(j);
					eventsNameList.add(event.getName());
				}
				events.getItems().addAll(eventsNameList);

				contentOfEvent = eventCopy.get(0).getContent();
				eventName.setText(eventCopy.get(0).getName());
				eventRange.setText(eventCopy.get(0).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						+ " to " + eventCopy.get(0).getStop().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				eventHead_desc.setText(contentOfEvent.get(0));
				eventBody_desc.setText(contentOfEvent.get(1));
				eventPicture.setVisible(true);
				this.pic = eventCopy.get(0).getImg();
				eventPicture.setImage(pic);
			}
		});

		events.setOnMouseClicked(e->{
			int index = events.getSelectionModel().getSelectedIndex();
			ArrayList<Event> tmp = allTimelines.get(timelines.getSelectionModel().getSelectedIndex()).getContent();
			if(tmp.isEmpty()) {
				eventName.setText("No events to display!");
				eventHead_desc.setText("The creator will likely add events at any given time.");
				eventBody_desc.setText("");
				eventRange.setText("");
				eventPicture.setVisible(false);
			}
			else {
				ArrayList<String> contentOfEvent = tmp.get(index).getContent();
				eventName.setText(tmp.get(index).getName());
				eventRange.setText(tmp.get(index).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						+ " to " + tmp.get(index).getStop().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				eventHead_desc.setText(contentOfEvent.get(0));
				eventBody_desc.setText(contentOfEvent.get(1));
				eventPicture.setVisible(true);
				this.pic = tmp.get(index).getImg();
				eventPicture.setImage(pic);
			}
		});

		home = new Scene(layout, 1200, 800);

		return home;
	}

	private BorderPane getLayout() {
		layout = new BorderPane();
		layout.setPrefWidth(1200);
		layout.setPrefHeight(800);
		layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		Button toEditbtn, toDeletebtn, toCreatebtn, toHome, userList;

		globalMenu = new VBox();
		globalMenu.setPrefWidth(200);
		globalMenu.setPrefHeight(800);
		globalMenu.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		toEditbtn = new Button("Edit");
		toEditbtn.setPrefWidth(100);
		toEditbtn.setPadding(new Insets(10));
		toEditbtn.setTextFill(Color.BLACK);
		toEditbtn.setFont(new Font("Arial", 14));

		toDeletebtn = new Button("Delete");
		toDeletebtn.setPrefWidth(100);
		toDeletebtn.setPadding(new Insets(10));
		toDeletebtn.setTextFill(Color.BLACK);
		toDeletebtn.setFont(new Font("Arial", 14));

		toCreatebtn = new Button("Create");
		toCreatebtn.setPrefWidth(100);
		toCreatebtn.setPadding(new Insets(10));
		toCreatebtn.setTextFill(Color.BLACK);
		toCreatebtn.setFont(new Font("Arial", 14));

		toHome = new Button("Home");
		toHome.setPrefWidth(100);
		toHome.setPadding(new Insets(10));
		toHome.setTextFill(Color.BLACK);
		toHome.setFont(new Font("Arial", 14));

		userList = new Button("User list");
		userList.setPrefWidth(100);
		userList.setPadding(new Insets(10));
		userList.setTextFill(Color.BLACK);
		userList.setFont(new Font("Arial", 14));

		VBox.setMargin(toCreatebtn, new Insets(20, 50, 0, 50));
		VBox.setMargin(toEditbtn, new Insets(20, 50, 0, 50));
		VBox.setMargin(toDeletebtn, new Insets(20, 50, 0, 50));
		VBox.setMargin(userList, new Insets(20, 50, 0, 50));
		VBox.setMargin(toHome, new Insets(40, 50, 0, 50));

		globalMenu.getChildren().addAll(toHome, toCreatebtn, toEditbtn, toDeletebtn, userList);
		layout.setLeft(globalMenu);

		toEditbtn.setOnMouseClicked(e->{

			this.setScene(getEdit());
			this.show();
		});

		toDeletebtn.setOnMouseClicked(e->{
			this.setScene(getDelete());
			this.show();
		});

		toCreatebtn.setOnMouseClicked(e->{
			this.setScene(getCreate());
			this.show();
		});

		toHome.setOnMouseClicked(e->{
			this.setScene(getHome());
			this.show();
		});

		userList.setOnMouseClicked(e->{
			try {
				new Users().CheckBoxes();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		return layout;
	}

	private Scene getCreate() {
		layout = getLayout();

		HBox timelineCreation = new HBox();
		timelineCreation.setPrefWidth(1000);
		timelineCreation.setPrefHeight(400);

		VBox timelineCreation1 = new VBox();
		timelineCreation1.setPrefWidth(300);
		timelineCreation1.setPrefHeight(400);

		VBox timelineCreation2 = new VBox();
		timelineCreation2.setPrefWidth(300);
		timelineCreation2.setPrefHeight(400);

		HBox.setMargin(timelineCreation1, new Insets(10));
		HBox.setMargin(timelineCreation2, new Insets(10));
		VBox.setMargin(timelineCreation2, new Insets(20));

		Label lbTimelineName = new Label("Timeline name*");
		TextField timelineName = new TextField();
		timelineName.setPrefWidth(100);

		Label lbTimelineDescription = new Label("Description*");
		TextField timelineDescription = new TextField();
		timelineDescription.setPrefSize(100, 200);
		timelineCreation1.getChildren().addAll(lbTimelineName, timelineName, lbTimelineDescription, timelineDescription);

		Label lbTimelineStartDate = new Label("Start date*");
		DatePicker timelineStartDate = new DatePicker();
		Label lbTimelineStartTime = new Label("Start time*");
		TextField timelineStartTime = new TextField();
		timelineStartTime.setPrefWidth(100);

		Label lbTimelineEndDate = new Label("End date*");
		DatePicker timelineEndDate = new DatePicker();
		Label lbTimelineEndTime = new Label("End time*");
		TextField timelineEndTime = new TextField();
		timelineEndTime.setPrefWidth(100);

		Label lbTimelineTags = new Label("Keywords*");
		TextField timelineKeywords = new TextField();
		timelineKeywords.setPrefWidth(100);

		Button createTimelinebtn = new Button("Create timeline*");
		createTimelinebtn.setPrefWidth(120);

		timelineCreation2.getChildren().addAll(lbTimelineStartDate, timelineStartDate, lbTimelineStartTime, timelineStartTime,
				lbTimelineEndDate, timelineEndDate, lbTimelineEndTime, timelineEndTime, lbTimelineTags, timelineKeywords, createTimelinebtn);
		timelineCreation.getChildren().addAll(timelineCreation1, timelineCreation2);

		HBox eventCreation = new HBox();
		eventCreation.setPrefWidth(1000);
		eventCreation.setPrefHeight(400);

		VBox eventCreation1 = new VBox();
		eventCreation1.setPrefWidth(300);
		eventCreation1.setPrefHeight(400);

		VBox eventCreation2 = new VBox();
		eventCreation2.setPrefWidth(300);
		eventCreation2.setPrefHeight(400);

		HBox.setMargin(eventCreation1, new Insets(10));
		HBox.setMargin(eventCreation2, new Insets(10));
		VBox.setMargin(eventCreation2, new Insets(20));

		Button createEventbtn = new Button("Create event*");
		createEventbtn.setPrefWidth(120);

		Label lbEventName = new Label("Event name*");
		TextField eventName = new TextField();
		eventName.setPrefWidth(100);

		Label lbEventHead_desc = new Label("Small description for the event*");
		TextField eventHead_desc = new TextField();
		eventHead_desc.setPrefWidth(100);

		Label lbEventBody_desc = new Label("Small description for the event*");
		TextField eventBody_desc = new TextField();
		eventBody_desc.setPrefSize(100, 200);

		eventCreation1.getChildren().addAll(lbEventName, eventName, lbEventHead_desc, eventHead_desc,
				lbEventBody_desc, eventBody_desc);

		Label lbEventStartDate = new Label("Start date*");
		DatePicker eventStartDate = new DatePicker();
		Label lbEventStartTime = new Label("Start time*");
		TextField eventStartTime = new TextField();
		eventStartTime.setPrefWidth(100);

		Label lbEventEndDate = new Label("End date*");
		DatePicker eventEndDate = new DatePicker();
		Label lbEventEndTime = new Label("End time*");
		TextField eventEndTime = new TextField();
		eventEndTime.setPrefWidth(100);

		Label label = new Label("Drag a file here!");
		Label dropped = new Label("");
		VBox dragTarget = new VBox();

		dragTarget.getChildren().addAll(label, dropped);
		dragTarget.setOnDragOver(e->{
			if(e.getGestureSource() != dragTarget && e.getDragboard().hasFiles()) {
				e.acceptTransferModes(TransferMode.COPY);
			}
			e.consume();
		});

		dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if(db.hasFiles()) {
					dropped.setText(db.getFiles().toString());
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();

				picture = handleDragDropped(event);
			}

			private File handleDragDropped(DragEvent event) {
				Dragboard db = event.getDragboard();
				File tmp = db.getFiles().get(0);
				return tmp;
			}

		});

		eventCreation2.getChildren().addAll(lbEventStartDate, eventStartDate, lbEventStartTime, eventStartTime,
				lbEventEndDate, eventEndDate, lbEventEndTime, eventEndTime, dragTarget);
		eventCreation.getChildren().addAll(eventCreation1, eventCreation2, createEventbtn);
		eventCreation.setVisible(false);


		createTimelinebtn.setOnAction(e->{
			// Starting date and time of timeline
			LocalDate ldStartDate = timelineStartDate.getValue();
			LocalTime ltStartTime = LocalTime.parse(timelineStartTime.getCharacters());
			LocalDateTime startDateTime = LocalDateTime.of(ldStartDate, ltStartTime);

			// Ending date and time of timeline
			LocalDate ldEndDate = timelineEndDate.getValue();
			LocalTime ltEndTime = LocalTime.parse(timelineEndTime.getCharacters());
			LocalDateTime endDateTime = LocalDateTime.of(ldEndDate, ltEndTime);

			ArrayList<String> keywords = new ArrayList<String>();
			String[] tmp = timelineKeywords.getText().split(",");
			for(String s : tmp) {
				keywords.add(s);
			}

			Timeline newTimeline;
			allTimelines.add(newTimeline = new Timeline(currentUser, timelineName.getText(), startDateTime, endDateTime, timelineDescription.getText(), keywords));

			indexOfNewTimeline = allTimelines.indexOf(newTimeline);

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation of creation");
			alert.setHeaderText("Your timeline is now created.");
			alert.setContentText("You can see your timeline in the display section, and you can add events to it here in the dashboard.");
			alert.setHeight(150);
			alert.show();

			eventCreation.setVisible(true);
		});

		createEventbtn.setOnAction(e->{
			// Starting date and time of event
			LocalDate ldEventStartDate = eventStartDate.getValue();
			LocalTime ltEventStartTime = LocalTime.parse(eventStartTime.getCharacters());
			LocalDateTime eventStartDateTime = LocalDateTime.of(ldEventStartDate, ltEventStartTime);

			// Ending date and time of event
			LocalDate ldEventEndDate = eventEndDate.getValue();
			LocalTime ltEventEndTime = LocalTime.parse(eventEndTime.getCharacters());
			LocalDateTime eventEndDateTime = LocalDateTime.of(ldEventEndDate, ltEventEndTime);
			
			// Adding timeline to list and to database
			if(picture.equals(new File(""))) {
				allTimelines.get(indexOfNewTimeline).addContent(new Event(allTimelines.get(indexOfNewTimeline).getTitle(), eventName.getText(),
						eventStartDateTime, eventEndDateTime, eventHead_desc.getText(), eventBody_desc.getText()));
			}
			else {
				allTimelines.get(indexOfNewTimeline).addContent(new Event(allTimelines.get(indexOfNewTimeline).getTitle(), eventName.getText(),
						eventStartDateTime, eventEndDateTime, eventHead_desc.getText(), eventBody_desc.getText(), picture));
			}

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation of creation");
			alert.setHeaderText("Your event is now created.");
			alert.setContentText("You can see your event in the display section.");
			alert.setHeight(150);
			alert.show();

		});

		VBox creation = new VBox();
		creation.getChildren().addAll(timelineCreation, eventCreation);
		creation.setVisible(false);


		Button createNewTimeline = new Button("Create a new timeline");
		Button createNewEvent = new Button("Create a new event");

		createNewTimeline.setOnAction(e->{
			createNewTimeline.setVisible(false);
			creation.setVisible(true);
			layout.setCenter(creation);
		});
		
		ArrayList<Timeline> lstTimeLine = new ArrayList<Timeline>();
		createNewEvent.setOnAction(e->{
			ComboBox<String> timelines = new ComboBox<String>();
			timelines.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			timelines.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");

			ObservableList<String> timelinesNameList = FXCollections.observableArrayList();
			for(int i = 0; i < allTimelines.size(); i++) {
				Timeline timeline = allTimelines.get(i);
				String user = currentUser.getUserName();
				if(timeline.getCreator().equals(user)) {
					timelinesNameList.add(timeline.getTitle());
					lstTimeLine.add(timeline);
				}
			}
			timelines.getItems().addAll(timelinesNameList);
			if(lstTimeLine != null && lstTimeLine.size() > 0) {
				timelines.setValue(lstTimeLine.get(0).getTitle());
			}

			createEventbtn.setOnAction(f->{
				// Starting date and time of event
				LocalDate ldEventStartDate = eventStartDate.getValue();
				LocalTime ltEventStartTime = LocalTime.parse(eventStartTime.getCharacters());
				LocalDateTime eventStartDateTime = LocalDateTime.of(ldEventStartDate, ltEventStartTime);

				// Ending date and time of event
				LocalDate ldEventEndDate = eventEndDate.getValue();
				LocalTime ltEventEndTime = LocalTime.parse(eventEndTime.getCharacters());
				LocalDateTime eventEndDateTime = LocalDateTime.of(ldEventEndDate, ltEventEndTime);

				if(lstTimeLine != null && lstTimeLine.size() > 0) {
					// Adding timeline to list and to database
					if(picture.equals(new File(""))) {
						lstTimeLine.get(timelines.getSelectionModel().getSelectedIndex()).addContent(new Event(lstTimeLine.get(timelines.getSelectionModel().getSelectedIndex()).getTitle(), eventName.getText(),
								eventStartDateTime, eventEndDateTime, eventHead_desc.getText(), eventBody_desc.getText()));
					}
					else {
						lstTimeLine.get(timelines.getSelectionModel().getSelectedIndex()).addContent(new Event(lstTimeLine.get(timelines.getSelectionModel().getSelectedIndex()).getTitle(), eventName.getText(),
								eventStartDateTime, eventEndDateTime, eventHead_desc.getText(), eventBody_desc.getText(), picture));
					}

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Confirmation of creation");
					alert.setHeaderText("Your event is now created.");
					alert.setContentText("You can see your event in the display section.");
					alert.setHeight(150);
					alert.show();
				}else {
					JOptionPane.showMessageDialog(null, "Please first select timeline");
					return;
				}
			});

			createNewEvent.setVisible(false);
			eventCreation.setVisible(true);
			creation.getChildren().remove(0);
			creation.setVisible(true);
			layout.setTop(timelines);
			BorderPane.setMargin(timelines, new Insets(10, 0, 0, 400));
			layout.setCenter(creation);
		});

		HBox options = new HBox();
		options.getChildren().addAll(createNewTimeline, createNewEvent);
		HBox.setMargin(createNewTimeline, new Insets(300, 0, 10, 300));
		HBox.setMargin(createNewEvent, new Insets(300, 0, 10, 10));

		layout.setCenter(options);
		create = new Scene(layout, 1200, 800);

		return create;
	}

	private Scene getEdit() {
		layout = getLayout();
		eventList =  new ArrayList<Event>();
/*************************Starting timeline work********************************/
		//Makingn main timline hbox
		HBox timeLineHBOX = new HBox();
		timeLineHBOX.setPrefWidth(1000);
		timeLineHBOX.setPrefHeight(400);

		VBox timeLineVBOX1 = new VBox();
		timeLineVBOX1.setPrefWidth(300);
		timeLineVBOX1.setPrefHeight(400);

		VBox timeLineVBOX2 = new VBox();
		timeLineVBOX2.setPrefWidth(300);
		timeLineVBOX2.setPrefHeight(400);

		HBox.setMargin(timeLineVBOX1, new Insets(10));
		HBox.setMargin(timeLineVBOX2, new Insets(10));
		VBox.setMargin(timeLineVBOX2, new Insets(20));

		Label lbTimelineName = new Label("Timeline name*");
		TextField timelineName = new TextField();
		timelineName.setPrefWidth(100);

		Label lbTimelineDescription = new Label("Description*");
		TextField timelineDescription = new TextField();
		timelineDescription.setPrefSize(100, 200);
		timeLineVBOX1.getChildren().addAll(lbTimelineName, timelineName, lbTimelineDescription, timelineDescription);

		Label lbTimelineStartDate = new Label("Start date*");
		DatePicker timelineStartDate = new DatePicker();
		Label lbTimelineStartTime = new Label("Start time*");
		TextField timelineStartTime = new TextField();
		timelineStartTime.setPrefWidth(100);

		Label lbTimelineEndDate = new Label("End date*");
		DatePicker timelineEndDate = new DatePicker();
		Label lbTimelineEndTime = new Label("End time*");
		TextField timelineEndTime = new TextField();
		timelineEndTime.setPrefWidth(100);

		Label lbTimelineTags = new Label("Keywords*");
		TextField timelineKeywords = new TextField();
		timelineKeywords.setPrefWidth(100);

		Button btnEditTimeLine = new Button("Edit Timeline*");
		btnEditTimeLine.setPrefWidth(120);

		timeLineVBOX2.getChildren().addAll(lbTimelineStartDate, timelineStartDate, lbTimelineStartTime, timelineStartTime,
				lbTimelineEndDate, timelineEndDate, lbTimelineEndTime, timelineEndTime, lbTimelineTags, timelineKeywords, btnEditTimeLine);
		timeLineHBOX.getChildren().addAll(timeLineVBOX1, timeLineVBOX2);

		Button btnEditTimeLineMain = new Button("Edit Timeline");
		VBox timeLineVBOX = new VBox();
		timeLineVBOX.getChildren().addAll(timeLineHBOX);
		timeLineVBOX.setVisible(false);

		//Perform action on main edit timeline button
		btnEditTimeLineMain.setOnAction(e->{

			btnEditTimeLineMain.setVisible(false);
			timeLineHBOX.setVisible(true);
			timeLineVBOX.setVisible(true);

			//Add timeline combobox in Edit Event Screec
			ComboBox<String> cmbTimeline = new ComboBox<String>();
			cmbTimeline.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			cmbTimeline.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");

			ObservableList<String> timelineNameList = FXCollections.observableArrayList();
			ArrayList<Timeline> lstTimeLine = new ArrayList<Timeline>();
			//IF event list is empty then load null string in event combobox
			if(allTimelines.isEmpty()) {
				cmbTimeline.getItems().add("");
			}
			else {
				if(allTimelines != null && allTimelines.size() > 0) {
					for(int j = 0; j < allTimelines.size(); j++) {
						Timeline tl = allTimelines.get(j);
						String user = currentUser.getUserName();
						if(tl.getCreator().equals(user)) {
							timelineNameList.add(tl.getTitle());
							lstTimeLine.add(tl);
						}
					}

					cmbTimeline.getItems().addAll(timelineNameList);
					cmbTimeline.setValue(timelineNameList.get(0));

					//Getting 1st index from timeline list
					selectedTimelineForEdit = lstTimeLine.get(0);

					//Settig timeline name on text field
					timelineName.setText(selectedTimelineForEdit.getTitle());


					//Settig timeline desctiption on text field
					timelineDescription.setText(selectedTimelineForEdit.getDescription());

					//Settig timeline keywords on text field
					ArrayList<String> lstKeywords = new ArrayList<String>();
					lstKeywords = selectedTimelineForEdit.getTags();
					if(lstKeywords != null && lstKeywords.size() > 0) {
						for(int count = 0; count < lstKeywords.size(); count++) {
							keywordsForUpdate = keywordsForUpdate + lstKeywords.get(count);

						}
						timelineKeywords.setText(keywordsForUpdate);
					}

					//compute and set timeline start date
					timelineStartDate.getEditor().setDisable(true);
					if(selectedTimelineForEdit.getStart() != null && !selectedTimelineForEdit.getStart().toString().isBlank()) {
						LocalDate val = selectedTimelineForEdit.getStart().toLocalDate();
						timelineStartDate.setValue(val);
					}

					//compute and set timeline end date
					timelineEndDate.getEditor().setDisable(true);
					if(selectedTimelineForEdit.getStop() != null && !selectedTimelineForEdit.getStop().toString().isBlank()) {
						LocalDate val = selectedTimelineForEdit.getStop().toLocalDate();
						timelineEndDate.setValue(val);
					}
					//compute and set timeline start time
					LocalTime startTime = selectedTimelineForEdit.getStart().toLocalTime();
					timelineStartTime.setText(startTime+"");

					//compute and set timeline end date
					LocalTime endTime = selectedTimelineForEdit.getStop().toLocalTime();
					timelineEndTime.setText(endTime+"");
				}
			}

			//Perform action on Timeline Combobox
			cmbTimeline.setOnAction(f->{

				//Getting 1st index from timeline list
				selectedTimelineForEdit = lstTimeLine.get(cmbTimeline.getSelectionModel().getSelectedIndex());

				//Settig timeline name on text field
				timelineName.setText(selectedTimelineForEdit.getTitle());

				//Settig timeline desctiption on text field
				timelineDescription.setText(selectedTimelineForEdit.getDescription());

				//Settig timeline keywords on text field
				ArrayList<String> lstKeywords = new ArrayList<String>();
				lstKeywords = selectedTimelineForEdit.getTags();
				if(lstKeywords != null && lstKeywords.size() > 0) {
					keywordsForUpdate = lstKeywords.get(0);
					timelineKeywords.setText(keywordsForUpdate);

				}

				//compute and set timeline start date
				timelineStartDate.getEditor().setDisable(true);
				if(selectedTimelineForEdit.getStart() != null && !selectedTimelineForEdit.getStart().toString().isBlank()) {
					LocalDate val = selectedTimelineForEdit.getStart().toLocalDate();
					timelineStartDate.setValue(val);
				}

				//compute and set timeline end date
				timelineEndDate.getEditor().setDisable(true);
				if(selectedTimelineForEdit.getStop() != null && !selectedTimelineForEdit.getStop().toString().isBlank()) {
					LocalDate val = selectedTimelineForEdit.getStop().toLocalDate();
					timelineEndDate.setValue(val);
				}
				//compute and set timeline start time
				LocalTime startTime = selectedTimelineForEdit.getStart().toLocalTime();
				timelineStartTime.setText(startTime+"");

				//compute and set timeline end date
				LocalTime endTime = selectedTimelineForEdit.getStop().toLocalTime();
				timelineEndTime.setText(endTime+"");
			});

			VBox comboHBOX = new VBox();
			comboHBOX.setPrefWidth(1000);
			//adding timeline combobox in VBOX
			comboHBOX.getChildren().addAll(cmbTimeline);
			//set timeline combo box VBox at top of main boarder pane
			layout.setTop(comboHBOX);
			BorderPane.setMargin(comboHBOX, new Insets(10, 0, 0, 400));
			layout.setCenter(timeLineVBOX);
		});
		//end main timeline button action

		//Perform action on edit timeline button
		btnEditTimeLine.setOnAction(e->{
			keywordsForUpdate = timelineKeywords.getText();
			String timelineMsg = EditTimeline.updateTimeline(selectedTimelineForEdit, timelineStartDate, timelineEndDate, timelineName, timelineStartTime, timelineEndTime,timelineDescription, keywordsForUpdate);
			if(timelineMsg != null && timelineMsg.indexOf("Success") > -1) {
				this.close();
				try {
					new DashBoard().getDashBoard(currentUser);
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});



/*************************Starting event work********************************/
		HBox eventHBOX = new HBox();
		eventHBOX.setPrefWidth(1000);
		eventHBOX.setPrefHeight(400);

		VBox eventVBOX1 = new VBox();
		eventVBOX1.setPrefWidth(300);
		eventVBOX1.setPrefHeight(400);

		VBox eventVBOX2 = new VBox();
		eventVBOX2.setPrefWidth(300);
		eventVBOX2.setPrefHeight(400);

		HBox.setMargin(eventVBOX1, new Insets(10));
		HBox.setMargin(eventVBOX2, new Insets(10));
		VBox.setMargin(eventVBOX2, new Insets(20));

		Button btnEditEvent = new Button("Edit Event");
		btnEditEvent.setPrefWidth(120);

		Label lbEventName = new Label("Event name*");
		TextField eventName = new TextField();
		eventName.setPrefWidth(100);

		Label lbEventHead_desc = new Label("Head description for the event*");
		TextField eventHead_desc = new TextField();
		eventHead_desc.setPrefWidth(100);

		Label lbEventBody_desc = new Label("Body description for the event*");
		TextField eventBody_desc = new TextField();
		eventBody_desc.setPrefSize(100, 200);

		eventVBOX1.getChildren().addAll(lbEventName, eventName, lbEventHead_desc, eventHead_desc,
				lbEventBody_desc, eventBody_desc);

		Label lbEventStartDate = new Label("Start date*");
		DatePicker eventStartDate = new DatePicker();
		Label lbEventStartTime = new Label("Start time*");
		TextField eventStartTime = new TextField();
		eventStartTime.setPrefWidth(100);

		Label lbEventEndDate = new Label("End date*");
		DatePicker eventEndDate = new DatePicker();
		Label lbEventEndTime = new Label("End time*");
		TextField eventEndTime = new TextField();
		eventEndTime.setPrefWidth(100);
		
		Button btnDeleteEventPicture = new Button("Delete image");
		btnDeleteEventPicture.setPrefWidth(120);

		Label label = new Label("Drag a file here!");
		Label dropped = new Label("");
		VBox dragTarget = new VBox();

		dragTarget.getChildren().addAll(label, dropped, btnDeleteEventPicture);
		dragTarget.setOnDragOver(e->{
			if(e.getGestureSource() != dragTarget && e.getDragboard().hasFiles()) {
				e.acceptTransferModes(TransferMode.COPY);
			}
			e.consume();
		});

		dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if(db.hasFiles()) {
					dropped.setText(db.getFiles().toString());
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();

				picture = handleDragDropped(event);
			}

			private File handleDragDropped(DragEvent event) {
				Dragboard db = event.getDragboard();
				File tmp = db.getFiles().get(0);
				return tmp;
			}

		});

		eventVBOX2.getChildren().addAll(lbEventStartDate, eventStartDate, lbEventStartTime, eventStartTime,
				lbEventEndDate, eventEndDate, lbEventEndTime, eventEndTime, dragTarget);
		eventHBOX.getChildren().addAll(eventVBOX1, eventVBOX2, btnEditEvent);
		eventHBOX.setVisible(false);

		VBox eventVBOX = new VBox();
		eventVBOX.getChildren().addAll(eventHBOX);
		eventVBOX.setVisible(false);



		Button btnEditEventMain = new Button("Edit Event");
		//Add timeline combobox in Edit Event Screec
		ComboBox<String> cmbTimelineForEvent = new ComboBox<String>();
		//Perform action on main edit event button
		btnEditEventMain.setOnAction(e->{

			//Add event combobox in Edit Event Screec
			ComboBox<String> cmbEvents = new ComboBox<String>();
			cmbEvents.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			cmbEvents.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");
			ObservableList<String> eventsNameList = FXCollections.observableArrayList();

			cmbTimelineForEvent.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			cmbTimelineForEvent.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");

			ObservableList<String> timelineNameList = FXCollections.observableArrayList();
			ArrayList<Timeline> lstTimeLine = new ArrayList<Timeline>();
			//IF event list is empty then load null string in event combobox
			if(allTimelines.isEmpty()) {
				cmbTimelineForEvent.getItems().add("");
			}
			else {
				if(allTimelines != null && allTimelines.size() > 0) {
					for(int j = 0; j < allTimelines.size(); j++) {
						Timeline tl = allTimelines.get(j);
						
						if(tl.getCreator().equals(currentUser.getUserName())) {
							timelineNameList.add(tl.getTitle());
							lstTimeLine.add(tl);
						}
					}

					cmbTimelineForEvent.getItems().addAll(timelineNameList);
					cmbTimelineForEvent.setValue(timelineNameList.get(0));

					//Getting 1st index from timeline list
					selectedTimelineForEdit = lstTimeLine.get(0);

				}
			}
			
			eventList  = selectedTimelineForEdit.getContent();
			//IF event list is empty then load null string in event combobox
			if(eventList.isEmpty()) {
				cmbEvents.getItems().add("");
			}
			else {
				if(eventList != null && eventList.size() > 0) {
					for(int j = 0; j < eventList.size(); j++) {
						Event event = eventList.get(j);
						eventsNameList.add(event.getName());
					}
					cmbEvents.getItems().addAll(eventsNameList);
					cmbEvents.setValue(eventList.get(0).getName());

					//Getting 1st index from event list
					selectedEventForEdit = eventList.get(0);
					//setting event name
					eventName.setText(selectedEventForEdit.getName());
					//setting event head description
					eventHead_desc.setText(selectedEventForEdit.getHead_desc());
					//setting event body description
					eventBody_desc.setText(selectedEventForEdit.getBody_desc());

					//compute and set event start date
					eventStartDate.getEditor().setDisable(true);
					if(selectedEventForEdit.getStart() != null && !selectedEventForEdit.getStart().toString().isBlank()) {
						LocalDate val = selectedEventForEdit.getStart().toLocalDate();
						eventStartDate.setValue(val);
					}

					//compute and set event end date
					eventEndDate.getEditor().setDisable(true);
					if(selectedEventForEdit.getStop() != null && !selectedEventForEdit.getStop().toString().isBlank()) {
						LocalDate val = selectedEventForEdit.getStop().toLocalDate();
						eventEndDate.setValue(val);
					}
					//compute and set event start time
					LocalTime startTime = selectedEventForEdit.getStart().toLocalTime();
					eventStartTime.setText(startTime+"");

					//compute and set event end date
					LocalTime endTime = selectedEventForEdit.getStop().toLocalTime();
					eventEndTime.setText(endTime+"");

				}
			}

			cmbTimelineForEvent.setOnAction(f->{
				cmbEvents.getItems().clear();
				eventsNameList.clear();
				//Getting 1st index from timeline list
				selectedTimelineForEdit = lstTimeLine.get(cmbTimelineForEvent.getSelectionModel().getSelectedIndex());
				eventList  = selectedTimelineForEdit.getContent();
				//IF event list is empty then load null string in event combobox
				if(eventList.isEmpty()) {
					cmbEvents.getItems().add("");
					eventName.setText("");
					eventHead_desc.setText("");
					eventBody_desc.setText("");
					eventStartDate.setValue(null);
					eventEndDate.setValue(null);
					eventStartTime.setText(null);
					eventEndTime.setText(null);
				}
				else {
					if(eventList != null && eventList.size() > 0) {
						for(int j = 0; j < eventList.size(); j++) {
							Event event = eventList.get(j);
							eventsNameList.add(event.getName());
						}
						cmbEvents.getItems().addAll(eventsNameList);
						cmbEvents.setValue(eventList.get(0).getName());

						//Getting 1st index from event list
						selectedEventForEdit = eventList.get(0);
						//setting event name
						eventName.setText(selectedEventForEdit.getName());
						//setting event head description
						eventHead_desc.setText(selectedEventForEdit.getHead_desc());
						//setting event body description
						eventBody_desc.setText(selectedEventForEdit.getBody_desc());

						//compute and set event start date
						eventStartDate.getEditor().setDisable(true);
						if(selectedEventForEdit.getStart() != null && !selectedEventForEdit.getStart().toString().isBlank()) {
							LocalDate val = selectedEventForEdit.getStart().toLocalDate();
							eventStartDate.setValue(val);
						}

						//compute and set event end date
						eventEndDate.getEditor().setDisable(true);
						if(selectedEventForEdit.getStop() != null && !selectedEventForEdit.getStop().toString().isBlank()) {
							LocalDate val = selectedEventForEdit.getStop().toLocalDate();
							eventEndDate.setValue(val);
						}
						//compute and set event start time
						LocalTime startTime = selectedEventForEdit.getStart().toLocalTime();
						eventStartTime.setText(startTime+"");

						//compute and set event end date
						LocalTime endTime = selectedEventForEdit.getStop().toLocalTime();
						eventEndTime.setText(endTime+"");

					}
				}

			});

			//Perform action on Event Combobox
			cmbEvents.setOnAction(f->{
				if(eventList != null && eventList.size() > 0) {
					if(cmbEvents.getValue() != null) {
						cmbEvents.setValue(eventList.get(cmbEvents.getSelectionModel().getSelectedIndex()).getName());

						//Getting selected event index from event list
						selectedEventForEdit = eventList.get(cmbEvents.getSelectionModel().getSelectedIndex());
						//setting event name
						eventName.setText(selectedEventForEdit.getName());
						//setting event head description
						eventHead_desc.setText(selectedEventForEdit.getHead_desc());
						//setting event body description
						eventBody_desc.setText(selectedEventForEdit.getBody_desc());

						//compute and set event start date
						eventStartDate.getEditor().setDisable(true);
						if(selectedEventForEdit.getStart() != null && !selectedEventForEdit.getStart().toString().isBlank()) {
							LocalDate val = selectedEventForEdit.getStart().toLocalDate();
							eventStartDate.setValue(val);
						}

						//compute and set event end date
						eventEndDate.getEditor().setDisable(true);
						if(selectedEventForEdit.getStop() != null && !selectedEventForEdit.getStop().toString().isBlank()) {
							LocalDate val = selectedEventForEdit.getStop().toLocalDate();
							eventEndDate.setValue(val);
						}
						//compute and set event start time
						LocalTime startTime = selectedEventForEdit.getStart().toLocalTime();
						eventStartTime.setText(startTime+"");

						//compute and set event end date
						LocalTime endTime = selectedEventForEdit.getStop().toLocalTime();
						eventEndTime.setText(endTime+"");
					}

				}
			});
			
			Alert deleteEventNotification = new Alert(Alert.AlertType.INFORMATION);
			deleteEventNotification.setHeaderText(null);
			
			btnDeleteEventPicture.setOnMouseClicked(ev->{
				if(selectedEventForEdit.getImg() == null) {
					deleteEventNotification.setTitle("No picture available.");
					deleteEventNotification.setContentText("There is no picture to delete.");
					deleteEventNotification.showAndWait();
				}
				else {
					deleteEventImage(selectedEventForEdit, DBConnector());
					deleteEventNotification.setTitle("Deleting the picture...");
					deleteEventNotification.setContentText("The picture was successfully deleted.");
					deleteEventNotification.showAndWait();
				}
			});

			btnEditEventMain.setVisible(false);
			eventHBOX.setVisible(true);
			eventVBOX.setVisible(true);
			HBox comboHBOX = new HBox();
			comboHBOX.setPrefWidth(1000);
			//adding event combobox in VBOX
			comboHBOX.getChildren().addAll(cmbTimelineForEvent, cmbEvents);
			//set event combo box VBox at top of main boarder pane
			layout.setTop(comboHBOX);
			BorderPane.setMargin(comboHBOX, new Insets(10, 0, 0, 400));
			layout.setCenter(eventVBOX);
		});
		//End main edit event button code

		//Perform action on Edit event button
		btnEditEvent.setOnAction(e->{
			//calling edit method with event object, start date, end date, event name, event description, all timeline list and selected timeline name
			//Time line list and timeline name are used for applying date check
			String selectedTimeline = cmbTimelineForEvent.getSelectionModel().getSelectedItem();
			String eventMsg = new EditTimeline().updateEvent(selectedEventForEdit, eventStartDate, eventEndDate, eventName, eventHead_desc ,eventBody_desc, eventStartTime, eventEndTime, picture,  allTimelines, selectedTimeline);
			if(eventMsg != null && eventMsg.indexOf("Success") > -1) {
				this.close();
				try {
					getDashBoard(currentUser);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		HBox options = new HBox();
		options.getChildren().addAll(btnEditTimeLineMain, btnEditEventMain);
		HBox.setMargin(btnEditTimeLineMain, new Insets(300, 0, 10, 300));
		HBox.setMargin(btnEditEventMain, new Insets(300, 0, 10, 10));

		layout.setCenter(options);
		edit = new Scene(layout, 1200, 800);

		return edit;
	}


	private Scene getDelete() {
		layout = getLayout();
		ArrayList<String> timelineNamesOfUser = new ArrayList<String>();
		ArrayList<Timeline> timelinesOfUser = new ArrayList<Timeline>();
		selectTimelineNamesOfUser(currentUser, timelineNamesOfUser, DBConnector());
		for(int i = 0; i < timelineNamesOfUser.size(); i++) {
			try {
				timelinesOfUser.add(new Timeline(timelineNamesOfUser.get(i)));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		ComboBox<String> timelines = new ComboBox<String>();
		ListView<String> events = new ListView<String>();

		timelines.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		timelines.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");

		events.setMaxWidth(235);
		events.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		events.setStyle("-fx-border-width:0; -fx-font-size:16;-fx-text-fill: black;");

		ObservableList<String> timelinesNameList = FXCollections.observableArrayList();
		ObservableList<String> eventsNameList = FXCollections.observableArrayList();

		timelinesNameList.clear();
		eventsNameList.clear();
		events.getItems().clear();

		for(int i = 0; i < timelinesOfUser.size(); i++) {
			Timeline timeline = timelinesOfUser.get(i);
			timelinesNameList.add(timeline.getTitle());
		}
		timelines.getItems().addAll(timelinesNameList);
		timelines.setValue(timelinesOfUser.get(0).getTitle());

		ArrayList<Event> copy = timelinesOfUser.get(0).getContent();
		for(int j = 0; j < timelinesOfUser.get(0).getContent().size(); j++) {
			Event event = copy.get(j);
			eventsNameList.add(event.getName());
		}
		events.getItems().addAll(eventsNameList);

		BorderPane centerLayout = new BorderPane();
		centerLayout.setLeft(events);

		Button deleteTimeline = new Button("Delete selected timeline");
		deleteTimeline.setPrefWidth(200);
		Button deleteEvent = new Button("Delete selected event");
		deleteEvent.setPrefWidth(deleteTimeline.getPrefWidth());
		VBox buttons = new VBox();
		VBox.setMargin(deleteTimeline, new Insets(0, 20, 10, 0));
		VBox.setMargin(deleteEvent, new Insets(10, 20, 0, 0));
		buttons.getChildren().addAll(deleteTimeline, deleteEvent);
		centerLayout.setRight(buttons);

		VBox confirmDelete = new VBox();
		Label title = new Label("Confirmation of deletion");
		Label header = new Label();
		Label content = new Label();

		Button confirm = new Button("Confirm");
		Button cancel = new Button("Cancel");

		confirmDelete.getChildren().addAll(title, header, content, confirm, cancel);
		centerLayout.setCenter(confirmDelete);
		confirmDelete.setVisible(false);

		layout.setTop(timelines);
		BorderPane.setMargin(timelines, new Insets(5, 0, 0, 500));
		layout.setCenter(centerLayout);

		timelines.setOnAction(e->{
			eventsNameList.clear();
			events.getItems().clear();
			confirmDelete.setVisible(false);

			ArrayList<Event> eventCopy = timelinesOfUser.get(timelines.getSelectionModel().getSelectedIndex()).getContent();
			if(eventCopy.isEmpty()) {
				eventsNameList.add("No events!");
			}
			else {
				for(int j = 0; j < eventCopy.size(); j++) {
					Event event = eventCopy.get(j);
					eventsNameList.add(event.getName());
				}
			}
			events.getItems().addAll(eventsNameList);
		});

		deleteTimeline.setOnMouseClicked(e->{
			confirmDelete.setVisible(true);
			header.setText("You are deleting a timeline!");
			content.setText("Are you sure you want to delete this timeline and therefor all its events?");

			confirm.setOnMouseClicked(ev->{
				timelinesOfUser.get(timelines.getSelectionModel().getSelectedIndex()).update('d');
				header.setText("You have now deleted this timeline.");
				content.setText("You can make a new timeline and add events to it if you want.");
				try {
					getDelete();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			});

			cancel.setOnMouseClicked(v->{
				confirmDelete.setVisible(false);
			});
		});

		deleteEvent.setOnMouseClicked(e->{
			confirmDelete.setVisible(true);
			header.setText("You are deleting an event of this timeline!");
			content.setText("Are you sure you want to continue?");

			confirm.setOnMouseClicked(ev->{
				Timeline tmp = timelinesOfUser.get(timelines.getSelectionModel().getSelectedIndex());
				ArrayList<Event> eventNames = tmp.getContent();
				tmp.removeContent(eventNames.get(events.getSelectionModel().getSelectedIndex()));

				header.setText("You have now deleted this event.");
				content.setText("You can make a new event if you want.");
				
				try {
					getDelete();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			});

			cancel.setOnMouseClicked(v->{
				confirmDelete.setVisible(false);
			});
		});

		delete = new Scene(layout, 1200, 800);

		return delete;
	}







}
