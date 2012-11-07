package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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
	
	public void draw(Graphics g, Board board){
		g.setColor(Color.YELLOW);
		g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT);
	}
}
