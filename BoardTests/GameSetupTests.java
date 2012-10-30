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
		
		Assert.assertEquals(board.getPlayers().get(3).getName(), "Captain Jirk");
		Assert.assertEquals(board.getPlayers().get(0).getName(), "Datum");
		Assert.assertEquals(board.getPlayers().get(1).getName(), "Lieutenant Dwarf");
		//Test player colors
		Assert.assertEquals(board.getPlayers().get(3).getColor(), Color.yellow);
		Assert.assertEquals(board.getPlayers().get(0).getColor(), Color.cyan);
		Assert.assertEquals(board.getPlayers().get(1).getColor(), Color.red);
		//Test player starting locations
		Assert.assertEquals(board.getPlayers().get(3).getStart(), board.calcIndex(33, 2));
		Assert.assertEquals(board.getPlayers().get(0).getStart(), board.calcIndex(3, 6));
		Assert.assertEquals(board.getPlayers().get(1).getStart(), board.calcIndex(22,0));
	}
	
	@Test
	public void testCardLoad() {
		Assert.assertEquals(board.getAllCards().size(), 21);	// 6 players, 9 rooms, 6 weapons
		
		int numWeapons=0;
		int numPlayers=0;
		int numRooms=0;
		for(Card c:board.getAllCards()) {
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
		Assert.assertTrue(board.getAllCards().contains(new Card("Datum", cardType.PERSON)));
		Assert.assertTrue(board.getAllCards().contains(new Card("Kirk's Fists", cardType.WEAPON)));
		Assert.assertTrue(board.getAllCards().contains(new Card("Bridge", cardType.ROOM)));
	}
	
	@Test
	public void testCardDeal() {
		//Tests that all cards were dealt and all players have 3 cards
		Assert.assertEquals(board.getSolution().size(), 3);
		
		for(Card c:board.getCards()) {
			System.out.println(c.getName());
		}
		for(int i=0; i<6; i++) {
			/*System.out.println("oi");
			for(Card c:board.getPlayers().get(i).getCards()) {
				System.out.println(c.getName());
				
			}
			System.out.println();*/
			Assert.assertEquals(board.getPlayers().get(i).getCards().size(),3);
		}
		//Test to make sure all cards are dealt
		Set<Card> dealtCards = new HashSet<Card>();
		for(Player p: board.getPlayers()) {
			for(Card c:p.getCards()) {
				dealtCards.add(c);
			}
		}
		Assert.assertEquals(dealtCards.size()+board.getSolution().size(), board.getAllCards().size());
	}
}
