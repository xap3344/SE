package foreground;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private MyJMenuBar myMenuBar = new MyJMenuBar();
	ImageCanvas ca = new ImageCanvas();
	JPanel welcome = new JPanel();
	JPanel jp = new JPanel();

	public MyFrame() {

		// 适配当前电脑UI风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置软件图标
		Image icon = Toolkit.getDefaultToolkit().getImage("word.png");
		this.setIconImage(icon);
		welcome.setBackground(new Color(240, 240, 240));

		// 设置Main Frame
		this.setTitle("Word Master");
		this.setSize(450, 280);
		this.setLayout(new BorderLayout());
		this.add(myMenuBar, BorderLayout.NORTH);
		this.add(welcome, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel
		welcome.setLayout(new GridLayout(1, 2, 5, 5));
		welcome.add(ca);
		jp.setLayout(new GridLayout(3, 1, 30, 30));
		welcome.add(jp);
		JLabel jl = new JLabel();
		jl.setText("<html>================================"
				+ "===<br>&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用Word Master!"
				+ "<br>===================================</html>");
		jp.add(jl);
		JButton jbChoose = new JButton("选择词库");
		jp.add(jbChoose);
		JButton jbExit = new JButton("退出");
		jp.add(jbExit);

		jp.validate();

		// 添加事件监听器
		jbChoose.addActionListener(new MyAction(this));
		jbExit.addActionListener(new MyAction(this));
		myMenuBar.exit.addActionListener(new MyAction(this));
		myMenuBar.choosen.addActionListener(new MyAction(this));
		myMenuBar.doc.addActionListener(new MyAction(this));
	}

	class ImageCanvas extends JPanel {
		private static final long serialVersionUID = 1L;
		ImageIcon imageIcon = new ImageIcon("word.png");
		Image image = imageIcon.getImage();

		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
