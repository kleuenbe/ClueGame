package clueGame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameControlPanel extends JPanel{
	private ButtonPanel bp;
	private GameInfoPanel gip;
	public GameControlPanel(Board board){
		gip=new GameInfoPanel(board);
		bp=new ButtonPanel(board,gip);		
		setLayout(new GridLayout(2,1));
		add(bp);
		add(gip);
	}
}
