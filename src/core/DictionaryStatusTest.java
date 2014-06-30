package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DictionaryStatusTest {

	public static DictionaryStatusImpl ds = new DictionaryStatusImpl(
			"dictionary.txt", 100, 43, 20, 63, 43.0d / 63.0d);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDictionaryStatus() {
		assertTrue(ds != null);
	}

	@Test
	public void testGetName() {
		assertEquals(ds.getName(), "dictionary.txt");
	}

	@Test
	public void testGetTotalLength() {
		assertEquals(ds.getTotalLength(), 100);
	}

	@Test
	public void testGetCorrectCount() {
		assertEquals(ds.getCorrectCount(), 43);
	}

	@Test
	public void testGetIncorrectCount() {
		assertEquals(ds.getIncorrectCount(), 20);
	}

	@Test
	public void testGetRecitedCount() {
		assertEquals(ds.getRecitedCount(), 63);
	}

	@Test
	public void testGetAccuracy() {
		assertEquals(ds.getAccuracy(), 43.0d / 63.0d, 0.001);
	}

}
