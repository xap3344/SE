package userInterface;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.DictionaryStatus;
import core.Reciter;

public class Controler {
	private Displayer frame = new Displayer();
	private Reciter r = new Reciter();

	public Controler() {
		/* Ϊ�˳���������ActonListener */
		ActionListener onExit = new ExitListener();
		frame.getExitButton().addActionListener(onExit);
		frame.getExitMenuItem().addActionListener(onExit);

		/* Ϊѡ��ʿ��������ActionListener */
		ActionListener onChoose = new ChooseListener();
		frame.getChooseButton().addActionListener(onChoose);
		frame.getChooseMenuItem().addActionListener(onChoose);

		/* Ϊ������������ActionListener */
		ActionListener onHelp = new HelpListener();
		frame.getHelpMenuItem().addActionListener(onHelp);

		/* Ϊѡ��ʼ���ʵĸ��ֲ�������ActionListener */
		ActionListener onFirst = new FirstListener();
		ActionListener onLast = new LastListener();
		ActionListener onAny = new AnyListener();
		frame.getFirstButton().addActionListener(onFirst);
		frame.getLastButton().addActionListener(onLast);
		frame.getAnyButton().addActionListener(onAny);

		/* Ϊ�ֶ�������ʼ���ʲ�������ActionListener */
		KeyAdapter onShowAlternative = new ShowAlternativeListener();
		ActionListener onSubmit = new SubmitListener();
		frame.getJTC().addKeyListener(onShowAlternative);
		frame.getSubmitButton().addActionListener(onSubmit);

		/* Ϊ�ύ��������Ŀ��������ActionListener */
		ActionListener onStart = new StartListener();
		frame.getStartButton().addActionListener(onStart);

		/* Ϊ��һ����������ActionListener */
		ActionListener onNext = new NextListener();
		frame.getNext().addActionListener(onNext);
	}

	private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class ChooseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter f = new FileNameExtensionFilter(
					"*.txt(�ı��ĵ�)", "txt");
			jfc.setFileFilter(f);
			int result = jfc.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				String myPath = jfc.getSelectedFile().getPath();
				r.initializeDictionary(myPath);
				frame.dictChosenView(r.getDictStatus());
			}
		}
	}

	private class HelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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

	private class AnyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.enterCustomWordView();
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (frame.getJtf().getText().matches("^[0-9]*$")
					&& Integer.parseInt(frame.getJtf().getText()) > 0) {
				if (!r.setReciteCount(Integer
						.parseInt(frame.getJtf().getText()))) {
					JOptionPane.showMessageDialog(null, "��Ŀ������Χ������Ϊֹ", "",
							JOptionPane.WARNING_MESSAGE);
				}
				frame.reciteView(r.showMeaningOfCurrentWord());
			} else {
				JOptionPane.showMessageDialog(null, "����������", "",
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
				JOptionPane.showMessageDialog(null, "���ʲ����ڣ��ӵ�һ����ʼ", "",
						JOptionPane.WARNING_MESSAGE);
			}
			frame.inputCounterView();
		}
	}

	private class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String spelling = frame.getJtf1().getText();
			if (!r.testMatching(spelling)) {
				JOptionPane.showMessageDialog(null, "��������Ϊֹ��������", "",
						JOptionPane.WARNING_MESSAGE);
			}
			String nextMeaning = r.showMeaningOfCurrentWord();
			if (nextMeaning != null)
				frame.reciteView(nextMeaning);
			else {
				r.updateToFile();
				DictionaryStatus recStatus = r.getRecitationStatus();
				JOptionPane.showMessageDialog(null,
						"��������\n����" + recStatus.getTotalLength() + "\n��ȷ��"
								+ recStatus.getCorrectCount() + "\n����"
								+ recStatus.getIncorrectCount() + "\n��ȷ��:"
								+ recStatus.getAccuracy(), "",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
	}

}