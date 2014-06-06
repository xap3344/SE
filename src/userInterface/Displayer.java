package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import core.DictionaryStatus;

public class Displayer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyMenuBar menuBar = new MyMenuBar();
	private ImageCanvas ca = new ImageCanvas();
	private JPanel welcome = new JPanel();
	private JPanel jp = new JPanel();

	private JButton jbChoose = new JButton("ѡ��ʿ�");
	private JButton jbExit = new JButton("�˳�");

	private JButton jbFirst = new JButton("�ӵ�һ�����ʿ�ʼ");
	private JButton jbLast = new JButton("���ϴν������ʿ�ʼ");
	private JButton jbAny = new JButton("ѡ��ʼ����");

	private JButton jbStart = new JButton("��ʼ����");
	private JButton jbSubmit = new JButton("�ύ");

	private JButton jbNext = new JButton("��һ��");

	private JComboBox<String> bar = new JComboBox<String>();
	private JTextComponent jtc = (JTextComponent) (bar.getEditor()
			.getEditorComponent());

	private JTextField jtf, jtf1;

	public Displayer() {
		init();
		entranceView();
	}

	public void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// �������ͼ��
		Image icon = Toolkit.getDefaultToolkit().getImage("word.png");
		this.setIconImage(icon);
		welcome.setBackground(new Color(240, 240, 240));

		// ����Main Frame
		this.setTitle("Word Master");
		this.setSize(450, 280);
		this.setLayout(new BorderLayout());
		this.add(menuBar, BorderLayout.NORTH);
		this.add(welcome, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bar.setEditable(true);
	}

	public void entranceView() {
		// JPanel
		welcome.removeAll();
		welcome.setLayout(new GridLayout(1, 2, 5, 5));
		welcome.add(ca);
		jp.setLayout(new GridLayout(3, 1, 30, 30));
		welcome.add(jp);
		JLabel jl = new JLabel();
		jl.setText("<html>================================"
				+ "===<br>&nbsp;&nbsp;&nbsp;&nbsp;��ӭʹ��Word Master!"
				+ "<br>===================================</html>");
		jp.add(jl);

		jp.add(jbChoose);

		jp.add(jbExit);

		jp.validate();
	}

	public void dictChosenView(DictionaryStatus ds) {
		welcome.removeAll();

		// ��ʾͳ����Ϣ
		JPanel jp1 = new JPanel();
		JLabel jl = new JLabel("", JLabel.CENTER);
		String s = ds.getName() + "<br>";
		s += "����������" + ds.getTotalLength() + "<br>";
		s += "�Ѿ����У�" + ds.getRecitedCount() + "<br>";
		s += "��ȷ��Ŀ��" + ds.getCorrectCount() + "<br>";
		s += "������Ŀ��" + ds.getIncorrectCount() + "<br>";
		s += "��ȷ�ʣ�&nbsp;&nbsp;&nbsp;" + ds.getAccuracy() + "<br>";
		s = "<html>" + s + "</html>";
		jl.setText(s);
		jl.setFont((new java.awt.Font("Dialog", 0, 18)));
		jp1.setLayout(new BorderLayout());
		jp1.add(jl, BorderLayout.CENTER);
		welcome.add(jp1);

		// ��ʾ��ʼ����ѡ��
		jp = new JPanel();
		jp.setLayout(new GridLayout(3, 1, 30, 30));
		jp.add(jbFirst);
		jp.add(jbLast);
		jp.add(jbAny);
		welcome.add(jp);
		repaint();
		validate();
	}

	public void enterCustomWordView() {
		welcome.remove(jp);
		jp = new JPanel();
		jp.setLayout(new GridLayout(7, 1, 10, 10));

		jp.add(new JLabel());
		jp.add(bar);
		jp.add(new JLabel());
		jp.add(new JLabel());
		jp.add(new JLabel());
		jp.add(jbSubmit);
		jp.add(new JLabel());
		welcome.add(jp);
		repaint();
		validate();
	}

	public void inputCounterView() {
		welcome.removeAll();
		welcome.add(ca);

		jp = new JPanel();
		jp.setLayout(new GridLayout(3, 1, 20, 40));

		JLabel jl = new JLabel("������Ҫ���еĵ�����Ŀ��");
		jtf = new JTextField();
		jp.add(jl);
		jp.add(jtf);
		jp.add(jbStart);

		welcome.add(jp);
		repaint();
		validate();
	}

	public void reciteView(String meaning) {
		welcome.removeAll();

		jp = new JPanel();
		jp.setLayout(new GridLayout(4, 1, 20, 40));

		JLabel jl = new JLabel("���������浥�ʵ���˼��");
		JLabel jl1 = new JLabel(meaning);
		jl1.setFont((new java.awt.Font("Dialog", 0, 18)));
		jtf1 = new JTextField();
		jp.add(jl);
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jbNext);

		welcome.add(jp);
		repaint();
		validate();
	}

	public JButton getExitButton() {
		return jbExit;
	}

	public JMenuItem getExitMenuItem() {
		return menuBar.exit;
	}

	public JButton getChooseButton() {
		return jbChoose;
	}

	public JMenuItem getChooseMenuItem() {
		return menuBar.choosen;
	}

	public JMenuItem getBarMenuItem() {
		return menuBar.bar;
	}

	public JMenuItem getPieMenuItem() {
		return menuBar.pie;
	}

	public JMenuItem getHelpMenuItem() {
		return menuBar.doc;
	}

	public JButton getFirstButton() {
		return jbFirst;
	}

	public JButton getLastButton() {
		return jbLast;
	}

	public JButton getAnyButton() {
		return jbAny;
	}

	public JButton getStartButton() {
		return jbStart;
	}

	public JButton getSubmitButton() {
		return jbSubmit;
	}

	public JTextComponent getJTC() {
		return jtc;
	}

	public JComboBox<String> getBar() {
		return bar;
	}

	public JTextField getJtf() {
		return jtf;
	}

	public JTextField getJtf1() {
		return jtf1;
	}

	public JButton getNext() {
		return jbNext;
	}

	private class ImageCanvas extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ImageIcon imageIcon = new ImageIcon("word.png");
		Image image = imageIcon.getImage();

		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}

	private class MyMenuBar extends JMenuBar {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JMenu option;
		JMenuItem choosen;
		JMenuItem exit;

		JMenu help;
		JMenuItem doc;

		JMenu chart;
		JMenuItem bar;
		JMenuItem pie;

		public MyMenuBar() {

			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			option = new JMenu("ѡ��");
			choosen = new JMenuItem("ѡ��ʿ�");
			choosen.setBackground(Color.WHITE);
			ImageIcon dic = new ImageIcon("dic.png");
			choosen.setIcon(dic);
			exit = new JMenuItem("�˳�");
			ImageIcon exit1 = new ImageIcon("exit.png");
			exit.setIcon(exit1);

			help = new JMenu("����");
			doc = new JMenuItem("�����ĵ�");
			doc.setBackground(Color.WHITE);
			ImageIcon doc1 = new ImageIcon("doc.png");
			doc.setIcon(doc1);

			chart = new JMenu("ͼ��ͳ��");
			bar = new JMenuItem("��״ͼ");
			ImageIcon bar1 = new ImageIcon("bar.png");
			bar.setIcon(bar1);
			bar.setEnabled(false);
			pie = new JMenuItem("��ͼ");
			ImageIcon pie1 = new ImageIcon("pie.png");
			pie.setIcon(pie1);
			pie.setEnabled(false);

			add(option);
			option.add(choosen);
			option.addSeparator();
			option.add(exit);

			add(help);
			help.add(doc);

			add(chart);
			chart.add(bar);
			chart.addSeparator();
			chart.add(pie);

		}

	}
}

class Chart extends JFrame {

	private static final long serialVersionUID = 1L;

	public Chart(int i, ArrayList<Float> arr1, ArrayList<String> arr2) {
		this.setSize(800, 600);
		this.setTitle("ͳ��ͼ��");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		MyPanel panel = new MyPanel(i, arr1, arr2);
		panel.setHistogramTitle("������Ŀ������", "���");
		this.add(panel);
	}
}

class MyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MyPanel(int type, ArrayList<Float> arr1, ArrayList<String> arr2) {
		elem.clear();
		value.clear();
		for (int i = 0; i < Math.min(arr1.size(), arr2.size()); i++) {
			MyPanel.insert(arr2.get(i), arr1.get(i));
		}
		if (type == 0)
			flag = true;
		else
			flag = false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, 800, 600);
		if (flag)
			drawHistogram(g);
		else
			drawCaky(g);
	}

	public static void insert(String aElem, float aValue) {
		elem.add(aElem);
		value.add(aValue);
	}

	// ����״ͼ
	public void drawHistogram(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("��״ͼ", SIZE + 200, 30);
		g.setColor(Color.GREEN);
		// ����
		g.drawLine(SIZE, 600 - SIZE, SIZE, SIZE);
		// ����
		g.drawLine(SIZE, 600 - SIZE, 800 - SIZE, 600 - SIZE);
		// ��ͷ
		g.setColor(Color.RED);
		int[] x1 = { SIZE - 6, SIZE, SIZE + 6 };
		int[] y1 = { SIZE + 8, SIZE, SIZE + 8 };
		g.drawPolyline(x1, y1, 3);
		int[] x2 = { 800 - SIZE - 8, 800 - SIZE, 800 - SIZE - 8 };
		int[] y2 = { 600 - SIZE - 6, 600 - SIZE, 600 - SIZE + 6 };
		g.drawPolyline(x2, y2, 3);
		// title
		g.drawString(this.yTitle, SIZE - 20, SIZE - 6);
		g.drawString(this.xTitle, 800 - SIZE - 20, 600 - SIZE + 20);
		// ��̬����ÿ����״ͼ�Ŀ�
		int wigth = (int) ((800 - 3 * SIZE) / (value.size() * 2));
		// ȡ�����е����ֵ
		float max = 0;
		for (Float elem : value) {
			if (max < elem.intValue()) {
				max = elem.intValue();
			}
		}
		// ����������߶�
		double num = (double) (600 - 2 * (SIZE + 10)) / (double) max;
		// ����ÿ����״ͼ
		for (int i = 0; i < elem.size(); i++) {
			int height = (int) (value.get(i) * num);
			// g.drawRect(wigth*(i*2+2),Main.HEIGHT-SIZE-height,wigth,height);
			g.setColor(new java.awt.Color(Digit.getDigit(255), Digit
					.getDigit(255), Digit.getDigit(255)));
			// �����ɫ
			g.fillRect(wigth * (i * 2 + 1) + SIZE, 600 - SIZE - height, wigth,
					height);
			g.setColor(Color.RED);
			// ����ÿ������
			g.drawString(Float.toString(value.get(i)), wigth * (i * 2 + 1)
					+ SIZE, 600 - SIZE - 20 - height);
			// �ں����ϻ���ÿ������
			g.drawString(elem.get(i), wigth * (i * 2 + 1) + SIZE,
					600 - SIZE + 20);
			// �������ϻ���ÿ��߶�ֵ
			g.drawString(Float.toString(value.get(i)), SIZE - 40, 600 - SIZE
					- height + 5);
			// �������ϻ�����ʶ��
			g.drawLine(SIZE, 600 - SIZE - height, SIZE + 3, 600 - SIZE - height);
		}
	}

	// ����ͼ
	public void drawCaky(Graphics g) {
		g.setColor(Color.BLUE);
		int wigth = (int) ((800 - CAKY_WIDTH) / 2);
		int height = (int) ((600 - CAKY_WIDTH) / 2);
		g.drawOval(wigth, height, CAKY_WIDTH, CAKY_WIDTH);
		g.drawString("��ͼ", (int) (800 / 2) - 30, height - 50);
		int sum = 0;
		for (Float elem : value) {
			sum += elem.intValue();
		}
		double Test = 360d / sum;
		int currentAngle = 0;
		int half = (int) (CAKY_WIDTH + 50) / 2;
		int xFlag = 1;
		int yFlag = -1;
		for (int i = 0; i < elem.size(); i++) {
			int angle = (int) (Test * value.get(i).intValue() + 0.5);
			g.setColor(new java.awt.Color(Digit.getDigit(255), Digit
					.getDigit(255), Digit.getDigit(255)));
			g.fillArc(wigth, height, CAKY_WIDTH, CAKY_WIDTH, currentAngle,
					angle);
			// ���ڼ���ÿ��Բ�ܵ�ֵ
			if (currentAngle > 90 && currentAngle < 181) {
				xFlag = 1;
				yFlag = -1;
			} else if (currentAngle > 180 && currentAngle < 271) {
				xFlag = 1;
				yFlag = -1;
			} else if (currentAngle > 270) {
				xFlag = 1;
				yFlag = -1;
			}
			// ����ÿ��ı�ʶ�ַ�������
			int x = (int) (Math.cos((double) (currentAngle + angle / 2)
					* Math.PI / 180)
					* xFlag * half + wigth - 40 + half);
			int y = (int) (Math.sin((double) (currentAngle + angle / 2)
					* Math.PI / 180)
					* yFlag * half + height - 20 + half);
			g.drawString(elem.get(i), x, y);
			currentAngle += angle;
		}
	}

	public void setHistogramTitle(String y, String x) {
		xTitle = x;
		yTitle = y;
	}

	private boolean flag = true;
	private final int SIZE = 100;
	private final int CAKY_WIDTH = 400;
	private String xTitle;
	private String yTitle;
	private static List<String> elem = new ArrayList<String>();
	private static List<Float> value = new ArrayList<Float>();
}

class Digit {
	public Digit() {
	}

	// ����0-digit��Χ�ڵ��������
	public static int getDigit(int digit) {
		java.util.Random ran = new Random();
		return (int) (ran.nextDouble() * digit);
	}
}