package powercraft.transport;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import powercraft.api.gres.PC_GresBaseWithInventory;
import powercraft.api.inventory.PC_Slot;

public class PCtr_ContainerSeparationBelt extends PC_GresBaseWithInventory<PCtr_TileEntitySeparationBelt>
{

	PCtr_TileEntitySeparationBelt te;
	
    public PCtr_ContainerSeparationBelt(EntityPlayer player, TileEntity te, Object[]o){
        super(player, (PCtr_TileEntitySeparationBelt)te, o);
        this.te = (PCtr_TileEntitySeparationBelt) te;
	    int i = -18;
	    int j;
	    int k;
	    int index = 0;
	    invSlot = new ArrayList<PC_Slot>();
	    for (j = 0; j < 3; ++j){
	    	for (k = 0; k < 3; ++k){
	    			invSlot.add((PC_Slot)new Slot(this.te.sepInv, index++, 29 + k * 18, 15 + j * 18));
	        }
	        for (k = 3; k < 6; ++k){
	        		invSlot.add((PC_Slot)new Slot(this.te.sepInv, index++, 39 + k * 18, 15 + j * 18));
	        }
	    }
	    
	    for(int a = 0; a < invSlot.size(); a++) {
	    		addSlotToContainer(invSlot.get(a));
	    }

	    for (j = 0; j < 3; ++j){
	    		for (k = 0; k < 9; ++k){
	    			this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
	        }
	    }

	    for (j = 0; j < 9; ++j){
	    		this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 160 + i));
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
