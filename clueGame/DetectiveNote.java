package clueGame;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class DetectiveNote extends JFrame {
	
	public DetectiveNote(Board board){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Detective Notes");
		setSize(800, 500);
		setLayout(new GridLayout(3,2));
		addFeatures(board);
	}
	
	public void addFeatures(Board board){
		PersonGuessPanel personGuess = new PersonGuessPanel();
		PeoplePanel people = new PeoplePanel(board, personGuess);
		RoomGuessPanel roomGuess = new RoomGuessPanel();
		RoomPanel room = new RoomPanel(board, roomGuess);
		WeaponGuessPanel weaponGuess = new WeaponGuessPanel();
		WeaponPanel weapon = new WeaponPanel(board, weaponGuess);
		add(people);
		add(personGuess);
		add(room);
		add(roomGuess);
		add(weapon);
		add(weaponGuess);
	}
	
	public static void main(String[] args) {
		DetectiveNote board = new DetectiveNote(new Board("Legend", "BoardLayout.csv", "PlayerFile", "CardFile"));
		board.setVisible(true);
	}
}
