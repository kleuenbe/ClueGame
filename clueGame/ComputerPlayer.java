package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

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
	public void makeMove(Board board, GameInfoPanel gip) {
		if(accuse) {
			if(!board.checkAccusation(accuseSet)) {
				accuse=false;
				JOptionPane.showMessageDialog(null, "The Computer made an incorrect accusation of "+ this.getAccused(accuseSet), "Accusation", JOptionPane.INFORMATION_MESSAGE);
				accuseSet.removeAll(accuseSet);
			} else {
				JOptionPane.showMessageDialog(null, "Computer wins!"+'\n'+"The Computer made an correct accusation of "+ this.getAccused(accuseSet), "Accusation", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		} else {
			board.calcTargets(startingIndex,gip.getDp().getNumber());
			BoardCell newLoc=pickLocation(board.getTargets());
			startingIndex=board.calcIndex(newLoc.getRow(), newLoc.getCol());
			board.repaint();
			if(newLoc instanceof RoomCell) {
				ArrayList<Card> suggestion=createSuggestion(board.getAllCards(),board.getSeenCards(),board.getRooms());
				Card disprove=board.handleSuggestion(suggestion, this);				
				for(Card c:suggestion) {
					if(c.getType()==cardType.PERSON) {
						ArrayList<Player> players = new ArrayList<Player>(board.getPlayers());
						players.add(board.getHuman());
						for(Player p:players) {
							if(p.getName().equalsIgnoreCase(c.getName())) {
								p.setStart(this.startingIndex);
								board.repaint();
							}
						}
					}
				}
				if(disprove==null) {
					accuse=true;
					accuseSet.addAll(suggestion);
					gip.getGrp().setDisplay("No new clue");
				} else {
					gip.getGrp().setDisplay(disprove.getName());
				}
				gip.getGp().setDisplay(this.getSuggested(suggestion));				
			}
		}
	}

	public String getSuggested(ArrayList<Card> suggested) {
		String suggest="";
		for(Card c:suggested) {
			suggest = suggest + c.getName() + " ";
		}
		return suggest;
	}
	public String getAccused(Set<Card> accused) {
		String accuse="";
		for(Card c:accused) {
			accuse = accuse +c.getName() + " ";
		}
		return accuse;
	}
}
