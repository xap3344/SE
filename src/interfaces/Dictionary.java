package interfaces;

import java.util.ArrayList;

public interface Dictionary {
	public String getDicName();

	public void insertWord(WordStatus newWord);

	public WordStatus getWordByIndex(int index);

	public int getIndexByWord(String word);

	public int getDicLength();

	public int sumCorrectCounts();

	public int sumIncorrectCounts();

	public int sumRecitedCounts();

	public double getAccuracy();

	public DictionaryStatus getDictionaryStatus();

	public ArrayList<WordStatus> getWordList();

}
