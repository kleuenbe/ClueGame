package clueGame;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WhoseTurnPanel extends JPanel{
	private JTextField display;
	private JLabel label;
	public WhoseTurnPanel(Player player){
		label = new JLabel("Whose turn? ");
		add(label);
		display = new JTextField(9);
		display.setText(player.getName());
		display.setEditable(false);
		add(display);
	}
	public void setDisplay(String display) {
		this.display.setText(display);
	}
}
