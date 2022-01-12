package HangmanGame;

/**
 * 
 * This is a class<Hangman> that contains 9 parts for execution showing and a
 * method to show a winner message
 * 
 * @author Amirhossein Soltaninejad
 *
 */
public class Hangman {
	private int bodyExecuted;
	private String[] sticker;

	public Hangman() {

		bodyExecuted = 0;
		sticker = new String[6];
		for (int i = 0; i < 5; i++) {
			sticker[i] = "";
		}
		sticker[5] = " ";
	}

	/**
	 * This is a method<hangmanSticker> that shows hangman current status.
	 */
	public void hangmanSticker() {
		System.out.println();
		for (int i = 0; i < sticker.length; i++) {
			System.out.println(sticker[i]);
		}
	}

	/**
	 * This is a method<hangmanWin> that print a win message
	 */
	public void hangmanWin() {
		System.out.println();
		System.out.println("!*************");
		System.out.println("! *Y*      *N*");
		System.out.println("!  *O*    *O*");
		System.out.println("!   *U*  *W*");
		System.out.println("!*************");
		System.out.println("!........");
	}

	/**
	 * This is a method<bodyPart> that add 9 pieces for hangman,if all parts is
	 * shown,hangman is executed
	 */
	public void bodyPart() {
		if (bodyExecuted == 0) {
			for (int i = 1; i <= 4; i++) {
				sticker[i] = "!";

				sticker[5] = "!.........";
			}
		} else if (bodyExecuted == 1)
			sticker[0] = " _ _ _ _";
		else if (bodyExecuted == 2)
			sticker[1] = "!   |";
		else if (bodyExecuted == 3)
			sticker[2] = "!   @";
		else if (bodyExecuted == 4)
			sticker[3] = "!   |";
		else if (bodyExecuted == 5)
			sticker[3] = "!  /|";
		else if (bodyExecuted == 6)
			sticker[3] = "!  /|\\";
		else if (bodyExecuted == 7)
			sticker[4] = "!  /";
		else if (bodyExecuted == 8)
			sticker[4] = "!  / \\";
		bodyExecuted++;
	}
}