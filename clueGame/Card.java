package clueGame;

public class Card {
	private String name;
	public enum cardType {PERSON,WEAPON,ROOM};
	private cardType type;
	public Card(String name, cardType type) {
		this.name=name;
		this.type=type;
	}
	public Card(Card card) {
		name=card.getName();
		type=card.getType();		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public cardType getType() {
		return type;
	}
	public void setType(cardType type) {
		this.type = type;
	}
	@Override
	public boolean equals(Object o) {
		return true;
	}
}
