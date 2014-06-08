package interfaces;

public interface DictionaryStatus {
	public String getName();

	public int getTotalLength();

	public int getCorrectCount();

	public int getIncorrectCount();

	public int getRecitedCount();

	public double getAccuracy();
}
