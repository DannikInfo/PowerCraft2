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
		super(player, (PCtr_TileEntitySplitter)te, o);
		 this.te = (PCtr_TileEntitySplitter) te;
		    int j;
		    int k;
		    int index = 0;
			for(j=0; j<6; j++){
				for(k=0; k<5; k++){
					invSlot.add((PC_Slot)new Slot(this.te.sepInv, index++, 29 + k * 18, 15 + j * 18));
				}
			}		    
		    for(int a = 0; a < invSlot.size(); a++) {
		    		//addSlotToContainer(invSlot.get(a));
		    }
	}

	 @Override
	    public ItemStack transferStackInSlot(EntityPlayer player, int slot_i){
			ItemStack is = null;
			Slot slot = (Slot)inventorySlots.get(slot_i);
			System.out.println(is);
			if (slot != null && slot.getHasStack()) {
				ItemStack is1 = slot.getStack();
				is = is1.copy();


				if (slot_i < te.sepInv.getSizeInventory()) {
					if (!mergeItemStack(is1, te.sepInv.getSizeInventory(), inventorySlots.size(), true)) return null;
				}else if (!mergeItemStack(is1, 0, te.sepInv.getSizeInventory(), false)) return null;
		
				if (is1.stackSize == 0) slot.putStack((ItemStack)null);
				else slot.onSlotChanged();
			}
			
			return is;
	    }

		@Override
		public boolean canInteractWith(EntityPlayer p_75145_1_) {
			return true;
		}

}
