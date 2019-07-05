package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.api.network.PC_IPacketHandler;
import powercraft.api.utils.PC_Serializer;
import powercraft.mobile.PCmo_EntityMiner;

public class PC_PacketSyncMinerClient extends AbstractServerMessage<PC_PacketSyncMinerClient> {

	NBTTagCompound nbt = new NBTTagCompound();

	public PC_PacketSyncMinerClient() {
	}

	public PC_PacketSyncMinerClient(Object... o) {
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
		if (side.isClient()) {
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
			if(player.worldObj.getEntityByID((int)o[0]) instanceof PCmo_EntityMiner) {
				PCmo_EntityMiner miner = (PCmo_EntityMiner)player.worldObj.getEntityByID((int)o[0]);
				switch((int)o[1]) {
				case 0:
					miner.doInfoSet((String)o[2], (Object[])o[3]);
					break;
				case 1:
					miner.setInfo((String)o[2], (Object[])o[3]);
					break;
				}
			}
		}
	}
}
