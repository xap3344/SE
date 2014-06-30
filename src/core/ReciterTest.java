package core;

import static org.junit.Assert.*;
import interfaces.DictionaryStatus;

import java.util.ArrayList;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import core.Reciter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReciterTest {

	public static Reciter r = new Reciter();
	@SuppressWarnings("unused")
	private Mockery context = new Mockery(); // ģ����

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test001InitializeDictionary() {
		//assertTrue(r.initializeDictionary("hdushdus") == false);
	}

	@Test
	public void test002InitializeDictionary1() {
		//assertTrue(r.initializeDictionary("dictionary.txt") == true);
	}

	@Test
	public void test003ChoosePieceWithInitial() {
		//assertTrue(r.choosePieceWithInitial('0') == false);
	}

	@Test
	public void test004ChoosePieceWithInitial1() {
		//assertTrue(r.choosePieceWithInitial('a') == true);
	}

	@Test
	public void test005GetDictStatus() {
		assertTrue(r.getDictStatus() != null);
	}

	@Test
	public void test006GetAllDictStatus() {
		final DictionaryStatus[] dicts = r.getAllDictStatus();
		assertEquals(27, dicts.length);

//		for (int i = 0; i < 26; i++) {
//			dicts[i] = context.mock(interfaces.DictionaryStatus.class);
//			final int temp = i;
//			context.checking(new Expectations() {
//				{
//					allowing(dicts[temp]).getTotalLength();
//					will(returnValue(2));
//				}
//			});
//			context.checking(new Expectations() {
//				{
//					allowing(dicts[temp]).getCorrectCount();
//					will(returnValue(2));
//				}
//			});
//			context.checking(new Expectations() {
//				{
//					allowing(dicts[temp]).getIncorrectCount();
//					will(returnValue(2));
//				}
//			});
//			context.checking(new Expectations() {
//				{
//					allowing(dicts[temp]).getRecitedCount();
//					will(returnValue(2));
//				}
//			});
//		}
//
//		dicts[26] = context.mock(interfaces.DictionaryStatus.class);
//
//		context.checking(new Expectations() {
//			{
//				allowing(dicts[26]).getTotalLength();
//				will(returnValue(52));
//			}
//		});
//		context.checking(new Expectations() {
//			{
//				allowing(dicts[26]).getCorrectCount();
//				will(returnValue(52));
//			}
//		});
//		context.checking(new Expectations() {
//			{
//				allowing(dicts[26]).getIncorrectCount();
//				will(returnValue(52));
//			}
//		});
//		context.checking(new Expectations() {
//			{
//				allowing(dicts[26]).getRecitedCount();
//				will(returnValue(52));
//			}
//		});

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
		assertEquals("v.���ܣ�ͬ��", r.showMeaningOfCurrentWord());
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
		assertNotEquals(null, r.getRecitationStatus());
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
