package database;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

import javax.swing.filechooser.FileSystemView;

import object_classes.*;

public class DBManager extends DBConnection {

	// Main method for testing purposes only
	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("unused")
		User newUser = new User();
		try {
			newUser = new User("hashes", "testing123", "testing123");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
	
	// Method for easy connection for all methods via DBConnection
	public static Connection DBConnector() {
		try {
			// Creates a new DBConnection instance
			// and sets the connection to that instances method
			DBConnection getconn = new DBConnection();
			Connection conn = getconn.getConnection();
			
			// Returns connection if established successfully
			return conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns null if failed to establish a connection
		return null;
	}
	
	// User related Methods
	// Inserts a user to table users
	public static boolean insertUser(User newUser, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;

		// The mysql query to execute
		String query = "insert into users (username, salt, hashed, user_type) values (?, ?, ?, ?)";

		try {
			// Set the PreparedStatement to the query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, newUser.getUserName());
			prepStmt.setBytes(2, newUser.getSalt());
			prepStmt.setString(3, newUser.getHash());
			prepStmt.setBoolean(4, newUser.hasRights());
			//ADD TIMELINES

			// Execute said PreparedStatement
			prepStmt.execute();

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	// Updates a current user on all columns in table users
	public static boolean updateUser(String currentUserID, User updatedUser, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;

		// The mysql query to execute
		String query = "update users set username = ?, hashed = ?, user_type = ? where hashed = ?";

		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, updatedUser.getUserName());
			prepStmt.setString(2, updatedUser.getHash());
			prepStmt.setBoolean(3, updatedUser.hasRights());
			prepStmt.setString(4, currentUserID);

			// Execute said PreparedStatement
			prepStmt.execute();

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;

	}
	
	//Change Rights
	public static boolean changeRights(User currentUser, User usersPublicProfile, Boolean Rights, Connection con) {
		//Checks currentUser's rights for this action
		if (!currentUser.hasRights()) { return false; }

		// Create a PreparedStatement
		PreparedStatement prepStmt = null;

		// The mysql query to execute
		String query = "update users set user_type = ? where username = ?";

		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setBoolean(1, Rights);
			prepStmt.setString(2, usersPublicProfile.getUserName());

			// Execute said PreparedStatement
			prepStmt.execute();

			// Close connection to database after PreparedStatement is executed
			con.close();
			// Returns true is the execution went through
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	// Select all users
	public static boolean publicUserProfile(ArrayList<User> list, Connection con) {
		// Create a Statement and ResultSet
		Statement stmt;
		ResultSet rs;

		// The query to execute
		String query = "select username, user_type from users";

		try {

			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			// Adds every username to list
			while(rs.next()) {
				list.add(new User(rs.getString("username"), rs.getBoolean("user_type")));
			}

			// Close connection to database after Statement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}

	// Authentication
	public static boolean confirmUser(String itsUsername, String itsHashCode, ArrayList<Boolean> itsRights, Connection con) {
		// Create a PreparedStatement and ResultSet
		PreparedStatement prepStmt = null;
		ResultSet rs;

		// The query to execute
		String query = "select user_type, hashed from users where username = ?";

		try {

			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, itsUsername);
			rs = prepStmt.executeQuery();
			
			while(rs.next()) {
				if (rs.getString("hashed").equals(itsHashCode)) {
					itsRights.add(rs.getBoolean("user_type"));
				}
			}
			
			// Close connection to database after Statement is executed
			con.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		// Returns false is execution failed
		return false;
	}
	
	public static byte[] getUserSalt(String itsUsername, Connection con) {
		// Create a PreparedStatement and ResultSet
		PreparedStatement prepStmt = null;
		ResultSet rs;
		
		byte[] salt = new byte[1000];

		// The query to execute
		String query = "select salt from users where username = ?";

		try {

			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, itsUsername);
			rs = prepStmt.executeQuery();
			
			while(rs.next()) {
				salt = rs.getBytes("salt");
			}
			
			// Close connection to database after Statement is executed
			con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return salt;
	}

	// Timeline related Methods
	// Inserts a user to table users
	public static boolean insertTimeline(Timeline newTimeline, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;

		// The mysql query to execute
		String query = "insert into timelines (username, name, description, keywords, start_date, start_time, end_date, end_time) values (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			// Set the PreparedStatement to the query with values
			LocalDate dateStart = newTimeline.getStart().toLocalDate();
			LocalTime timeStart = newTimeline.getStart().toLocalTime();
			LocalDate dateStop = newTimeline.getStop().toLocalDate();
			LocalTime timeStop = newTimeline.getStop().toLocalTime();
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, newTimeline.getCreator());
			prepStmt.setString(2, newTimeline.getTitle());
			prepStmt.setString(3, newTimeline.getDescription());
			prepStmt.setString(4, newTimeline.displayTags());
			prepStmt.setDate(5, java.sql.Date.valueOf(dateStart));
			prepStmt.setTime(6, java.sql.Time.valueOf(timeStart));
			prepStmt.setDate(7, java.sql.Date.valueOf(dateStop));
			prepStmt.setTime(8, java.sql.Time.valueOf(timeStop));
			//ADD TIMELINES

			// Execute said PreparedStatement
			prepStmt.execute();
			
			ArrayList<Event> events = newTimeline.getContent();
			for (int i = 0; i < events.size(); i++) {
				// Set the PreparedStatement to the query with values
				query = "insert into events (timeline_id, event_name, start_date, start_time, end_date, end_time, head_desc, body_desc) values (?, ?, ?, ?, ?, ?, ?, ?)";
				dateStart = events.get(i).getStart().toLocalDate();
				timeStart = events.get(i).getStart().toLocalTime();
				dateStop = events.get(i).getStop().toLocalDate();
				timeStop = events.get(i).getStop().toLocalTime();
				ArrayList<String> content = events.get(i).getContent();
				
				prepStmt = con.prepareStatement(query);
				prepStmt.setInt(1, selectTimelineId(newTimeline.getTitle(), DBConnector()));
				prepStmt.setString(2, events.get(i).getName());
				prepStmt.setDate(3, java.sql.Date.valueOf(dateStart));
				prepStmt.setTime(4, java.sql.Time.valueOf(timeStart));
				prepStmt.setDate(5, java.sql.Date.valueOf(dateStop));
				prepStmt.setTime(6, java.sql.Time.valueOf(timeStop));
				prepStmt.setString(7, content.get(0));
				prepStmt.setString(8, content.get(1));
				//ADD TIMELINES

				// Execute said PreparedStatement
				prepStmt.execute();
			}

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}

	// Pulls a Timeline
	public static boolean getTimeline(String itsTitle, ArrayList<String> stringFields, ArrayList<Timestamp> longFields, ArrayList<LocalDateTime> dateFields, ArrayList<Event> itsEvents, Connection con) {
		// Create a PreparedStatement and ResultSet
		PreparedStatement prepStmt = null;
		ResultSet rs;
		int id = selectTimelineId(itsTitle, DBConnector());

		// The query to execute
		String query = "select id, username, description, keywords, date_of_creation, date_of_last_edit, start_date, start_time, end_date, end_time from timelines where id = ?";

		try {
			// Pulls Timeline
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery();
			while(rs.next()) {
				stringFields.add(rs.getString("username"));
				stringFields.add(rs.getString("description"));
				stringFields.add(rs.getString("keywords"));
				stringFields.add(rs.getString("start_date"));
				stringFields.add(rs.getString("end_date"));
				stringFields.add(rs.getString("id"));
				longFields.add(rs.getTimestamp("date_of_creation"));
				longFields.add(rs.getTimestamp("date_of_last_edit"));
				dateFields.add(LocalDateTime.of(rs.getDate("start_date").toLocalDate(), rs.getTime("start_time").toLocalTime()));
				dateFields.add(LocalDateTime.of(rs.getDate("end_date").toLocalDate(), rs.getTime("end_time").toLocalTime()));
				
			}
			
			// Pulls its Events
			query = "select event_name, start_date, start_time, end_date, end_time, head_desc, body_desc, event_id, picture from events where timeline_id = ?";
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery();

			String fromTimeline = itsTitle;
			String name;
			LocalDateTime begins;
			LocalDateTime ends;
			String head_desc;
			String body_desc;
			File itsFile;
			int event_id;

			while (rs.next()) {
				
				name = rs.getString("event_name");
				begins = LocalDateTime.of(rs.getDate("start_date").toLocalDate(), rs.getTime("start_time").toLocalTime());
				ends = LocalDateTime.of(rs.getDate("end_date").toLocalDate(), rs.getTime("end_time").toLocalTime());
				head_desc = rs.getString("head_desc");
				body_desc = rs.getString("body_desc");
				event_id = rs.getInt("event_id");
				
				if(eventPicExists(name, DBConnector())) {
					itsFile = getEventPic(name, DBConnector());
					itsEvents.add(new Event(fromTimeline, name, begins, ends, head_desc, body_desc, event_id, itsFile));
				}
				else {
					itsEvents.add(new Event(fromTimeline, name, begins, ends, head_desc, body_desc, event_id));
				}
			}

			// Close connection to database after Statement is executed
			con.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		// Returns false is execution failed
		return false;
	}
	
	// Update a Timeline
	public static boolean updateTimeline(String currentID, Timeline timeline, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;

		// The mysql query to execute
		String query = "update timelines set username = ?, name = ?, description = ?, keywords = ?, start_date = ?, start_time = ?, end_date = ?, end_time = ? where id = ?";

		try {
			// Set the PreparedStatement to the query with values

			
			Date dateStart = Date.valueOf(timeline.getStartdate());
			Date dateStop = Date.valueOf(timeline.getEnddate());
			
			LocalTime ltTimelineStartTime = LocalTime.parse(timeline.getStartTime());
			LocalTime ltTimelineEndTime = LocalTime.parse(timeline.getEndTime());
			
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, timeline.getCreator());
			prepStmt.setString(2, timeline.getTitle());
			prepStmt.setString(3, timeline.getDescription());
			prepStmt.setString(4, timeline.getTimeLineKeywords());
			prepStmt.setDate(5, dateStart);
			prepStmt.setTime(6, java.sql.Time.valueOf(ltTimelineStartTime));
			prepStmt.setDate(7, dateStop);
			prepStmt.setTime(8, java.sql.Time.valueOf(ltTimelineEndTime));
			prepStmt.setString(9, timeline.getId());
			//ADD TIMELINES

			// Execute said PreparedStatement
			prepStmt.execute();

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	public static boolean eventPicExists(String name, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = "select picture from events where event_name = ?";
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getBlob("picture") != null) {
					con.close();
					return true;
				}
			}
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static File getEventPic(String name, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		File itsFile = null;
		String path = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		path = path + "/db_pics/";
		File eventPicsFolder = new File(path);
		eventPicsFolder.mkdir();
		
		String query = "select picture from events where event_name = ?";
		
		FileOutputStream fos = null;
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			itsFile = new File(path + "/" + name + "_BLOB.jpg");
			fos = new FileOutputStream(itsFile);
			
			while(rs.next()) {
				InputStream input = rs.getBinaryStream("picture");
				byte[] buffer = new byte[1024];
				while(input.read(buffer) > 0) {
					fos.write(buffer);
				}
			}
			
			
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return itsFile;
	}
	
	public static boolean updateEvent(Event event, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;

		// The mysql query to execute
		String query = "update events set event_name = ?, start_date = ?, end_date = ?, head_desc = ?, body_desc = ?, start_time = ?, end_time = ? where event_id = ?";

		try {
			
			Date dateStart = Date.valueOf(event.getStartdate());
			Date dateStop = Date.valueOf(event.getEnddate());
			
			LocalTime ltEventStartTime = LocalTime.parse(event.getStartTime());
			LocalTime ltEventEndTime = LocalTime.parse(event.getEndTime());
			
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, event.getName());
			prepStmt.setDate(2, dateStart);
			prepStmt.setDate(3, dateStop);
			prepStmt.setString(4, event.getHead_desc());
			prepStmt.setString(5, event.getBody_desc());
			prepStmt.setTime(6, java.sql.Time.valueOf(ltEventStartTime));
			prepStmt.setTime(7, java.sql.Time.valueOf(ltEventEndTime));
			prepStmt.setInt(8, event.getEvent_id());

			// Execute said PreparedStatement
			prepStmt.execute();
			
			if(event.getFiles() == null) {
				;
			}
			else {
				PreparedStatement stmt = null;
				String picQuery = "update events set picture = ? where event_id = ?";
				
				try {
					FileInputStream file = new FileInputStream(event.getFiles());
					stmt = con.prepareStatement(picQuery);
					stmt.setBlob(1, file);
					stmt.setInt(2, event.getEvent_id());
					
					stmt.execute();
					
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	// My own methods, some will be used
	// 8 methods for inserting, updating and selecting data in table users
	
	// Creates and inserts a user to table users
	public static boolean insertToUsers(String username, String password, boolean admin, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "insert into users (username, password, user_type) values (?, ?, ?)";
		
		try {
			// Set the PreparedStatement to the query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			prepStmt.setBoolean(3, admin);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates a current user on all columns in table users
	public static boolean updateAUser(String currentUsername, String newUsername, String newPassword, boolean newAdmin, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update users set username = ?, password = ?, user_type = ? where username = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, newUsername);
			prepStmt.setString(2, newPassword);
			prepStmt.setBoolean(3, newAdmin);
			prepStmt.setString(4, currentUsername);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates a current user's username in table users
	public static boolean updateUsername(String currentUsername, String newUsername, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update users set username = ? where username = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, newUsername);
			prepStmt.setString(2, currentUsername);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates a current user's admin-rights in table users
	public static boolean updateUserAdmin(String username, boolean admin, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update users set user_type = ? where username = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setBoolean(1, admin);
			prepStmt.setString(2, username);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Select all users
	public static boolean selectAllUsers(ArrayList<String> list, Connection con) {
		// Create a Statement and ResultSet
		Statement stmt;
		ResultSet rs;
		
		// The query to execute 
		String query = "select username from users";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			// Adds every username to list
			while(rs.next()) {
				list.add(rs.getString("username"));
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Select all users and admin status
	public static boolean selectUsersAndStatus(ArrayList<String> users, ArrayList<Integer> adminRights, Connection con) {
		// Create a Statement and ResultSet
		Statement stmt;
		ResultSet rs;
		
		// The query to execute 
		String query = "select username, user_type from users";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			// Adds every username to list
			while(rs.next()) {
				users.add(rs.getString("username"));
				adminRights.add(rs.getInt("user_type"));
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// 5 methods for inserting, updating and selecting data in table timelines
	
	// Creates and inserts a timeline to table timelines
	public static boolean insertTimeline(String creator, String timelineName, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "insert into timelines (username, name) values (?, ?)";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, creator);
			prepStmt.setString(2, timelineName);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates the creator of a timeline in table timelines
	public static boolean updateTimelineCreator(String creator, int timelineId, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update timelines set username = ? where id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, creator);
			prepStmt.setInt(2, timelineId);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Update the name of a timeline in table timelines
	public static boolean updateTimelineName(String timelineName, int timelineId, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update timelines set name = ? where id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, timelineName);
			prepStmt.setInt(2, timelineId);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Selects all names of the timelines from table timelines
	public static boolean selectTimelineNames(ArrayList<String> list, Connection con) {
		// Create a Statement and ResultSet
		Statement stmt;
		ResultSet rs;
		
		// The query to execute 
		String query = "select name from timelines";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			// Adds every username to list
			while(rs.next()) {
				list.add(rs.getString("name"));
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	public static int selectTimelineId(String name, Connection con) {
		PreparedStatement stmt;
		ResultSet rs;
		int id = -1;
		
		String query = "select id from timelines where name = ?";
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
			
			con.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static boolean selectTimelineNamesOfUser(User user, ArrayList<String> list, Connection con) {
		PreparedStatement stmt;
		ResultSet rs;

		String query = "select name from timelines where username = ?";
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, user.getUserName());
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString("name"));
			}
			
			con.close();
			
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String selectTimelineUsername(String name, Connection con) {
		// Create a Statement and ResultSet
		PreparedStatement stmt;
		ResultSet rs;
		String username = null;
		
		// The query to execute 
		String query = "select username from timelines where name = ?";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			// Adds every username to list
			while(rs.next()) {
				username = rs.getString("username");
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return username;
	}
	
	
	// 8 methods for inserting, updating and selecting data in table events
	
	// Creates and inserts an event to table events
	public static boolean insertEventWithOutPic(Timeline timeline, Connection con) throws FileNotFoundException {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		boolean success = false;
		String query;
		LocalDate dateStart;
		LocalTime timeStart;
		LocalDate dateStop;
		LocalTime timeStop;
		
		// The mysql query to execute 
		ArrayList<Event> events = timeline.getContent();
		ArrayList<String> dbEvents = new ArrayList<String>();
		selectEventNames(dbEvents, DBConnector());
		for (int i = 0; i < events.size(); i++) {
			if(dbEvents.contains(events.get(i).getName())) {
				continue;
			}
			else {
				// Set the PreparedStatement to the query with values
				query = "insert into events (timeline_id, event_name, start_date, start_time, end_date, end_time, head_desc, body_desc) values (?, ?, ?, ?, ?, ?, ?, ?)";
				dateStart = events.get(i).getStart().toLocalDate();
				timeStart = events.get(i).getStart().toLocalTime();
				dateStop = events.get(i).getStop().toLocalDate();
				timeStop = events.get(i).getStop().toLocalTime();
				ArrayList<String> content = events.get(i).getContent();
				
				try {
					prepStmt = con.prepareStatement(query);
					prepStmt.setInt(1, selectTimelineId(timeline.getTitle(), DBConnector()));
					prepStmt.setString(2, events.get(i).getName());
					prepStmt.setDate(3, java.sql.Date.valueOf(dateStart));
					prepStmt.setTime(4, java.sql.Time.valueOf(timeStart));
					prepStmt.setDate(5, java.sql.Date.valueOf(dateStop));
					prepStmt.setTime(6, java.sql.Time.valueOf(timeStop));
					prepStmt.setString(7, content.get(0));
					prepStmt.setString(8, content.get(1));
					
					// Execute said PreparedStatement
					prepStmt.execute();
					
					con.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				
				success = true;
			}
		}
		
		// Returns false is execution failed
		return success;
	}
	
	// Creates and inserts an event to table events
	public static boolean insertEventWithPic(Timeline timeline, Connection con) throws FileNotFoundException {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		boolean success = false;
		String query;
		LocalDate dateStart;
		LocalTime timeStart;
		LocalDate dateStop;
		LocalTime timeStop;
		
		// The mysql query to execute 
		ArrayList<Event> events = timeline.getContent();
		ArrayList<String> dbEvents = new ArrayList<String>();
		selectEventNames(dbEvents, DBConnector());
		for (int i = 0; i < events.size(); i++) {
			if(dbEvents.contains(events.get(i).getName())) {
				continue;
			}
			else {
				// Set the PreparedStatement to the query with values
				query = "insert into events (timeline_id, event_name, start_date, start_time, end_date, end_time, head_desc, body_desc, picture) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				dateStart = events.get(i).getStart().toLocalDate();
				timeStart = events.get(i).getStart().toLocalTime();
				dateStop = events.get(i).getStop().toLocalDate();
				timeStop = events.get(i).getStop().toLocalTime();
				ArrayList<String> content = events.get(i).getContent();
				FileInputStream file = new FileInputStream(events.get(i).getFiles());
				
				try {
					prepStmt = con.prepareStatement(query);
					prepStmt.setInt(1, selectTimelineId(timeline.getTitle(), DBConnector()));
					prepStmt.setString(2, events.get(i).getName());
					prepStmt.setDate(3, java.sql.Date.valueOf(dateStart));
					prepStmt.setTime(4, java.sql.Time.valueOf(timeStart));
					prepStmt.setDate(5, java.sql.Date.valueOf(dateStop));
					prepStmt.setTime(6, java.sql.Time.valueOf(timeStop));
					prepStmt.setString(7, content.get(0));
					prepStmt.setString(8, content.get(1));
					prepStmt.setBlob(9, file);
					
					// Execute said PreparedStatement
					prepStmt.execute();
					
					con.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				
				success = true;
			}
		}
		
		// Returns false is execution failed
		return success;
	}
	
	// Updates the name of an event in table events
	public static boolean updateEventName(String eventName, int eventId, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The query to execute 
		String query = "update events set event_name = ? where event_id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, eventName);
			prepStmt.setInt(2, eventId);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates the smallDesc of an event in table events
	public static boolean updateEventSmallDesc(String smallDesc, int eventId, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update events set head_desc = ? where event_id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, smallDesc);
			prepStmt.setInt(2, eventId);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates the fullDesc of an event in table events
	public static boolean updateEventFullDesc(String fullDesc, int eventId, Connection con) {
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update events set body_desc = ? where event_id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, fullDesc);
			prepStmt.setInt(2, eventId);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	// Updates the picture of an event in table events
	public static boolean updateEventPicture(String picture, int eventId, Connection con) throws FileNotFoundException {
		// Creates a FileInputStream from the file URL picture
		FileInputStream file = new FileInputStream(new File(picture));
		
		// Create a PreparedStatement
		PreparedStatement prepStmt = null;
		
		// The mysql query to execute 
		String query = "update events set picture = ? where event_id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setBlob(1, file);
			prepStmt.setInt(2, eventId);
			
			// Execute said PreparedStatement
			prepStmt.execute();
			
			// Close connection to database after PreparedStatement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	public static boolean deleteEventImage(Event event, Connection con) {
        // Create a PreparedStatement
        PreparedStatement prepStmt = null;

        // The mysql query to execute
        String query = "update events set picture = null where event_id = ?";

        try {
            // Set the PreparedStatement to query with values
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, event.getEvent_id());

            // Execute said PreparedStatement
            prepStmt.execute();

            // Close connection to database after PreparedStatement is executed
            con.close();

            // Returns true is the execution went through
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Returns false is execution failed
        return false;
	}
	
	// Select names of events from table events
	public static boolean selectEventNames(ArrayList<String> list, Connection con) {
		// Create a Statement and ResultSet
		Statement stmt;
		ResultSet rs;
		
		// The query to execute 
		String query = "select event_name from events";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			// Adds every username to list
			while(rs.next()) {
				list.add(rs.getString("event_name"));
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return false;
	}
	
	public static int selectStatusOfUser(String username, Connection con) {
		int admin = 0;
		// Create a Statement and ResultSet
		PreparedStatement stmt;
		ResultSet rs;
		
		// The query to execute 
		String query = "select user_type from users where username = ?";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			
			// Adds every username to list
			while(rs.next()) {
				admin = rs.getInt("user_type");
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns false is execution failed
		return admin;
	}
	
	public static boolean selectUsersStatus(ArrayList<String> adminRights, Connection con) {
		// Create a Statement and ResultSet
		Statement stmt;
		ResultSet rs;

		// The query to execute
		String query = "select username, user_type from users";

		try {

			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			// Adds every username to list
			while(rs.next()) {
				adminRights.add(rs.getString("user_type"));
			}

			// Close connection to database after Statement is executed
			con.close();

			// Returns true is the execution went through
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	public static boolean deleteTimeline(int timelineId, Connection con) {
		if(deleteEventsByTimelineId(timelineId, DBConnector()) == true) {
			
			// Create a PreparedStatement
			PreparedStatement prepStmt = null;
			// The query to execute
			String query = "DELETE FROM timelines WHERE id = ?";

			try {
				// Set the PreparedStatement to query with values
				prepStmt = con.prepareStatement(query);
				prepStmt.setInt(1, timelineId);
				
				// Execute said PreparedStatement
				prepStmt.executeUpdate();
				// Close connection to database after PreparedStatement is executed
				con.close();
				
				// Returns true is the execution went through
				return true;
				
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		// Returns false is execution failed
		return false;
	}

	public static boolean deleteEventsByTimelineId(int timelineId, Connection con) {

		// Create a PreparedStatement
		PreparedStatement prepStmt;
		
		// The mysql query to execute
		String query = "DELETE FROM events WHERE timeline_id = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, timelineId);

			// Execute said PreparedStatement
			prepStmt.executeUpdate();

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	public static boolean deleteEventOfTimeline(Event event, int timelineId, Connection con) {

		// Create a PreparedStatement
		PreparedStatement prepStmt;
		
		// The mysql query to execute
		String query = "DELETE FROM events WHERE timeline_id = ? AND event_name = ?";
		
		try {
			// Set the PreparedStatement to query with values
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, timelineId);
			prepStmt.setString(2, event.getName());

			// Execute said PreparedStatement
			prepStmt.executeUpdate();

			// Close connection to database after PreparedStatement is executed
			con.close();

			// Returns true is the execution went through
			return true;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		// Returns false is execution failed
		return false;
	}
	
	public static boolean insertRating(int timelineID, String username, int vote, Connection con) {
		
		// Create a PreparedStatement
				PreparedStatement prepStmt = null;

				// The mysql query to execute
				String query = "insert into votes (timeline_id, username, stars_number) values (?, ?, ?)";

				try {
					// Set the PreparedStatement to the query with values
					prepStmt = con.prepareStatement(query);
					prepStmt.setInt(1, timelineID);
					prepStmt.setString(2, username	);
					prepStmt.setInt(3, vote);

					// Execute said PreparedStatement
					prepStmt.execute();

					// Close connection to database after PreparedStatement is executed
					con.close();

					// Returns true is the query sent to database
					return true;
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				// Returns false is sending failed
				return false;
	}


	public static boolean getRating(int timelineID, String username, Connection conn) {
	    // Create statement and resultSet
		PreparedStatement stmt;
		ResultSet rs;
		
		String query = "select stars_number from votes where timeline_id = ? and username = ?";
		
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, timelineID);
			stmt.setString(2, username);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static int getAverageRating(int timelineID, Connection conn) {
		int average = 0;
		int ratingSum = 0;
		int numberOfRatings = 0;
		// Create statement and resultSet
		PreparedStatement stmt;
		ResultSet rs;
		
		String query = "select stars_number from votes where timeline_id = ?";
		
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, timelineID);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				ratingSum = ratingSum + rs.getInt("stars_number");
				numberOfRatings++;
			}
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	    if(numberOfRatings == 0) {return 0;}

	    average = ratingSum / numberOfRatings;
			    
		return average;
	
	}


	public static String selectTimelineDesc(int timelineID, Connection con) {
		String timelineDesc = null;
		// Create a Statement and ResultSet
		PreparedStatement stmt;
		ResultSet rs;
		
		// The query to execute 
		String query = "select description from timelines where id = ?";
		
		try {
			
			// Creates a Statement to the database and
			// sets the ResultSet to Statement with query
			stmt = con.prepareStatement(query);
			stmt.setInt(1, timelineID);
			rs = stmt.executeQuery();
			
			// Get timeline description
			while(rs.next()) {
				timelineDesc = rs.getString("description");
			}
			
			// Close connection to database after Statement is executed
			con.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns timeline description
		return timelineDesc;
		
	}

}
