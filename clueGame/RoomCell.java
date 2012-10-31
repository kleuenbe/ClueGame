package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE}
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public char getRoomInitial() {
		return roomInitial;
	}

	public RoomCell(String s, int row, int col) {
		super(row, col);
		roomInitial = s.charAt(0);
		if(s.length() == 1) {
			this.doorDirection = DoorDirection.NONE;
		} else if(s.charAt(1) == 'U') {
			this.doorDirection = DoorDirection.UP;
		} else if(s.charAt(1) == 'D') {
			this.doorDirection = DoorDirection.DOWN;
		} else if(s.charAt(1) == 'L') {
			this.doorDirection = DoorDirection.LEFT;
		} else if(s.charAt(1) == 'R') {
			this.doorDirection = DoorDirection.RIGHT;
		}
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
	@Override
	public boolean isDoorway() {
		if(this.getDoorDirection() == DoorDirection.NONE) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof RoomCell) {
			RoomCell tempRoom = (RoomCell)o;
			if(tempRoom.getInitial()==roomInitial) {
				return true;
			}
		}
		return false;
	}

}
