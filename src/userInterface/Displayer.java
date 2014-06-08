package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.KeyedValues2DDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import core.DictionaryStatus;

public class Displayer extends JFrame {

	private MyMenuBar menuBar = new MyMenuBar();
	private ImageCanvas ca = new ImageCanvas();
	private JPanel welcome = new JPanel();
	private JPanel jp = new JPanel();

	private JButton jbChoose = new JButton("选择词库源文件");
	private JButton jbExit = new JButton("退出");

	private JButton jbFirst = new JButton("从第一个单词开始");
	private JButton jbLast = new JButton("从上次结束单词开始");
	private JButton jbAny = new JButton("选择开始单词");

	private JButton jbStart = new JButton("开始背诵");
	private JButton jbSubmit = new JButton("提交");

	private JButton jbNext = new JButton("下一个");

	private JComboBox<String> bar = new JComboBox<String>();
	private JTextComponent jtc = (JTextComponent) (bar.getEditor()
			.getEditorComponent());

	private JComboBox<String> initialLetterList;
	private JButton initialLetterSubmit = new JButton("选择该词库");

	private JTextField jtf, jtf1;

	public Displayer() {
		init();
	//	entranceView();
	}

	public void init() {
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
				+ "===<br>&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用Word Master!"
				+ "<br>===================================</html>");
		jp.add(jl);

		jp.add(jbChoose);

		jp.add(jbExit);

		jp.validate();
	}

	public void dictFileChosenView(DictionaryStatus ds) {
		welcome.removeAll();
		welcome.setLayout(new GridLayout(1, 2, 5, 5));
		// 显示统计信息
		JPanel jp1 = new JPanel();
		JLabel jl = new JLabel("", JLabel.CENTER);
		String s = ds.getName() + "<br>";
		s += "单词总数：" + ds.getTotalLength() + "<br>";
		s += "已经背诵：" + ds.getRecitedCount() + "<br>";
		s += "正确数目：" + ds.getCorrectCount() + "<br>";
		s += "错误数目：" + ds.getIncorrectCount() + "<br>";
		s += "正确率：&nbsp;&nbsp;&nbsp;" + ds.getAccuracy() + "<br>";
		s = "<html>" + s + "</html>";
		jl.setText(s);
		jl.setFont((new java.awt.Font("Dialog", 0, 18)));
		jp1.setLayout(new BorderLayout());
		jp1.add(jl, BorderLayout.CENTER);
		welcome.add(jp1);

		// 显示首字母候选
		jp = new JPanel();
		jp.setLayout(new GridLayout(3, 1, 30, 30));
		
		
		initialLetterList = new JComboBox<String>();
		for (char i = 'a'; i <= 'z'; i++) {
			initialLetterList.addItem(i + "");
		}
		
		initialLetterList.setSelectedIndex(0);

		jp.add(initialLetterList);
		jp.add(initialLetterSubmit);
		welcome.add(jp);
		repaint();
		validate();
	}

	public void dictPieceChosenView(DictionaryStatus ds) {
		welcome.removeAll();

		// 显示统计信息
		JPanel jp1 = new JPanel();
		JLabel jl = new JLabel("", JLabel.CENTER);
		String s = ds.getName() + "<br>";
		s += "单词总数：" + ds.getTotalLength() + "<br>";
		s += "已经背诵：" + ds.getRecitedCount() + "<br>";
		s += "正确数目：" + ds.getCorrectCount() + "<br>";
		s += "错误数目：" + ds.getIncorrectCount() + "<br>";
		s += "正确率：&nbsp;&nbsp;&nbsp;" + ds.getAccuracy() + "<br>";
		s = "<html>" + s + "</html>";
		jl.setText(s);
		jl.setFont((new java.awt.Font("Dialog", 0, 18)));
		jp1.setLayout(new BorderLayout());
		jp1.add(jl, BorderLayout.CENTER);
		welcome.add(jp1);

		// 显示开始背诵选项
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

		JLabel jl = new JLabel("请输入要背诵的单词数目：");
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

		JLabel jl = new JLabel("请输入下面单词的意思：");
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
	
	public char getSelectedInitialLetter(){
		return ((String)(initialLetterList.getSelectedItem())).charAt(0);
	}
	
	public JButton getInitialLetterSubmit(){
		return initialLetterSubmit;
	}

	public JMenuItem getBarMenuItem() {
		return menuBar.bar;
	}

	public JMenuItem getPieMenuItem(int i) {
		return menuBar.pieOptions[i];
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

	/*
	public JMenuItem getChooseMenuItem() {
		return menuBar.choosen;
	}*/

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
		//JMenuItem choosen;
		JMenuItem exit;

		JMenu help;
		JMenuItem doc;

		JMenu chart;
		JMenuItem bar;
		JMenu pie;
		JMenuItem[] pieOptions = new JMenuItem[28];

		public MyMenuBar() {

			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			option = new JMenu("选项");
		//	choosen = new JMenuItem("选择词库");
		//	choosen.setBackground(Color.WHITE);
		//	ImageIcon dic = new ImageIcon("dic.png");
		//	choosen.setIcon(dic);
			exit = new JMenuItem("退出");
			ImageIcon exit1 = new ImageIcon("exit.png");
			exit.setIcon(exit1);

			help = new JMenu("帮助");
			doc = new JMenuItem("帮助文档");
			doc.setBackground(Color.WHITE);
			ImageIcon doc1 = new ImageIcon("doc.png");
			doc.setIcon(doc1);

			chart = new JMenu("图表统计");
			bar = new JMenuItem("柱状图");
			ImageIcon bar1 = new ImageIcon("bar.png");
			bar.setIcon(bar1);
			pie = new JMenu("饼图");
			ImageIcon pie1 = new ImageIcon("pie.png");
			pie.setIcon(pie1);
			
			pieOptions[0] = new JMenuItem("当前词库");
			pieOptions[0].setEnabled(false);
			pie.add(pieOptions[0]);
			pieOptions[1] = new JMenuItem("所有词库");
			pie.add(pieOptions[1]);
			pie.addSeparator();
			for(int i = 2; i <28;i++){
				pieOptions[i] = new JMenuItem((char)('a'-2+i)+"");
				pie.add(pieOptions[i]);
			}
			
			add(option);
		//	option.add(choosen);
		//	option.addSeparator();
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

class DictBar extends JFrame {

	public DictBar(DictionaryStatus[] ds) {
		super("全部词库中已背单词数量及正确率图");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		KeyedValues2DDataset keyedvalues2ddataset = createDataset(ds);
		JFreeChart chart = ChartFactory.createStackedBarChart(
				"全部词库中已背单词数量及正确率图", "词库",
				"正确率                                             已背单词数",
				keyedvalues2ddataset, PlotOrientation.HORIZONTAL, true, true,
				false);

		Font defaultFont = new Font("宋体", Font.BOLD, 15);// 将字体设置为支持中文的字体
		chart.getCategoryPlot().getRangeAxis().setLabelFont(defaultFont);// 设置Y轴标识字体
		chart.getCategoryPlot().getDomainAxis().setLabelFont(defaultFont);// 设置X轴标识字体
		chart.getLegend().setItemFont(defaultFont);// 设置图例说明字体
		chart.getTitle().setFont(defaultFont);// 设置标题字体

		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setPreferredSize(new Dimension(640, 640));
		setContentPane(chartpanel);
	}

	private KeyedValues2DDataset createDataset(DictionaryStatus[] ds) {
		DefaultKeyedValues2DDataset defaultkeyedvalues2ddataset = new DefaultKeyedValues2DDataset();
		for(int i =0;i<27;i++){
			DictionaryStatus tempStatus = ds[i];
			defaultkeyedvalues2ddataset.addValue(tempStatus.getAccuracy()*(-1), "正确率", tempStatus.getName());
			defaultkeyedvalues2ddataset.addValue(tempStatus.getRecitedCount(), "已背单词数", tempStatus.getName());
		}
		return defaultkeyedvalues2ddataset;
	}
}

class DictPie extends JFrame {

	public DictPie(DictionaryStatus ds) {
		super(ds.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(createDemoPanel(ds));
	}

	public static JPanel createDemoPanel(DictionaryStatus ds) {
		JPanel jpanel = new JPanel(new GridLayout(1, 2));
		DefaultPieDataset count1 = new DefaultPieDataset();
		count1.setValue("Correct", ds.getCorrectCount());
		count1.setValue("Incorrect", ds.getIncorrectCount());

		DefaultPieDataset count2 = new DefaultPieDataset();
		count2.setValue("Recited", ds.getRecitedCount());
		count2.setValue("Total", ds.getTotalLength());

		JFreeChart jfreechart = ChartFactory.createPieChart(
				"Correct/Incorrect", count1, false, false, false);
		PiePlot pieplot = (PiePlot) jfreechart.getPlot();
		JFreeChart jfreechart1 = ChartFactory.createPieChart("Recited/Total",
				count2, false, false, false);
		PiePlot pieplot1 = (PiePlot) jfreechart1.getPlot();
		jpanel.add(new ChartPanel(jfreechart));
		jpanel.add(new ChartPanel(jfreechart1));
		jpanel.setPreferredSize(new Dimension(640, 400));
		return jpanel;
	}
}