package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	private JButton next;
	private JButton makeAccusation;
	public ButtonPanel(Board board){
		next = new JButton("Next Player");
		makeAccusation = new JButton("Make an Accusation");
		setLayout(new GridLayout(1,3));
		add(new WhoseTurnPanel(board.getHuman()));
		add(next);
		add(makeAccusation);
	}
}
