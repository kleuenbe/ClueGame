package clueGame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameControlPanel extends JPanel{
	public GameControlPanel(Board board){
		setLayout(new GridLayout(2,1));
		add(new ButtonPanel(board));
		add(new GameInfoPanel(board));
	}
}
