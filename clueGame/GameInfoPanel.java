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
		gp=new GuessPanel();
		grp=new GuessResultPanel();
		add(dp, BorderLayout.WEST);
		add(gp, BorderLayout.CENTER);
		add(grp, BorderLayout.EAST);
	}
	public GuessPanel getGp() {
		return gp;
	}
	public GuessResultPanel getGrp() {
		return grp;
	}
	public DiePanel getDp() {
		return dp;
	}
}
