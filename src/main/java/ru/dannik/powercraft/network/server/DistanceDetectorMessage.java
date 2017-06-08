package ru.dannik.powercraft.network.server;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import ru.dannik.powercraft.blocks.BlockList;
import ru.dannik.powercraft.blocks.logic.detector.TileEntityDetector;
import ru.dannik.powercraft.blocks.logic.detector.TileEntityDetectorOff;
import ru.dannik.powercraft.network.AbstractMessage.AbstractServerMessage;

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
		if(player.getEntityWorld().getBlock(x, y, z) == BlockList.detector){
			TileEntityDetector tile = (TileEntityDetector) player.getEntityWorld().getTileEntity(x, y, z);
			tile.setRange(distance, player.getEntityWorld(), x, y, z);
		}else{
			if(player.getEntityWorld().getBlock(x, y, z) == BlockList.detectoroff){
				TileEntityDetectorOff tile = (TileEntityDetectorOff) player.getEntityWorld().getTileEntity(x, y, z);
				tile.setRange(distance, player.getEntityWorld(), x, y, z);
			}
		}
	}

}
