package test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import interfaces.DictionaryStatus;
import core.Reciter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReciterTest {

	public static Reciter r = new Reciter();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test001InitializeDictionary() {
		assertTrue(r.initializeDictionary("hdushdus") == false);
	}

	@Test
	public void test002InitializeDictionary1() {
		assertTrue(r.initializeDictionary("dictionary.txt") == true);
	}

	@Test
	public void test003ChoosePieceWithInitial() {
		assertTrue(r.choosePieceWithInitial('0') == false);
	}

	/** 测不来 */
	@Test
	public void test004ChoosePieceWithInitial1() {
		assertTrue(r.choosePieceWithInitial('a') == true);
	}

	@Test
	public void test005GetDictStatus() {
		assertTrue(r.getDictStatus() != null);
	}

	@Test
	public void test006GetAllDictStatus() {
		DictionaryStatus[] dicts = r.getAllDictStatus();
		assertEquals(27, dicts.length);

		int totalLengthAll = 0, correctCountAll = 0, incorrectCountAll = 0, recitedCountAll = 0;
		for (char i = 'a'; i <= 'z'; i++) {
			int tempDicLength = dicts[i - 'a'].getTotalLength();
			int tempCorrectCount = dicts[i - 'a'].getCorrectCount();
			int tempIncorrectCount = dicts[i - 'a'].getIncorrectCount();
			int tempRecitedCount = dicts[i - 'a'].getRecitedCount();

			totalLengthAll += tempDicLength;
			correctCountAll += tempCorrectCount;
			incorrectCountAll += tempIncorrectCount;
			recitedCountAll += tempRecitedCount;
		}

		assertEquals(totalLengthAll, dicts[26].getTotalLength());
		assertEquals(correctCountAll, dicts[26].getCorrectCount());
		assertEquals(incorrectCountAll, dicts[26].getIncorrectCount());
		assertEquals(recitedCountAll, dicts[26].getRecitedCount());
	}

	@Test
	public void test007SetStartingIndexByWord() {
		assertTrue(r.setStartingIndexByWord("bello") == false);
	}

	@Test
	public void test008SetStartingIndexByWord1() {
		assertTrue(r.setStartingIndexByWord("accept") == true);
	}

	@Test
	public void test009SetReciteCount() {
		assertTrue(r.setReciteCount(5) == true);
	}

	@Test
	public void test010ShowMeaningOfCurrentWord() {
		assertEquals("v.接受，同意", r.showMeaningOfCurrentWord());
	}

	@Test
	public void test011TestMatching() {
		assertEquals(null, r.testMatching("accept"));
	}

	@Test
	public void test012TestMatching() {
		assertEquals("acceptable", r.testMatching("blabla"));
	}

	@Test
	public void test013GetDicPath() {
		assertEquals("dictionary.txt", r.getDicPath());
	}

	@Test
	public void test014GetRecitationStatus() {
	//	assertNotEquals(null, r.getRecitationStatus());
	}

	@Test
	public void test015GetChosenInitial() {
		assertEquals('a', r.getChosenInitial());
	}

	@Test
	public void test017Suggest() {
		ArrayList<String> ret = r.suggest("abbababa");
		assertEquals(null, ret);
	}
	
	@Test
	public void test018Suggest1() {
		ArrayList<String> ret = r.suggest("acc");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("accede");
		expected.add("accelerate");
		expected.add("acceleration");
		expected.add("accent");
		expected.add("accept");
		
		assertEquals(expected, ret);
	}

}
