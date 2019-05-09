package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.inventory.PC_IInventory;
import powercraft.api.network.AbstractMessage.AbstractClientMessage;
import powercraft.api.tileentity.PC_TileEntity;

public class PC_PacketSyncPlayerInvTC_pt2 extends AbstractClientMessage<PC_PacketSyncPlayerInvTC_pt2> {

	NBTTagCompound nbt = new NBTTagCompound();
	int slot;

	public PC_PacketSyncPlayerInvTC_pt2() {
	}

	public PC_PacketSyncPlayerInvTC_pt2(int slot, ItemStack is) {
		this.slot = slot;
		if (is == null)
			is = new ItemStack(Blocks.air);
		is.writeToNBT(nbt);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		slot = buffer.readInt();
		nbt = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(slot);
		buffer.writeNBTTagCompoundToBuffer(nbt);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		ItemStack is = ItemStack.loadItemStackFromNBT(nbt);
		if (side == Side.CLIENT)
			player.inventory.setInventorySlotContents(slot, is);
	}

}
