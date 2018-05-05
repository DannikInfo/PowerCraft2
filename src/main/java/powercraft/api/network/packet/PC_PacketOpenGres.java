package powercraft.api.network.packet;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import powercraft.api.network.AbstractMessage.AbstractClientMessage;
import powercraft.api.registry.PC_GresRegistry;
import powercraft.api.tileentity.PC_TileEntity;

public class PC_PacketOpenGres extends AbstractClientMessage<PC_PacketOpenGres>{
	
	String name;
	int x, y, z, id;
	NBTTagCompound nbt;
	
	public PC_PacketOpenGres() {}	

	public PC_PacketOpenGres(String name,  TileEntity te, int id) {
		this.name = name;
		if(te != null){
			this.x = te.xCoord;
			this.y = te.yCoord;
			this.z = te.zCoord;
		}else {
			this.x = 999999999;
		}
		this.id = id;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException{
		name = buffer.readStringFromBuffer(255);
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		id = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeStringToBuffer(name);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(id);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te;
		if(x != 999999999){
			te = player.getEntityWorld().getTileEntity(x, y, z);
		}else {
			te = null;
		}
		PC_GresRegistry.openGres(name, player, te, new Object[] {id, x, y, z});
	}

}
