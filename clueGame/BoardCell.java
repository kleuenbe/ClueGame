package clueGame;

public abstract class BoardCell {
	private int row;
	private int col;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	

}
