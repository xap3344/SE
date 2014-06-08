package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import interfaces.Dictionary;
import core.DictionaryImpl;
import core.WordStatusImpl;
import interfaces.WordStatus;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 

public class DictionaryTest {

	public static Dictionary d = new DictionaryImpl("Dictionary.txt");

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test001Dictionary() {

		assertTrue(d != null);
	}

	@Test
	public void test002GetDicName() {
		assertEquals(d.getDicName(), "Dictionary.txt");
	}

	@Test
	public void test003InsertWord() {
		WordStatus word = new WordStatusImpl("affluence", "富裕", 0, 0);
		d.insertWord(word);
		assertEquals(d.getWordByIndex(d.getDicLength()-1), word);
	}

	@Test
	public void test004GetWordByIndex() {
		WordStatus w = d.getWordByIndex(0);
		
		assertEquals(w.getWord(), "affluence");
	}

	@Test
	public void test005GetWordByIndex1() {
		WordStatus w = d.getWordByIndex(-1);
		assertEquals(w, null);
	}

	@Test
	public void test006GetWordByIndex2() {
	//	WordStatus w = d.getWordByIndex(100000);
		assertEquals(d.getWordByIndex(100000), null);
	}

	/* 测试第一个单词abandon */
	@Test
	public void test007GetIndexByWord() {
		assertEquals(0, d.getIndexByWord("affluence"));
	}

	@Test
	public void test008GetIndexByWord1() {
		assertEquals(-1, d.getIndexByWord("sadsdsds"));
	}

	@Test
	public void test009GetDicLength() {
		assertEquals(1, d.getDicLength());
		System.out.println(d.getDicLength());
	}

	@Test
	public void test010SumCorrectCounts() {
		assertEquals(0, d.sumCorrectCounts());
	}

	@Test
	public void test011SumIncorrectCounts() {
		assertEquals(0, d.sumIncorrectCounts());
	}

	@Test
	public void test012SumRecitedCounts() {
		assertEquals(0, d.sumRecitedCounts());
	}

	@Test
	public void test013GetAccuracy() {
		assertEquals(0.0d, d.getAccuracy(), 0.00001);
	}

	@Test
	public void test014GetDictionaryStatus() {
		assertTrue(d.getDictionaryStatus() != null);
	}

}
