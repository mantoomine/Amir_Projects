package JavaFXSimpleGames;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarAndPeace {

	public static void main(String[] args) throws Exception {

		String text = readText("./src/as225rr_assign2/WarAndPeace.txt"); // My own method
		String[] words = text.split(" "); // Simple split, will be cleaned up later on
		System.out.println("Initial word count: " + words.length); // We found 577091

		Stream<String> stream = Arrays.stream(words);
		stream = stream.map(s -> s.toLowerCase().replaceAll("[^a-z-\']", "")); // remove all non-alphabetic characters															
		stream = stream.filter(s -> !s.isEmpty());
		stream = stream.distinct();
		int unique = (int) stream.count();
		System.out.println("\nInitial unique words: " + unique);
	}
	public static String readText(String file) throws Exception {
		return Files.readAllLines(Paths.get(file), Charset.forName("UTF-8")).stream().collect(Collectors.joining(" "));
	}
}
