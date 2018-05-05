package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.core.PCco_ClientMobSpawnerSetter;
import powercraft.transport.PCtr_TileEntitySeparationBeltBase;

public class PC_PacketSyncInv extends AbstractServerMessage<PC_PacketSyncInv>{
	
	NBTTagCompound nbt = new NBTTagCompound();
	int x, y, z, slot;
	
	public PC_PacketSyncInv() {}	

	public PC_PacketSyncInv(int x, int y, int z, int slot, ItemStack is) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.slot = slot;
		is.writeToNBT(nbt);
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		slot = buffer.readInt();
		nbt = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(slot);
		buffer.writeNBTTagCompoundToBuffer(nbt);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		PCtr_TileEntitySeparationBeltBase te = (PCtr_TileEntitySeparationBeltBase) player.worldObj.getTileEntity(x, y, z);
		ItemStack is = ItemStack.loadItemStackFromNBT(nbt);
		te.setInventorySlotContents(slot, is);
	}

}
