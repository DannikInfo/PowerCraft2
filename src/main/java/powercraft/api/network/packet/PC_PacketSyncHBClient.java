package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.network.AbstractMessage.AbstractClientMessage;
import powercraft.api.tileentity.PC_TileEntity;
import powercraft.api.utils.PC_VecI;
import powercraft.hologram.PChg_TileEntityHologramBlock;

public class PC_PacketSyncHBClient extends AbstractClientMessage<PC_PacketSyncHBClient> {

	private PC_VecI pos = new PC_VecI();
	private int sw, swMax;
	PC_TileEntity te;
	NBTTagCompound nbt = new NBTTagCompound();

	public PC_PacketSyncHBClient() {
	}

	public PC_PacketSyncHBClient(int sw, int swMax, PC_TileEntity te) {
		nbt.setInteger("sw", sw);
		nbt.setInteger("swMax", swMax);
		NBTTagCompound pos = new NBTTagCompound();
		this.pos = new PC_VecI(te.xCoord, te.yCoord, te.zCoord);
		this.pos.writeToNBT(pos);
		nbt.setTag("pos", pos);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		nbt = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeNBTTagCompoundToBuffer(nbt);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (side.isClient()) {
			if (nbt != null) {
				sw = nbt.getInteger("sw");
				swMax = nbt.getInteger("swMax");
				NBTTagCompound pos = (NBTTagCompound) nbt.getTag("pos");
				this.pos.readFromNBT(pos);
				PChg_TileEntityHologramBlock tile = (PChg_TileEntityHologramBlock) player.getEntityWorld()
						.getTileEntity(this.pos.x, this.pos.y, this.pos.z);
				System.out.println(sw);
				// tile.setSwitchBlock(sw);
				// tile.setSwitchBlockMax(swMax);
			}
		}
	}

}
