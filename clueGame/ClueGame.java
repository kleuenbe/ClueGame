package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame {
	private Board board;
	private DetectiveNote note;
	
	public ClueGame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("ClueGame");
		setSize(500, 500);
		addFeatures();
		setVisible(true);
	}
	
	public void addFeatures(){
		String layoutFile = "BoardLayout.csv";
		String legendFile = "Legend";
		String playerFile = "PlayerFile";
		String cardFile = "CardFile";
		board = new Board(legendFile, layoutFile, playerFile, cardFile);
		note = new DetectiveNote(board);
		add(board, BorderLayout.CENTER);
		
		//	File menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	public JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		//	Detective note option
			JMenuItem noteItem = new JMenuItem("Detective Notes");
			class NoteItemListener implements ActionListener {
				public void actionPerformed(ActionEvent e)
				{
					note.setVisible(true);
				}
			}
			noteItem.addActionListener(new NoteItemListener());
			menu.add(noteItem);
		//	Exit option
		JMenuItem exitItem = new JMenuItem("Exit");
		class ExitItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		exitItem.addActionListener(new ExitItemListener());
		menu.add(exitItem);
		return menu;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
}