package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
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
		player=board.getPlayers().size();
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
					p.makeMove(board, gip);					
				} else {
					player=-1;
					whosTurn.setDisplay(board.getHuman().getName());
					gip.getDp().setDisplay();
					board.highlightTargets(gip.getDp().getNumber());
					boolean turnEnd = false;
					MListen mlistener = new MListen(board,board.getHuman());
					board.addMouseListener(mlistener);
					
				}
			} else {
				// Human make accusation
			}
		}			
	}
	public class MListen implements MouseListener {
		private Board board;
		private Player player;
		public static final int WIDTH = 25;
		public static final int HEIGHT = 25;
		public MListen(Board board, Player player) {
			super();
			this.board=board;
			this.player=player;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			int x=e.getX()/WIDTH;
			int y=e.getY()/HEIGHT;
			int index=board.calcIndex(y, x);
			BoardCell clicked = board.getCellAt(index);
			ArrayList<BoardCell> targets=new ArrayList<BoardCell>();
			targets.addAll(board.getTargets());
			if(targets.contains(clicked)) {
				player.setStart(index);
				board.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Please select a valid location highlighted in cyan.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
}
