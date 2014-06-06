package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Reciter {
	private String dicPath;
	private Dictionary dict;
	int startingIndexInDict = -1;
	private Dictionary wordsToBeRecited;
	int currentIndexInWordsToBeRecited = 0;

	public void initializeDictionary(String dicPath) {
		this.dicPath = dicPath;
		Scanner scanner;
		try {
			File dicFile = new File(dicPath);

			/* ��dicFile�ж����ļ�������Ϊ�ʿ�����ʼ��allWords */
			String dicName = dicFile.getName();
			dict = new Dictionary(dicName);

			/* ��ʼ��ȡ�ļ� */
			scanner = new Scanner(dicFile);
			String thisLine;

			/* ��ȡ��һ�����ݣ���Ϊ���֣�˵������һ�α���ʱ���µļ�¼ */
			/* ���������֣�˵���ôʿ��ǵ�һ�α� */
			if (scanner.hasNext()) {
				thisLine = scanner.nextLine();
				try {
					startingIndexInDict = Integer.parseInt(thisLine);
				} catch (NumberFormatException nfex) {
					String[] splittedThisLine = thisLine.split("   ");
					/* ���ǵ���һ��û�����֣���֤�˴ʿ��һ�α������Բ����ܼ�¼����ȷ�������� */
					dict.insertWord(new WordStatus(splittedThisLine[0],
							splittedThisLine[1], 0, 0));
				}
			}

			while (scanner.hasNext()) {
				thisLine = scanner.nextLine();
				String[] splittedThisLine = thisLine.split("   ");
				if (splittedThisLine.length == 2) {
					dict.insertWord(new WordStatus(splittedThisLine[0],
							splittedThisLine[1], 0, 0));
				} else if (splittedThisLine.length == 4) {
					String word = splittedThisLine[0];
					String meaning = splittedThisLine[1];
					int correctCount = Integer.parseInt(splittedThisLine[2]);
					int incorrectCount = Integer.parseInt(splittedThisLine[3]);
					dict.insertWord(new WordStatus(word, meaning, correctCount,
							incorrectCount));
				}
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public DictionaryStatus getDictStatus() {
		return dict.getDictionaryStatus();
	}

	public boolean setStartingIndexByWord(String word) {
		int tempIndex = dict.getIndexByWord(word);
		if (tempIndex == -1) {
			startingIndexInDict = 0;
			return false;
		} else {
			startingIndexInDict = tempIndex;
			return true;
		}
	}

	public void setStartingIndexToFirstWord() {
		startingIndexInDict = 0;
	}

	public void setStartingIndexToRecordedWord() {
		if (startingIndexInDict == -1)
			startingIndexInDict = 0;
	}

	public boolean setReciteCount(int reciteCount) {

		boolean ret = false;
		int maxIndex = dict.getDicLength() - 1;
		if (startingIndexInDict + reciteCount - 1 < maxIndex) {
			maxIndex = startingIndexInDict + reciteCount - 1;
			ret = true;
		}

		wordsToBeRecited = new Dictionary("���Ҫ��������");
		for (int i = startingIndexInDict; i <= maxIndex; i++) {
			WordStatus originalWord = dict.getWordByIndex(i);
			wordsToBeRecited.insertWord(new WordStatus(originalWord.getWord(),
					originalWord.getMeaning(), 0, 0));
		}
		return ret;
	}

	private WordStatus getCurrentWord() {
		return wordsToBeRecited.getWordByIndex(currentIndexInWordsToBeRecited);
	}

	public String showMeaningOfCurrentWord() {
		if (getCurrentWord() != null)
			return getCurrentWord().getMeaning();
		else
			return null;
	}

	public boolean testMatching(String word) {
		WordStatus currentWord = getCurrentWord();
		if (word.equals(currentWord.getWord())) {
			currentIndexInWordsToBeRecited++;
			currentWord.increaseCorrectCount();
			return true;
		} else {
			currentWord.increaseIncorrectCount();
			return false;
		}
	}

	public DictionaryStatus getRecitationStatus() {
		return wordsToBeRecited.getDictionaryStatus();
	}

	public void updateToFile() {
		try {
			PrintWriter writer = new PrintWriter(new File(dicPath));

			/* ����û���ĵ��ʼ���A�����α����ĵ��ʼ���B������û���ĵ��ʼ���C */
			/* ��˳�����������洢 */
			int endingIndexInDict = startingIndexInDict
					+ wordsToBeRecited.getDicLength() - 1;

			writer.println((endingIndexInDict + 1) % dict.getDicLength());

			for (int i = 0; i < startingIndexInDict; i++) {
				WordStatus currentWord = dict.getWordByIndex(i);
				writer.println(currentWord.getWord() + "   "
						+ currentWord.getMeaning() + "   "
						+ currentWord.getCorrectCount() + "   "
						+ currentWord.getIncorrectCount());
			}

			for (int i = startingIndexInDict; i <= endingIndexInDict; i++) {
				WordStatus currentWordInDict = dict.getWordByIndex(i);
				WordStatus currentWordInRecitedList = wordsToBeRecited
						.getWordByIndex(i - startingIndexInDict);
				writer.println(currentWordInDict.getWord()
						+ "   "
						+ currentWordInDict.getMeaning()
						+ "   "
						+ (currentWordInDict.getCorrectCount() + currentWordInRecitedList
								.getCorrectCount())
						+ "   "
						+ (currentWordInDict.getIncorrectCount() + currentWordInRecitedList
								.getIncorrectCount()));
			}

			for (int i = endingIndexInDict + 1; i < dict.getDicLength(); i++) {
				WordStatus currentWord = dict.getWordByIndex(i);
				writer.println(currentWord.getWord() + "   "
						+ currentWord.getMeaning() + "   "
						+ currentWord.getCorrectCount() + "   "
						+ currentWord.getIncorrectCount());
			}

			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> suggest(String prefix) {
		int i = 0;
		while (i < dict.getDicLength()) {
			WordStatus word = dict.getWordByIndex(i);
			if (word.getWord().startsWith(prefix)) {
				ArrayList<String> suggestList = new ArrayList<String>();
				suggestList.add(word.getWord());

				int j = 1;
				while (j < 5) {
					WordStatus wordNext = dict.getWordByIndex(i + j);
					if (wordNext.getWord().startsWith(prefix)) {
						suggestList.add(wordNext.getWord());
					}
					j++;
				}
				return suggestList;
			}
			i++;
		}

		return null;
	}

	/*
	 * public static void main(String[] main) { Reciter r = new Reciter();
	 * r.initializeDictionary("E:/fukutan/�������/lab4/dictionary.txt");
	 * r.setStartingIndexByWord("zip"); r.setReciteCount(5);
	 * r.testMatching("zip"); r.testMatching("zipcode");
	 * r.testMatching("zipper"); r.testMatching("zone"); r.testMatching("zoo");
	 * r.updateToFile(); }
	 */
}
