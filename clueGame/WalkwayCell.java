package clueGame;

public class WalkwayCell extends BoardCell {
	
	
	public WalkwayCell(int row, int col) {
		super(row, col);
	}

	@Override
	public boolean isWalkway() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof WalkwayCell) {
			WalkwayCell tempWalk = (WalkwayCell)o;
			if(tempWalk.getCol()==col&&tempWalk.getRow()==row) {
				return true;
			}
		}
		return false;
	}
}
