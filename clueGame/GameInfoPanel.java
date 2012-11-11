package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameInfoPanel extends JPanel{
	public GameInfoPanel(Board board){
		add(new DiePanel(), BorderLayout.WEST);
		add(new GuessPanel(board.getHuman().getCards()), BorderLayout.CENTER);
		add(new GuessResultPanel(new Card("Default Card", Card.cardType.PERSON)), BorderLayout.EAST);
	}
}
