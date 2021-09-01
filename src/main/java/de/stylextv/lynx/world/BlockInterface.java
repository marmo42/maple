package de.stylextv.lynx.world;

import de.stylextv.lynx.cache.BlockType;
import de.stylextv.lynx.cache.CacheManager;
import de.stylextv.lynx.context.LevelContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockInterface {
	
	public static BlockState getState(int x, int y, int z) {
		return getState(new BlockPos(x, y, z));
	}
	
	public static BlockState getState(BlockPos pos) {
		boolean loaded = WorldBorderInterface.isInside(pos) && LevelContext.isBlockLoaded(pos);
		
		if(loaded) return LevelContext.getBlockState(pos);
		
		BlockType type = CacheManager.getBlockType(pos);
		
		return type.getState();
	}
	
}
