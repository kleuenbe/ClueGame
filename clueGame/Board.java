package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Card.cardType;
import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel{
	private ArrayList<BoardCell> cells;
	public Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private Map<Integer, LinkedList<Integer>> adjLST;
	private boolean[] seen;
	private Set<BoardCell> targets;
	public Set<Card> getSolution() {
		return solution;
	}
	private ArrayList<Player> players;
	private HumanPlayer human;
	public HumanPlayer getHuman() {
		return human;
	}
	private ArrayList<Card> cards;
	private ArrayList<Card> allCards;
	private Set<Card> seenCards;
	public Set<Card> getSeenCards() {
		return seenCards;
	}
	private Set<Card> solution;
	private int whosTurn;
	
	public Board(String legend, String layout, String playerLoc, String cardFile){
		//cant load players due to test right now
		players = new ArrayList<Player>();
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		cards = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
		solution = new HashSet<Card>();
		loadConfigFiles(legend,layout,playerLoc,cardFile);
		adjLST = new HashMap<Integer, LinkedList<Integer>>();
		this.calcAdjacencies();
		//allCards=cards;
		selectSolution();
		deal();
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}

	public void loadConfigFiles(String legend, String layout, String playerLoc, String cardFile){
		Scanner in = null;
		try { 
			FileReader reader = new FileReader(layout);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("Layout file not found!");
		}
		try {
			String s1;
			int col = 0;
			int row = 0;
			numRows = 0;
			if(in.hasNext()) {
				s1=in.nextLine();
				String[] temp = s1.split(",");
				numColumns=temp.length;
				for(String s:temp) {
					if(s.equalsIgnoreCase("W")) {
						cells.add(new WalkwayCell(row, col));
					}  else {
						BoardCell temp2 = new RoomCell(s, row, col);
						cells.add(temp2);
					}
					col++;
				}
				row++;
			}
			while(in.hasNext()) {
				s1 = in.nextLine();
				String[] temp = s1.split(",");
				if(numColumns != temp.length) {
					throw new BadConfigFormatException("The number of rows aren't the same in each column");
				}
				col = 0;
				for(int i = 0; i < temp.length; ++ i) {
					if(temp[i].equalsIgnoreCase("W")) {
						cells.add(new WalkwayCell(row, col));
					}  else {
						BoardCell temp2 = new RoomCell(temp[i], row, col);
						cells.add(temp2);
					}
					col ++;
				}
				row ++;
			}
			numRows = row;
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		Scanner in2 = null;
		try {
			FileReader reader2 = new FileReader(legend);
			in2 = new Scanner(reader2);
		} catch (FileNotFoundException e) {
			System.out.println("Legend file not found!");
		}
		try {
			String s2;
			while(in2.hasNext()) {
				char r;
				s2 = in2.nextLine();
				String[] temp = s2.split(",");
				if(!(temp.length == 2||temp.length ==4)) {	// Closet and walkway have 2 entries because we don't print names for those
					throw new BadConfigFormatException("Input from room file is too long");
				}
				r = temp[0].trim().charAt(0);
				rooms.put(r, temp[1].trim());
				//3rd and 4th entries are row and cell of which room cell should print out the name of the room
				if(temp.length==4) {
					getRoomCellAt(Integer.parseInt(temp[2].trim()),Integer.parseInt(temp[3].trim())).setTitle(temp[1].trim());
				}
			}
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		Scanner in3 = null;
		try { 
			FileReader reader3 = new FileReader(playerLoc);
			in3 = new Scanner(reader3);
		} catch (FileNotFoundException e) {
			System.out.println("Player file not found!");
		}
		try {
			String s3;
			int col = 0;
			int row = 0;
			String name;
			Color color;
			while(in3.hasNext()) {
				s3=in3.nextLine();
				String[] temp = s3.split(",");
				if(temp.length != 4) {
					throw new BadConfigFormatException("There are not the correct number of attributes for loading players");
				}
				name = temp[0].trim();
				row = Integer.parseInt(temp[1].trim());
				col = Integer.parseInt(temp[2].trim());
				color = convertColor(temp[3].trim());
				if (human != null)
					players.add(new ComputerPlayer(name,calcIndex(row,col),color));
				else 
					human = new HumanPlayer(name,calcIndex(row,col),color);
			}
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		Scanner in4 = null;
		try { 
			FileReader reader4 = new FileReader(cardFile);
			in4 = new Scanner(reader4);
		} catch (FileNotFoundException e) {
			System.out.println("Card file not found!");
		}
		try {
			String s4;
			String cardName;
			String cType;
			while(in4.hasNext()) {
				s4=in4.nextLine();
				String[] temp = s4.split(",");
				if(temp.length != 2) {
					throw new BadConfigFormatException("There are not the correct number of attributes for loading cards");
				}
				cType = temp[0].trim();
				cardName = temp[1].trim();
				
				cards.add(new Card(cardName,convertType(cType)));
			}
			
			allCards= new ArrayList<Card>(cards);
			/*for(Card c:allCards) {
				System.out.println(c.getType());
			}*/
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public cardType convertType(String cType) {
		switch(cType) {
		case "Weapon": return cardType.WEAPON;
		case "Person": return cardType.PERSON;
		case "Room": return cardType.ROOM;
		default: return null;
		}
	}
	
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	
	public int calcIndex(int row, int col) {
		return (row*numColumns) + col;
	}
	
	public BoardCell getCellAt(int vertex) {
		return cells.get(vertex);
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		int vertex = calcIndex(row, col);
		if(cells.get(vertex).isRoom()) {
			BoardCell cell = cells.get(vertex);
			return (RoomCell) cell;
		} else {
			return null;
		}
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		LinkedList<Integer> a = new LinkedList<Integer> (adjLST.get(index));
		return a;
	}
	
	public void calcTargets(int vertex, int steps) {
		int start = vertex;
		targets = new HashSet<BoardCell>();
		seen = new boolean[numRows*numColumns];
		this.setSeen();
		seen[start]=true;
		LinkedList<Integer> path = new LinkedList<Integer>();
		this.recurseTargets(start, path, steps);
	}
	
	private void setSeen(){
		for (int i=0; i<numRows*numColumns; i++){
			seen[i]=false;
		}
	}
	
	private void recurseTargets(int target, LinkedList<Integer> path, int steps){
		LinkedList<Integer> tempAdj=new LinkedList<Integer>();
		ListIterator<Integer> itr = this.getAdjList(target).listIterator();
		while (itr.hasNext()){
			int next=itr.next();
			if (seen[next]==false){
				tempAdj.add(next);
			}
		}
		ListIterator<Integer> itrAdj = tempAdj.listIterator();
		while (itrAdj.hasNext()){
			int nextNode=itrAdj.next();
			seen[nextNode]=true;
			path.push(nextNode);
			if (path.size() == steps){
				targets.add(cells.get(nextNode));
			} else if (cells.get(nextNode).isDoorway()) {
				targets.add(cells.get(nextNode));
			} else {
				recurseTargets(nextNode, path, steps);
			}
			path.removeLast();
			seen[nextNode]=false;
		}
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> a = new HashSet<BoardCell>(targets);
		return a;
	}
	
	public void calcAdjacencies() {
		LinkedList<Integer> tempList = new LinkedList<Integer>();
		for (int i=0; i<numRows; i++){
			for (int j=0; j<numColumns; j++){
				if(cells.get(calcIndex(i, j)).isWalkway()) {
					if (i-1>=0){
						if(cells.get(calcIndex(i - 1, j)).isWalkway()) {
							tempList.addLast(this.calcIndex(i-1, j));
						}  else if(cells.get(calcIndex(i -1, j)).isDoorway()){
							 RoomCell a = (RoomCell)cells.get(calcIndex(i - 1, j));
							 if(a.getDoorDirection() == DoorDirection.DOWN) {
								 tempList.addLast(this.calcIndex(i -1, j));
							 }
						 }
					} 
					if (i+1<numRows){
						if(cells.get(calcIndex(i + 1, j)).isWalkway()) {
							tempList.addLast(this.calcIndex(i+1, j));
						} else if(cells.get(calcIndex(i + 1, j)).isDoorway()){
							 RoomCell a = (RoomCell)cells.get(calcIndex(i + 1, j));
							 if(a.getDoorDirection() == DoorDirection.UP) {
								 tempList.addLast(this.calcIndex(i + 1, j));
							 }
						 }
					}
					if (j-1>=0){
						 if(cells.get(calcIndex(i, j - 1)).isWalkway()) {
							tempList.addLast(this.calcIndex(i, j-1));
						 } else if(cells.get(calcIndex(i, j - 1)).isDoorway()){
							 RoomCell a = (RoomCell)cells.get(calcIndex(i, j - 1));
							 if(a.getDoorDirection() == DoorDirection.RIGHT) {
								 tempList.addLast(this.calcIndex(i, j-1));
							 }
						 }
					}
					if (j+1<numColumns){
						 if(cells.get(calcIndex(i, j + 1)).isWalkway()) {
							tempList.addLast(this.calcIndex(i, j+1));
						 } else if(cells.get(calcIndex(i, j + 1)).isDoorway()){
							 RoomCell a = (RoomCell)cells.get(calcIndex(i, j + 1));
							 if(a.getDoorDirection() == DoorDirection.LEFT) {
								 tempList.addLast(this.calcIndex(i, j+1));
							 }
						 }
					}
				} else if((cells.get(calcIndex(i, j)).isRoom()) && (cells.get(calcIndex(i, j)).isDoorway())) {
						RoomCell a = (RoomCell)cells.get(calcIndex(i, j));
						if(cells.get(calcIndex(i, j)).isDoorway()) {
							if(a.getDoorDirection() == DoorDirection.RIGHT) {
								tempList.addLast(this.calcIndex(i, j + 1));
							}
							if(a.getDoorDirection() == DoorDirection.LEFT) {
								tempList.addLast(this.calcIndex(i, j - 1));
							}
							if(a.getDoorDirection() == DoorDirection.UP) {
								tempList.addLast(this.calcIndex(i - 1, j));
							}
							if(a.getDoorDirection() == DoorDirection.DOWN) {
								tempList.addLast(this.calcIndex(i + 1, j));
							}
						}
					} else {
						adjLST.put(this.calcIndex(i,j), tempList);
					}
				for (Integer k : tempList){
						adjLST.put(this.calcIndex(i, j), tempList);
				}
				tempList = new LinkedList<Integer>();
			}
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	public void selectSolution() {
		Random randomGen = new Random();
		while(true) {
			int tempIndex=randomGen.nextInt(cards.size());
			if(cards.get(tempIndex).getType()==cardType.ROOM) {
				solution.add(cards.get(tempIndex));
				cards.remove(tempIndex);
				//System.out.println("Ere");
				break;
			}
		}
		while(true) {
			int tempIndex=randomGen.nextInt(cards.size());
			if(cards.get(tempIndex).getType()==cardType.WEAPON) {
				solution.add(cards.get(tempIndex));
				cards.remove(tempIndex);
				//System.out.println("Ere");
				break;
			}
		}
		while(true) {
			int tempIndex=randomGen.nextInt(cards.size());
			if(cards.get(tempIndex).getType()==cardType.PERSON) {
				solution.add(cards.get(tempIndex));
				cards.remove(tempIndex);
			//	System.out.println("Ere");
				break;
			}
		}
	}
	/*public void deal(ArrayList<String> cardlist) {
		
	}*/
	
	public void deal() {
		Random randomGen = new Random();
		ArrayList<Card> undealtCards = new ArrayList<Card>(cards);
		int numCardPerPlayer = undealtCards.size()/players.size();
		for(int k = 0; k < players.size(); k++){
			//System.out.println(undealtCards.size()/players.size());
			for(int i = 0; i < numCardPerPlayer; i++){
				int tempInd = randomGen.nextInt(undealtCards.size());
				players.get(k).addCard(undealtCards.get(tempInd));
				undealtCards.remove(tempInd);
			}
		}
		for (Card i: undealtCards)
			human.addCard(i);
	}
	
	public boolean checkAccusation(Set<Card> guess) {
		ArrayList<Card> guessList = new ArrayList<Card>();
		guessList.addAll(guess);
		ArrayList<Card> solutionList = new ArrayList<Card>();
		solutionList.addAll(solution);
		if(guessList.size() != 3){
		  	return false;
		}else{
			for(Card c : guessList){
			  if(!solutionList.contains(c)){
				  return false;
			  }
			  
		  }
		  return true;
		}		 
	}
	public Card handleSuggestion(ArrayList<Card> guess,Player suggester) {
		Random randomGen = new Random();
		ArrayList<Player> tempPlayers = new ArrayList<Player>(players);
		tempPlayers.add(human);
		tempPlayers.remove(suggester);
		int index = randomGen.nextInt(tempPlayers.size());
		while(!tempPlayers.isEmpty()) {			
			Player tempPlayer = tempPlayers.get(index);
			Card disproved = tempPlayer.disproveSuggestion(guess);
			
			if(disproved!=null) {
				//System.out.println(disproved.getName());
				seenCards.add(disproved);
				return new Card(disproved);
			} else {
				tempPlayers.remove(tempPlayer);
				if(tempPlayers.size() == 0){
					break;
				}else{	
					index=(index)%tempPlayers.size();
				}
			}
		}
		return null;
	}
	public void addPlayers(Player p){
		players.add(p);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		for (BoardCell cell: cells)
			cell.draw(g, this,false);
		for(Player p:players) {
			p.draw(g, this);
		}
		human.draw(g, this);
	}
	public void highlightTargets(int roll) {
		calcTargets(human.getStart(),roll);
		for(BoardCell cell:targets) {
			cell.draw(getGraphics(), this, true);
		}
	}	
	public class MListen implements MouseListener {
		private Player player;
		private boolean turnEnd;
		public static final int WIDTH = 25;
		public static final int HEIGHT = 25;
		public MListen(Player player,Boolean turnEnd) {
			super();
			this.player=player;
			this.turnEnd=turnEnd;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			int x=e.getX()/WIDTH;
			int y=e.getY()/HEIGHT;
			int index=calcIndex(y, x);
			BoardCell clicked = getCellAt(index);
			ArrayList<BoardCell> targets=new ArrayList<BoardCell>();
			targets.addAll(getTargets());
			if(!turnEnd) {
				if(targets.contains(clicked)) {
					player.setStart(index);
					System.out.println(y+" "+x);
					repaint();
					turnEnd=true;
				} else {
					JOptionPane.showMessageDialog(null, "Please select a valid location highlighted in cyan.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
				}
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
