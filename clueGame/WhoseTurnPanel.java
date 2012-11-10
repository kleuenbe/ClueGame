package clueGame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class WhoseTurnPanel extends JPanel{
	private JTextField display;
	private JLabel label;
	public WhoseTurnPanel(Player player){
		label = new JLabel("Whose turn? ");
		add(label);
		display = new JTextField(player.getName());
		display.setEditable(false);
		add(display);
	}
}
