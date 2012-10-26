package BoardTests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class BoardInitTests {
	private static Board board;
	public static int NUM_ROOMS = 11;
	public static int NUM_COLS = 24;
	public static int NUM_ROWS = 19;
	
	@BeforeClass
	public static void setUp(){
		board = new Board("legend.txt","Clue Layout.csv");
	}
	

	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		
		assertEquals(NUM_ROOMS, rooms.size());
		
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Entry Way", rooms.get('E'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Closet", rooms.get('X'));
	}

	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());		
	}
	
	@Test
	public void FourDoorDirections() {

		RoomCell room = board.getRoomCellAt(8, 5);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(10, 5);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(8, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(14, 6);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());

		room = board.getRoomCellAt(0, 0);
		assertFalse(room.isDoorway());	

		
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());		
	}
	
	@Test
	public void testNumDoorways() {
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(456, totalCells);
		for (int i=0; i<totalCells; i++)
		{
			BoardCell cell = board.getCellAt(i);
			if (cell.isDoorway())
				numDoors++;
		}
		Assert.assertEquals(16, numDoors);
	}
	
	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLS-1, board.calcIndex(0, NUM_COLS-1));
		assertEquals(432, board.calcIndex(NUM_ROWS-1, 0));
		assertEquals(455, board.calcIndex(NUM_ROWS-1, NUM_COLS-1));
		assertEquals(25, board.calcIndex(1, 1));
		assertEquals(68, board.calcIndex(2, 20));		
	}
	
	@Test
	public void testRoomInitials() {
		assertEquals('C', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('L', board.getRoomCellAt(4, 8).getInitial());
		assertEquals('E', board.getRoomCellAt(9, 0).getInitial());
		assertEquals('D', board.getRoomCellAt(15, 22).getInitial());
		assertEquals('B', board.getRoomCellAt(18, 0).getInitial());
	}
	
	@Test
	public void testAdjecency293() {
		LinkedList testList = board.getAdjList(293);
		Assert.assertTrue(testList.contains(294));
		Assert.assertTrue(testList.contains(292));
		Assert.assertTrue(testList.contains(269));
		Assert.assertTrue(testList.contains(317));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjecency6() {
		LinkedList testList = board.getAdjList(6);
		Assert.assertTrue(testList.contains(7));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 6)));
		Assert.assertTrue(testList.contains(5));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjecency20() {
		LinkedList testList = board.getAdjList(20);
		Assert.assertTrue(testList.contains(19));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 20)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjecency165() {
		LinkedList testList = board.getAdjList(165);
		Assert.assertTrue(testList.contains(166));
		Assert.assertTrue(testList.contains(164));
		Assert.assertTrue(testList.contains(141));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjecency184() {
		LinkedList testList = board.getAdjList(184);
		Assert.assertTrue(testList.contains(183));
		Assert.assertTrue(testList.contains(185));
		Assert.assertTrue(testList.contains(160));
		Assert.assertTrue(testList.contains(208));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjecency171() {
		LinkedList testList = board.getAdjList(171);
		Assert.assertTrue(testList.contains(147));
		Assert.assertTrue(testList.contains(172));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testTargets12_1() {
		board.calcTargets(12, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(36)));
		Assert.assertTrue(targets.contains(board.getCellAt(13)));
	}
	
	@Test
	public void testTargets68_2() {
		board.calcTargets(68, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(20)));
		Assert.assertTrue(targets.contains(board.getCellAt(43)));
		Assert.assertTrue(targets.contains(board.getCellAt(116)));
		Assert.assertTrue(targets.contains(board.getCellAt(91)));
	}
	
	@Test
	public void testTargets311_3() {
		board.calcTargets(311, 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(333)));
		Assert.assertTrue(targets.contains(board.getCellAt(308)));
		Assert.assertTrue(targets.contains(board.getCellAt(310)));
		Assert.assertTrue(targets.contains(board.getCellAt(335)));
	}
	
	@Test
	public void testTargets340_1() {
		board.calcTargets(340, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(341)));
		Assert.assertTrue(targets.contains(board.getCellAt(364)));
		Assert.assertTrue(targets.contains(board.getCellAt(316)));
	}
	
	@Test
	public void testTargets139_2() {
		board.calcTargets(139, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(114)));
		Assert.assertTrue(targets.contains(board.getCellAt(137)));
		Assert.assertTrue(targets.contains(board.getCellAt(164)));
		Assert.assertTrue(targets.contains(board.getCellAt(91)));
		Assert.assertTrue(targets.contains(board.getCellAt(116)));
		Assert.assertTrue(targets.contains(board.getCellAt(162)));
	}
	
	@Test
	public void testTargets54_3() {
		board.calcTargets(54, 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(7)));
		Assert.assertTrue(targets.contains(board.getCellAt(30)));
		Assert.assertTrue(targets.contains(board.getCellAt(55)));
		Assert.assertTrue(targets.contains(board.getCellAt(55)));
		Assert.assertTrue(targets.contains(board.getCellAt(126)));
		Assert.assertTrue(targets.contains(board.getCellAt(103)));
		Assert.assertTrue(targets.contains(board.getCellAt(103)));
		Assert.assertEquals(8, targets.size());
	}
	
	@Test
	public void testTargets98_1() {
		board.calcTargets(98, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(122)));
	}
	
	@Test
	public void testTargets114_2() {
		board.calcTargets(114, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(139)));
		Assert.assertTrue(targets.contains(board.getCellAt(137)));
		Assert.assertTrue(targets.contains(board.getCellAt(162)));
	}
}
