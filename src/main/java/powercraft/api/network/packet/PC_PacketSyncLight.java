package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.tileentity.PC_TileEntity;
import powercraft.api.utils.PC_Color;
import powercraft.api.utils.PC_VecI;
import powercraft.light.PCli_TileEntityLight;

public class PC_PacketSyncLight extends AbstractServerMessage<PC_PacketSyncLight> {

	private PC_VecI pos = new PC_VecI();
	private boolean s, h;
	PC_TileEntity te;
	NBTTagCompound nbt = new NBTTagCompound();
	private PC_Color color = new PC_Color();

	public PC_PacketSyncLight() {
	}

	public PC_PacketSyncLight(PC_Color color, boolean h, boolean s, PC_TileEntity te) {
		NBTTagCompound c = new NBTTagCompound();
		color.writeToNBT(c);
		nbt.setTag("color", c);
		nbt.setBoolean("isStable", s);
		nbt.setBoolean("isHuge", h);
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
		if (side.isServer()) {
			if (nbt != null) {
				NBTTagCompound c = (NBTTagCompound) nbt.getTag("color");
				color.readFromNBT(c);
				s = nbt.getBoolean("isStable");
				h = nbt.getBoolean("isHuge");
				NBTTagCompound pos = (NBTTagCompound) nbt.getTag("pos");
				this.pos.readFromNBT(pos);
				PCli_TileEntityLight tile = (PCli_TileEntityLight) player.getEntityWorld().getTileEntity(this.pos.x,
						this.pos.y, this.pos.z);

				tile.setColor(color);
				tile.setHuge(h);
				tile.setStable(s);
			}
		}
	}

}
