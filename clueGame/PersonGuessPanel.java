package clueGame;

import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PersonGuessPanel extends JPanel{
	private JComboBox guess;
	
	public PersonGuessPanel(){
		guess = new JComboBox();
		add(guess);
		setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
	}
	
	public void updateGuess(Set<String> item){
		guess.removeAllItems();
		for (String i: item)
			guess.addItem(i);
		guess.addItem("Unsure");
	}
}
