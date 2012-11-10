package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessPanel extends JPanel{
	private JTextField display;
	private JLabel label;
	public GuessPanel(ArrayList<Card> cards){
		setLayout(new GridLayout(2,1));
		label = new JLabel("Guess");
		add(label);
		String allCards = "";
		for (Card i : cards)
			allCards = allCards + i.getName() + " ";
		display = new JTextField(allCards);
		display.setEditable(false);
		add(display);
		setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
	}
}
