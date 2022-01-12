package BoatClubManagement.view;

import BoatClubManagement.controller.ClubOperations;
import BoatClubManagement.model.Boat;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Console extends ConsoleSupervisor {

    private final HashMap<ClubOperations, String> mapMenu = new HashMap<>();
    private final HashMap<Boat.TYPE, String> boatMap = new HashMap<>();
    private final String listFormat = "  %4s   %20s   %22s   %35s  %38s %31s \n";
    private final Pattern personalNumberPattern = Pattern.compile("[0-9]{8}[0-9]{4}");
    private final Pattern phonePattern = Pattern.compile("[0-9]{10}");
    private final Pattern namePattern = Pattern.compile("[a-zA-Z]+");
    private final Pattern lastNamePattern= Pattern.compile("[a-zA-Z]+");


    public Console() {
        startUp();
    }

    public void getMenu(BoatClubManagement.controller.ClubOperations[] actions) {
        displayMenu(actions, mapMenu);
    }


    public void printDataMember(String name, String lastName, String socialSecurityNumber, String phoneNumber, int id) {
        breakDisplay("\n\"Member's List\"");
        breakDisplay("ID: " + id + "\t\t\t\t\t");
        lineSeparator();
        askQuestion("First Name: ",name);
        lineSeparator();
        askQuestion("Last Name: ",lastName);
        lineSeparator();
        askQuestion("Phone Number: " ,phoneNumber);
        lineSeparator();
        breakDisplay("Social Security Number: " + dash(socialSecurityNumber) + "\t");
        lineSeparator();

    }

    public String editName(String name) {
        redDisplay("Do you want to change your name?");
        if (printConfirmation("Your current name in the system is: " + name + "\n👇")) {
           noBreak("What do you want to change your Name to: ");
            return scanNext();
        }
        return name;
    }

    public void greetings() {
        redDisplay("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWelcome to the yacht Club");
    }

    public String editMemberPhoneNumber(String phoneNumber) {
        redDisplay("Do you want to change your Phone Number?");
        if (printConfirmation("Your current Phone Number in the system is: " + phoneNumber + "\n👇")) {
            noBreak("What do you want to change your Phone Number to: ");
            return scanNext();
        }
        redDisplay("Changes were saved successfully!");
        return phoneNumber;
    }

    public String editMemberLastName(String lastName) {
        redDisplay("Do you want to change your Last Name?");
        if (printConfirmation("Your current Last name in the system is: " + lastName + "\n👇")) {
            noBreak("What do you want to change your last Name to: ");
            return scanNext();
        }
        return lastName;
    }
    public String editSocialSecurity(String socialSecurityNumber) {
        redDisplay("Do you want to change your personal Number?");
        if (printConfirmation("Your current personal Number in the system is: " + socialSecurityNumber + "\n👇")) {
           noBreak("What do you want to change your personal Number to:");
            return scanNext();
        }
        return socialSecurityNumber;
    }

    public int getMemberInputId() {
        return checkInteger("What is your identifier? : ");
    }

    public String getMemberInputFirstName() {
        return getName(namePattern);
    }


    public String getMemberInputLastName() {
        return getLastName(lastNamePattern);
    }

    public String getMemberInputPhoneNumber() {
        return getPhoneNumber(phonePattern);
    }

    public String getSocialSecurityNumber() {
        return getPersonalNumber(personalNumberPattern);
    }


    public void printBoatInformation(Boat.TYPE type, double length) {
        breakDisplay("Boat's Information ");


        if (boatMap.get(type).length() > 5) {
            breakDisplay("Your current registered boat type in the system is :Type: " + type);

        } else {
            breakDisplay("Boat type in the system is: " + type);

        }

        if (length <= 1.0) {
            breakDisplay("Your current registered boat length in the system is: " + length + " meter");
        } else {
            breakDisplay("Your current registered boat length in the system is: " + length + " meters");

        }
        lineSeparator();
    }

    public Boat.TYPE printEditBoatType(Boat.TYPE currentType) {
        redDisplay("Your current registered boat's type in the system is: " + boatMap.get(currentType));
        if (printConfirmation("")) {
            return getInputBoatType();
        }

        return currentType;
    }

    public double printEditBoatLength(double currentLength) {
        redDisplay("Your current registered boat's length in the system is: " + currentLength);
        if (printConfirmation("")) {
            return checkDouble("What do you you want to change the boat length to? ");
        }
        return currentLength;
    }

    public int getInputBoatIndex() {
        return checkInteger("What is the boat indicator? (Note: The range will start from zero)");
    }

    public Boat.TYPE getInputBoatType() {
        Boat.TYPE[] types = Boat.TYPE.values();
        displayMenu(types, boatMap);
        return getTheUserChoice(types);
    }

    public double getInputBoatLength() {
        return checkDouble("What is the length of your boat?");
    }

    public void getListHeader() {
        printFormat(listFormat, "Identifier", "First Name:   ", "Last Name:   ", "Personal Number:   ", "Phone Number:    ", "Registered Boats:");
    }

    public void getList(int id, String name, String lastName, String socialSecurityNumber, String phoneNumber, int numberOfBoats) {
        printFormat(listFormat, id, name, lastName, dash(socialSecurityNumber), phoneNumber, numberOfBoats);
        breakDisplay("-----------------------------------------------------------------------------" +
                "-----------------------------------------------------------------------------------------------------------------");
    }

    public ClubOperations getUserRequest(ClubOperations[] actions) {
        return getTheUserChoice(actions);
    }

    public void lineSeparator(){
        breakDisplay("--------------------");
    }

    public void askQuestion(String message, String kind){
        System.out.println(message+kind);
    }

    public void noBreak(String question){
        System.out.print(question);
    }

    public void breakDisplay(String message){
        System.out.println(message);
    }

    public void redDisplay(String message){
        System.err.println(message);
    }

    private String dash(String input) {
        String output = input.substring(0, 8);
        output += "-" + input.substring(8, 12);
        return output;
    }

    /**
     * attach String to proper enum's actions
     * it will be used for further displays
     */

    private void startUp() {
        mapMenu.put(ClubOperations.MEMBER_SIGN_UP, "Member SignUp");
        mapMenu.put(ClubOperations.BOAT_SIGN_UP, "Boat SignUp");
        mapMenu.put(ClubOperations.MEMBER_UPDATE, "Member update");
        mapMenu.put(ClubOperations.BOAT_UPDATE, "Boat Update");
        mapMenu.put(ClubOperations.MEMBER_DETACH, "Member detach");
        mapMenu.put(ClubOperations.BOAT_DETACH, "Boat detach");
        mapMenu.put(ClubOperations.LIST_OF_MEMBERS, "Member list");
        mapMenu.put(ClubOperations.LIST_TYPE_1, "List Mode1");
        mapMenu.put(ClubOperations.LIST_TYPE_2, "List Mode2");
        mapMenu.put(ClubOperations.EXIT, "Exit");

        boatMap.put(Boat.TYPE.SAILBOAT, "Sailboat");
        boatMap.put(Boat.TYPE.MOTOR_SAILOR, "MotorSailor");
        boatMap.put(Boat.TYPE.Kayak, "Kayak");
        boatMap.put(Boat.TYPE.CANOE, "Canoe");
        boatMap.put(Boat.TYPE.OTHER, "etc");
    }
}
