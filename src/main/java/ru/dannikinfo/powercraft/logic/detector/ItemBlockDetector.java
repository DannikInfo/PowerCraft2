package ru.dannikinfo.powercraft.logic.detector;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockDetector extends ItemMultiTexture {

    public ItemBlockDetector(Block block) {
		super(block, block, Detector.metadata);
	}

}

