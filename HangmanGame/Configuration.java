package HangmanGame;

import java.util.Scanner;

/**
 * This is a class<Configuration> that configures the single game. The player
 * guess letters and either win or lose.Player is also able to return to menu or
 * finish the game.
 * 
 * @author Amirhossein Sotaninejad
 *
 */
public class Configuration {
	private int left;
	private List list;
	private String wr;
	private String[] point;
	private ConfigureLetters remain;
	Hangman hangman;
	private Scanner sc;
	private boolean follow;

	public Configuration() {
		left = 9;
		hangman = new Hangman();
		sc = new Scanner(System.in);
		list = new List();
		setList(list.getRandom());
		remain = new ConfigureLetters();
		follow = true;
	}

	/**
	 * This is a method<start> that start the game, user is able to return to menu
	 * or finish the game.
	 */
	public void start() {
		follow = true;

		while (win() == false & left > 0 & follow == true & HangmanMain.gameContinue() == true) {
			System.out.println("1) <<<Return to the menu>>>");
			System.out.println("2) <<< Finish the Game>>>");
			System.out.println(gesturesToString());
			hangman.hangmanSticker();
			System.out.print("Predicted letters: " + remain.toString());
			System.out.print("\nEnter a number from the menu or guess a letter: ");
			String in = sc.next();
			while (in.length() != 1 | !(Character.isLetter(in.charAt(0)) | in.charAt(0) == '1' | in.charAt(0) == '2')) {
				System.out.println("Enter a letter or a number from the menu: ");
				in = sc.next();
			}
			if (in.charAt(0) == '1') {
				this.menuReturn();
			} else if (in.charAt(0) == '2') {
				HangmanMain.gameFinish();
			} else if (Character.isLetter(in.charAt(0))) {
				letterPredict(in);
			}
		}
		if (win() == true) {
			hangman.hangmanWin();
			System.out.println("the Hangman did it , <<<Correct word>>> : " + wr);
		} else if (left == 0) {
			HangmanMain.gameStop();
			hangman.hangmanSticker();
			System.out.println("the Hangman executed!!! , Correct word is : " + wr);
		}
	}

	/**
	 * This is a method<win> that check if all letters are predicted
	 * 
	 * @return true if all letters are predicted, otherwise return false
	 */
	public boolean win() {
		for (int i = 0; i < point.length; i++) {
			if (point[i] == " _") {
				return false;
			}
		}
		return true;
	}

	/**
	 * This is a method<lost>that check if the player does not have any chance
	 * 
	 * @return true if the game is lost, otherwise return false
	 */
	public boolean lost() {
		return left == 0;
	}

	/**
	 * This is a method<setList> that sets the correct word
	 * 
	 * @param list New solution for game
	 */
	public void setList(String list) throws IllegalArgumentException {
		if (wordCheck(list)) {
			wr = list;
			point = new String[wr.length()];
			for (int i = 0; i < wr.length(); i++) {
				if (wr.charAt(i) == '-') {
					point[i] = " -";
				} else {
					point[i] = " _";
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * This is a method<getList> that return the correct word
	 * 
	 * @return the solution for the game
	 */
	public String getList() {

		return wr;
	}

	/**
	 * This is a method<letterPredict> that controls letter prediction, if letter is
	 * correct , underline will be changed with the letter If the letter is
	 * incorrect , it will be added to the predicted letters and to the hangman
	 * 
	 * @param in input letter to be predicted
	 */
	public void letterPredict(String in) {
		Boolean right = false;
		char predict = Character.toLowerCase(in.charAt(0));
		for (int i = 0; i < wr.length(); i++) {
			if (predict == Character.toLowerCase(wr.charAt(i))) {
				point[i] = " " + wr.charAt(i);
				right = true;
			}
		}
		if (!right) {
			if (remain.predictedLetters(predict) == false) {
				remain.letterAdd(predict);
				hangman.bodyPart();
				left--;
			}
		}
	}

	/**
	 * This is a method<wordCheck>that check if it is a valid word with letter and
	 * dash
	 * 
	 * @param wr Word should be checked
	 * @return True if word input is valid, otherwise return false
	 */
	public boolean wordCheck(String wr) {
		for (int i = 0; i < wr.length(); i++) {
			if (!Character.isLetter(wr.charAt(i)) & wr.charAt(i) != '-') {
				return false;
			}
		}
		return true;
	}


	/**
	 * This is a method<gesturesToString>that return array of underlines or letters
	 * as a string
	 * 
	 * @return underlines with either underlines and/or letters
	 */
	public String gesturesToString() {
		String gestureString = "";
		for (int i = 0; i < point.length; i++) {
			gestureString += point[i];
		}
		return gestureString;
	}

	/**
	 * This is a method<menuReturn>that ask the player if he/she wants to return to
	 * the menu, returns, otherwise player can choose return to the game
	 */
	private void menuReturn() {
		System.out.println("Are you sure you want to go back to the menu?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		HangmanMain.intIn(sc);
		int agree = sc.nextInt();
		while (!(agree == 1 | agree == 2)) {
			System.out.print("just enter 1 or 2: ");
			HangmanMain.intIn(sc);
			agree = sc.nextInt();
		}
		if (agree == 1) {
			follow = false;
			HangmanMain.gameStop();
		}
	}
}