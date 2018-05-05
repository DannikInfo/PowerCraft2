package ru.dannikinfo.powercraft.transport.elevator;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemBlockElevator extends ItemMultiTexture {

    public ItemBlockElevator(Block block) {
		super(block, block, Elevator.metadata);
    }
}
