package powercraft.api.gres;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import powercraft.api.inventory.PC_IInventorySpecialSlots;
import powercraft.api.inventory.PC_IInventoryWrapper;
import powercraft.api.inventory.PC_Slot;
import powercraft.api.tileentity.PC_TileEntity;

public abstract class PC_GresBaseWithInventory<t extends PC_TileEntity> extends Container
{
    public EntityPlayer thePlayer;

    private static final int playerSlots = 9 * 4;

    public PC_Slot[][] inventoryPlayerUpper = new PC_Slot[9][3];

    public PC_Slot[][] inventoryPlayerLower = new PC_Slot[9][1];
    
    public PC_Slot[] invSlots;

    protected t tileEntity;
    
    protected List<PC_Slot> invSlot;
    
    public PC_GresBaseWithInventory(EntityPlayer player, t te, Object[] o){
        thePlayer = player;
        
        tileEntity = te;
        if (thePlayer != null)
        {
            for (int i = 0; i < 9; i++)
            {
                inventoryPlayerLower[i][0] = new PC_Slot(player.inventory, i);
                addSlotToContainer(inventoryPlayerLower[i][0]);
            }

            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    inventoryPlayerUpper[i][j] = new PC_Slot(player.inventory, i + j * 9 + 9);
                    addSlotToContainer(inventoryPlayerUpper[i][j]);
                }
            }
        }
    }
    
/* protected void init(Object[] o){}
    
   protected PC_Slot[] getAllSlots(){
    	IInventory inv = null;
    	if(tileEntity instanceof IInventory){
    		inv = (IInventory)tileEntity;
    	}else if(tileEntity instanceof PC_IInventoryWrapper){
    		inv = ((PC_IInventoryWrapper) tileEntity).getInventory();
    	}else{
    		return null;
    	}
    	invSlots = new PC_Slot[inv.getSizeInventory()];
    	for(int i=0; i<invSlots.length; i++){
    		if(inv instanceof PC_IInventorySpecialSlots){
    			invSlots[i] = ((PC_IInventorySpecialSlots) inv).getSlot(i);
    		}else{
    			invSlots[i] = new PC_Slot(inv, i);
    		}
    	}
    	return invSlots;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    protected boolean canShiftTransfer()
    {
        return false;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        if (slotIndex < playerSlots && !canShiftTransfer())
        {
            return null;
        }

        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex < playerSlots)
            {
                if (!mergeItemStack(itemstack1, playerSlots, inventorySlots.size(), false))
                {
                    return null;
                }
                else
                {
                    slot.onPickupFromSlot(player, itemstack);
                }
            }
            else if (!mergeItemStack(itemstack1, 0, playerSlots, false))
            {
                return null;
            }
            else
            {
                slot.onPickupFromSlot(player, itemstack);
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
                slot.onSlotChanged();
            }
            else
            {
                slot.onSlotChanged();
            }
        }
         
        return itemstack;
    }

    private int getLimit(Slot slot, int a, boolean flag)
    {
        if (flag)
        {
            return a;
        }

        return Math.min(a, slot.getSlotStackLimit());
    }

    /*  @Override
    protected boolean mergeItemStack(ItemStack itemstack, int i, int j, boolean flag)
    {
  boolean flag1 = false;
        int k = p_75135_2_;

        if (p_75135_4_)
        {
            k = p_75135_3_ - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (p_75135_1_.isStackable())
        {
            while (p_75135_1_.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == p_75135_1_.getItem() && (!p_75135_1_.getHasSubtypes() || p_75135_1_.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(p_75135_1_, itemstack1))
                {
                    int l = itemstack1.stackSize + p_75135_1_.stackSize;

                    if (l <= p_75135_1_.getMaxStackSize())
                    {
                        p_75135_1_.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < p_75135_1_.getMaxStackSize())
                    {
                        p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = p_75135_1_.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (p_75135_4_)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (p_75135_1_.stackSize > 0)
        {
            if (p_75135_4_)
            {
                k = p_75135_3_ - 1;
            }
            else
            {
                k = p_75135_2_;
            }

            while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null)
                {
                    slot.putStack(p_75135_1_.copy());
                    slot.onSlotChanged();
                    p_75135_1_.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (p_75135_4_)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

       return true;
    }*/
    
    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer){
	    	if ((par3 == 0 || par3 == 1) && (par2 == 0 || par2 == 1)){
	    		if (par1 >= 0 && par3 != 1){
	    			Slot slot = (Slot)this.inventorySlots.get(par1);
	
	             if(slot instanceof PC_Slot){
	                	if(((PC_Slot) slot).isHandlingSlotClick()){
	                		return ((PC_Slot) slot).slotClick(par2, par3, par4EntityPlayer);
	                	}
	             }
	    		}
	    }
	    	return super.slotClick(par1, par2, par3, par4EntityPlayer);
    }
    
}
