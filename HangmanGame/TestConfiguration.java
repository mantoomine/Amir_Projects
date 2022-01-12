package HangmanGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConfiguration {
	private Configuration configureA;

	@BeforeEach
	public void setUp() {
		configureA = new Configuration();
	}

	@Test
	public void testPredictRightLetters() { 
		String deset = "max";
		configureA.setList(deset);
		configureA.letterPredict("x");
		String expected = " _ _ x";
		String actual = configureA.gesturesToString();
		assertEquals(expected, actual);
		configureA.letterPredict("m");
		expected = " m _ x";
		actual = configureA.gesturesToString();
		assertEquals(expected, actual);
	}

	@Test
	public void testInvalidWord() {
		String set = "max";
		configureA.setList(set);
		configureA.letterPredict("t");
		configureA.letterPredict("e");
		configureA.letterPredict("s");
		configureA.letterPredict("t");
		configureA.letterPredict("s");
		configureA.letterPredict("o");
		configureA.letterPredict("f");
		configureA.letterPredict("t");
		configureA.letterPredict("y");
		String expected = " _ _ _";
		String actual = configureA.gesturesToString();
		assertEquals(expected, actual);
	}

	@Test
	public void testFalsePlaySucceded() {
		boolean expected = false;
		configureA.letterPredict("t");
		configureA.letterPredict("e");
		configureA.letterPredict("q");
		boolean actual = configureA.win();
		assertEquals(expected, actual);
	}

	@Test
	public void testTruePlaySucceded() {
		boolean expected = true;
		String deset = "max";
		configureA.setList(deset);
		configureA.letterPredict("m");
		configureA.letterPredict("a");
		configureA.letterPredict("x");
		boolean actual = configureA.win();
		assertEquals(expected, actual);
	}

	@Test
	public void testFalseLost() {
		String deset = "max";
		configureA.setList(deset);
		boolean expected = false;
		configureA.letterPredict("t");
		configureA.letterPredict("t"); 
		configureA.letterPredict("e");
		configureA.letterPredict("s");
		configureA.letterPredict("r");
		configureA.letterPredict("q");
		configureA.letterPredict("o");
		configureA.letterPredict("f");
		configureA.letterPredict("y"); 
		boolean actual = configureA.lost();
		assertEquals(expected, actual);
		configureA.letterPredict("m");
		configureA.letterPredict("a");
		configureA.letterPredict("x");
		actual = configureA.lost();
		assertEquals(expected, actual);
	}

	@Test
	public void testTrueLost() {
		boolean expected = true;
		String deset = "max";
		configureA.setList(deset);
		configureA.letterPredict("y");
		configureA.letterPredict("p");
		configureA.letterPredict("z");
		configureA.letterPredict("v");
		configureA.letterPredict("b");
		configureA.letterPredict("n");
		configureA.letterPredict("e");
		configureA.letterPredict("q");
		configureA.letterPredict("w");
		boolean actual = configureA.lost();
		assertEquals(expected, actual);
	}

	@Test
	public void testGesturesToString() {
		String deset = "max";
		configureA.setList(deset);
		String expected = " _ _ _";
		String actual = configureA.gesturesToString();
		assertEquals(expected, actual);
	}

	@Test
	public void testGesturesToStringWithDash() {
		configureA.setList("hand-holding");
		String expected = " _ _ _ _ - _ _ _ _ _ _ _";
		String actual = configureA.gesturesToString();
		assertEquals(expected, actual);
	}

	@Test
	public void testIfWordIsValid() {
		boolean expected = true;
		boolean actual = configureA.wordCheck("java");
		assertEquals(expected, actual);
	}

	@Test
	public void testIfWordIsValidWithDash() {
		boolean expected = true;
		boolean actual = configureA.wordCheck("hand-holding");
		assertEquals(expected, actual);
	}

	@Test
	public void testInvalidCharacters() {
		boolean expected = false;
		boolean actual = configureA.wordCheck("%$#@");
		assertEquals(expected, actual);
	}

	
	@Test
	public void testGetList() {
		String deset = "max";
		configureA.setList(deset);
		String expected = "max";
		String actual = configureA.getList();
		assertEquals(expected, actual);
	}

	@Test
	public void testSetList() {
		String expected = "max";
		configureA.setList(expected);
		String actual = configureA.getList();
		assertEquals(expected, actual);
	}

	@Test
	public void testSetListWithDash() {
		String expected = "hand-holding";
		configureA.setList(expected);
		String actual = configureA.getList();
		assertEquals(expected, actual);
	}
     @Test
	public void testSetListWithInvalidCharacter() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class, () ->{
		String invalidWord = "&^%$#";
		configureA.setList(invalidWord); 
	});
	}
	
}