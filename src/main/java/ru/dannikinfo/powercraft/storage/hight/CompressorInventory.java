package ru.dannikinfo.powercraft.storage.hight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.dannikinfo.powercraft.api.inventory.PCIInventory;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.storage.ItemsStorage;

public abstract class CompressorInventory implements PCIInventory {

	protected EntityPlayer player;
	protected VecI size;
	protected int equipped;
	protected ItemStack compressor;
	
	public CompressorInventory(EntityPlayer player, int equipment, VecI size){
		this.player = player;
		equipped = equipment;
		compressor = player.inventory.getStackInSlot(equipped);
		this.size = size;
	}
	
	public VecI getSize(){
		return size.copy();
	}

	@Override
	public String getInventoryName() {
		return "Compressor";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public boolean canPlayerInsertStackTo(int slot, ItemStack stack) {
		boolean s = false;
		if(stack.getItem() != ItemsStorage.Compressor) s = true;
		if(stack.getItem() != ItemsStorage.BigCompressor) s = true;
		if(stack.getItem() != ItemsStorage.EnderCompressor) s = true;
		if(stack.getItem() != ItemsStorage.HighStackCompressor) s = true;
		return s;
	}

	@Override
	public boolean canDispenseStackFrom(int slot) {
		return false;
	}

	@Override
	public boolean canDropStackFrom(int slot) {
		return false;
	}
	

	@Override
	public int getSlotStackLimit(int slotIndex) {
		return getInventoryStackLimit();
	}

	@Override
	public boolean canPlayerTakeStack(int slotIndex, EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

}
