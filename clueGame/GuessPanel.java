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
	public GuessPanel(){
		setLayout(new GridLayout(2,1));
		label = new JLabel("Guess");
		add(label);
		display = new JTextField(21);
		display.setEditable(false);
		add(display);
		setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
	}
	public void setDisplay(String display) {
		this.display.setText(display);
	}
}
