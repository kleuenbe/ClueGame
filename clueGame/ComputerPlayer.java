package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	public BoardCell pickLocation(Set<BoardCell> target) {
		return new WalkwayCell(8,10);
	}
}
