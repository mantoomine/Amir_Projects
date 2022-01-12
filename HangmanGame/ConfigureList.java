package HangmanGame;

import java.util.Scanner;

/**
 * This is a class<ConfigureWords> that ask the player to enter the
 * word(multiple words) that wants to be removed. Player is also able to return
 * to menu or finish the game.
 * 
 * @author Amirhossein Soltaninejad
 *
 */
public class ConfigureList {
	private boolean returnM;
	private Scanner sc;
	private List li;

	ConfigureList() {
		returnM = false;
		sc = new Scanner(System.in);
		li = new List();
	}

	/**
	 * This is a method<listRemove>that ask player to enter the word that wants to
	 * be deleted. Player is also able to return to the menu or finish the
	 * game.player is also able to remove multiple words
	 *
	 */
	public void listRemove() {
		while (returnM == false) {
			System.out.println("1) <<<Return to the Menu>>>");
			System.out.println("2) <<<Finish the game>>>");
			System.out.print("\nEnter the word to be removed or a  number from the menu: ");
			String string = sc.next();
			if (string.charAt(0) == '1') {
				returnM = true;
			} else if (string.charAt(0) == '2') {
				HangmanMain.gameFinish();
			} else {
				if (li.wordPlace(string) < 0) {
					System.out.println(string + " is not in the list!");
					System.out.println("Enter any character to continue the game: ");
					string = sc.next();
					continue;
				}
				System.out.println("Are you sure you want to remove the word: " + string);
				System.out.println("1) Yes");
				System.out.println("2) No");
				intCheck(sc);
				int answer = sc.nextInt();
				while (!(answer == 1 | answer == 2)) {
					System.out.print("\njust enter 1 or 2: ");
					intCheck(sc);
					answer = sc.nextInt();
				}
				if (answer == 1) {
					li.wordRemove(string);
					System.out.println(string + " is now removed");
				} else {
					System.out.println(string + " was not removed");
				}
				System.out.println("Enter any character to continue the game: ");
				sc.next();

			}
		}
	}

	/**
	 * This is a method<intCheck> that check input is integer or not.
	 * 
	 * @param sc Scanner to get the input from player.
	 */
	private void intCheck(Scanner sc) {
		while (!sc.hasNextInt()) {
			System.out.print("\njust enter a number: ");
			sc.next();
		}
	}

}