package BoatClubManagement.controller;


import BoatClubManagement.model.Registry;
import BoatClubManagement.view.Console;
import BoatClubManagement.model.Boat;
import BoatClubManagement.model.Member;


public class Controller {

    private final Console console;
    private final Registry registry;
    private boolean run;
    private boolean isValid = true;
    private ClubOperations[] steps;
    private boolean validName = true;
    private boolean checkLastName = true;
    private boolean validPhoneNumber = true;

    /**
     * isValid, validName,and validPhoneNumber objects that are boolean are used to check the user input for the invalid input.
     * If the input is invalid an error message will be displayed and this loop will happen until the the conditions have been met.
     */

    public Controller(Registry registry, Console console) {
        this.registry = registry;
        this.console = console;
    }

    public void ignition() {
        run = true;
        steps = ClubOperations.values();
        reFresh();
    }

    public void reFresh() {
        console.getMenu(steps);
        ClubOperations action = console.getUserRequest(steps);
        try {
            validateInput(action);
            registry.storeInformation();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        if (run) {
            reFresh();
        }
    }

    /**
     * The cases below are the options that the user can choose from to preform different actions.
     */

    public void menuAction(ClubOperations entry) {
        switch (entry) {
            case LIST_TYPE_1:
                compactList();
                break;
            case LIST_TYPE_2:
                verboseList();
                break;
            case MEMBER_SIGN_UP:
                registerMember();
                break;
            case LIST_OF_MEMBERS:
                viewMember();
                break;
            case MEMBER_UPDATE:
                editMember();
                break;
            case MEMBER_DETACH:
                removeMember();
                break;
            case BOAT_SIGN_UP:
                registerBoat();
                break;
            case BOAT_UPDATE:
                editBoat();
                break;
            case BOAT_DETACH:
                removeBoat();
                break;
            case EXIT:
                run = false;
                displayRedLine("The application has been terminated!!!");
                break;
            default:

        }
    }

    public void compactList() {
        console.getListHeader();
        for (Member member : registry.getMemberArray()) {
            console.getList(member.getId(), member.getName(), member.getLastName(),member.getSocialSecurityNumber(), member.getPhoneNumber(), member.getBoatListSize());
        }
    }

   public void verboseList()   {
        for (Member member : registry.getMemberArray()) {
            console.printDataMember(member.getName(), member.getLastName(),member.getSocialSecurityNumber(), member.getPhoneNumber(), member.getId());
            listBoat(member.getBoatArray());
        }
    }

    private void listBoat(Boat[] boats) {
        for (Boat boat : boats) {
            console.printBoatInformation(boat.getType(), boat.getLength());
        }
    }

    private void validateInput(ClubOperations action) {
        menuAction(action);
    }

    private Member getMember() {
        int id = console.getMemberInputId();
        return registry.getMemberRegistry(id);
    }

    private void registerMember() {
        String name = console.getMemberInputFirstName();
        String lastName = console.getMemberInputLastName();
        String socialSecurityNumber = console.getSocialSecurityNumber();
        String phoneNumber = console.getMemberInputPhoneNumber();
        registry.registerMember(name, lastName,socialSecurityNumber, phoneNumber);
        displayRedLine("Registration was successful!");
    }

    private void viewMember() {
        Member member = getMember();
        console.printDataMember(member.getName(), member.getLastName(),member.getSocialSecurityNumber(), member.getPhoneNumber(), member.getId());

    }
    private void editMember() {

        Member member = getMember();

        while (validName){
            String name = console.editName(member.getName());
            try {
                member.setName(name);
                validName=false;
            }catch (Exception e){
                displayRedLine(e.getMessage());
            }
        }

        do {
            String lastName = console.editMemberLastName(member.getLastName());
            if (lastName.matches("^[a-zA-Z]*$")) {
                member.setLastName(lastName);
                checkLastName = false;
            } else {
                displayRedLine("Wrong input");
            }
        }while (checkLastName);


        String socialSecurityNumber = console.editSocialSecurity(member.getSocialSecurityNumber());
        member.setSocialSecurityNumber(socialSecurityNumber);

        while (validPhoneNumber){
            String phoneNumber = console.editMemberPhoneNumber(member.getPhoneNumber());
            try {
                member.setPhoneNumber(phoneNumber);
                validPhoneNumber = false;
                displayRedLine("Changes were saved successfully ");
            }catch (Exception e){
                displayRedLine(e.getMessage());

            }
        }
    }

    private void removeMember()  {
        int id = console.getMemberInputId();
        registry.removeMember(id);
        displayRedLine("Member was successfully deleted! ");
    }

    private void registerBoat()  {
        Member member = getMember();
        displayRedLine("Choose one the followings as your boat type: ");
        member.registerBoat(console.getInputBoatType(), console.getInputBoatLength());
        displayRedLine("Registration was successful!!!");
    }

    private void editBoat() {
        Member member = getMember();
        Boat boat = getBoat(member);
        Boat.TYPE boatType = console.printEditBoatType(boat.getType());
        boat.setType(boatType);
        while (isValid) {
            double length = console.printEditBoatLength(boat.getLength());
            try {
                boat.setLength(length);
                displayRedLine("Changes were saved successfully");
                isValid =false;
            } catch (Exception e) {
                displayRedLine("The length is not acceptable");
            }
        }
    }
    private void removeBoat() {
        Member member = getMember();
        member.removeBoatPosition(console.getInputBoatIndex());
        displayRedLine("Boat has been dismissed successfully");
    }

    private Boat getBoat(Member member)  {
        return member.getBoatPosition(console.getInputBoatIndex());
    }

    private void displayRedLine(String message){
        System.err.println(message);
    }

}