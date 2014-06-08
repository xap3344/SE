package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordStatusTest {

	public static WordStatusImpl word;

	@Before
	public void setUp() throws Exception {
		word = new WordStatusImpl("morose", "闷闷不乐的", 0, 0);
		System.out.println(word.getCorrectCount());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWordStatus() {
		assertTrue(word != null);
	}

	@Test
	public void testGetCorrectCount() {
		int correctCount = word.getCorrectCount();
		assertEquals(correctCount, 0);
	}

	@Test
	public void testIncreaseCorrectCount() {
		word.increaseCorrectCount();
		int correctCount = word.getCorrectCount();
		assertEquals(correctCount, 1);
	}

	@Test
	public void testGetIncorrectCount() {
		int incorrectCount = word.getIncorrectCount();
		assertEquals(incorrectCount, 0);
	}

	@Test
	public void testIncreaseIncorrectCount() {
		word.increaseIncorrectCount();
		int incorrectCount = word.getIncorrectCount();
		assertEquals(incorrectCount, 1);
	}

	@Test
	public void testGetWord() {
		assertEquals(word.getWord(), "morose");
	}

	@Test
	public void testGetMeaning() {
		assertEquals(word.getMeaning(), "闷闷不乐的");
	}

}
