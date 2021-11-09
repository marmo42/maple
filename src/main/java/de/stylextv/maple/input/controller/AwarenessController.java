package de.stylextv.maple.input.controller;

import de.stylextv.maple.cache.CacheManager;
import de.stylextv.maple.cache.block.BlockType;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.util.world.Offset;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class AwarenessController {
	
	private static final Offset[] BONK_HEAD_CORNERS = {
			new Offset(-0.3,  2.9, -0.3),
			new Offset( 0.3,  2.9, -0.3),
			new Offset(-0.3,  2.9,  0.3),
			new Offset( 0.3,  2.9,  0.3)
	};
	
	public static boolean isSafeToBreak(BlockPos pos) {
		if(!BreakController.isSafeToBreak(pos)) return false;
		
		BlockPos p = PlayerContext.feetPosition().down();
		
		if(p.equals(pos)) return false;
		
		pos = pos.up();
		
		boolean falls = isFallingBlock(pos);
		
		if(falls) return isSafeToBreak(pos);
		
		return true;
	}
	
	public static boolean isFallingBlock(BlockPos pos) {
		BlockState state = BlockInterface.getState(pos);
		
		Block block = state.getBlock();
		
		return block instanceof FallingBlock;
	}
	
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
		
		Vec3d v = Vec3d.ofCenter(pos);
		
		double dis = eyePos.distanceTo(v);
		
		return dis <= PlayerContext.reachDistance();
	}
	
	public static boolean isBlockingPos(BlockPos pos) {
		ClientPlayerEntity p = PlayerContext.player();
		
		Box box = p.getBoundingBox();
		
		Box other = new Box(pos);
		
		return box.intersects(other);
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
