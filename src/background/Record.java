package background;

//类中分别记录单词，对应意思，错误次数，正确次数
public class Record {
	String word;
	String meaning;
	int wrong = 0;
	int right = 0;

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the meaning
	 */
	public String getMeaning() {
		return meaning;
	}

	/**
	 * @param meaning the meaning to set
	 */
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	/**
	 * @return the wrong
	 */
	public int getWrong() {
		return wrong;
	}

	/**
	 * @param wrong the wrong to set
	 */
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}

	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}

	public Record(String w, String m) {
		word = w;
		meaning = m;
	}

	public Record(String w, String m, int _w, int _r) {
		word = w;
		meaning = m;
		wrong = _w;
		right = _r;
	}
}
