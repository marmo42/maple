package de.stylextv.lynx.cache;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public class BlockType {
	
	public static final BlockType AIR = new BlockType("air", 0, Blocks.AIR).passable(true).air(true);
	public static final BlockType SOLID = new BlockType("solid", 1, Blocks.STONE).breakable(true);
	public static final BlockType WATER = new BlockType("water", 2, Blocks.WATER).passable(true);
	public static final BlockType DANGER = new BlockType("danger", 3, Blocks.LAVA);
	
	public static final BlockType UNLOADED = new BlockType("unloaded", Blocks.AIR).passable(true).air(true);
	public static final BlockType VOID = new BlockType("void", Blocks.AIR).passable(true).air(true);
	public static final BlockType BORDER = new BlockType("border", Blocks.BEDROCK);
	
	private static final Block[] DANGER_BLOCKS = new Block[] {
			Blocks.LAVA,
			Blocks.FIRE,
			Blocks.SOUL_FIRE,
			Blocks.CAMPFIRE,
			Blocks.SOUL_CAMPFIRE,
			Blocks.SWEET_BERRY_BUSH,
			Blocks.CACTUS
	};
	
	private String name;
	
	private boolean[] bits;
	
	private BlockState state;
	
	private boolean passable;
	
	private boolean breakable;
	
	private boolean air;
	
	private BlockType(String name, Block block) {
		this(name, -1, block);
	}
	
	private BlockType(String name, int bits, Block block) {
		this.name = name;
		this.bits = new boolean[] {
				(bits & 2) != 0,
				(bits & 1) != 0
		};
		
		this.state = block.getDefaultState();
	}
	
	public BlockType passable(boolean b) {
		this.passable = b;
		
		return this;
	}
	
	public BlockType breakable(boolean b) {
		this.breakable = b;
		
		return this;
	}
	
	public BlockType air(boolean b) {
		this.air = b;
		
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean[] getBits() {
		return bits;
	}
	
	public BlockState getState() {
		return state;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public boolean isBreakable() {
		return breakable;
	}
	
	public boolean isAir() {
		return air;
	}
	
	public static BlockType fromBlocks(BlockState state, BlockState below, BlockState above) {
		Block block = state.getBlock();
		
		for(Block b : DANGER_BLOCKS) {
			if(block.equals(b)) return DANGER;
		}
		
		if(block.equals(Blocks.LILY_PAD)) return AIR;
		
		Block blockBelow = below.getBlock();
		
		boolean aboveFence = blockBelow instanceof FenceBlock || blockBelow instanceof FenceGateBlock;
		
		if(aboveFence || above.getBlock().equals(Blocks.LILY_PAD)) {
			return SOLID;
		}
		
		Fluid fluid = state.getFluidState().getFluid();
		
		if(fluid.equals(Fluids.FLOWING_WATER)) return DANGER;
		
		boolean isWater = fluid.equals(Fluids.WATER);
		
		boolean aboveMagma = blockBelow.equals(Blocks.MAGMA_BLOCK);
		
		if(isWater) return aboveMagma ? DANGER : WATER;
		
		boolean isSolid = state.getMaterial().blocksMovement();
		
		if(isSolid) return SOLID;
		
		return aboveMagma ? DANGER : AIR;
	}
	
	public static BlockType fromBits(boolean b1, boolean b2) {
		return b1 ? b2 ? DANGER : WATER : b2 ? SOLID : AIR;
	}
	
}
