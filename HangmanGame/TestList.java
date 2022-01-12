package HangmanGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestList {
	private List configureT;

	@BeforeEach
	public void setUp() {
		configureT = new List();
	}

	@Test
	public void testListIndexIfWordExist() {
		int expected = 6781;
		int actual = configureT.wordPlace("java");
		assertEquals(expected, actual);
	}

	@Test
	public void testListIndexIfWordNotExist() {
		int expected = -1;
		int actual = configureT.wordPlace("WordNotExist");
		assertEquals(expected, actual);
	}

	@Test
	public void testGetRandom() {
		String actual = configureT.getRandom();
		String expected = actual;
		assertEquals(expected, actual);
	}

	@Test
	public void testRemoveWordFromList() {
		int expected = -1;
		configureT.wordRemove("java");
		int actual = configureT.wordPlace("java");
		assertEquals(expected, actual);
		configureT = new List();
		actual = configureT.wordPlace("java");
		assertEquals(expected, actual);
		configureT.wordAdd("java");
		expected = 6781;
		actual = configureT.wordPlace("java");
		assertEquals(expected, actual);
		configureT = new List();
		actual = configureT.wordPlace("java");
		assertEquals(expected, actual);
	}

}