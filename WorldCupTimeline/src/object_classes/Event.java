package object_classes;

//Imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Event {
	@SuppressWarnings("unused")
	private String fromTimeline;
    private String currentID;
    private String name;
    private LocalDateTime begins;
    private LocalDateTime ends;
    private String head_desc;
    private String body_desc;

	private File attachedFile;
	private Image img;
	
    private String startdate;
	private String enddate;
	private int event_id;
	private String startTime;
	private String endTime;
		
	public Event() {
		
	}

    //Constructors
    //periodical Event
    //Initialisation w/o additional files
    public Event(String itsTimeline, String itsName, LocalDateTime itsStart, LocalDateTime itsEnd, String itsHead_desc, String itsBody_desc) {
        this.fromTimeline = itsTimeline;
        this.name = itsName;
        this.begins = itsStart;
        this.ends = itsEnd;
        this.head_desc = itsHead_desc;
        this.body_desc = itsBody_desc;
        this.attachedFile = null;
    }
    
  //Initialisation w/o additional files
    public Event(String itsTimeline, String itsName, LocalDateTime itsStart, LocalDateTime itsEnd, String itsHead_desc, String itsBody_desc, int event_id) {
        this.fromTimeline = itsTimeline;
        this.name = itsName;
        this.begins = itsStart;
        this.ends = itsEnd;
        this.head_desc = itsHead_desc;
        this.body_desc = itsBody_desc;
        this.attachedFile = null;
        this.event_id = event_id;
    }
    
    // Initialisation w/ additional files
    public Event(String itsTimeline, String itsName, LocalDateTime itsStart, LocalDateTime itsEnd, String itsHead_desc, String itsBody_desc, int event_id, File itsFile) {
        this.fromTimeline = itsTimeline;
        this.name = itsName;
        this.begins = itsStart;
        this.ends = itsEnd;
        this.head_desc = itsHead_desc;
        this.body_desc = itsBody_desc;
        this.attachedFile = itsFile;
        try {
			this.img = new Image(new FileInputStream(this.attachedFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.event_id = event_id;
    }

    //Initialisation w/ additional files
    public Event(String itsTimeline, String itsName, LocalDateTime itsStart, LocalDateTime itsEnd, String itsHead_desc, String itsBody_desc, File itsFile) {
        this.fromTimeline = itsTimeline;
        this.name = itsName;
        this.begins = itsStart;
        this.ends = itsEnd;
        this.head_desc = itsHead_desc;
        this.body_desc = itsBody_desc;
        this.attachedFile = itsFile;
        try {
			this.img = new Image(new FileInputStream(this.attachedFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Punctual Event
    //Initialisation w/o additional files
    public Event(String itsTimeline, String itsName, LocalDateTime itsStart, String itsHead_desc, String itsBody_desc) {
        this.fromTimeline = itsTimeline;
        this.name = itsName;
        this.begins = this.ends = itsStart;
        this.head_desc = itsHead_desc;
        this.body_desc = itsBody_desc;
        this.attachedFile = null;
    }

    //Initialisation w/ additional files
    public Event(String itsTimeline, String itsName, LocalDateTime itsStart,String itsHead_desc, String itsBody_desc, File itsFile) {
        this.fromTimeline = itsTimeline;
        this.name = itsName;
        this.begins = this.ends = itsStart;
        this.head_desc = itsHead_desc;
        this.body_desc = itsBody_desc;
        this.attachedFile = itsFile;
        try {
			this.img = new Image(new FileInputStream(this.attachedFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    //setters / Getters
    //Name
    public String getName() { return this.name; }
    public void setName(String itsName) { this.name = itsName; }

    //Content
    public ArrayList<String> getContent() {
    	ArrayList<String> tmp = new ArrayList<String>();
    	tmp.add(this.head_desc);
    	tmp.add(this.body_desc);
    	
    	return tmp;
    }
    public void setContent(ArrayList<String> itsContent) {
    	this.head_desc = itsContent.get(0);
    	this.body_desc = itsContent.get(1);
    }

    //Attached Files
    public File getFiles() { return this.attachedFile; }
    public void setFiles(File itsFile) {
        this.attachedFile = itsFile;
        try {
			this.img = new Image(new FileInputStream(this.attachedFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public Image getImg() { return this.img; }

    //Begining
    public LocalDateTime getStart() { return this.begins; }
    public void setStart(LocalDateTime itBegins) {
        this.begins = itBegins;
    }

    //End
    public LocalDateTime getStop() { return this.ends; }
    public void setStop(LocalDateTime itEnds) {
        this.ends = itEnds;
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

	public String getHead_desc() {
		return head_desc;
	}

	public void setHead_desc(String head_desc) {
		this.head_desc = head_desc;
	}
	
    public String getBody_desc() {
		return body_desc;
	}

	public void setBody_desc(String body_desc) {
		this.body_desc = body_desc;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
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

	//CurrentID
    public String getCurrentID() { return this.currentID; }

    //Approve update
    public void update() { this.currentID = this.name; }
}
