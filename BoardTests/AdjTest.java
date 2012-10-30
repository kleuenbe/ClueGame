package BoardTests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjTest {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("Legend","BoardLayout.csv");
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(2, 6));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(15, 9));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(23, 5));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(7, 9));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(29, 6));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(24, 6));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(24, 7)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(board.calcIndex(41, 6));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(41, 5)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(4, 10));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 10)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(12, 2));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(11, 2)));
		
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, two walkway pieces
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(3, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 3)));
		Assert.assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(32, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(31, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(32, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(33, 0)));
		Assert.assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(19, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 4)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(12,5));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 5)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(43, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(43, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(42, 5)));
		Assert.assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(31, 11));
		Assert.assertTrue(testList.contains(board.calcIndex(31, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(32, 11)));
		Assert.assertEquals(2, testList.size());

	}

	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(7, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 5)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(19, 2));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 2)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(30, 9));
		Assert.assertTrue(testList.contains(board.calcIndex(30, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(30, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(31, 9)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(31, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(31, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(31, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(30, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(32, 5)));
		Assert.assertEquals(4, testList.size());
	}

	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(board.calcIndex(43, 5), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(43, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(42, 5))));	
		
		board.calcTargets(board.calcIndex(32, 0), 1);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(32, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(31, 0))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(33, 0))));			
	}
	// Tests of just walkways, 2 steps
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(board.calcIndex(24, 0), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(26, 0))));
		
		board.calcTargets(board.calcIndex(18, 8), 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 7))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 7))));			
	}
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(board.calcIndex(19, 0), 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());	// 3 due to door
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(23, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 4))));
		
		
		// Includes a path that doesn't have enough length
		board.calcTargets(board.calcIndex(43, 4), 4);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());	// 2 due to door
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(40, 5))));
	}	
	// Tests of just walkways plus one door, 6 steps
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(board.calcIndex(5, 0), 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());	// 5 due to door
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 5))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 5))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 4))));
	}	
	
	// Test getting into a room
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(board.calcIndex(5, 2), 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 3))));
	}
	
	// Test getting into room, doesn't require all steps
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(board.calcIndex(24, 8), 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(24, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(26, 7))));
	}

	// Test getting out of a room
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(board.calcIndex(10, 9), 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 9))));
		// Take two steps
		board.calcTargets(board.calcIndex(10, 9), 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 8))));
	}

}