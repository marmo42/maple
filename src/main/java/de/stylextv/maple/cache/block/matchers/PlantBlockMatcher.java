package de.stylextv.maple.cache.block.matchers;

import de.stylextv.maple.cache.block.BlockMatcher;
import de.stylextv.maple.cache.block.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class PlantBlockMatcher extends BlockMatcher {
	
	@Override
	public BlockType match(BlockState state, BlockState above, BlockState below) {
		Block block = above.getBlock();
		
		boolean belowLilyPad = block.equals(Blocks.LILY_PAD);
		
		if(belowLilyPad) return BlockType.UNBREAKABLE;
		
		return null;
	}
	
}
