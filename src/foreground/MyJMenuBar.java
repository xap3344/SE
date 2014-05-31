package foreground;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MyJMenuBar extends JMenuBar {

	static final long serialVersionUID = 1L;
	JMenu option;
	JMenuItem choosen;
	JMenuItem exit;
	
	JMenu help;
	JMenuItem doc;

	public MyJMenuBar() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		option = new JMenu("ѡ��");
		choosen = new JMenuItem("ѡ��ʿ�");
		choosen.setBackground(Color.WHITE);
		ImageIcon dic=new ImageIcon("dic.png");  
		choosen.setIcon(dic);
		exit = new JMenuItem("�˳�");
		ImageIcon exit1=new ImageIcon("exit.png");  
		exit.setIcon(exit1);

		
		help = new JMenu("����");
		doc = new JMenuItem("�����ĵ�");
		doc.setBackground(Color.WHITE);
		ImageIcon doc1=new ImageIcon("doc.png");  
		doc.setIcon(doc1);


		add(option);
		option.add(choosen);
		option.addSeparator();
		option.add(exit);
		
		add(help);
		help.add(doc);

	}

}
