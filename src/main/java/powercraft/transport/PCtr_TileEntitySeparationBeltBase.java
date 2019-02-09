package powercraft.transport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercraft.api.inventory.PC_IInventory;
import powercraft.api.inventory.PC_InventoryUtils;
import powercraft.api.network.PC_PacketHandler;
import powercraft.api.network.packet.PC_PacketSyncInv;
import powercraft.api.network.packet.PC_PacketSyncInvTC_pt2;
import powercraft.api.network.packet.PC_PacketSyncPlayerInv;
import powercraft.api.network.packet.PC_PacketSyncPlayerInvTC_pt2;

public abstract class PCtr_TileEntitySeparationBeltBase extends PCtr_TileEntityRedirectionBeltBase
		implements PC_IInventory {

	public ItemStack separatorContents[];

	// @PC_ClientServerSync
	private boolean group_logs;
	// @PC_ClientServerSync
	private boolean group_planks;
	// @PC_ClientServerSync
	private boolean group_all;

	@Override
	public void syncInventory(int side, EntityPlayer player) {
		if (side == 0) {
			if (worldObj.isRemote) {
				for (int i = 0; i < this.getSizeInventory(); i++) {
					PC_PacketHandler.sendToServer(new PC_PacketSyncInv(this, i, this.getStackInSlot(i)));
				}
				for (int c = 0; c < player.inventory.getSizeInventory(); c++) {
					PC_PacketHandler.sendToServer(new PC_PacketSyncPlayerInv(c, player.inventory.getStackInSlot(c)));
				}
			}
		}
		if (side == 1) {
			if (!worldObj.isRemote) {
				for (int i = 0; i < this.getSizeInventory(); i++) {
					PC_PacketHandler.sendToAllAround(new PC_PacketSyncInvTC_pt2(this, i, this.getStackInSlot(i)),
							player.dimension, xCoord, yCoord, zCoord, 10);
				}

			}
		}
		if (side == 2) {
			if (!worldObj.isRemote) {
				for (int c = 0; c < player.inventory.getSizeInventory(); c++) {
					PC_PacketHandler.sendToAllAround(
							new PC_PacketSyncPlayerInvTC_pt2(c, player.inventory.getStackInSlot(c)), player.dimension,
							xCoord, yCoord, zCoord, 2);
				}
			}
		}
	}

	public boolean isGroupLogs() {
		return group_logs;
	}

	public void setGroupLogs(boolean state) {
		if (group_logs != state) {
			group_logs = state;
		}
	}

	public boolean isGroupPlanks() {
		return group_planks;
	}

	public void setGroupPlanks(boolean state) {
		if (group_planks != state) {
			group_planks = state;
		}
	}

	public boolean isGroupAll() {
		return group_all;
	}

	public void setGroupAll(boolean state) {
		if (group_all != state) {
			group_all = state;
		}
	}

	@Override
	public int getSizeInventory() {
		return separatorContents.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return separatorContents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (separatorContents[i] != null) {
			if (separatorContents[i].stackSize <= j) {
				ItemStack itemstack = separatorContents[i];
				separatorContents[i] = null;
				this.markDirty();
				return itemstack;
			}

			ItemStack itemstack1 = separatorContents[i].splitStack(j);

			if (separatorContents[i].stackSize == 0) {
				separatorContents[i] = null;
			}

			this.markDirty();
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		separatorContents[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Item Separator";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		PC_InventoryUtils.loadInventoryFromNBT(nbttagcompound, "Items", this);
		group_all = nbttagcompound.getBoolean("groupAll");
		group_logs = nbttagcompound.getBoolean("groupLogs");
		group_planks = nbttagcompound.getBoolean("groupPlanks");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		PC_InventoryUtils.saveInventoryToNBT(nbttagcompound, "Items", this);
		nbttagcompound.setBoolean("groupAll", group_all);
		nbttagcompound.setBoolean("groupLogs", group_logs);
		nbttagcompound.setBoolean("groupPlanks", group_planks);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (separatorContents[par1] != null) {
			ItemStack itemstack = separatorContents[par1];
			separatorContents[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public boolean canPlayerInsertStackTo(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public boolean canDispenseStackFrom(int slot) {
		return false;
	}

	@Override
	public boolean canDropStackFrom(int slot) {
		return true;
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
		return PC_InventoryUtils.makeIndexList(0, 0);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
	}

}