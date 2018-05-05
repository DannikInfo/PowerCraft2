package ru.dannikinfo.powercraft.storage.hight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.InventoryUtils;
import ru.dannikinfo.powercraft.api.utils.VecI;

public class NormalCompressorInventory extends CompressorInventory {

	protected ItemStack[] is;
	
	protected NormalCompressorInventory(EntityPlayer player, int equipment, VecI size) {
		super(player, equipment, size);
		NBTTagCompound tag = compressor.getTagCompound();
		if(tag==null){
			compressor.setTagCompound(tag = new NBTTagCompound());
			BaseUtils.saveToNBT(tag, "invSize", size);
			is = new ItemStack[size.x*size.y];
		}else{
			BaseUtils.loadFromNBT(tag, "invSize", size);
			is = new ItemStack[size.x*size.y];
			InventoryUtils.loadInventoryFromNBT(tag, "inv", this);
		}
	}
	
	public NormalCompressorInventory(EntityPlayer player, int equipment) {
		this(player, equipment, new VecI(9, 3));
	}

	@Override
	public int getSizeInventory() {
		return is.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return is[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		if (is[var1] != null) {
			if (is[var1].stackSize <= var2) {
				ItemStack itemstack = is[var1];
				is[var1] = null;
				return itemstack;
			}
			ItemStack itemstack1 = splitStack(is[var1], var2);
			if (is[var1].stackSize == 0) {
				is[var1] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return is[var1];
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		is[var1] = var2;
	}

	@Override
	public void closeInventory() {
		ItemStack backpack = player.inventory.getStackInSlot(equipped);
		if(backpack!=null)
			InventoryUtils.saveInventoryToNBT(backpack.getTagCompound(), "inv", this);
	}

	public ItemStack splitStack(ItemStack is, int par1) {
        ItemStack var2 = new ItemStack(is.getItem(), par1, is.getItemDamage());

        if (is.stackTagCompound != null){
            var2.stackTagCompound = (NBTTagCompound)is.stackTagCompound.copy();
        }

        is.stackSize -= par1;
        return var2;
    }

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
