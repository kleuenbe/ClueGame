package clueGame;

import java.io.*;
import java.util.*;

import clueGame.Card.cardType;
import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private Map<Integer, LinkedList<Integer>> adjLST;
	private boolean[] seen;
	private Set<BoardCell> targets;
	public Set<Card> getSolution() {
		return solution;
	}
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	private ArrayList<Card> seenCards;
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	private Set<Card> solution;
	private int whosTurn;
	
	public Board(String legend, String layout){
		//cant load players due to test right now
		players = new ArrayList<Player>();
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		loadConfigFiles(legend,layout);
		adjLST = new HashMap<Integer, LinkedList<Integer>>();
		this.calcAdjacencies();
		//solution=new HashSet<Card>();
	}
	
	public void loadConfigFiles(String legend, String layout){
		Scanner in = null;
		try {
			FileReader reader = new FileReader(legend);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		try {
			String s;
			while(in.hasNext()) {
				char r;
				s = in.nextLine();
				String[] temp = s.split(",");
				if(temp.length > 2) {
					throw new BadConfigFormatException("Input from room file is too long");
				}
				r = temp[0].trim().charAt(0);
				rooms.put(r, temp[1].trim());
			}
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		Scanner in2 = null;
		try { 
			FileReader reader2 = new FileReader(layout);
			in2 = new Scanner(reader2);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		try {
			String s2;
			int col = 0;
			int row = 0;
			numRows = 0;
			if(in2.hasNext()) {
				s2=in2.nextLine();
				String[] temp = s2.split(",");
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
			while(in2.hasNext()) {
				s2 = in2.nextLine();
				String[] temp = s2.split(",");
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

	public ArrayList<Card> getCards() {
		return cards;
	}
	public void selectAnswer() {
		
	}
	public void deal(ArrayList<String> cardlist) {
		
	}
	public void deal() {
		
	}
	public boolean checkAccusation(Set<Card> guess) {
		/*if(guess.size() != 3){
		  	return false;
		}else{
		  for(Card c : guess){
			  if(!solution.contains(c)){
				  return false;
			  }
		  }
		  return true;
		}*/
		return false;		 
	}
	public Card handleSuggestion(ArrayList<Card> guess,Player suggester) {
		return new Card("blater", cardType.WEAPON);
	}
	public void addPlayers(Player p){
		players.add(p);
	}
}
