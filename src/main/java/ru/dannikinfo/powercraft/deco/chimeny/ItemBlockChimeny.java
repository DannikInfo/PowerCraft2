package ru.dannikinfo.powercraft.deco.chimeny;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockChimeny extends ItemMultiTexture {

    public ItemBlockChimeny(Block block) {
		super(block, block, Chimeny.metadata);
	}

}
