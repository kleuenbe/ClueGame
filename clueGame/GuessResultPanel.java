package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessResultPanel extends JPanel{
	private JTextField display;
	private JLabel label;
	public GuessResultPanel(Card card){
		setLayout(new GridLayout(1,2));
		label = new JLabel("Response");
		add(label);
		display = new JTextField(card.getName());
		display.setEditable(false);
		add(display);
		setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
	}
	public void setDisplay(String display) {
		this.display.setText(display);
	}
}
