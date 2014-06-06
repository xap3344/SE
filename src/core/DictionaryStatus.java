package core;

public class DictionaryStatus {
	private String name;
	private int totalLength, correctCount, incorrectCount, recitedCount;
	double accuracy;

	public DictionaryStatus(String name, int totalLength, int correctCount,
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
	
}
