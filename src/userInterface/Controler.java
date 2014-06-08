package userInterface;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.ui.RefineryUtilities;

import interfaces.DictionaryStatus;
import core.Reciter;

import java.util.ArrayList;

public class Controler {
	private Displayer frame = new Displayer();
	private Reciter r = new Reciter();

	public Controler() {

		r.initializeDictionary("dictionary.txt");
		frame.dictFileChosenView(r.getAllDictStatus()[26]);

		/* 为退出操作增加ActonListener */
		ActionListener onExit = new ExitListener();
		frame.getExitButton().addActionListener(onExit);
		frame.getExitMenuItem().addActionListener(onExit);

		/* 为选择词库操作增加ActionListener */
		/*
		 * ActionListener onChoose = new ChooseListener();
		 * frame.getChooseButton().addActionListener(onChoose);
		 * frame.getChooseMenuItem().addActionListener(onChoose);
		 */

		/* 为帮助操作增加ActionListener */
		ActionListener onHelp = new HelpListener();
		frame.getHelpMenuItem().addActionListener(onHelp);

		/* 为首字母提交操作增加ActionListener */
		ActionListener onSubmitInitialLetter = new SumitInitialLetterListener();
		frame.getInitialLetterSubmit().addActionListener(onSubmitInitialLetter);

		/* 为选择开始单词的各种操作增加ActionListener */
		ActionListener onFirst = new FirstListener();
		ActionListener onLast = new LastListener();
		ActionListener onAny = new AnyListener();
		frame.getFirstButton().addActionListener(onFirst);
		frame.getLastButton().addActionListener(onLast);
		frame.getAnyButton().addActionListener(onAny);

		/* 为手动输入起始单词操作增加ActionListener */
		KeyAdapter onShowAlternative = new ShowAlternativeListener();
		ActionListener onSubmit = new SubmitListener();
		frame.getJTC().addKeyListener(onShowAlternative);
		frame.getSubmitButton().addActionListener(onSubmit);

		/* 为图标操作增加ActionListener */
		ActionListener bar = new BarListener();
		ActionListener pie = new PieListener();
		frame.getBarRecitedMenuItem().addActionListener(bar);
		frame.getBarAccuracyMenuItem().addActionListener(bar);
		for (int i = 0; i < 28; i++)
			frame.getPieMenuItem(i).addActionListener(pie);

		/* 为提交背单词数目操作增加ActionListener */
		ActionListener onStart = new StartListener();
		frame.getStartButton().addActionListener(onStart);

		/* 为下一个操作增加ActionListener */
		ActionListener onNext = new NextListener();
		frame.getNext().addActionListener(onNext);
	}

	private class BarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String from = e.getActionCommand();
			if (from.equals("全部词库中已背单词数量图")) {
				DictBarRecited c = new DictBarRecited(r.getAllDictStatus());
				c.pack();
				RefineryUtilities.centerFrameOnScreen(c);
				c.setVisible(true);
			} else if (from.equals("全部词库中已背单词正确率图")) {
				DictBarAccuracy c = new DictBarAccuracy(r.getAllDictStatus());
				c.pack();
				RefineryUtilities.centerFrameOnScreen(c);
				c.setVisible(true);
			}
		}
	}

	private class PieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String from = e.getActionCommand();
			DictionaryStatus ds;
			if (from.length() == 1) {
				ds = r.getAllDictStatus()[from.charAt(0) - 'a'];

			} else {
				if (from.equals("当前词库"))
					ds = r.getDictStatus();
				else
					ds = r.getAllDictStatus()[26];
			}

			DictPie p = new DictPie(ds);
			p.pack();
			RefineryUtilities.centerFrameOnScreen(p);
			p.setVisible(true);
		}
	}

	private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (r.getDicPath() != null && r.getWordsToBeRecited() != null)
				r.updateToFile();
			System.exit(0);
		}
	}

	/*
	 * private class ChooseListener implements ActionListener {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { JFileChooser jfc =
	 * new JFileChooser(); FileNameExtensionFilter f = new
	 * FileNameExtensionFilter( "*.txt(文本文档)", "txt"); jfc.setFileFilter(f); int
	 * result = jfc.showOpenDialog(frame); if (result ==
	 * JFileChooser.APPROVE_OPTION) { String myPath =
	 * jfc.getSelectedFile().getPath(); r.initializeDictionary(myPath);
	 * frame.dictFileChosenView(r.getAllDictStatus());
	 * frame.getPieMenuItem().setEnabled(true);
	 * frame.getBarMenuItem().setEnabled(true); } } }
	 */

	private class HelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				frame.HelpDoc();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class AnyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.enterCustomWordView();
		}
	}

	private class FirstListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			r.setStartingIndexToFirstWord();
			frame.inputCounterView();
		}
	}

	private class LastListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			r.setStartingIndexToRecordedWord();
			frame.inputCounterView();
		}
	}

	private class SumitInitialLetterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			char initial = frame.getSelectedInitialLetter();
			r.choosePieceWithInitial(initial);
			frame.dictPieceChosenView(r.getDictStatus());
			frame.getPieMenuItem(0).setEnabled(true);
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (frame.getJtf().getText().matches("^[0-9]*$")
					&& Integer.parseInt(frame.getJtf().getText()) > 0) {
				if (!r.setReciteCount(Integer
						.parseInt(frame.getJtf().getText()))) {
					JOptionPane.showMessageDialog(null, "数目超出范围！背完为止", "",
							JOptionPane.WARNING_MESSAGE);
				}
				frame.reciteView(r.showMeaningOfCurrentWord());
			} else {
				JOptionPane.showMessageDialog(null, "请输入数字", "",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class ShowAlternativeListener extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			String str = frame.getJTC().getText();
			if (str == null || str.length() == 0) {
				return;
			}
			if (r.suggest(str) != null) {
				String[] datas = r.suggest(str).toArray(new String[0]);
				frame.getBar().removeAllItems();
				for (int i = datas.length - 1; i >= 0; i--) {
					frame.getBar().insertItemAt(datas[i], 0);
				}
				frame.getJTC().setText(str);
				frame.getBar().showPopup();
			}
		}
	}

	private class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!r.setStartingIndexByWord(frame.getJTC().getText())) {
				JOptionPane.showMessageDialog(null, "单词不存在！从第一个开始", "",
						JOptionPane.WARNING_MESSAGE);
			}
			frame.inputCounterView();
		}
	}

	private class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String spelling = frame.getJtf1().getText();
			String ans = r.testMatching(spelling);
			if (ans != null) {
				JOptionPane.showMessageDialog(null, "错！应该是" + ans, "",
						JOptionPane.WARNING_MESSAGE);
			}
			String nextMeaning = r.showMeaningOfCurrentWord();
			if (nextMeaning != null)
				frame.reciteView(nextMeaning);
			else {
				r.updateToFile();
				DictionaryStatus recStatus = r.getRecitationStatus();
				JOptionPane.showMessageDialog(null,
						"背完啦！\n背诵" + recStatus.getTotalLength() + "\n正确："
								+ recStatus.getCorrectCount() + "\n错误："
								+ recStatus.getIncorrectCount() + "\n正确率:"
								+ recStatus.getAccuracy(), "",
						JOptionPane.INFORMATION_MESSAGE);
				frame.dictFileChosenView(r.getAllDictStatus()[26]);
			}
		}
	}

}
