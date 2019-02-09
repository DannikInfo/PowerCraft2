package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import powercraft.api.inventory.PC_IInventory;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.tileentity.PC_TileEntity;

public class PC_PacketSyncInvTC_pt1 extends AbstractServerMessage<PC_PacketSyncInvTC_pt1> {

	int x, y, z, slot;

	public PC_PacketSyncInvTC_pt1() {
	}

	public PC_PacketSyncInvTC_pt1(PC_TileEntity te, int slot) {
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
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		slot = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(slot);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		PC_IInventory te = (PC_IInventory) player.worldObj.getTileEntity(x, y, z);
		if (side == Side.SERVER) {
			if (te == null) {
				// PC_IInventory item =
				// (PC_IInventory)player.getCurrentEquippedItem().getItem();
				// item.syncInventory(1, player);
				// if(player.getCurrentEquippedItem().getItem() instanceof PCis_ItemCompressor)
				// {
				// PCis_ItemCompressor item =
				// (PCis_ItemCompressor)player.getCurrentEquippedItem().getItem();
				// item.getInventoryFor(player,
				// player.getCurrentEquippedItem().geti)//.syncInventory(2, player);
				// PCis_ItemCompressor item =
				// (PCis_ItemCompressor)player.getCurrentEquippedItem().getItem();
				// PCis_CompressorInventory inv =
				// (PCis_CompressorInventory)item.getInventoryFor(player,
				// player.inventory.currentItem);
				// inv.setInventorySlotContents(0, new ItemStack(Blocks.acacia_stairs));
				// inv.syncInventory(1, player);
				// }
			} else {
				te.syncInventory(1, player);
			}
		}
	}

}
