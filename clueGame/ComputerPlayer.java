package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import clueGame.Card.cardType;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, int index, Color color) {
		super(name,index,color);
	}
	/*public BoardCell pickLocation(Set<BoardCell> target) {
		return new WalkwayCell(8,10);
	}*/
	@Override
	public BoardCell pickLocation(Set<BoardCell> t){
		BoardCell[] targets = t.toArray(new BoardCell[0]);
		Random randomGen = new Random();
		ArrayList<Integer> doorWays = new ArrayList<Integer>();
		int tempCounter = 0;
		for(BoardCell b : targets){
			if(b.isDoorway() && !((RoomCell)b).equals(lastVisited)){
				doorWays.add(tempCounter);
			}
			tempCounter++;
		}
		if(doorWays.size() == 0){
			return targets[randomGen.nextInt(targets.length)];
		}else{
			RoomCell tempBoardCell = (RoomCell)targets[doorWays.get(randomGen.nextInt(doorWays.size()))];
			lastVisitedName=tempBoardCell.getRoomInitial();
			lastVisited = tempBoardCell;
			return targets[doorWays.get(randomGen.nextInt(doorWays.size()))];//targets[doorWays.get(randomGen.nextInt(doorWays.size()))];
		}
	}
	public ArrayList<Card> createSuggestion(ArrayList<Card> allCards, Set<Card> seenCards, Map<Character,String> rooms){
		ArrayList<Card> sugg = new ArrayList<Card>();
		sugg.add(new Card(rooms.get(lastVisitedName),cardType.ROOM));
		//get a weapon
		Card tempCard = null;
		int overFlow = 0; 
		do{
			tempCard = new Card(getRandomCard(cardType.WEAPON,allCards));
			overFlow++;
		}while(!seenCards.contains(tempCard)&&overFlow <= allCards.size()&&!(cards.contains(tempCard)));
		overFlow = 0;
		
		sugg.add(tempCard);
		tempCard = null;
		do{
			tempCard = new Card(getRandomCard(cardType.PERSON,allCards));
			overFlow++;
		}while(!seenCards.contains(tempCard)&&overFlow <= allCards.size()&&!(cards.contains(tempCard)));
		sugg.add(tempCard);
		return sugg;

	}
	public Card getRandomCard(cardType type, ArrayList<Card> fromWhat){
		Random randomGen = new Random();
		while(true){
			int tempIndex = randomGen.nextInt(fromWhat.size());
			if(fromWhat.get(tempIndex).getType() == type){
				return fromWhat.get(tempIndex);
			}
		}
	}
}
