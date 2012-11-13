package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import clueGame.Card.cardType;

public class ComputerPlayer extends Player {
	private boolean accuse;
	private Set<Card> accuseSet;
	public ComputerPlayer(String name, int index, Color color) {
		super(name,index,color);
		accuse=false;
		accuseSet = new HashSet<Card>();
	}
	
	@Override
	public BoardCell pickLocation(Set<BoardCell> t){
		BoardCell[] targets = t.toArray(new BoardCell[0]);
		Random randomGen = new Random();
		ArrayList<Integer> doorWays = new ArrayList<Integer>();
		int tempCounter = 0;
		
		for(BoardCell b : targets){
			if(b.isDoorway() && ((RoomCell)b).getRoomInitial()!=lastVisitedName){
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
			return targets[doorWays.get(randomGen.nextInt(doorWays.size()))];
		}
	}
	public ArrayList<Card> createSuggestion(ArrayList<Card> allCards, Set<Card> seenCards, Map<Character,String> rooms){
		ArrayList<Card> sugg = new ArrayList<Card>();
		sugg.add(new Card(rooms.get(lastVisitedName),cardType.ROOM));
		//get a weapon
		Card tempCard = null;
		int overFlow = 0; 
		while(true){
			/*if(overFlow == allCards.size()*2){	// Every now and then, make a misleading suggestion to trick other players
				Card misleadingCard = getRandomCard(cardType.WEAPON,cards);
				if(misleadingCard != null){
					tempCard = misleadingCard;					
					break;					
				}
			}*/
			tempCard = getRandomCard(cardType.WEAPON,allCards);
			overFlow++;
			for(Card c:cards) {
				
			}
			//System.out.println(cards.size());
			if(!seenCards.contains(tempCard)&&!cards.contains(tempCard)){
				break;
			}
		//overFlow = 0;
		}
		sugg.add(new Card(tempCard));
		tempCard = null;
		while(true){
			/*if(overFlow == allCards.size()*2){	// Every now and then, make a misleading suggestion to trick other players
				Card misleadingCard = getRandomCard(cardType.WEAPON,cards);
				if(misleadingCard != null){
					tempCard = misleadingCard;					
					break;					
				}
			}*/
			tempCard = getRandomCard(cardType.PERSON,allCards);
			overFlow++;
			
			if(!seenCards.contains(tempCard)&&!cards.contains(tempCard)){
				break;
			}
		//overFlow = 0;
		}
		sugg.add(new Card(tempCard));
		return sugg;

	}
	public Card getRandomCard(cardType type, ArrayList<Card> fromWhat){
		Random randomGen = new Random();
		if(!hasCardType(type,fromWhat)){
			return null;
		}
		while(true){
			int tempIndex = randomGen.nextInt(fromWhat.size());
			if(fromWhat.get(tempIndex).getType() == type){
				return fromWhat.get(tempIndex);
			}
		}
	}
	public boolean hasCardType(cardType type, ArrayList<Card> what){
		for(Card c : what){
			if(c.getType() == type){
				return true;
			}
		}
		return false;
	}
	public boolean makeMove(Board board, int roll) {
		boolean win=false;
		if(accuse) {
			if(!board.checkAccusation(accuseSet)) {
				accuse=false;
			} else {
				win=true;
				return win;
			}
		} else {
			board.calcTargets(startingIndex,roll);
			BoardCell newLoc=pickLocation(board.getTargets());
			startingIndex=board.calcIndex(newLoc.getRow(), newLoc.getCol());
			board.repaint();
			if(newLoc instanceof RoomCell) {
				ArrayList<Card> suggestion=createSuggestion(board.getAllCards(),board.getSeenCards(),board.getRooms());
				Card disprove=board.handleSuggestion(suggestion, this);
				if(disprove==null) {
					accuse=true;
					accuseSet.addAll(suggestion);
				}
			}
		}
		return win;
	}
}
