package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.LevelContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BreakController {
	
	public static boolean isBreakable(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		
		return isBreakable(pos);
	}
	
	public static boolean isBreakable(BlockPos pos) {
		BlockState state = LevelContext.getBlockState(pos);
		
		return state.getMaterial().blocksMotion();
	}
	
}
