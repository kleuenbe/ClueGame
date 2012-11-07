package clueGame;

import java.awt.Graphics;

public abstract class BoardCell {
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	protected int row;
	protected int col;
	
	public int getRow() {
		return row;
	}


	public int getCol() {
		return col;
	}


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
	
	abstract public void draw(Graphics g, Board board);
	
	static public int toPixel(int x){
		return x*WIDTH;
	}
}
