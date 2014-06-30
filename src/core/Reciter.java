package core;

import interfaces.Dictionary;
import interfaces.DictionaryStatus;
import interfaces.WordStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Reciter {
	private String dicPath;
	private String dicPathWithoutExtension;
	private Dictionary dict;
	int startingIndexInDict = -1;
	private Dictionary wordsToBeRecited;
	int currentIndexInWordsToBeRecited = 0;

	String initialChosen = "";
	public static HashMap<String, Integer> map = new HashMap<String, Integer>();
	public final static String[] names = { "v", "n", "adj", "adv", "conj",
			"prep", "pron", "int", "num", "null" };

	public boolean initializeDictionary(String dicPath) throws Exception {
		if (!new File(dicPath).exists())
			return false;
		this.dicPath = dicPath;
		this.dicPathWithoutExtension = dicPath.substring(0,
				dicPath.length() - 4);
		File pieceV = new File(dicPathWithoutExtension + "-v.log");
		if (!pieceV.exists()) {
			SAXReader reader = new SAXReader();
			File file = new File(dicPath);
			Document doc = reader.read(file);
			Element root = doc.getRootElement();

			PrintWriter[] writer = new PrintWriter[10];

			map.put("v", 0);
			map.put("n", 1);
			map.put("adj", 2);
			map.put("adv", 3);
			map.put("conj", 4);
			map.put("prep", 5);
			map.put("pron", 6);
			map.put("int", 7);
			map.put("num", 8);
			map.put("null", 9);

			for (int i = 0; i < 10; i++) {
				writer[i] = new PrintWriter(dicPathWithoutExtension + "-"
						+ names[i] + ".log");
				writer[i].println("0");
				writer[i].flush();

			}
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				Element ele = (Element) i.next();
				String chinese = ele.elementText("chinese");
				String english = ele.elementText("english");
				if (!chinese.contains(".")) {
					writer[9].println(english + "   " + chinese + "   0   0");
				} else {
					String str = chinese.substring(0, chinese.indexOf("."));
					writer[map.get(str)].println(english + "   " + chinese
							+ "   0   0");
				}
			}

			for (int i = 0; i < 10; i++) {
				writer[i].flush();
				writer[i].close();
			}

		}
		return true;
	}

	public boolean choosePieceWithInitial(String initial) {

		initialChosen = initial;
		startingIndexInDict = -1;
		currentIndexInWordsToBeRecited = 0;

		Scanner scanner;
		try {
			File dicFile = new File(dicPathWithoutExtension + "-"
					+ initialChosen + ".log");

			/* 从dicFile中读入文件名，作为词库名初始化allWords */
			String dicName = dicFile.getName();
			dict = new DictionaryImpl(dicName);

			/* 开始读取文件 */
			scanner = new Scanner(dicFile);
			String thisLine;

			/* 读取第一行内容，是上次保存的单词序号 */
			if (scanner.hasNext()) {
				thisLine = scanner.nextLine();
				startingIndexInDict = Integer.parseInt(thisLine);
			}

			/* 读剩余内容 */
			while (scanner.hasNext()) {
				thisLine = scanner.nextLine();
				String[] splittedThisLine = thisLine.split("   ");

				String word = splittedThisLine[0];
				String meaning = splittedThisLine[1];
				int correctCount = Integer.parseInt(splittedThisLine[2]);
				int incorrectCount = Integer.parseInt(splittedThisLine[3]);
				dict.insertWord(new WordStatusImpl(word, meaning, correctCount,
						incorrectCount));
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public DictionaryStatus getDictStatus() {
		return dict.getDictionaryStatus();
	}

	public DictionaryStatus[] getAllDictStatus() {
		Dictionary[] dictPiece = new Dictionary[11];
		DictionaryStatus[] dictPieceStatus = new DictionaryStatus[11];
		int totalLengthAll = 0, correctCountAll = 0, incorrectCountAll = 0, recitedCountAll = 0;

		for (char i = 0; i < 10; i++) {
			Scanner scanner;
			try {
				File dicFile = new File(dicPathWithoutExtension + "-"
						+ names[i] + ".log");

				/* 从dicFile中读入文件名，作为词库名初始化allWords */
				String dicName = dicFile.getName();
				dictPiece[i] = new DictionaryImpl(dicName);

				/* 开始读取文件 */
				scanner = new Scanner(dicFile);
				String thisLine;

				/* 读取第一行内容，是上次保存的单词序号 */
				if (scanner.hasNext()) {
					thisLine = scanner.nextLine();
				}

				/* 读剩余内容 */
				while (scanner.hasNext()) {
					thisLine = scanner.nextLine();
					String[] splittedThisLine = thisLine.split("   ");

					String word = splittedThisLine[0];
					String meaning = splittedThisLine[1];
					int correctCount = Integer.parseInt(splittedThisLine[2]);
					int incorrectCount = Integer.parseInt(splittedThisLine[3]);
					dictPiece[i].insertWord(new WordStatusImpl(word, meaning,
							correctCount, incorrectCount));
				}

				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			int tempDicLength = dictPiece[i].getDicLength();
			int tempCorrectCount = dictPiece[i].sumCorrectCounts();
			int tempIncorrectCount = dictPiece[i].sumIncorrectCounts();
			int tempRecitedCount = dictPiece[i].sumRecitedCounts();
			double tempAccuracy = ((int) ((tempCorrectCount * 1.0 / (tempCorrectCount + tempIncorrectCount)) * 1000)) * 1.0 / 1000;

			dictPieceStatus[i] = new DictionaryStatusImpl(names[i],
					tempDicLength, tempCorrectCount, tempIncorrectCount,
					tempRecitedCount, tempAccuracy);

			totalLengthAll += tempDicLength;
			correctCountAll += tempCorrectCount;
			incorrectCountAll += tempIncorrectCount;
			recitedCountAll += tempRecitedCount;

		}

		double accuracyAll = ((int) ((correctCountAll * 1.0 / (correctCountAll + incorrectCountAll)) * 1000)) * 1.0 / 1000;

		dictPieceStatus[10] = new DictionaryStatusImpl("Total", totalLengthAll,
				correctCountAll, incorrectCountAll, recitedCountAll,
				accuracyAll);
		return dictPieceStatus;
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
		if (startingIndexInDict + reciteCount - 1 <= maxIndex) {
			maxIndex = startingIndexInDict + reciteCount - 1;
			ret = true;
		}

		wordsToBeRecited = new DictionaryImpl("这次要背的内容");
		for (int i = startingIndexInDict; i <= maxIndex; i++) {
			WordStatus originalWord = dict.getWordByIndex(i);
			wordsToBeRecited.insertWord(new WordStatusImpl(originalWord
					.getWord(), originalWord.getMeaning(), 0, 0));
		}
		return ret;
	}

	public Dictionary getWordsToBeRecited() {
		return wordsToBeRecited;
	}

	public void setWordsToBeRecited(Dictionary wordsToBeRecited) {
		this.wordsToBeRecited = wordsToBeRecited;
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

	public String testMatching(String word) {
		WordStatus currentWord = getCurrentWord();
		String answer = currentWord.getWord();
		if (word.equals(currentWord.getWord())) {
			currentIndexInWordsToBeRecited++;
			currentWord.increaseCorrectCount();
			return null;
		} else {
			currentIndexInWordsToBeRecited++;
			currentWord.increaseIncorrectCount();
			return answer;
		}
	}

	public String getDicPath() {
		return dicPath;
	}

	public DictionaryStatus getRecitationStatus() {
		return wordsToBeRecited.getDictionaryStatus();
	}

	public String getChosenInitial() {
		return initialChosen;
	}

	public void updateToFile() {
		try {
			PrintWriter writer = new PrintWriter(new File(
					dicPathWithoutExtension + "-" + initialChosen + ".log"));

			/* 本次没背的单词集合A，本次背过的单词集合B，本次没背的单词集合C */
			/* 按顺序分三种情况存储 */
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

	public static String readme() throws FileNotFoundException {
		File read = new File("readme.txt");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(read);
		String thisLine = "<html>";
		while (scanner.hasNext()) {
			thisLine += scanner.nextLine() + "<br>";
		}
		thisLine += "</html>";
		return thisLine;

	}

}
