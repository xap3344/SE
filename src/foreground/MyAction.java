package foreground;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import background.Helper;
import background.Session;

public class MyAction implements ActionListener {
	private MyFrame frame;
	private static String myPath;
	private static Helper h = null;
	private static String startWord = "";
	private static JTextField jtf, jtf1;
	private static JTextComponent component;
	private static int startIndex;
	private static int lastRecNum;
	private static Session s;

	public MyAction(MyFrame frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if ("�����ĵ�".equals(name))
			help();
		else if ("�˳�".equals(name)) {
			h.writeFile();
			System.exit(0);
		} else if ("ѡ��ʿ�".equals(name))
			choose();
		else if ("�ӵ�һ�����ʿ�ʼ".equals(name))
			firstOrLast(0);
		else if ("ѡ��ʼ����".equals(name))
			any();
		else if ("���ϴν������ʿ�ʼ".equals(name))
			firstOrLast(1);
		else if ("��ʼ����".equals(name)) {
			start(h.wordlist.get(h.getIndexForWord(startWord)).getMeaning());
		} else if ("��һ��".equals(name))
			check();
		else if ("ѡ��ʼ".equals(name))
			firstOrLast(2);

	}

	private void check() {
		String temp = jtf1.getText();
		if (!temp.equals(h.wordlist.get(startIndex).getWord())) {
			JOptionPane.showMessageDialog(null,
					"����\n ��ȷ�𰸣�" + h.wordlist.get(startIndex).getWord(), "",
					JOptionPane.WARNING_MESSAGE);
			s.setWrongnum(s.getWrongnum() + 1);
			h.addWrong(startIndex);
		} else {
			s.setRightnum(s.getRightnum() + 1);
			h.addRight(startIndex);
		}
		startIndex++;
		if (startIndex < lastRecNum) {
			start(h.wordlist.get(startIndex).getMeaning());
		} else {
			h.setLastWord(startIndex);
			h.writeFile();
			JOptionPane.showMessageDialog(null, "��ϲ�����н���\n����" + s.getWordnum()
					+ "\n��ȷ��" + s.getRightnum() + "\n����" + s.getWrongnum()
					+ "\n��ȷ��:" + (float) s.getRightnum() / s.getWordnum() * 100
					+ "%", "", JOptionPane.INFORMATION_MESSAGE);
			frame.welcome.removeAll();
			frame.welcome.add(frame.ca);
			JButton jbFirst = new JButton("�ӵ�һ�����ʿ�ʼ");
			jbFirst.addActionListener(new MyAction(frame));

			JButton jbLast = new JButton("���ϴν������ʿ�ʼ");
			jbLast.addActionListener(new MyAction(frame));

			JButton jbAny = new JButton("ѡ��ʼ����");
			jbAny.addActionListener(new MyAction(frame));

			frame.jp = new JPanel();
			frame.jp.setLayout(new GridLayout(3, 1, 30, 30));
			frame.jp.add(jbFirst);
			frame.jp.add(jbLast);
			frame.jp.add(jbAny);
			frame.welcome.add(frame.jp);
			frame.repaint();
			frame.validate();

		}
	}

	private void start(String str) {
		if (jtf.getText().matches("^[0-9]*$")
				&& Integer.parseInt(jtf.getText()) > 0) {

			if (str.equals(h.wordlist.get(h.getIndexForWord(startWord))
					.getMeaning())) {
				startIndex = h.getIndexForWord(startWord);
				lastRecNum = Integer.parseInt(jtf.getText()) + startIndex;
				s = h.start(lastRecNum - startIndex);
			}
			if (h.checknum(lastRecNum)) {
				frame.welcome.removeAll();

				frame.jp = new JPanel();
				frame.jp.setLayout(new GridLayout(4, 1, 20, 40));

				JLabel jl = new JLabel("���������浥�ʵ���˼��");
				JLabel jl1 = new JLabel(str);
				jl1.setFont((new java.awt.Font("Dialog", 0, 18)));
				jtf1 = new JTextField();
				JButton jb = new JButton("��һ��");
				jb.addActionListener(new MyAction(frame));
				frame.jp.add(jl);
				frame.jp.add(jl1);
				frame.jp.add(jtf1);
				frame.jp.add(jb);

				frame.welcome.add(frame.jp);
				frame.repaint();
				frame.validate();

			} else {
				JOptionPane.showMessageDialog(null, "������Χ", "",
						JOptionPane.WARNING_MESSAGE);
			}
		} else
			JOptionPane.showMessageDialog(null, "������ȷ�ĵ�����Ŀ", "",
					JOptionPane.WARNING_MESSAGE);

	}

	// �ӿ�ͷ�����ϴο�ʼ
	private void firstOrLast(int num) {
		if (num == 2)
			startWord = component.getText();
		else if (num == 0)
			startWord = h.getFirstWord();
		else
			startWord = h.getLastWord();
		if (h.getIndexForWord(startWord) != -1) {
			frame.welcome.removeAll();
			frame.welcome.add(frame.ca);

			frame.jp = new JPanel();
			frame.jp.setLayout(new GridLayout(3, 1, 20, 40));

			JLabel jl = new JLabel("������Ҫ���еĵ�����Ŀ��");
			jtf = new JTextField();
			JButton jb = new JButton("��ʼ����");
			jb.addActionListener(new MyAction(frame));
			frame.jp.add(jl);
			frame.jp.add(jtf);
			frame.jp.add(jb);

			frame.welcome.add(frame.jp);
			frame.repaint();
			frame.validate();
		} else {
			JOptionPane.showMessageDialog(null, "������ȷ�ĵ���", "",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	// ��ѡ������ⵥ�ʿ�ʼ���е���
	private void any() {
		frame.welcome.remove(frame.jp);
		JPanel jp = new JPanel();
		final JComboBox<String> bar = new JComboBox<String>();
		Component com = bar.getEditor().getEditorComponent();
		component = (JTextComponent) com;

		component.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String str = component.getText();
				if (str == null || str.length() == 0) {
					return;
				}
				if (h.suggest(str) != null) {
					String[] datas = h.suggest(str).toArray(new String[0]);
					// System.out.println(h.suggest(component.getText()));
					bar.removeAllItems();
					for (int i = datas.length - 1; i >= 0; i--) {
						bar.insertItemAt(datas[i], 0);
					}
					component.setText(str);
					bar.showPopup();
				}
			}
		});

		bar.setEditable(true);
		jp.setLayout(new GridLayout(7, 1, 10, 10));
		JButton jb = new JButton("ѡ��ʼ");
		jb.addActionListener(new MyAction(frame));
		jp.add(new JLabel());
		jp.add(bar);
		jp.add(new JLabel());
		jp.add(new JLabel());
		jp.add(new JLabel());
		jp.add(jb);
		jp.add(new JLabel());
		frame.welcome.add(jp);
		frame.repaint();
		frame.validate();

	}

	// ѡ��ʿ����
	private void choose() {
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter f = new FileNameExtensionFilter("*.txt(�ı��ĵ�)",
				"txt");
		jfc.setFileFilter(f);
		int result = jfc.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			myPath = jfc.getSelectedFile().getPath();
			h = new Helper(myPath);
		}

		if (myPath != null) {
			frame.welcome.remove(frame.jp);
			frame.welcome.remove(frame.ca);
			frame.welcome.removeAll();

			// ��ʾͳ����Ϣ
			JPanel jp1 = new JPanel();
			JLabel jl = new JLabel("", JLabel.CENTER);
			String s = new String();
			s += "��          �⣺"
					+ h.dicName.substring(h.dicName.lastIndexOf("\\") + 1)
					+ "<br>";
			s += "�Ѿ����У�" + h.getRecited() + "<br>";
			s += "��ȷ��Ŀ��" + h.getRight() + "<br>";
			s += "������Ŀ��" + h.getWrong() + "<br>";
			s += "��ȷ�ʣ�&nbsp;&nbsp;&nbsp;" + h.getRightRatePercent() + "<br>";
			s = "<html>" + s + "</html>";
			jl.setText(s);
			jl.setFont((new java.awt.Font("Dialog", 0, 18)));
			jp1.setLayout(new BorderLayout());
			jp1.add(jl, BorderLayout.CENTER);
			frame.welcome.add(jp1);

			// ��ʾ��ʼ����ѡ��
			JButton jbFirst = new JButton("�ӵ�һ�����ʿ�ʼ");
			jbFirst.addActionListener(new MyAction(frame));

			JButton jbLast = new JButton("���ϴν������ʿ�ʼ");
			jbLast.addActionListener(new MyAction(frame));

			JButton jbAny = new JButton("ѡ��ʼ����");
			jbAny.addActionListener(new MyAction(frame));

			frame.jp = new JPanel();
			frame.jp.setLayout(new GridLayout(3, 1, 30, 30));
			frame.jp.add(jbFirst);
			frame.jp.add(jbLast);
			frame.jp.add(jbAny);
			frame.welcome.add(frame.jp);
			frame.repaint();
			frame.validate();
		}

	}

	// �����ĵ�����
	private void help() {
		JFrame jf = new JFrame();
		jf.setTitle("Word Master Help");
		jf.setSize(400, 500);
		Image icon = Toolkit.getDefaultToolkit().getImage("word.png");
		jf.setIconImage(icon);
		jf.setLocation(0, 0);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
