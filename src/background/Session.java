package background;

public class Session {
	// ��ѡ�ʿ�������ѡ������������ȷ�����������󵥴�������ȷ��
	String dicName;
	int wordnum;
	/**
	 * @return the wordnum
	 */
	public int getWordnum() {
		return wordnum;
	}

	/**
	 * @param wordnum the wordnum to set
	 */
	public void setWordnum(int wordnum) {
		this.wordnum = wordnum;
	}

	/**
	 * @return the rightnum
	 */
	public int getRightnum() {
		return rightnum;
	}

	/**
	 * @param rightnum the rightnum to set
	 */
	public void setRightnum(int rightnum) {
		this.rightnum = rightnum;
	}

	/**
	 * @return the wrongnum
	 */
	public int getWrongnum() {
		return wrongnum;
	}

	/**
	 * @param wrongnum the wrongnum to set
	 */
	public void setWrongnum(int wrongnum) {
		this.wrongnum = wrongnum;
	}

	/**
	 * @return the rightRate
	 */
	public float getRightRate() {
		return rightRate;
	}

	/**
	 * @param rightRate the rightRate to set
	 */
	public void setRightRate(float rightRate) {
		this.rightRate = rightRate;
	}

	int rightnum = 0;
	int wrongnum = 0;
	float rightRate;

	public Session(String dic, int num) {
		dicName = dic;
		wordnum = num;
	}
}
