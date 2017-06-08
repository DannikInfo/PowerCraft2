package ru.dannik.powercraft.network.server;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import ru.dannik.powercraft.blocks.logic.pulsar.Pulsar;
import ru.dannik.powercraft.blocks.logic.pulsar.TileEntityPulsar;
import ru.dannik.powercraft.network.AbstractMessage.AbstractServerMessage;

public class DelayPulsarMessage extends AbstractServerMessage<DelayPulsarMessage>{
	
	private int delay, hold, x, y, z;
	
	public DelayPulsarMessage() {}	

	public DelayPulsarMessage(int delay, int hold, int x, int y, int z) {
		this.delay = delay;
		this.hold = hold;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	protected void read(PacketBuffer buffer){
		delay = buffer.readInt();
		hold = buffer.readInt();
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(delay);
		buffer.writeInt(hold);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntityPulsar tile = (TileEntityPulsar) player.getEntityWorld().getTileEntity(x, y, z);
		tile.setTimes(delay, hold, player.getEntityWorld(), x, y, z);
	}

}
