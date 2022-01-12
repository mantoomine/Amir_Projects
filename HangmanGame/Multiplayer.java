package HangmanGame;

import java.util.Scanner;

/**
 * This is a main class<Multiplayer> that configure two player game. Ask a player
 * to enter a word and the other is able to play a game with this word. Display
 * result when game is over and then return to main menu.
 * 
 * @author Amirhossein Soltaninejad
 *
 */
public class Multiplayer {

	private Configuration play;
	private Scanner sc;
	private boolean playHangMan;
	private String wr;

	public Multiplayer() {
		play = new Configuration();
		sc = new Scanner(System.in);
		playHangMan = false;
	}

	/**
	 * This is a method<twoPlayers> that two players can play the game. It ask one
	 * player to enter a word and the other to play the game with that word.
	 */
	public void twoPlayers() {
		System.out.println(
				"Multiplayer game: first player should enter a word and the other is able to play the game with this word");
		while (playHangMan == false) {
			System.out.print("Enter a word player 1: ");
			wr = sc.next();
			while (listCheck(wr) == false) {
				System.out.println("Write a word that contain only letters!");
				System.out.print("Enter a word player 1: ");
				wr = sc.next();
			}
			System.out.println("Enter 1 to play with the word");
			System.out.println("Enter 2 to change the word");
			HangmanMain.intIn(sc);
			int input = sc.nextInt();
			while (!(input == 1 | input == 2)) {
				System.out.print("Just enter a number from the menu: ");
				HangmanMain.intIn(sc);
				input = sc.nextInt();
			}
			if (input == 1) {
				System.out.println("Player 2 , enter any character to play the game");
				sc.next();
				playHangMan = true;
				play.setList(wr);
				play.start();
			}
		}
		if (play.win()) {
			System.out.println();
			System.out.println("!*************");
			System.out.println("!<<<PLAYER TWO>>> ");
			System.out.println("! *Y*      *N*");
			System.out.println("!  *O*    *O*");
			System.out.println("!   *U*  *W*");
			System.out.println("!*************");
			System.out.println("!.............");
		} else {
			System.out.println();
			System.out.println("!*************");
			System.out.println("!<<<PLAYER ONE>>> ");
			System.out.println("! *Y*      *N*");
			System.out.println("!  *O*    *O*");
			System.out.println("!   *U*  *W*");
			System.out.println("!*************");
			System.out.println("!.............");
		}
		System.out.println("Enter any character to continue the game");
		sc.next();
	}

	/**
	 * This is a method<listCheck> that check the word with only letters and dash
	 * 
	 * @param wr String that should be checked
	 * @return true if the word is valid, otherwise return false
	 */
	public boolean listCheck(String wr) {
		for (int i = 0; i < wr.length(); i++) {
			if (!Character.isLetter(wr.charAt(i)) & wr.charAt(i) != '-') {          //in order to get succeed result in the TestTwoPlayers you should replace
                return false;                                                          // if (!Character.isLetter(wr.charAt(i)) & wr.charAt(i) != '-') {
			}                                                                          // with the code on line 89.
		}
		return true;
	}

}