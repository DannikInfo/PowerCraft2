package powercraft.api.network.packet;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.tileentity.PC_TileEntity;
import powercraft.hologram.PChg_TileEntityHologramBlock;

public class PC_PacketSyncHBFCTS extends AbstractServerMessage<PC_PacketSyncHBFCTS> {

	private int x, y, z;
	PC_TileEntity te;

	public PC_PacketSyncHBFCTS() {
	}

	public PC_PacketSyncHBFCTS(PC_TileEntity te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
	}

	@Override
	protected void read(PacketBuffer buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		PChg_TileEntityHologramBlock tile = (PChg_TileEntityHologramBlock) player.getEntityWorld().getTileEntity(x, y,
				z);
		// if(side == Side.SERVER)
		// tile.getData();
	}

}
