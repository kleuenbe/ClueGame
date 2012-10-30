package BoardTests;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.cardType;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;
	@BeforeClass
	public static void setUp(){
		board = new Board("Legend","BoardLayout.csv","PlayerFile","CardFile");
	}

	@Test
	public void testPlayerLoad() {
		//Make sure there are 6 players
		Assert.assertEquals(board.getPlayers().size(), 6);
		//Test a few player names
		Assert.assertEquals(board.getPlayers().get(0).getName(), "Captain Jirk");
		Assert.assertEquals(board.getPlayers().get(1).getName(), "Datum");
		Assert.assertEquals(board.getPlayers().get(5).getName(), "Lieutenant Dwarf");
		//Test player colors
		Assert.assertEquals(board.getPlayers().get(0).getColor(), Color.yellow);
		Assert.assertEquals(board.getPlayers().get(1).getColor(), Color.cyan);
		Assert.assertEquals(board.getPlayers().get(5).getColor(), Color.red);
		//Test player starting locations
		Assert.assertEquals(board.getPlayers().get(0).getStart(), board.calcIndex(33, 2));
		Assert.assertEquals(board.getPlayers().get(1).getStart(), board.calcIndex(3, 6));
		Assert.assertEquals(board.getPlayers().get(5).getStart(), board.calcIndex(22,0));
	}
	
	@Test
	public void testCardLoad() {
		Assert.assertEquals(board.getCards().size(), 27);	// 6 players, 11 rooms, 10 weapons
		
		int numWeapons=0;
		int numPlayers=0;
		int numRooms=0;
		for(Card c:board.getCards()) {
			switch(c.getType()) {
			case ROOM: numRooms++; break;
			case WEAPON: numWeapons++; break;
			case PERSON: numPlayers++; break;
			}
		}
		Assert.assertEquals(numWeapons, 6);
		Assert.assertEquals(numPlayers, 6);
		Assert.assertEquals(numRooms, 9);
		//Testing that some certain cards were loaded
		Assert.assertTrue(board.getCards().contains(new Card("Datum", cardType.PERSON)));
		Assert.assertTrue(board.getCards().contains(new Card("Kirk's Fists", cardType.WEAPON)));
		Assert.assertTrue(board.getCards().contains(new Card("Bridge", cardType.ROOM)));
	}
	
	@Test
	public void testCardDeal() {
		//Tests that all cards were dealt and all players have 4 cards
		Assert.assertEquals(board.getSolution().size(), 3);
		for(int i=0; i<6; i++) {
			Assert.assertEquals(board.getPlayers().get(i).getCards().size(),4);
		}
		//Test to make sure all cards are dealt
		Set<Card> dealtCards = new HashSet<Card>();
		for(Player p: board.getPlayers()) {
			for(Card c:p.getCards()) {
				dealtCards.add(c);
			}
		}
		Assert.assertEquals(dealtCards.size()+board.getSolution().size(), board.getCards().size());
	}
}
