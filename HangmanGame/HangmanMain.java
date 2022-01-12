package HangmanGame;

import java.util.Scanner;

/**
 * This is a main class<HangmanMain> for the HANGMAN game and present a menu
 * with choices to play the game. User is able to start a new single player
 * game,or play a two players version. The player is also able to remove words
 * from the words list.
 * 
 * @author Amirhossein Sotaninejad
 */

public class HangmanMain {

	private static Scanner sc = new Scanner(System.in);
	private static boolean gameContinue = true;
	private static boolean start = false;
	private static int point = 0;
	private static Configuration play = new Configuration();

	public static void main(String[] args) {
		welcomePrint();
		while (gameContinue == true) {
			printHelpMenu();
			intIn(sc);
			int in = enterMenu();
			if (in == 1) {
				point = 0;
				start = true;
				while (start == true) {
					play = new Configuration();
					singlePlayer();
				}
			} else if (in == 2) {
				Multiplayer two = new Multiplayer();
				two.twoPlayers();
			} else if (in == 3) {
				ConfigureList li = new ConfigureList();
				li.listRemove();
			} else if (in == 4) {
				gameFinish();
			} else {
				System.out.println("just enter a letter: ");
			}
		}
		sc.close();
	}

	/**
	 * This is a method<singlePlayer> to run the single player game and raise point
	 * if the player won. Also print result of game and current point.
	 */
	public static void singlePlayer() {
		play.start();
		if (play.win() == true) {
			point++;
			System.out.println("Your Point: " + point);
			System.out.println("Enter any character to continue the game");
			sc.next();
		} else if (play.lost() == true) {
			System.out.println("Your point was: " + point);
			System.out.println("Enter any character to continue the game");
			sc.next();
		}
	}

	/**
	 * This is a method<enterMenu> to return an integer from 1 to 4
	 * 
	 * @return an integer from 1 to 4
	 */
	private static int enterMenu() {
		int in = sc.nextInt();
		if (!(in == 1 | in == 2 | in == 3 | in == 4)) {
			System.out.print("just enter a number from the menu: ");
			intIn(sc);
			in = sc.nextInt();
		}
		return in;
	}

	/**
	 * This is a method<gameStop>to break the loop of single player game.
	 */
	public static void gameStop() {
		start = false;
	}

	/**
	 * This is a method<gameContinue> to check if the variable gameContinue is true
	 * 
	 * @return true if variable gameContinue is true
	 */
	public static boolean gameContinue() {
		return gameContinue;
	}

	/**
	 * This is a method<intIn> to Make sure that the scanner input is an integer, if
	 * not ask player for new number until it is an integer
	 * 
	 * @param sc Scanner that takes the number
	 */
	public static void intIn(Scanner sc) {
		while (!sc.hasNextInt()) {
			System.out.print("just enter a number: ");
			sc.next();
		}
	}

	/**
	 * This is a method<gameFinish> to ask the player to finish the game. otherwise
	 * it will return to the previous section.
	 */
	public static void gameFinish() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Are you sure you want to finish the game?");
		System.out.println("1) Yes");
		System.out.println("2) No");
		intIn(sc);
		int agree = sc.nextInt();
		while (!(agree == 1 | agree == 2)) {
			System.out.print("just enter 1 or 2: ");
			intIn(sc);
			agree = sc.nextInt();
		}
		if (agree == 1) {
			HangmanMain.gameContinue = false;
			HangmanMain.gameStop();
		}
	}

	/**
	 * This is a method<printHelpMenu> to print the menu
	 * 
	 */
	public static void printHelpMenu() {
		System.out.println("\t<<<the Menu>>>");
		System.out.println("1) <<<Play single player>>> ");
		System.out.println("2) <<<Play Multiplayer>>>");
		System.out.println("3) <<<Remove word from the word list>>>");
		System.out.println("4) <<<Finish The Game>>>");
		System.out.print("\nChoose a number from the menu: ");
	}
	
	/**
	 * This is a method<welcomePrint> to print the welcome message
	 * 
	 */
	public static void welcomePrint() {
		System.out.println("Welcome to the HANGMAN Game!!!");
		System.out.println("*   *     *     *   * ****     *        *     *     *   *");
		System.out.println("*   *    * *    **  * *        **      **    * *    **  *");
		System.out.println("*****   *****   * * * *    *** * *    * *   *****   * * *");
		System.out.println("*   *  *     *  *  ** *      * *  *  *  *  *     *  *  **");
		System.out.println("*   * *       * *   * ******** *   *    * *       * *   *");

	}
}
