package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.inventory.PC_IInventory;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.tileentity.PC_TileEntity;

public class PC_PacketSyncInv extends AbstractServerMessage<PC_PacketSyncInv> {

	NBTTagCompound nbt = new NBTTagCompound();
	int x, y, z, slot;

	public PC_PacketSyncInv() {
	}

	public PC_PacketSyncInv(PC_TileEntity te, int slot, ItemStack is) {
		if (te != null) {
			this.x = te.xCoord;
			this.y = te.yCoord;
			this.z = te.zCoord;
		} else {
			x = 0;
			y = 0;
			z = 0;
		}
		this.slot = slot;
		if (is == null)
			is = new ItemStack(Blocks.air);
		is.writeToNBT(nbt);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
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
		PC_IInventory te = (PC_IInventory) player.worldObj.getTileEntity(x, y, z);
		ItemStack is = ItemStack.loadItemStackFromNBT(nbt);
		if (side == Side.SERVER) {
			if (te == null) {
				// if(player.getCurrentEquippedItem().getItem() instanceof PCis_ItemCompressor)
				// {
				// PCis_ItemCompressor item =
				// (PCis_ItemCompressor)player.getCurrentEquippedItem().getItem();
				// int slot2;
				// for(slot2 = 0; slot2 < player.inventory.getSizeInventory(); slot2++) {
				// if(player.inventory.getStackInSlot(slot2) != null)
				// if(player.inventory.getStackInSlot(slot2) == player.getCurrentEquippedItem())
				// break;
				// }

				// PC_IInventory inv = item.getInventoryFor(player, slot2);
				// inv.setInventorySlotContents(0, new ItemStack(Blocks.acacia_stairs));
				// inv.setInventorySlotContents(slot, is);
				// System.out.println(slot2);
				// }
			} else
				te.setInventorySlotContents(slot, is);
		}
	}

}
