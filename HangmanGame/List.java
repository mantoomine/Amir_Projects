package HangmanGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This is a class<List> that reads a file filled with nouns
 * 
 * @author Amirhossein Soltaninejad
 *
 */
public class List {
	private String fileL = "./src/as225rr_assign3/wordsList.txt";
	private ArrayList<String> list = new ArrayList<String>();

	public List() {
		Path path = Paths.get(fileL);
		File file = new File(path.toAbsolutePath().toString());
		try {
			Scanner fileS;
			fileS = new Scanner(file);
			while (fileS.hasNext()) {
				list.add(fileS.next());
			}
			fileS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is a method <wordRemove>that Add the word to the list .
	 * 
	 * @param wr The word that should be added
	 */
	public void wordAdd(String wr) {
		if (wordPlace(wr) == -1) {
			list.add(wr);
		}
		try {
			PrintWriter out = new PrintWriter(fileL);
			for (int i = 0; i < list.size(); i++) {
				out.println(list.get(i));
			}
			out.close();
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * This is a method <wordRemove>that Remove the word from the list.
	 * 
	 * @param wr The word that should be removed.
	 */
	public void wordRemove(String wr) {
		if (wordPlace(wr) >= 0) {
			list.remove(wordPlace(wr));
		}
		try {
			PrintWriter out = new PrintWriter(fileL);
			for (int i = 0; i < list.size(); i++) {
				out.println(list.get(i));
			}
			out.close();
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * This is a method <wordPlace>that Check the index of the word, if not exist
	 * returns -1.
	 * 
	 * @param wr Word that should be checked
	 * @return index of the word, -1 if not exist
	 */
	public int wordPlace(String wr) {
		String wordS = "";
		for (int i = 0; i < wr.length(); i++) {
			if (Character.isLetter(wr.charAt(i))) {
				wordS += Character.toLowerCase(wr.charAt(i));
			} else {
				wordS += wr.charAt(i);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			String listWr = "";
			for (int j = 0; j < list.get(i).length(); j++) {
				if (Character.isLetter(list.get(i).charAt(j))) {
					listWr += Character.toLowerCase(list.get(i).charAt(j));
				} else {
					listWr += list.get(i).charAt(j);
				}
			}
			 if (listWr.equals(wordS)) {  //comment the error
				return i;
			}
		 }
		return -1;
	}

	/**
	 * This is method<getRandom> that pick a word from the list randomly
	 * 
	 * @return String contains a word
	 */
	public String getRandom() {
		Random rand = new Random();
		String wr = list.get(rand.nextInt(list.size()));
		return wr;
	}
}
