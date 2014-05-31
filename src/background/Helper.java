package background;

import java.io.*;
import java.util.ArrayList;

public class Helper {
	// public static void main(String[] s) {
	// Helper h = new Helper("dictionary.txt");
	//
	// h.addRight(0);
	// h.addWrong(0);
	// h.setLastWord(569);
	// System.out.println(h.getRightRatePercent());
	//
	// ArrayList<Record> l = h.suggest("a");
	// int i = 0;
	// while (i < l.size()) {
	// Record r = l.get(i);
	// System.out.println(r.word);
	// i++;
	// }
	// h.writeFile();
	//
	// }

	public ArrayList<Record> wordlist;
	public String dicName = null;
	int lastWord;

	public Helper(String path) {
		wordlist = new ArrayList<Record>();
		loadFile(path);
	}

	// ��ʼһ�ֱ�����
	public Session start(int num) {
		Session s = new Session(dicName, num);
		return s;
	}

	// ���ﷵ���Ƽ��Ĵ�������������û�����ƵĻ��ͷ���null
	public ArrayList<String> suggest(String prefix) {
		int i = 0;
		while (i < wordlist.size()) {
			Record r = wordlist.get(i);
			if (r.word.startsWith(prefix)) {
				ArrayList<String> suggestList = new ArrayList<String>();
				suggestList.add(r.word);

				int j = 1;
				while (j < 5) {
					Record rn = wordlist.get(i + j);
					if (rn.word.startsWith(prefix)) {
						suggestList.add(rn.word);
					}
					j++;
				}
				return suggestList;
			}
			i++;
		}

		return null;
	}

	public String getFirstWord() {
		return wordlist.get(0).word;
	}

	// ����ĳ�������ڴʿ��е�����
	public int getIndexForWord(String word) {
		int i = 0;
		while (i < wordlist.size()) {
			Record r = wordlist.get(i);
			if (r.word.equals(word)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	// ��Ե���ȷ������һ
	public void addRight(int index) {
		wordlist.get(index).right++;
	}

	// ���Ļ����������һ
	public void addWrong(int index) {
		wordlist.get(index).wrong++;
	}

	// �趨��󱳵��ĵ���
	public void setLastWord(int index) {
		lastWord = index;
	}

	// �õ���󱳵��ĵ���
	public String getLastWord() {
		return wordlist.get(lastWord).word;
	}

	// �õ����й��ĵ���
	public int getRecited() {
		int i = 0;
		int count = 0;
		while (i < wordlist.size()) {
			Record r = wordlist.get(i);
			if (r.right > 0 || r.wrong > 0) {
				count++;
			}
			i++;
		}
		return count;
	}

	// �õ���ȷ�ĵ�����Ŀ
	public int getRight() {
		int i = 0;
		int count = 0;
		while (i < wordlist.size()) {
			Record r = wordlist.get(i);
			if (r.right > 0) {
				count++;
			}
			i++;
		}
		return count;
	}

	// �õ�����ĵ�����Ŀ
	public int getWrong() {
		int i = 0;
		int count = 0;
		while (i < wordlist.size()) {
			Record r = wordlist.get(i);
			if (r.right == 0 && r.wrong > 0) {
				count++;
			}
			i++;
		}
		return count;
	}

	// �õ���ȷ��
	public float getRightRate() {
		float rec = (float) getRecited();
		if (rec == 0) {
			return 0;
		}
		return (float) getRight() / rec;
	}

	// �õ���ȷ�ʰٷ���
	public int getRightRatePercent() {
		float rec = (float) getRecited();
		if (rec == 0) {
			return 0;
		}
		return (int) ((float) getRight() / rec * 100f);
	}

	// �������ĵ����Ƿ���ȷ
	boolean checkWords(String word, int index) {
		Record r = wordlist.get(index);
		if (r.word.equals(word)) {
			return true;
		}
		return false;
	}

	// ����word��������Ƿ�Ϸ�
	// �Ϸ��Ļ�����ʣ�൥�����������Ϸ��Ļ�����-1
	int wordsLeft(String word) {
		int i = 0;
		while (i < wordlist.size()) {
			Record r = wordlist.get(i);
			if (r.word.equals(word)) {
				return wordlist.size() - i;
			}
			i++;
		}

		return -1;
	}

	// ������״̬�б�д��set�ļ�(��ʵ���Ǻ�׺)
	public void writeFile() {
		String setting = dicName + ".set";

		BufferedWriter bw1;
		try {

			bw1 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(setting, false), "GBK")); // ָ�������ʽ�������ȡʱ�����ַ��쳣

			bw1.write(dicName + " " + lastWord);
			bw1.newLine();
			bw1.flush();
			int i = 0;
			while (i < wordlist.size()) {
				Record r = wordlist.get(i);
				bw1.write(r.word + " " + r.meaning + " " + r.wrong + " "
						+ r.right);
				bw1.newLine();
				bw1.flush();
				i++;
			}
			bw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ���شʿ�
	void loadFile(String path) {
		String setting = path.substring(0, path.lastIndexOf('.')) + ".set";

		File setFile = new File(setting);

		// ����������й�������� ��ô˵���Ѿ���set�ļ�
		// ��set�ļ�����
		if (setFile.isFile() && setFile.exists()) {
			try {
				String encoding = "GBK";
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(setFile), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				lineTxt = bufferedReader.readLine();
				String[] lineSplit = lineTxt.split(" ");
				dicName = lineSplit[0];
				lastWord = Integer.parseInt(lineSplit[1]);

				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineSplit = lineTxt.split(" ");
					// System.out.println(lineSplit[1] + " "
					// + lineSplit[lineSplit.length - 1]);
					wordlist.add(new Record(lineSplit[0], lineSplit[1], Integer
							.parseInt(lineSplit[2]), Integer
							.parseInt(lineSplit[3])));

				}
				read.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			// ���û�б��й�����ļ���ô��Դ�ļ�����
			try {
				String encoding = "GBK";
				File file = new File(path);
				if (file.isFile() && file.exists()) {
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(file), encoding);
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						String[] lineSplit = lineTxt.split(" ");
						wordlist.add(new Record(lineSplit[0],
								lineSplit[lineSplit.length - 1]));
						// System.out.println(lineSplit[0] + " "
						// + lineSplit[lineSplit.length - 1]);
					}
					read.close();

					dicName = path.substring(0, path.lastIndexOf('.'));
					lastWord = 0;

				} else {
					System.out.println("File not exist.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checknum(int lastRecNum) {
		if (lastRecNum <= wordlist.size())
			return true;
		else
			return false;
	}
}
