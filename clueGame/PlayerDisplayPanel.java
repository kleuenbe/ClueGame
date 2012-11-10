package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PlayerDisplayPanel extends JPanel {
	public PlayerDisplayPanel(ArrayList<Card> cards){
		setLayout(new GridLayout(4, 1));
		add(new JLabel("My Cards"));
		for (Card i: cards)
			add(new DisplayCardPanel(i));
	}
}
