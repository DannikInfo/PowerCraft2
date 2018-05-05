package ru.dannikinfo.powercraft.hologram;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import ru.dannikinfo.powercraft.api.renderer.PCRenderBlocks;

public class HologramRenderBlocks extends PCRenderBlocks{

	public HologramRenderBlocks(IBlockAccess blockAccess){
		super(blockAccess);
	}

	@Override
	public boolean shouldSideBeRendered(Block block, int x, int y, int z, int dir){
		if(block!=blockAccess.getBlock( x, y, z)){
			return true;
		}
		return block.shouldSideBeRendered(blockAccess, x, y, z, dir);
	}
}
