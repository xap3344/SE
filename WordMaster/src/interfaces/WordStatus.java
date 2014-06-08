package interfaces;

public interface WordStatus {
	public int getCorrectCount();

	public void increaseCorrectCount();

	public int getIncorrectCount();

	public void increaseIncorrectCount();

	public String getWord();

	public String getMeaning();
}
