package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, int index, Color color) {
		super(name,index,color);
	}
	/*public BoardCell pickLocation(Set<BoardCell> target) {
		return new WalkwayCell(8,10);
	}*/
	@Override
	public BoardCell pickLocation(Set<BoardCell> t){
		BoardCell[] targets = t.toArray(new BoardCell[0]);
		Random randomGen = new Random();
		ArrayList<Integer> doorWays = new ArrayList<Integer>();
		int tempCounter = 0;
		for(BoardCell b : targets){
			if(b.isDoorway() && !((RoomCell)b).equals(lastVisited)){
				doorWays.add(tempCounter);
			}
			tempCounter++;
		}
		if(doorWays.size() == 0){
			return targets[randomGen.nextInt(targets.length)];
		}else{
			return targets[doorWays.get(randomGen.nextInt(doorWays.size()))];
		}
	}
}
