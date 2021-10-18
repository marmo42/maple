package de.stylextv.maple.input.controller;

import de.stylextv.maple.cache.BlockType;
import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.util.world.Offset;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AwarenessController {
	
	private static final Offset[] BONK_HEAD_CORNERS = {
			new Offset(-0.3,  2.9, -0.3),
			new Offset( 0.3,  2.9, -0.3),
			new Offset(-0.3,  2.9,  0.3),
			new Offset( 0.3,  2.9,  0.3)
	};
	
	public static boolean canJump() {
		if(!PlayerContext.isOnGround()) return false;
		
		Vec3d v = PlayerContext.position();
		
		for(Offset o : BONK_HEAD_CORNERS) {
			
			double x = v.getX() + o.getX();
			double y = v.getY() + o.getY();
			double z = v.getZ() + o.getZ();
			
			BlockType type = CacheManager.getBlockType(x, y, z);
			
			if(!type.isPassable()) return false;
		}
		
		return true;
	}
	
	public static boolean canReach(BlockPos pos) {
		Vec3d eyePos = PlayerContext.eyePosition();
		
		double x = pos.getX() + 0.5;
		double y = pos.getY() + 0.5;
		double z = pos.getZ() + 0.5;
		
		Vec3d v = new Vec3d(x, y, z);
		
		double dis = eyePos.distanceTo(v);
		
		if(dis > PlayerContext.reachDistance()) return false;
		
		return ViewController.canSee(pos);
	}
	
	public static BlockPos getTargetedPos() {
		BlockHitResult result = getBlockTarget();
		
		if(result == null) return null;
		
		return result.getBlockPos();
	}
	
	public static BlockHitResult getBlockTarget() {
		HitResult result = getCrosshairTarget();
		
		if(result == null || result.getType() != Type.BLOCK) return null;
		
		return (BlockHitResult) result;
	}
	
	public static HitResult getCrosshairTarget() {
		return PlayerContext.crosshairTarget();
	}
	
}
