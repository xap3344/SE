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

	// 开始一轮背单词
	public Session start(int num) {
		Session s = new Session(dicName, num);
		return s;
	}

	// 这里返回推荐的词语，最多会有五个，没有相似的话就返回null
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

	// 返回某个单词在词库中的索引
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

	// 答对的正确次数加一
	public void addRight(int index) {
		wordlist.get(index).right++;
	}

	// 打错的话错误次数加一
	public void addWrong(int index) {
		wordlist.get(index).wrong++;
	}

	// 设定最后背到的单词
	public void setLastWord(int index) {
		lastWord = index;
	}

	// 得到最后背到的单词
	public String getLastWord() {
		return wordlist.get(lastWord).word;
	}

	// 得到背诵过的单词
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

	// 得到正确的单词数目
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

	// 得到错误的单词数目
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

	// 得到正确率
	public float getRightRate() {
		float rec = (float) getRecited();
		if (rec == 0) {
			return 0;
		}
		return (float) getRight() / rec;
	}

	// 得到正确率百分数
	public int getRightRatePercent() {
		float rec = (float) getRecited();
		if (rec == 0) {
			return 0;
		}
		return (int) ((float) getRight() / rec * 100f);
	}

	// 检测输入的单词是否正确
	boolean checkWords(String word, int index) {
		Record r = wordlist.get(index);
		if (r.word.equals(word)) {
			return true;
		}
		return false;
	}

	// 查找word这个单词是否合法
	// 合法的话返回剩余单词数量，不合法的话返回-1
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

	// 将单词状态列表写入set文件(其实就是后缀)
	public void writeFile() {
		String setting = dicName + ".set";

		BufferedWriter bw1;
		try {

			bw1 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(setting, false), "GBK")); // 指定编码格式，以免读取时中文字符异常

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

	// 加载词库
	void loadFile(String path) {
		String setting = path.substring(0, path.lastIndexOf('.')) + ".set";

		File setFile = new File(setting);

		// 如果曾经背诵过这个单词 那么说明已经有set文件
		// 从set文件加载
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

			// 如果没有背诵过这个文件那么从源文件加载
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
