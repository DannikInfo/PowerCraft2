package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import powercraft.api.inventory.PC_IInventory;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.tileentity.PC_TileEntity;

public class PC_PacketSyncPlayerInvTC_pt1 extends AbstractServerMessage<PC_PacketSyncPlayerInvTC_pt1> {

	int x, y, z;

	public PC_PacketSyncPlayerInvTC_pt1() {
	}

	public PC_PacketSyncPlayerInvTC_pt1(PC_TileEntity te) {
		if (te != null) {
			this.x = te.xCoord;
			this.y = te.yCoord;
			this.z = te.zCoord;
		} else {
			x = 0;
			y = 0;
			z = 0;
		}
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		PC_IInventory te = (PC_IInventory) player.worldObj.getTileEntity(x, y, z);
		if (side == Side.SERVER) {
			if (te == null) {
				//if(player.getCurrentEquippedItem().getItem() instanceof PCis_ItemCompressor){
				 //PCis_ItemCompressor item = (PCis_ItemCompressor)player.getCurrentEquippedItem().getItem();
				 //item.getInventoryFor(player, player.inventory.currentItem);
				 //PC_IInventory inv = (PC_IInventory)new PCis_NormalCompressorInventory(player,
				 //0);
				 //inv.syncInventory(2, player, 0);
				//}
			} else
				te.syncInventory(2, player, 0);
		}
	}
}
