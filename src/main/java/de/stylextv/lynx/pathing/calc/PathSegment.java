package de.stylextv.lynx.pathing.calc;

import java.util.List;

import de.stylextv.lynx.pathing.movement.Movement;
import net.minecraft.core.BlockPos;

public class PathSegment {
	
	private List<Movement> list;
	
	private int pointer;
	
	public PathSegment(List<Movement> list) {
		this.list = list;
	}
	
	public void next() {
		pointer++;
	}
	
	public BlockPos lastPosition() {
		int l = list.size();
		
		Movement m = list.get(l - 1);
		
		Node n = m.getDestination();
		
		return n.blockPos();
	}
	
	public double ticksLeft() {
		double sum = 0;
		
		for(Movement m : list) {
			
			sum += m.cost();
		}
		
		return sum;
	}
	
	public int nodesLeft() {
		return length() - pointer;
	}
	
	public Node getCurrentNode() {
		if(isFinished()) return null;
		
		return getNode(pointer);
	}
	
	public Node getNode(int index) {
		Movement m = getMovement(index);
		
		return m.getDestination();
	}
	
	public Movement getCurrentMovement() {
		if(isFinished()) return null;
		
		return getMovement(pointer);
	}
	
	public Movement getMovement(int index) {
		return list.get(index);
	}
	
	public boolean isFinished() {
		return pointer >= length();
	}
	
	public int length() {
		return list.size();
	}
	
}
