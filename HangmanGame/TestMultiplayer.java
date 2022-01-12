package HangmanGame;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestMultiplayer {
	private Multiplayer configureTwo;
	
	@BeforeEach
	public void setUp() {
		configureTwo = new Multiplayer();
	}
	
	@Test
	public void testIfWordExist() {
		boolean expected = true;
		boolean actual = configureTwo.listCheck("java");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIfWordWithDashExist() {
		boolean expected = true;
		boolean actual = configureTwo.listCheck("hand-holding");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testInvalidWord() {
		boolean expected = false;
		boolean actual = configureTwo.listCheck("%^&");
		assertEquals(expected, actual);
	}
}