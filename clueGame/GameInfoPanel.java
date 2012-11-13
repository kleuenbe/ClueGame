package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameInfoPanel extends JPanel{
	private DiePanel dp;
	private GuessPanel gp;
	private GuessResultPanel grp;
	public GameInfoPanel(Board board){
		dp=new DiePanel();
		gp=new GuessPanel(board.getHuman().getCards());
		grp=new GuessResultPanel(new Card("Default Card", Card.cardType.PERSON));
		add(dp, BorderLayout.WEST);
		add(gp, BorderLayout.CENTER);
		add(grp, BorderLayout.EAST);
	}
	public DiePanel getDp() {
		return dp;
	}
}
