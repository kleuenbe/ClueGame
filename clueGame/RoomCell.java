package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE}
	private DoorDirection doorDirection;
	private char roomInitial;
	private String title="";
	
	public char getRoomInitial() {
		return roomInitial;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public void draw(Graphics g, Board board){
		g.setColor(Color.GRAY);
		g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT);
		if (isDoorway()){
			g.setColor(Color.BLUE);
			switch(this.getDoorDirection()){
			case RIGHT:
				g.fillRect((int) (toPixel(this.getCol()) + WIDTH*.8), toPixel(this.getRow()), WIDTH/5, HEIGHT);
				break;
			case LEFT:
				g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH/5, HEIGHT);
				break;
			case UP:
				g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT/5);
				break;
			case DOWN:
				g.fillRect(toPixel(this.getCol()), (int) (toPixel(this.getRow()) + WIDTH*.8), WIDTH, HEIGHT/5);
				break;
			}
		}
		if(title!="") {
			g.setColor(Color.BLACK);
			g.drawString(title, toPixel(this.getCol()), toPixel(this.getRow()));
		}
	}
}
