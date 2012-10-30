package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import clueGame.Card.cardType;

public class Player {
	private String name;
	public String lastVisited;
	private ArrayList<Card> cards;
	private Color color;
	private int startingIndex;
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
	public Card disproveSuggestion(Set<Card> guess) {
		return new Card("Datum",cardType.WEAPON);
	}
	public ArrayList<Card> makeSuggestion() {
		return new ArrayList<Card>();
	}
}
