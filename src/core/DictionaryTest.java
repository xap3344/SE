package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import interfaces.WordStatus;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {

	private Mockery context = new JUnit4Mockery(); // ģ����
	private interfaces.WordStatus word = context
			.mock(interfaces.WordStatus.class); // ģ��һ���ýӿڵ�ʵ��
	public static DictionaryImpl d = new DictionaryImpl("Dictionary.txt");


	@Before
	public void setUp() throws Exception {
		// word
		context.checking(new Expectations() {
			{
				allowing(word).getWord();
				will(returnValue("affluence"));
			}
		});
		context.checking(new Expectations() {
			{
				allowing(word).getCorrectCount();
				will(returnValue(0));
			}
		});
		context.checking(new Expectations() {
			{
				allowing(word).getIncorrectCount();
				will(returnValue(0));
			}
		});
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDictionary() {

		assertTrue(d != null);
	}

	@Test
	public void testGetDicName() {
		assertEquals(d.getDicName(), "Dictionary.txt");
	}

	/* �����򵥴ʱ������һ�����ʣ����鵥���Ƿ�����ȥ */
	@Test
	public void testInsertWord() {
		// Object[] objectArray = new Object[]{"affluence", "��ԣ", 0, 0};
		// WordStatus word = new WordStatus("affluence", "��ԣ", 0, 0);

		d.insertWord(word);
		
		ArrayList<interfaces.WordStatus> wordList = d.getWordList();
		// ArrayList<WordStatus> wordList = d.getWordList();
		assertEquals(wordList.get(wordList.size() - 1), word);
	}

	/* ������getWordByIndex�������صĵ����Ƿ���֮ǰ�ӽ�ȥ�ĵ��� */
	@Test
	public void testGetWordByIndex() {
		//d.insertWord(word);
		WordStatus w = d.getWordByIndex(0);
		System.out.println(w.getWord());
		assertEquals(w.getWord(), word.getWord());
	}

	@Test
	public void testGetWordByIndex1() {
		WordStatus w = d.getWordByIndex(-1);
		assertEquals(w, null);
	}

	@Test
	public void testGetWordByIndex2() {
		// WordStatus w = d.getWordByIndex(100000);
		assertEquals(d.getWordByIndex(100000), null);
	}

	/* ���Ե�һ������ */
	@Test
	public void testGetIndexByWord() {
		
		System.out.println("======");
	//	System.out.println("d.getIndexByWord" + d.getIndexByWord("affluence"));
		assertEquals(0, d.getIndexByWord("affluence"));

	}

	@Test
	public void testGetIndexByWord1() {
		assertEquals(-1, d.getIndexByWord("sadsdsds"));
	}

	@Test
	public void testGetDicLength() {
		d.insertWord(word);
		System.out.println(d.getWordByIndex(0));
		System.out.println(d.getWordByIndex(1));
		assertEquals(1, d.getDicLength());
	}

	@Test
	public void testSumCorrectCounts() {
		assertEquals(0, d.sumCorrectCounts());
	}

	@Test
	public void testSumIncorrectCounts() {
		assertEquals(0, d.sumIncorrectCounts());
	}

	@Test
	public void testSumRecitedCounts() {
		assertEquals(0, d.sumRecitedCounts());
	}

	@Test
	public void testGetAccuracy() {
		assertEquals(0.0d, d.getAccuracy(), 0.00001);
	}

	@Test
	public void testGetDictionaryStatus() {
		assertTrue(d.getDictionaryStatus() != null);
	}

}
