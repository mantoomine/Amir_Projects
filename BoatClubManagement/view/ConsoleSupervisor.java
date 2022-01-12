package BoatClubManagement.view;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;


abstract class ConsoleSupervisor {

    private final Scanner scanner = new Scanner(System.in);


    public void printFormat(String format, Object... args) {
        System.out.format(format, args);
    }

    public String getPersonalNumber(Pattern pattern) {
        displayWithoutBreak("Enter your personal number:"+" "+"For example \"YYYYMMDDXXXX\": ");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            printTryAgain();
        }
        return scanner.next();
    }

    public String getPhoneNumber(Pattern pattern) {
        displayWithoutBreak("Enter your Phone Number:");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            printTryAgain();
        }
        return scanner.next();
    }

    public String getName(Pattern pattern) {
        displayWithoutBreak("Enter your Name: ");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            printTryAgain();
        }
        return scanner.next();
    }

    public String getLastName(Pattern pattern) {
        displayWithoutBreak("Enter your LastName: ");

        while (!scanner.hasNext(pattern)) {
            scanner.next();
            printTryAgain();
        }
        return scanner.next();
    }

    public int checkInteger(String text) {
        displayWithoutBreak(" " + text);
        while (!scanner.hasNextInt()) {
            scanner.next();
            printTryAgain();
        }
        return scanner.nextInt();
    }

    public double checkDouble(String text) {
        displayWithoutBreak(text);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            printTryAgain();
        }
        return scanner.nextDouble();
    }

    public String scanNext() {
        return scanner.next();
    }

    public boolean printConfirmation(String message) {
        while (true) {
            displayWithBreak(" " + message);
            displayWithBreak(" 1. Continue");
            displayWithBreak(" 2. Cancel");
            int input = scanner.nextInt();
            if (input == 1) {
                return true;
            } else if (input == 2) {
                return false;
            }
        }
    }

    private void printTryAgain() {
        displayRedLine();
    }

    private boolean isInputValid(int input, int number) {
        return input > 0 && input <= number;
    }

    private String stringBuilder(int stage) {
        switch (stage) {
            case 0:
            case 2:
            case 3:
            case 1:
                return "\n";
            default:
                return null;
        }
    }
    private void displayWithoutBreak(String question){
        System.out.print(question);
    }

    private void displayWithBreak(String message){
        System.out.println(message);
    }

    private void displayRedLine(){
        System.err.println("Invalid Input, try again: ");
    }

    /**
     * Basically this method is connected to the string Builder method.That is, cases 0,2,3,and 1 will make a new line.
     * Also, this method is responsible for the way how the menu is presenting. such as (New line, space,..).
     *
     */

    protected <Element> void displayMenu(Element[] value, HashMap<Element, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringBuilder(0));
        for (int i = 0; i < value.length; i++) {
            Element integer = value[i];
            if (map.containsKey(integer)) {
                if (i == 0) {
                    stringBuilder(1);
                } else {
                    stringBuilder.append(stringBuilder(2));
                }
                // Get Last Element & Re-map it:
                // Retrieve the latest value and remap it.
                if (value.length == i + 1) {
                    if (value[i].toString().length() <= 5) {
                        stringBuilder.append(0+". ").append(map.get(integer));
                    }
                    else {
                        stringBuilder.append(0 +". ").append(map.get(integer)).append("\t\t\t");
                    }
                } else {
                    stringBuilder.append(i + 1).append(". ").append(map.get(integer)).append("\n------------------");
                }
            }
        }
        stringBuilder.append(stringBuilder(3));

        displayWithBreak(stringBuilder.toString());
    }

    /**
     * This method basically enables the user to choose from the options intervals. (Options in the menu), if
     * the user has chosen one, the program will be terminated.Otherwise, the application will receive a new value as the entry (input).
     */
    protected <Element> Element getTheUserChoice(Element[] value) {
        int input = 0;
        while (!isInputValid(input, value.length)) {
            displayWithoutBreak("Choose a number from the list:");
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input == 0) {
                    input = value.length;
                }
                else if (input == value.length) {
                    input = value.length + 1;
                }
            } else {
                scanner.next();
            }
        }
        return value[input - 1];
    }

}
