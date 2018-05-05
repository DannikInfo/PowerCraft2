package ru.dannikinfo.powercraft.api.inventory;

	import net.minecraft.item.ItemStack;

public interface IInventoryBackground {

	public ItemStack getBackgroundStack(int slotIndex);

	public boolean renderTooltipWhenEmpty(int slotIndex);

	public boolean renderGrayWhenEmpty(int slotIndex);
		
	public boolean useAlwaysBackground(int slotIndex);
		
}

