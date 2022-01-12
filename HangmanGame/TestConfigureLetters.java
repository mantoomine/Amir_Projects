package HangmanGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConfigureLetters {

	ConfigureLetters configureL;

	@BeforeEach
	public void setup() {
		configureL = new ConfigureLetters();
	}

	@Test
	public void testletterAdd() {
		configureL.letterAdd('m');
		configureL.letterAdd('a');
		configureL.letterAdd('x');
		String expected = "m a x ";
		String actual = configureL.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddSameLetterMultipleTimes() {
		configureL.letterAdd('m');
		configureL.letterAdd('m');
		configureL.letterAdd('m');
		configureL.letterAdd('a');
		configureL.letterAdd('a');
		configureL.letterAdd('a');
		configureL.letterAdd('x');
		configureL.letterAdd('x');
		configureL.letterAdd('x');
		String expected = "m a x ";
		String actual = configureL.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testLetterAddPredictedFalse() {
		boolean expected = false;
		configureL.letterAdd('m');
		configureL.letterAdd('a');
		configureL.letterAdd('x');
		boolean actual1 = configureL.predictedLetters('g');
		boolean actual2 = configureL.predictedLetters('e');
		boolean actual3 = configureL.predictedLetters('r');
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);

	}

	@Test
	public void testLetterAddPredictedTrue() {
		configureL.letterAdd('m');
		configureL.letterAdd('a');
		configureL.letterAdd('x');
		boolean expected = true;
		boolean actual1 = configureL.predictedLetters('m');
		boolean actual2 = configureL.predictedLetters('a');
		boolean actual3 = configureL.predictedLetters('x');
		assertEquals(expected, actual1);
		assertEquals(expected, actual2);
		assertEquals(expected, actual3);
	}

	@Test
	public void testToString() {
		configureL.letterAdd('m');
		String expected = "m ";
		String actual = configureL.toString();
		assertEquals(expected, actual);
		configureL.letterAdd('a');
		configureL.letterAdd('x');
		expected = "m a x ";
		actual = configureL.toString();
		assertEquals(expected, actual);
	}

}
