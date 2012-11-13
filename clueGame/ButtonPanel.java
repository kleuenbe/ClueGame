package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	private JButton next;
	private JButton makeAccusation;
	private WhoseTurnPanel whosTurn;
	private int player;	// -1 for human, otherwise index of players ArrayList for computer players
	public ButtonPanel(Board board,GameInfoPanel gip){
		next = new JButton("Next Player");
		makeAccusation = new JButton("Make an Accusation");
		setLayout(new GridLayout(1,3));
		player=-1;
		whosTurn=new WhoseTurnPanel(board.getHuman());
		add(whosTurn);
		add(next);
		add(makeAccusation);
		ButtonListener listener = new ButtonListener(board,gip);
		next.addActionListener(listener);
		makeAccusation.addActionListener(listener);
	}
	
	class ButtonListener implements ActionListener{
		private Board board;
		private GameInfoPanel gip;
		public ButtonListener(Board board,GameInfoPanel gip) {
			super();
			this.board=board;
			this.gip=gip;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==next) {
				player++;
				if(player<board.getPlayers().size()) {
					ComputerPlayer p = (ComputerPlayer)board.getPlayers().get(player);
					whosTurn.setDisplay(p.getName());
					gip.getDp().setDisplay();
					p.makeMove(board, gip.getDp().getNumber());
					
				} else {
					player=-1;
					whosTurn.setDisplay(board.getHuman().getName());
					gip.getDp().setDisplay();
				}
			} else {
				// Make accusation
			}
		}			
	}
}
