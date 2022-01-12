package BoatClubManagement.view;

import BoatClubManagement.controller.Controller;
import BoatClubManagement.model.Registry;

public class ClubMain {

    public static void main(String[] args) {
        try {
            Console console = new Console();
            console.greetings();
            Registry registry = new Registry();
            Controller controller = new Controller(registry, console);
            controller.ignition();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}