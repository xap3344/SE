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

	private Mockery context = new JUnit4Mockery(); // 模拟器
	private interfaces.WordStatus word = context
			.mock(interfaces.WordStatus.class); // 模拟一个该接口的实现
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

	/* 测试向单词表中添加一个单词，检验单词是否加入进去 */
	@Test
	public void testInsertWord() {
		// Object[] objectArray = new Object[]{"affluence", "富裕", 0, 0};
		// WordStatus word = new WordStatus("affluence", "富裕", 0, 0);

		d.insertWord(word);
		
		ArrayList<interfaces.WordStatus> wordList = d.getWordList();
		// ArrayList<WordStatus> wordList = d.getWordList();
		assertEquals(wordList.get(wordList.size() - 1), word);
	}

	/* 测试用getWordByIndex方法返回的单词是否是之前加进去的单词 */
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

	/* 测试第一个单词 */
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
