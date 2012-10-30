package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.Card.cardType;

public class HumanPlayer extends Player {
	public HumanPlayer(String name, int index, Color color) {
		super(name,index,color);
	}
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets){
		Scanner scan = new Scanner(System.in);
		boolean valid=false;
		int row;
		int col;
		do{
			System.out.print("Enter your target row: " );
			row = scan.nextInt();
			System.out.print("Enter your target column: ");
			col = scan.nextInt();
			for(BoardCell b:targets) {
				if(row==b.getRow() && col==b.getCol()) {
					valid=true;
					if(b.isDoorway()) {
						lastVisited=(RoomCell)b;
						lastVisitedName=((RoomCell)b).getInitial();
					}
					return b;
				}
			}
			if(!valid) System.out.println("Invalid target selection.");
		} while(valid!=true);
		return null;
	}
	public ArrayList<Card> createSuggestion(ArrayList<Card> allCards, Set<Card> seenCards, Map<Character,String> rooms){
		Scanner scan = new Scanner(System.in);
		boolean valid=false;
		String weapon, room, person;
		ArrayList<Card> sugg = new ArrayList<Card>();
		do{
			System.out.print("Enter weapon: " );
			weapon = scan.nextLine();
			System.out.print("Enter person: ");
			person = scan.nextLine();
			room = rooms.get(lastVisitedName);
			valid=true;
			sugg.add(new Card(weapon,cardType.WEAPON));
			sugg.add(new Card(person,cardType.PERSON));
			sugg.add(new Card(room,cardType.ROOM));
			if(!valid) System.out.println("Invalid target selection.");
		} while(valid!=true);
		return sugg;
	}
}
