package WorldCupTimeline.src.object_classes;


import WorldCupTimeline.src.hashForPassword.PassHash;

import java.sql.SQLException;
import java.util.ArrayList;

import static WorldCupTimeline.src.database.DBManager.*;
import static WorldCupTimeline.src.hashForPassword.PassHash.getSecurePassword;


public class User {
    private String currentID;
    private String userName;
    private String hashedCredentials;
    private Boolean adminRights;
    private byte[] salt;

    //Constructors
    //Public User Profile
    //Dummy Object [Usefulness to be confirmed]
    public User() {}

    //Public profile
    public User(String itsUsername, Boolean itsRights) {
        this.userName =  itsUsername;
        this.adminRights = itsRights;
    }

    //User Registration
    public User(String itsUserName, String itsPassword, String itsPasswordConfirmation) throws Exception {
        StringBuilder validityCheck = new StringBuilder(); //Concatenate every errors to throw them at once

        try { //Try setting the UserName
            this.setUserName(itsUserName);
            this.setPassword(itsPassword, itsPasswordConfirmation);
        }
        catch (Exception e) { validityCheck.append(e).append("\n\n"); }
        //If the UserName/Password are judged invalid for some reason -> abort and report said reasons
        if (validityCheck.length() != 0) { throw new Exception(validityCheck.toString()); }
        else {
            this.adminRights = false;
            this.currentID = this.hashedCredentials;
            //User Object has been created and is ready to be sent to DB
            this.update('i');
        }
    }

    //User Signing in
    public User(String itsUserName, String itsPassword) throws Exception {
        //Attempt pulling User named itsUserName from Database (if no such User -> Throw new Exception)
        //If successful, pulls remaining info from Database and creates the User object
        ArrayList<Boolean> itsRights = new ArrayList<Boolean>();
        this.salt = getUserSalt(itsUserName, DBConnector());
        String tempHash = getSecurePassword(itsPassword, this.salt);
        
        if (DBConnector() == null) { throw new Exception("-------\n ERROR \t\t\t| Connection lost !|\n-------\n\n"); }
        Boolean confirmation = confirmUser(itsUserName, tempHash, itsRights, DBConnector());
        if (confirmation == false) { throw new Exception("-------\n ERROR \t\t\t| Failed to connect to the Database !|\n-------\n\n"); }
        else if (itsRights.isEmpty()) { throw new Exception("-------\n ERROR \t\t\t| Authentication failed : There are no match for the provided couple Username & Password !|\n-------\n\n"); }
        else {
            this.userName = itsUserName;
            this.hashedCredentials = this.currentID = tempHash;
            this.adminRights = itsRights.get(0);
        }
    }




    //Setters / Getters
    //UserName
    public String getUserName() { return this.userName;}
    public void setUserName(String itsUserName) throws Exception {
        if (verifyUserName(itsUserName).equals(itsUserName)) {
//			String outdatedUsername = this.userName;
            this.userName = itsUserName;
        }
        else { throw new Exception(verifyUserName(itsUserName)); }
    }

    //Password
    public String getHash() { return this.hashedCredentials; }
    public void setPassword(String itsPassword, String itsPasswordConfirmation) throws Exception {
        if (itsPassword.equals(itsPasswordConfirmation)) {
            String certifiedPassword = verifyPassword(itsPassword);
            if (certifiedPassword.equals(itsPassword)) {
            	this.salt = PassHash.getSalt();
            	this.hashedCredentials = getSecurePassword(certifiedPassword, this.salt);
            }
            else { throw new Exception(certifiedPassword); }
        }
        else { throw new Exception("-------\n ERROR \t\t\t| Your password and its confirmation must be identical !|\n-------\n\n"); }
    }
    public byte[] getSalt() { return this.salt; }

    //Rights
    public Boolean hasRights() { return this.adminRights; }

    //Update Database
    public Boolean update(char command) {
        Boolean confirmation = false;
        //Don't send dummy objects to DB
        if (this.userName == null || this.hashedCredentials == "" || this.adminRights == null) { return false; }
        //Else -> we proceed
        switch(command) {
            default:
                break;
            case'i'://insert
                confirmation = insertUser(this, DBConnector());
                break;
            case'u'://update
                confirmation = updateUser(this.currentID, this, DBConnector());
                if(confirmation) { this.currentID = this.hashedCredentials; }
                break;
            case'd'://delete
                break;
        }
        return confirmation;
    }



    //Private Methods
    //Credentials Validity Check
	private static String verifyUserName(String itsUserName) throws ClassNotFoundException, SQLException {  // Here we check the criteria that the user name needs to follow
        StringBuilder validityCheck = new StringBuilder();
        //Errors are append to validityCheck if they are found

        //Is 3 characters long or more?
        if (itsUserName.length() < 3) { validityCheck.append("-------\n ERROR \t\t\t| Your UserName needs to have at least 3 characters !|\n-------\n\n"); }

        //Is only composed of alphanumeric characters ?
        for (int i = 0; i < itsUserName.length(); i++) {
            int c = itsUserName.charAt(i);
            if (c < 48 || c > 57 && c < 65 || c > 90 && c < 97 || c > 122) {
                validityCheck.append("-------\n ERROR \t\t\t| Your UserName needs to be only composed of 'a'-'z', 'A'-'Z' and '0'-'9' characters !|\n-------\n\n");
                break;
            }
        }

        //Is available ?
        ArrayList<User> users = new ArrayList<User>();
        Boolean confirmation = publicUserProfile(users, DBConnector());
        if (confirmation == false) { validityCheck.append("-------\n ERROR \t\t\t| Couldn't connect to Database to verify UserName availability !|\n-------\n\n"); }
        else {
            Boolean available = true;
            for (User user : users) {
            	if(user.getUserName().equals(itsUserName)) {available = false;}
                if (available == false) {
                    validityCheck.append("-------\n ERROR \t\t\t| This UserName already exist !|\n-------\n\n");
                    break;
                }
            }
        }

        //After verification, if no errors were found, itsUserName is good to go,
//        if (validityCheck.equals("")) { validityCheck.append(itsUserName); }
        //If errors were found, errors are returned.
        return validityCheck.append(itsUserName).toString();
    }
    private static String verifyPassword(String itsPassword) {  // Here we check the criteria that the password needs to follow
        StringBuilder validityCheck = new StringBuilder();
        //Errors are append to validityCheck if they are found

        //Is 6 characters long or more?
        if (itsPassword.length() < 6) { validityCheck.append("-------\n ERROR \t\t\t| Your Password needs to have at least 6 characters !|\n-------\n\n"); }

        //Is only composed of alphanumeric characters and special characters ?
        for (int i = 0; i < itsPassword.length(); i++) {
            int c = itsPassword.charAt(i);
            if (c < 32 || c > 126) {
                validityCheck.append("-------\n ERROR \t\t\t| Unauthorised character detected !|\n-------\n\n");
                break;
            }
        }

        //After verification, if no errors were found, itsPassword is good to go
//        if (validityCheck.equals("")) { validityCheck.append(itsPassword);}
        //If errors were found, errors are returned.
        return validityCheck.append(itsPassword).toString();
    }
}
