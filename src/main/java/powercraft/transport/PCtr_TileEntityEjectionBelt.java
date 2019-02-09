package powercraft.transport;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import powercraft.api.annotation.PC_ClientServerSync;
import powercraft.api.network.PC_IPacketHandler;
import powercraft.api.network.PC_PacketHandler;
import powercraft.api.network.packet.PC_PacketSyncTEClient;
import powercraft.api.tileentity.PC_TileEntity;
import powercraft.api.utils.PC_VecI;
import powercraft.api.utils.PC_WorldData;

public class PCtr_TileEntityEjectionBelt extends PC_TileEntity implements PC_IPacketHandler {
	public static Random rand = new Random();

	@PC_ClientServerSync
	public int actionType = 0;
	@PC_ClientServerSync
	public int numStacksEjected = 1;
	@PC_ClientServerSync
	public int numItemsEjected = 1;
	@PC_ClientServerSync
	public int itemSelectMode = 0;

	public boolean isActive = false;

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		if (this.actionType != actionType) {
			this.actionType = actionType;
		}
	}

	public int getNumStacksEjected() {
		return numStacksEjected;
	}

	public void setNumStacksEjected(int numStacksEjected) {
		if (this.numStacksEjected != numStacksEjected) {
			this.numStacksEjected = numStacksEjected;
		}
	}

	public int getNumItemsEjected() {
		return numItemsEjected;
	}

	public void setNumItemsEjected(int numItemsEjected) {
		if (this.numItemsEjected != numItemsEjected) {
			this.numItemsEjected = numItemsEjected;
		}
	}

	public int getItemSelectMode() {
		return itemSelectMode;
	}

	public void setItemSelectMode(int itemSelectMode) {
		if (this.itemSelectMode != itemSelectMode) {
			this.itemSelectMode = itemSelectMode;
			notifyChanges("itemSelectMode");
		}
	}

	@Override
	public final boolean canUpdate() {
		return true;
	}

	@Override
	public final void updateEntity() {
		if (!worldObj.isRemote) {
			PC_VecI pos = new PC_VecI(xCoord, yCoord, zCoord);
			PCtr_BlockBeltEjector block = (PCtr_BlockBeltEjector) worldObj.getBlock(xCoord, yCoord, zCoord);
			if (block.isPowered) {
				if (!isActive) {
					if (!PCtr_BeltHelper.dispenseStackFromNearbyMinecart(worldObj, pos)) {
						PCtr_BeltHelper.tryToDispenseItem(worldObj, pos);
					}
					isActive = true;
				}
			} else {
				isActive = false;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
			PC_WorldData data = PC_WorldData.forWorld(worldObj);
			NBTTagCompound tag = data.getData();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("actionType", this.actionType);
		tag.setInteger("itemSelectMode", this.itemSelectMode);
		tag.setInteger("numItemsEjected", this.numItemsEjected);
		tag.setInteger("numStacksEjected", this.numStacksEjected);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.actionType = tag.getInteger("actionType");
		this.itemSelectMode = tag.getInteger("itemSelectMode");
		this.numItemsEjected = tag.getInteger("numItemsEjected");
		this.numStacksEjected = tag.getInteger("numStacksEjected");
	}

	@Override
	public boolean handleIncomingPacket(EntityPlayer player, Object[] o) {
		if ((int) o[0] == 0) {
			PC_PacketHandler.sendToAll(new PC_PacketSyncTEClient(new Object[] { 1, getCoord(), this.actionType,
					this.itemSelectMode, this.numItemsEjected, this.numStacksEjected }));
		} else if ((int) o[0] == 1) {
			setActionType((int) o[2]);
			setItemSelectMode((int) o[3]);
			setNumItemsEjected((int) o[4]);
			setNumStacksEjected((int) o[5]);
		}
		return true;
	}

}
