package ru.dannikinfo.powercraft.logic.pulsar;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import ru.dannikinfo.powercraft.api.network.AbstractMessage.AbstractServerMessage;
import ru.dannikinfo.powercraft.logic.BlocksLogic;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetector;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetectorOff;

public class DistanceDetectorMessage extends AbstractServerMessage<DistanceDetectorMessage>{
	
	private int distance, x, y, z;
	
	public DistanceDetectorMessage() {}	

	public DistanceDetectorMessage(int distance, int x, int y, int z) {
		this.distance = distance;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	protected void read(PacketBuffer buffer){
		distance = buffer.readInt();
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(distance);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.getEntityWorld().getBlock(x, y, z) == BlocksLogic.detector){
			TileEntityDetector tile = (TileEntityDetector) player.getEntityWorld().getTileEntity(x, y, z);
			tile.setRange(distance, player.getEntityWorld(), x, y, z);
		}else{
			if(player.getEntityWorld().getBlock(x, y, z) == BlocksLogic.detectoroff){
				TileEntityDetectorOff tile = (TileEntityDetectorOff) player.getEntityWorld().getTileEntity(x, y, z);
				tile.setRange(distance, player.getEntityWorld(), x, y, z);
			}
		}
	}

}
