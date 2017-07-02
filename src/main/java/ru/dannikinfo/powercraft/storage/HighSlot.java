package ru.dannikinfo.powercraft.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.dannikinfo.powercraft.api.inventory.IInventoryBackground;
import ru.dannikinfo.powercraft.api.inventory.IInventoryClickHandler;
import ru.dannikinfo.powercraft.api.inventory.PCIInventory;

public class HighSlot extends Slot{
private ItemStack bgStack = null;
	
	public HighSlot(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
		slotNumber = -1;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack){
		if (inventory instanceof PCIInventory){
			return ((PCIInventory) inventory).canPlayerInsertStackTo(getSlotIndex(), itemStack);
		}
		return true;
	}
	
	@Override
	public int getSlotStackLimit(){
		return 512;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer entityPlayer){
		//if(inventory instanceof PCIInventory){
		//	return ((PCIInventory) inventory).canPlayerTakeStack(getSlotIndex(), entityPlayer);
	//	}
		return true;
	}
	
    public ItemStack getBackgroundStack(){
    	if(inventory instanceof IInventoryBackground){
    		return ((IInventoryBackground) inventory).getBackgroundStack(getSlotIndex());
    	}
        return bgStack;
    }

    public HighSlot setBackgroundStack(ItemStack stack){
    	if(inventory instanceof IInventoryBackground)
    		return this;
    	if(stack==null){
    		bgStack = null;
    	}else{
    		bgStack = stack.copy();
    	}
        return this;
    }

    public boolean renderTooltipWhenEmpty(){
    	if(inventory instanceof IInventoryBackground){
    		return ((IInventoryBackground) inventory).renderTooltipWhenEmpty(getSlotIndex());
    	}
        return false;
    }

    public boolean renderGrayWhenEmpty(){
    	if(inventory instanceof IInventoryBackground){
    		return ((IInventoryBackground) inventory).renderGrayWhenEmpty(getSlotIndex());
    	}
        return true;
    }
    
    public boolean useAlwaysBackground(){
    	if(inventory instanceof IInventoryBackground){
    		return ((IInventoryBackground) inventory).useAlwaysBackground(getSlotIndex());
    	}
        return false;
    }
    
    public boolean isHandlingSlotClick(){
    	return inventory instanceof IInventoryClickHandler;
    }
    
    public ItemStack slotClick(int mouseKey, int par3, EntityPlayer entityPlayer){
    	if(inventory instanceof IInventoryClickHandler){
    		return ((IInventoryClickHandler) inventory).slotClick(getSlotIndex(), mouseKey, par3, entityPlayer);
    	}
    	return null;
    }

}