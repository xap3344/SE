package core;

import interfaces.DictionaryStatus;

public class DictionaryStatusImpl implements DictionaryStatus {
	private String name;
	private int totalLength, correctCount, incorrectCount, recitedCount;
	double accuracy;

	public DictionaryStatusImpl(String name, int totalLength, int correctCount,
			int incorrectCount, int recitedCount, double accuracy) {
		this.name = name;
		this.totalLength = totalLength;
		this.correctCount = correctCount;
		this.incorrectCount = incorrectCount;
		this.recitedCount = recitedCount;
		this.accuracy = accuracy;
	}

	public String getName() {
		return name;
	}

	public int getTotalLength() {
		return totalLength;
	}

	public int getCorrectCount() {
		return correctCount;
	}

	public int getIncorrectCount() {
		return incorrectCount;
	}

	public int getRecitedCount() {
		return recitedCount;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public String toString() {
		return "�ʿ�����" + getName() + "\n������Ŀ" + getTotalLength() + "\n��ȷ��Ŀ"
				+ getCorrectCount() + "\n������Ŀ" + getIncorrectCount()+"\n������Ŀ"+getRecitedCount() + "\n��ȷ��"
				+ getAccuracy();
	}

}
