package clueGame;

public class WalkwayCell extends BoardCell {
	
	
	public WalkwayCell(int row, int col) {
		super(row, col);
	}

	@Override
	public boolean isWalkway() {
		return true;
	}
}
