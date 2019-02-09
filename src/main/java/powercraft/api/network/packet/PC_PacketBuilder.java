package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.PC_BeamTracer;
import powercraft.api.interfaces.PC_IBeamHandlerExt;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.utils.PC_Serializer;
import powercraft.api.utils.PC_VecI;

public class PC_PacketBuilder extends AbstractServerMessage<PC_PacketBuilder> {

	NBTTagCompound nbt = new NBTTagCompound();

	public PC_PacketBuilder() {
	}

	public PC_PacketBuilder(Object... o) {
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
		if (side == Side.SERVER) {
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
				PC_VecI bc = (PC_VecI) o[0];
				Block block = player.worldObj.getBlock(bc.x, bc.y, bc.z);
				PC_BeamTracer beam = new PC_BeamTracer(player.worldObj, null);
				beam.setStartCoord((PC_VecI) o[1]);
				Block block2 = Block.getBlockById((int) o[2]);
				if (block instanceof PC_IBeamHandlerExt)
					((PC_IBeamHandlerExt) block).onBlockHit(beam, block2, (PC_VecI) o[3]);
			}
		}
	}
}
