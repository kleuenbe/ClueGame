package clueGame;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DisplayCardPanel extends JPanel{
	private JTextField display;
	public DisplayCardPanel(Card card){
		display = new JTextField(10);
		display.setText(card.getName());
		display.setEditable(false);
		add(display);
		setBorder(new TitledBorder (new EtchedBorder(), card.getType().toString()));
	}
}
