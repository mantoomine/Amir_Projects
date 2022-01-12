package HangmanGame;

import java.util.ArrayList;

/**
 * This is a class<ConfigureLetters> that store a list of letters the player has
 * predicted which was not part of the word.
 * 
 * @author Amirhossein Soltaninejad
 *
 */
public class ConfigureLetters {

	private ArrayList<Character> predictedLetter;

	public ConfigureLetters() {
		predictedLetter = new ArrayList<Character>();
	}

	/**
	 * This is a method<letterAdd> that add a letter to predicted word list if it is
	 * not already in the list.
	 * 
	 * @param let Letter will be added
	 */
	public void letterAdd(char let) {
		if (predictedLetters(let) == false) {
			predictedLetter.add(let);
		}
	}

	/**
	 * This is a method<toString>to returns a string showing all letters in the list
	 * 
	 * @return String showing all the letters in the list
	 */

	public String toString() {
		String result = "";
		for (int i = 0; i < predictedLetter.size(); i++) {
			result += predictedLetter.get(i) + " ";
		}
		return result;
	}

	/**
	 * This is a method<predictedLetters>to check if the letters is in the list
	 * 
	 * @param let letter to be checked
	 * 
	 * @return true if list has the letter, otherwise false
	 */
	public boolean predictedLetters(char let) {
		for (int i = 0; i < predictedLetter.size(); i++) {
			if (let == predictedLetter.get(i)) {
				return true;
			}
		}
		return false;
	}
}