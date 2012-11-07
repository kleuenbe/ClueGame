package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	private String name;
	public RoomCell lastVisited;
	public char lastVisitedName;
	protected ArrayList<Card> cards;
	private Color color;
	private int startingIndex;
	public Player(String name, int index, Color color) {
		this.name = name;
		this.startingIndex = index;
		this.color = color;
		cards = new ArrayList<Card>();
	}
	public int getStart() {
		return startingIndex;
	}
	public Color getColor() {
		return color;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public Card disproveSuggestion(ArrayList<Card> guess) {
		ArrayList<Card> returns=new ArrayList<Card>();
		
		for(Card c:cards) {
			if(guess.indexOf(c) != -1) {
				returns.add(c);
			}
		}
		Random randomGen = new Random();
		if(returns.size()!=0) {
			return returns.get(randomGen.nextInt(returns.size()));
		} else {
			return null;
		}
	}
	/*public ArrayList<Card> makeSuggestion() {
		return new ArrayList<Card>();
	}*/
	public void addCard(Card c) {
		cards.add(c);
	}
	public abstract BoardCell pickLocation(Set<BoardCell> targets);
	
	static public int toPixel(int x){
		return x*WIDTH;
	}
	public void draw(Graphics g, Board board){
		g.setColor(color);
		g.fillOval(toPixel(this.getStart()%board.getNumColumns()), toPixel(this.getStart()/board.getNumColumns()), WIDTH, HEIGHT);
		
	}
}
