package BoardTests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.cardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class GameActionTests {
	private static Board board;
	@Before
	public void setUp(){
		board = new Board("Legend","BoardLayout.csv","PlayerFile","CardFile");
	}
	
	@Test
	public void accusationTest() {
		Set<Card> guess=new HashSet<Card>();
		guess.add(new Card("Datum",cardType.PERSON));
		guess.add(new Card("Photon Torpedo Shard",cardType.WEAPON));
		guess.add(new Card("Engine Room",cardType.ROOM));
		Set<Card> guess2=new HashSet<Card>();
		guess2=guess;
		Set<Card> guess3=new HashSet<Card>();
		guess3=guess;
		//Test correct solution set
		Assert.assertTrue(board.checkAccusation(board.getSolution()));
		//Test wrong person
		guess.remove(new Card("Datum",cardType.PERSON));
		guess.add(new Card("Lieutenant Dwarf",cardType.PERSON));
		Assert.assertFalse(board.checkAccusation(guess));
		//Test wrong weapon
		guess2.remove(new Card("Photon Torpedo Shard",cardType.WEAPON));
		guess2.add(new Card("Phaser",cardType.WEAPON));
		Assert.assertFalse(board.checkAccusation(guess2));
		//Test wrong room
		guess3.remove(new Card("Engine Room",cardType.ROOM));
		guess3.add(new Card("Bridge",cardType.ROOM));
		Assert.assertFalse(board.checkAccusation(guess3));
		//Test wrong everything
		guess.remove(new Card("Photon Torpedo Shard",cardType.WEAPON));
		guess.add(new Card("Phaser",cardType.WEAPON));
		guess.remove(new Card("Engine Room",cardType.ROOM));
		guess.add(new Card("Bridge",cardType.ROOM));
		Assert.assertFalse(board.checkAccusation(guess));
	}
	@Test
	public void suggestionTest() {
		ArrayList<Card> hand0=new ArrayList<Card>();
		hand0.add(new Card("Datum",cardType.PERSON));
		hand0.add(new Card("Photon Torpedo Shard",cardType.WEAPON));
		hand0.add(new Card("Engine Room",cardType.ROOM));
		hand0.add(new Card("Phaser",cardType.WEAPON));
		Player player0 = board.getPlayers().get(0);
		player0.lastVisitedName='B';
		//player0.setCards(hand0);
		//board.addPlayers(player0);
		//Make sure not suggesting cards in hand and make sure suggesting the correct room
		ArrayList<Card> suggestion=((ComputerPlayer)player0).createSuggestion(board.getAllCards(),board.getSeenCards(), board.rooms);
		for(Card c:suggestion) {
			System.out.println(c.getName());
		}
		for(Card c:player0.getCards()) {
			System.out.println(c.getName());
		}
		Assert.assertFalse(!suggestion.contains(player0.getCards().get(0)));
		Assert.assertFalse(!suggestion.contains(board.getPlayers().get(0).getCards().get(1)));
		Assert.assertFalse(!suggestion.contains(board.getPlayers().get(0).getCards().get(2)));
		Assert.assertTrue(suggestion.contains(new Card(board.rooms.get(player0.lastVisitedName),cardType.ROOM)));
		//Test that suggestion is made up of a person, weapon, and room
		Assert.assertEquals(suggestion.size(),3);
		boolean hasRoom = false;
		boolean hasWeapon = false;
		boolean hasPerson = false;
		for(Card c:suggestion) {
			if(c.getType()==cardType.ROOM) {
				hasRoom = true;
			} else if(c.getType()==cardType.WEAPON) {
				hasWeapon=true;
			} else if(c.getType()==cardType.PERSON) {
				hasPerson=true;
			}
		}
		Assert.assertTrue(hasRoom&&hasWeapon&&hasPerson);
	}
	@Test
	public void disprovingTest() {
		
		//dealing cards
		ArrayList<Card> hand0=new ArrayList<Card>();
		hand0.add(new Card("Datum",cardType.PERSON));
		hand0.add(new Card("Photon Torpedo Shard",cardType.WEAPON));
		hand0.add(new Card("Engine Room",cardType.ROOM));
		hand0.add(new Card("Phaser",cardType.WEAPON));
		Player player0 = board.getPlayers().get(0);
		player0.setCards(hand0);
		//board.addPlayers(player0);
		
		ArrayList<Card> hand1=new ArrayList<Card>();
		hand1.add(new Card("Galley",cardType.ROOM));
		hand1.add(new Card("Bridge",cardType.ROOM));
		hand1.add(new Card("Sick Bay",cardType.ROOM));
		hand1.add(new Card("Ferengi Whip",cardType.WEAPON));
		Player player1 = board.getPlayers().get(1);
		player1.setCards(hand1);
		//board.addPlayers(player1);
		
		ArrayList<Card> hand2=new ArrayList<Card>();
		hand2.add(new Card("Holodeck",cardType.ROOM));
		hand2.add(new Card("Cargo Bay",cardType.ROOM));
		hand2.add(new Card("Crew Quarters",cardType.ROOM));
		hand2.add(new Card("Kirk's Fists",cardType.WEAPON));
		Player player2 = board.getPlayers().get(2);
		player2.setCards(hand2);
		//board.addPlayers(player2);
		
		//one possible return card
		ArrayList<Card> suggestion1=new ArrayList<Card>();
		suggestion1.add(new Card("Datum",cardType.PERSON));
		suggestion1.add(new Card("Crew Quarters",cardType.ROOM));
		suggestion1.add(new Card("Kirk's Fists",cardType.WEAPON));
		Assert.assertEquals(board.handleSuggestion(suggestion1,player2),new Card("Datum",cardType.PERSON));
		
		
		
		//two possible return cards from one player
		ArrayList<Card> suggestion0=new ArrayList<Card>();
		suggestion0.add(new Card("Datum",cardType.PERSON));
		suggestion0.add(new Card("Crew Quarters",cardType.ROOM));
		suggestion0.add(new Card("Photon Torpedo Shard",cardType.WEAPON));
	
		int choice1 = 0;
		int choice2 = 0;
		for(int i = 0; i < 10; i++){
			Card tempCard = board.handleSuggestion(suggestion0,player2);
			if(tempCard.equals(new Card("Datum",cardType.PERSON))){
				choice1++;
			}else if(tempCard.equals(new Card("Photon Torpedo Shard",cardType.WEAPON))){
				choice2++;
			}
		}
		Assert.assertEquals(choice1 +choice2,10);
		Assert.assertTrue(choice1 > 2);
		Assert.assertTrue(choice2 > 2);
		
		//return card can be from two possible players
		ArrayList<Card> suggestion2=new ArrayList<Card>();
		suggestion2.add(new Card("Datum",cardType.PERSON));
		suggestion2.add(new Card("Galley",cardType.ROOM));
		suggestion2.add(new Card("Kirk's Fists",cardType.WEAPON));
		choice1 = 0;
		choice2 = 0;
		for(int i = 0; i < 10; i++){
			Card tempCard = board.handleSuggestion(suggestion2,player2);
			if(tempCard.equals(new Card("Datum",cardType.PERSON))){
				choice1++;
			}else if(tempCard.equals(new Card("Galley",cardType.ROOM))){
				choice2++;
			}
		}
		Assert.assertEquals(choice1 +choice2,10);
		Assert.assertTrue(choice1 > 2);
		Assert.assertTrue(choice2 > 2);
		
		
		//test that involves human player
		ArrayList<Card> suggestion3=new ArrayList<Card>();
		suggestion3.add(new Card("Datum",cardType.PERSON));
		suggestion3.add(new Card("Galley",cardType.ROOM));
		suggestion3.add(new Card("Kirk's Fists",cardType.WEAPON));
		choice1 = 0;
		choice2 = 0;
		for(int i = 0; i < 10; i++){
			Card tempCard = board.handleSuggestion(suggestion3,player0);
			if(tempCard.equals(new Card("Kirk's Fists",cardType.WEAPON))){
				choice1++;
			}else if(tempCard.equals(new Card("Galley",cardType.ROOM))){
				choice2++;
			}
		}
		Assert.assertEquals(choice1 +choice2,10);
		Assert.assertTrue(choice1 > 2);
		Assert.assertTrue(choice2 > 2);
		
		//test that the player whose turn it is does not return a card
		ArrayList<Card> suggestion4=new ArrayList<Card>();
		suggestion4.add(new Card("Datum",cardType.PERSON));
		suggestion4.add(new Card("Engine Room",cardType.ROOM));
		suggestion4.add(new Card("Phaser",cardType.WEAPON));
		choice1 = 0;
		choice2 = 0;
		for(int i = 0; i < 10; i++){
			Card tempCard = board.handleSuggestion(suggestion4,player2);
			Assert.assertEquals(tempCard,null);
		}
		
	}
	@Test
	public void selectLocationTest() {
		System.out.println(board.getPlayers().size());
		ComputerPlayer player = (ComputerPlayer)board.getPlayers().get(0);
		// Pick a location with no rooms in target, just three targets
		board.calcTargets(board.calcIndex(3, 3),2);
		int loc1 = 0;
		int loc2 = 0;
		int loc3 = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(3, 5)))
				loc1++;
			else if (selected == board.getCellAt(board.calcIndex(5,3)))
				loc2++;
			else if (selected == board.getCellAt(board.calcIndex(4,4)))
				loc3++;
			else
				Assert.fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		Assert.assertEquals(100, loc1 + loc2 + loc3);
		// Ensure each target was selected more than once
		Assert.assertTrue(loc1 > 10);
		Assert.assertTrue(loc2 > 10);
		Assert.assertTrue(loc3 > 10);	
		board.calcTargets(56, 1);
		for(int i = 0; i < 100; i++){
			BoardCell selected = player.pickLocation(board.getTargets());
			Assert.assertEquals(selected, board.getCellAt(45));
		}
		player.lastVisitedName = 'E';
		loc1 = 0;
		loc2 = 0;
		loc3 = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(47))
				loc1++;
			else if (selected == board.getCellAt(45))
				loc2++;
			else
				Assert.fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		Assert.assertEquals(100, loc1 + loc2);
		// Ensure each target was selected more than once
		Assert.assertTrue(loc1 > 10);
		Assert.assertTrue(loc2 > 10);		
	}

}
