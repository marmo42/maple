package de.stylextv.lynx.pathing.movement.helper;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class ParkourHelper extends MovementHelper {
	
	public ParkourHelper(Movement m) {
		super(m);
	}
	
	@Override
	public double cost() {
		Movement m = getMovement();
		
		int dis = m.horizontalDistance();
		
		if(dis > 5) return Cost.INFINITY;
		
		Node source = m.getSource();
		
		int startX = source.getX();
		int startY = source.getY();
		int startZ = source.getZ();
		
		int dx = m.getDirectionX();
		int dz = m.getDirectionZ();
		
		for(int i = 0; i <= dis; i++) {
			
			int x = startX + dx * i;
			int y = startY;
			int z = startZ + dz * i;
			
			if(isAir(x, y, z, 3)) return Cost.INFINITY;
		}
		
		return dis * Cost.SPRINT_STRAIGHT;
	}
	
	public static boolean isAir(Node n, int height) {
		return isAir(n, 0, height);
	}
	
	public static boolean isAir(Node n, int offset, int height) {
		int x = n.getX();
		int y = n.getY();
		int z = n.getZ();
		
		return isAir(x, y, z, offset, height);
	}
	
	public static boolean isAir(int x, int y, int z, int height) {
		return isAir(x, y, z, 0, height);
	}
	
	public static boolean isAir(int x, int y, int z, int offset, int height) {
		for(int i = 0; i < height; i++) {
			
			if(!isAir(x, y + offset + i, z)) return false;
		}
		
		return true;
	}
	
	public static boolean isAir(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		return type.isAir();
	}
	
}
