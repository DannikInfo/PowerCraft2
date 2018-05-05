package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import powercraft.api.network.AbstractMessage.AbstractServerMessage;
import powercraft.core.PCco_ClientMobSpawnerSetter;

public class PC_PacketSpawnerSet extends AbstractServerMessage<PC_PacketSpawnerSet>{
	
	String mob;
	int x, y, z;
	
	public PC_PacketSpawnerSet() {}	

	public PC_PacketSpawnerSet(int x, int y, int z, String mob) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mob = mob;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		mob = buffer.readStringFromBuffer(255);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeStringToBuffer(mob);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		new PCco_ClientMobSpawnerSetter().handleIncomingPacket(player, new Object[]{x, y, z, mob});
	}

}
