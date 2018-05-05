package ru.dannikinfo.powercraft.machines.chunker;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import ru.dannikinfo.powercraft.api.beam.EntityLaserFX;
import ru.dannikinfo.powercraft.api.network.AbstractMessage.AbstractClientMessage;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;

public class BeamClientChunkerMessage extends AbstractClientMessage<BeamClientChunkerMessage>{
	
private int x, y, z, x1, y1, z1, r, g, b, death;
	
	public BeamClientChunkerMessage() {}	

	public BeamClientChunkerMessage( int x, int y, int z, int x1, int y1, int z1, int r, int g, int b, int death) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.r = r;
		this.g = g;
		this.b = b;
		this.death = death;
	}
	
	@Override
	protected void read(PacketBuffer buffer){
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		x1 = buffer.readInt();
		y1 = buffer.readInt();
		z1 = buffer.readInt();
		r = buffer.readInt();
		g = buffer.readInt();
		b = buffer.readInt();
		death = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(x1);
		buffer.writeInt(y1);
		buffer.writeInt(z1);
		buffer.writeInt(r);
		buffer.writeInt(g);
		buffer.writeInt(b);
		buffer.writeInt(death);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(side == Side.CLIENT)
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityLaserFX(player.worldObj, x, y, z, x1, y1, z1, 3, r, g, b, death));
	}
}
