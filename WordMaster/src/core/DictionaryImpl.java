package core;

import interfaces.Dictionary;
import interfaces.DictionaryStatus;
import interfaces.WordStatus;

import java.util.ArrayList;

/* 用数组的形式维护某词库中所有词条的状态信息 */
public class DictionaryImpl implements Dictionary {
	private String dicName;
	private ArrayList<WordStatus> allWords;

	public DictionaryImpl(String dicName) {
		this.dicName = dicName;
		allWords = new ArrayList<WordStatus>();
	}

	public String getDicName() {
		return dicName;
	}

	public void insertWord(WordStatus newWord) {
		allWords.add(newWord);
	}

	public WordStatus getWordByIndex(int index) {
		if (index < allWords.size() && index >= 0)
			return allWords.get(index);
		else
			return null;
	}

	public int getIndexByWord(String word) {
		for (int i = 0; i < allWords.size(); i++) {
			if (allWords.get(i).getWord().equals(word))
				return i;
		}
		return -1;
	}

	public int getDicLength() {
		return allWords.size();
	}

	public int sumCorrectCounts() {
		int counter = 0;
		for (WordStatus w : allWords) {
			int currentCorrectCount = w.getCorrectCount();
			if (currentCorrectCount != 0)
				counter++;
		}
		return counter;
	}

	public int sumIncorrectCounts() {
		int counter = 0;
		for (WordStatus w : allWords) {
			int currentIncorrectCount = w.getIncorrectCount();
			if (currentIncorrectCount != 0)
				counter++;
		}
		return counter;
	}

	public int sumRecitedCounts() {
		int counter = 0;
		for (WordStatus w : allWords) {
			int currentCorrectCount = w.getCorrectCount();
			int currentIncorrectCount = w.getIncorrectCount();
			if (currentCorrectCount != 0 || currentIncorrectCount != 0)
				counter++;
		}
		return counter;
	}

	public double getAccuracy() {
		return ((int) ((sumCorrectCounts() * 1.0 / (sumCorrectCounts() + sumIncorrectCounts())) * 1000)) * 1.0 / 1000;
	}

	public DictionaryStatus getDictionaryStatus() {
		return new DictionaryStatusImpl(dicName, getDicLength(),
				sumCorrectCounts(), sumIncorrectCounts(), sumRecitedCounts(),
				getAccuracy());
	}

	public ArrayList<WordStatus> getWordList() {
		// TODO Auto-generated method stub
		return allWords;
	}
}
