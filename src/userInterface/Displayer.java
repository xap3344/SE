package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

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
		ImageIcon imageIcon = new ImageIcon("word.png");
		Image image = imageIcon.getImage();

		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}

	private class MyMenuBar extends JMenuBar {

		JMenu option;
		JMenuItem choosen;
		JMenuItem exit;

		JMenu help;
		JMenuItem doc;

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

			add(option);
			option.add(choosen);
			option.addSeparator();
			option.add(exit);

			add(help);
			help.add(doc);

		}

	}
}
