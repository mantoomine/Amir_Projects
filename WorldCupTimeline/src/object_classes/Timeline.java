package object_classes;

//Imports

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static database.DBManager.*;

public class Timeline {
    private String creator;
    private String currentID;
    private String title;
    private String description;
    private ArrayList<String> tags;
    private Timestamp dateOfCreation;
    private Timestamp dateOfLastEdit;
    private LocalDateTime begins;
    private LocalDateTime ends;
    private ArrayList<Event> content;
    
    private String startdate;
	private String enddate;
	private String timeLineKeywords;
	private String startTime;
	private String endTime;
	private String id;

	public Timeline() {
		
	}
    //Constructors
    //Initialisation
    public Timeline(User itsCreator, String itsTitle, LocalDateTime itsStart, LocalDateTime itsEnd, String itsDescription, ArrayList<String> itsTags) {
        this.title = this.currentID = itsTitle;
        this.creator = itsCreator.getUserName();
        this.begins = itsStart;
        this.ends = itsEnd;
        this.description = itsDescription;
        this.tags = itsTags;
        this.content = new ArrayList<Event>();
        this.update('i');
    }

    //Pulling from DataBase
    public Timeline(String itsTitle) throws Exception{
        //Pulling Timeline from DataBase if itsName
        //has a match in DB's table "Timeline"
        //and throws an Exception if no match
        ArrayList<String> stringFields = new ArrayList<String>();
        ArrayList<Timestamp> timestampFields = new ArrayList<Timestamp>();
        ArrayList<LocalDateTime> dateFields = new ArrayList<LocalDateTime>();
        ArrayList<Event> itsEvents = new ArrayList<Event>();
        
        Boolean confirmation = getTimeline(itsTitle, stringFields, timestampFields, dateFields, itsEvents, DBConnector());
        if (confirmation == true) {
            this.title = itsTitle;
            this.creator = stringFields.get(0);
            this.description = stringFields.get(1);
            String[] tags = stringFields.get(2).trim().split(" ");
            this.startdate = stringFields.get(3);
            this.enddate = stringFields.get(4);
            this.id = stringFields.get(5);
            this.tags = new ArrayList<String>();
            this.tags.addAll(Arrays.asList(tags));
            this.dateOfCreation = timestampFields.get(0);
            this.dateOfLastEdit = timestampFields.get(1);
            this.begins = dateFields.get(0);
            this.ends = dateFields.get(1);
            this.content = itsEvents;
        }
        else { throw new Exception("-------\n ERROR \t\t\t| Failed to connect to the Database !|\n-------\n\n"); }
    }

    //Setters / Getters
    //Title
    public String getTitle() { return this.title; }
    public void setTitle(String itsTitle) {
        this.title = itsTitle;
    }

    //Creator
    public String getCreator() { return this.creator; }

    //Description
    public String getDescription() { return this.description; }
    public void setDescription(String itsDescription) {
        this.description = itsDescription;
    }

    //Content
	public ArrayList<Event> getContent() {
		ArrayList<Event> tmp = new ArrayList<Event>(this.content);
		return tmp;
	}
    public void clearContent() {
        this.content = new ArrayList<Event>();
    }
    public void addContent(Event itsEvent) {
        this.content.add(itsEvent);
        this.content.sort(Comparator.comparing(Event::getStart));
        if(itsEvent.getFiles() == null) {
            try {
    			insertEventWithOutPic(this, DBConnector());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
        else {
        	try {
        		insertEventWithPic(this, DBConnector());
        	}
        	catch(Exception ex) {
        		ex.printStackTrace();
        	}
        }
    }
    public void updateContent(int itsOriginalPosition, Event itsEvent) {
        this.content.set(itsOriginalPosition, itsEvent);
        this.content.sort(Comparator.comparing(Event::getStart));
    }
    public void removeContent(Event itsEvent) {
        this.content.remove(itsEvent);
        deleteEventOfTimeline(itsEvent, selectTimelineId(this.title, DBConnector()), DBConnector());
    }

    //Tags
    @SuppressWarnings("unchecked")
	public ArrayList<String> getTags() { return (ArrayList<String>) this.tags.clone(); }
    public String displayTags() { return getKeywords(); }
    public void clearTags() {
        this.tags = new ArrayList<String>();
    }
    public void addTag(String itsTag) {
        this.tags.add(itsTag);
    }
    public void updateTag(int itsOriginalPosition, String itsTag) {
        this.tags.set(itsOriginalPosition, itsTag);
    }
    public void removeTag(String itsTag) {
        this.tags.remove(itsTag);
    }

    //Date of creation
    public Timestamp getDateOfCreation() { return this.dateOfCreation; }

    //Date of last edit
    public Timestamp getDateOfLastEdit() { return this.dateOfLastEdit; }

    //Start
    public LocalDateTime getStart() { return this.begins; }
    public void setStart(LocalDateTime newStart) {
        this.begins = newStart;
    }

    //Stop
    public LocalDateTime getStop() { return this.ends; }
    public void setStop(LocalDateTime newStop) {
        this.ends = newStop;
    }

    public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTimeLineKeywords() {
		return timeLineKeywords;
	}
	public void setTimeLineKeywords(String timeLineKeywords) {
		this.timeLineKeywords = timeLineKeywords;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//Update Database
    public Boolean update(char command) {
        Boolean confirmation = false;
        //Else -> we proceed
        switch(command) {
            default:
                break;
            case'i'://insert
                confirmation = insertTimeline(this, DBConnector());
                break;
            case'u'://update
                confirmation = updateTimeline(this.currentID, this, DBConnector());
                if(confirmation == true) { this.currentID = this.title; }
                break;
            case'd'://delete
            	confirmation = deleteTimeline(selectTimelineId(this.title, DBConnector()), DBConnector());
                break;
        }
        return confirmation;
    }

    //Private Methods
    //toString
    private String getKeywords() {

        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < tags.size(); i++) {
            String bff = tags.get(i);
            if (i == tags.size() - 1) {
                buff.append(bff);
            }else {
                buff.append(bff).append(",");
            }
        }

        return buff.toString();
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "creator='" + creator + '\'' +
                ", currentID='" + currentID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfLastEdit=" + dateOfLastEdit +
                ", begins=" + begins +
                ", ends=" + ends +
                ", content=" + content +
                '}';
    }
}
