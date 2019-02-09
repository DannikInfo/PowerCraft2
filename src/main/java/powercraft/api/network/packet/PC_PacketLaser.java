package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.network.AbstractMessage.AbstractClientMessage;
import powercraft.api.network.PC_IPacketHandler;
import powercraft.api.utils.PC_Serializer;
import powercraft.light.PCli_TileEntityLaser;

public class PC_PacketLaser extends AbstractClientMessage<PC_PacketLaser> {

	NBTTagCompound nbt = new NBTTagCompound();

	public PC_PacketLaser() {
	}

	public PC_PacketLaser(Object... o) {
		PC_Serializer s = new PC_Serializer();
		byte[] b = null;
		if (o != null) {
			try {
				b = s.serialize(o);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (b != null)
				nbt.setByteArray("bytesObject", b);
		}
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
		if (side == Side.CLIENT) {
			PC_Serializer s = new PC_Serializer();
			Object[] o = null;
			if (nbt != null) {
				try {
					try {
						o = (Object[]) s.deserialize(nbt.getByteArray("bytesObject"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			PCli_TileEntityLaser te = (PCli_TileEntityLaser) player.worldObj.getTileEntity((int) o[0], (int) o[1],
					(int) o[2]);
			te.setPowered((boolean) o[3]);
		}
	}
}
