package ru.dannikinfo.powercraft.core.crystal;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockCrystal extends ItemMultiTexture {

    public ItemBlockCrystal(Block block) {
		super(block, block, Crystal.metadata);
	}

}
