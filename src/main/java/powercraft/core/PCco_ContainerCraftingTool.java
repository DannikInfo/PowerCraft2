package powercraft.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.world.World;
import powercraft.api.gres.PC_GresBaseWithInventory;
import powercraft.api.tileentity.PC_TileEntity;

public class PCco_ContainerCraftingTool extends ContainerWorkbench{
	public PCco_ContainerCraftingTool(InventoryPlayer p_i1808_1_, World p_i1808_2_, int p_i1808_3_, int p_i1808_4_, int p_i1808_5_) {
		super(p_i1808_1_, p_i1808_2_, p_i1808_3_, p_i1808_4_, p_i1808_5_);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}

