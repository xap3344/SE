package core;

import interfaces.WordStatus;

/* 用于记录每个词条的状态 */
public class WordStatusImpl implements WordStatus {
	private String word, meaning;
	private int correctCount, incorrectCount;

	public WordStatusImpl(String word, String meaning, int correctCount,
			int incorrectCount) {
		this.word = word;
		this.meaning = meaning;
		this.correctCount = correctCount;
		this.incorrectCount = incorrectCount;
	}

	public int getCorrectCount() {
		return correctCount;
	}

	public void increaseCorrectCount() {
		correctCount++;
	}

	public int getIncorrectCount() {
		return incorrectCount;
	}

	public void increaseIncorrectCount() {
		incorrectCount++;
	}

	public String getWord() {
		return word;
	}

	public String getMeaning() {
		return meaning;
	}

}
