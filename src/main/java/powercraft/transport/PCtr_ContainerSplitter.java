package powercraft.transport;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import powercraft.api.gres.PC_GresBaseWithInventory;
import powercraft.api.gres.PC_GresInventory;
import powercraft.api.inventory.PC_Slot;

public class PCtr_ContainerSplitter extends PC_GresBaseWithInventory<PCtr_TileEntitySplitter> {

	PCtr_TileEntitySplitter te;

	public PCtr_ContainerSplitter(EntityPlayer player, TileEntity te, Object[] o) {
		super(player, (PCtr_TileEntitySplitter) te, o);
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
