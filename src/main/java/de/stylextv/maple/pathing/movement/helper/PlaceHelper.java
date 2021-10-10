package de.stylextv.maple.pathing.movement.helper;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.input.controller.PlaceController;
import de.stylextv.maple.input.target.BlockTarget;
import de.stylextv.maple.pathing.calc.Cost;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.pathing.movement.Movement;
import net.minecraft.util.math.BlockPos;

public class PlaceHelper extends TargetHelper {
	
	public PlaceHelper(Movement m) {
		super(m);
	}
	
	@Override
	public void collectBlock(int x, int y, int z) {
		BlockType type = CacheManager.getBlockType(x, y, z);
		
		if(type.isSolid()) return;
		
		if(!hasTarget(x, y, z)) addTarget(x, y, z);
	}
	
	@Override
	public double cost() {
		Movement movement = getMovement();
		
		boolean hasSupport = false;
		
		if(!movement.isDiagonal3D()) {
			
			Node source = movement.getSource();
			
			BlockType type = source.getType();
			
			hasSupport = type != BlockType.WATER;
			
			Movement m = source.getMovement();
			
			if(m != null) hasSupport = m.getPlaceHelper().hasTargets();
		}
		
		for(BlockTarget target : getTargets()) {
			
			BlockPos pos = target.getPos();
			
			if(WorldContext.isOutOfHeightLimit(pos)) return Cost.INFINITY;
			
			if(!hasSupport && !PlaceController.canPlaceAt(pos)) return Cost.INFINITY;
		}
		
		int l = getTargets().size();
		
		return l * Cost.placeCost();
	}
	
	public boolean onRenderTick() {
		if(!hasTargets()) return false;
		
		for(BlockTarget target : getTargets()) {
			
			if(!target.isPlaceable()) {
				
				removeTarget(target);
				
				continue;
			}
			
			if(target.continueTransforming()) {
				
				InputController.setPressed(InputAction.SNEAK, true);
				
				return true;
			}
		}
		
		return false;
	}
	
}