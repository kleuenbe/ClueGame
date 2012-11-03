package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class WeaponPanel extends JPanel{
	private ArrayList<JCheckBox> list;
	private Set<String> item;
	private WeaponGuessPanel weaponGuess;
	
	public WeaponPanel(Board board, WeaponGuessPanel weaponGuess){
		this.weaponGuess = weaponGuess;
		item = new HashSet<String>();
		list = new ArrayList<JCheckBox>();
		setLayout(new GridLayout(3,2));
		addCheckBox(board);
		setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	}
	
	public void addCheckBox(Board board){
		ArrayList<Card> cards = board.getAllCards();
		for (Card i: cards){
			if (i.getType() == Card.cardType.WEAPON)
				list.add(new JCheckBox(i.getName()));
		}
		for (JCheckBox i: list){
			i.addActionListener(new CheckBoxListener());
			add(i);
			item.add(i.getText());
		}
		weaponGuess.updateGuess(item);
	}
	
	private class CheckBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			for(JCheckBox i: list)
				if (i.isSelected())
					item.remove(i.getText());
				else
					item.add(i.getText());	
			weaponGuess.updateGuess(item);
		}
	}
}
