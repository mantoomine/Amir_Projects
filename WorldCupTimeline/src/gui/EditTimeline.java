package gui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import javafx.scene.control.*;
import javafx.stage.Stage;

import database.DBManager;
import object_classes.*;

public class EditTimeline extends Stage{

    //Funciton to update timeline name with start date and end date in database.
    public static String updateTimeline(Timeline seltimeline, DatePicker start, DatePicker end, TextField name, TextField startTime, TextField endTime, TextField timelineDescription, String keywords) {
        try {
            if(validation(start, end, name, seltimeline.getCreator())) {
                //setting timeline name for edit
            	
            	Timeline timeline = seltimeline;
                timeline.setTitle(name.getText().trim());
                
                timeline.setDescription(timelineDescription.getText());
                //setting startdate for edit
                String startDate = start.getValue().toString();
                timeline.setStartdate(startDate);
                
                //setting end date for edit
                String endDate = end.getValue().toString();
                timeline.setEnddate(endDate);
                
              //setting keywords for edit
                timeline.setTimeLineKeywords(keywords);
                
              //setting start time for edit
                timeline.setStartTime(startTime.getText());
                
              //setting end time for edit
                timeline.setEndTime(endTime.getText());
                //Database funciton to update timeline name with startdate and enddate
                //This funciton receive Timeline Class Object
                boolean msg = DBManager.updateTimeline(seltimeline.getTitle(), timeline, DBManager.DBConnector());
                if(msg) {
                    JOptionPane.showMessageDialog(null, "Timeline updated successfully");
                    return "Success";
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //Making new method for updateEvent
    public String updateEvent(Event event, DatePicker start, DatePicker end, TextField name, TextField eventHead_desc, TextField eventBody_desc, TextField startTime, TextField endTime, File pic, ArrayList<Timeline> allTimelines, String selectedTimeline) {
        try {
        	
        	if(event != null) {
        		//Making selected timeline list for date validation
	        	List<Timeline> lstSelectedTimeline = allTimelines.stream().filter(p -> {
	
	                if ((selectedTimeline.equals(p.getTitle())))
	                {
	                    return true;
	                }
	                return false;
	            }).collect(Collectors.toList());
	        	
	        	//calling method for valdition
	            if(validateEvents(start, end, name, lstSelectedTimeline)) {
	                //setting even name for edit
	            	event.setName(name.getText());
	            	//setting description for edit
	            	if(eventHead_desc.getText() != null && !eventHead_desc.getText().isBlank()) {
	            		event.setHead_desc(eventHead_desc.getText());
	            	}
	            	if(eventBody_desc.getText() != null && !eventBody_desc.getText().isBlank()) {
	            		event.setBody_desc(eventBody_desc.getText());
	            	}
	            	
	            	//setting startdate for edit
	                String startDate = start.getValue().toString();
	                event.setStartdate(startDate);
	                //setting end date for edit
	                String endDate = end.getValue().toString();
	                event.setEnddate(endDate);
	                
	              //setting startdate for edit
	                String timeStart = startTime.getText();
	                event.setStartTime(timeStart);
	                //setting end date for edit
	                String timeEnd = endTime.getText();
	                event.setEndTime(timeEnd);
	                
	                if(event.getFiles() == null && pic.exists()) {
	                	event.setFiles(pic);
	                }
	                else if(event.getFiles() != null && pic.exists()) {
	                	event.setFiles(pic);
	                }
	                else if(event.getFiles() != null && !pic.exists()) {
	                	;
	                }
	                
	                
	                //calling database method to update event
	                boolean msg = DBManager.updateEvent(event, DBManager.DBConnector());
	                if(msg) {
	                    JOptionPane.showMessageDialog(null, "Event updated successfully");
	                    return "Success";
	                }
	            }
        	}
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean validation(DatePicker start, DatePicker end, TextField name, String timelineUser) {
        if(name.getText() == null || name.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter timeline name");
            name.requestFocus();
            return false;
        }
     
        String startDate = start.getValue().toString();
        String endDate = end.getValue().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;

        if(startDate != null && !startDate.equals("") && endDate != null && !endDate.equals("")) {
            try {
                date1 = sdf.parse(startDate.trim());
                date2 = sdf.parse(endDate.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (date1.after(date2)) {
                JOptionPane.showMessageDialog(null, "Start date must be older than End date");
                return false;
            }
        }

        if(start.getValue().toString() == null) {
            JOptionPane.showMessageDialog(null, "Please enter start date!");
            start.requestFocus();
            return false;
        }
        if(end.getValue().toString() == null) {
            JOptionPane.showMessageDialog(null, "Please enter end date!");
            end.requestFocus();
            return false;
        }
        return true;
    }
    
    private boolean validateEvents(DatePicker txtStartDate, DatePicker txtEndDate, TextField txtEventName, List<Timeline> lstSelectedTimeline) {
        if(txtEventName.getText() == null || txtEventName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter event name");
            txtEventName.requestFocus();
            return false;
        }

        String startDate = txtStartDate.getValue().toString();
        String endDate = txtEndDate.getValue().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;

        if(startDate != null && !startDate.equals("") && endDate != null && !endDate.equals("")) {
            try {
            	//put Start Date and End Date into Date variable, for comparing
                date1 = sdf.parse(startDate.trim());
                date2 = sdf.parse(endDate.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (date1.after(date2)) {
                JOptionPane.showMessageDialog(null, "Start date must be older than End date");
                return false;
            }
            
            Date datetimeLineStart = null;
            Date datetimeLineEnd = null;
            if(lstSelectedTimeline != null) {
            	
            	//Getting Timeline start and End Date
            	String timeLineStartDate = lstSelectedTimeline.get(0).getStartdate();
            	String timeLineEndDate = lstSelectedTimeline.get(0).getEnddate();
            	
            	if(timeLineStartDate != null && timeLineEndDate != null) {
	            	try {
	            		//put Timeline start and End Date into Date Format to convert it from String to Date Object
	            		datetimeLineStart = sdf.parse(timeLineStartDate.trim());
	            		datetimeLineEnd = sdf.parse(timeLineEndDate.trim());
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            	
	            	//Comparing dates, user before and after funciton of java Date class
	            	//date1 is user added event Start Date, and date2 is user added event End Date
	            	if (date1.before(datetimeLineStart) || date1.after(datetimeLineEnd) || date2.after(datetimeLineEnd) || date2.before(datetimeLineStart)) {
	                    JOptionPane.showMessageDialog(null, "Date range must be between Timeline Start Date("+timeLineStartDate+")"
	                    		+ " and Timeline End Date("+timeLineEndDate+")");
	                    return false;
	                }
            	}
            }
        }

        if(txtStartDate.getValue().toString() == null) {
            JOptionPane.showMessageDialog(null, "Please enter start date!");
            txtStartDate.requestFocus();
            return false;
        }
        if(txtEndDate.getValue().toString() == null) {
            JOptionPane.showMessageDialog(null, "Please enter end date!");
            txtEndDate.requestFocus();
            return false;
        }
        return true;
    }
}
