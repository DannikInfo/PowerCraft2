package ru.dannikinfo.powercraft.teleporter;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTeleporter extends TileEntity {

	public String name;
	Map<Integer, TileEntity> m = new HashMap<Integer, TileEntity>();
	
	public TileEntityTeleporter() {

	}

	public void updateEntity(){
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
		TeleporterManager t = new TeleporterManager();
		//if(m == null) m = t.getMap(worldObj);
		//System.out.println(m.get(0));
	}
	
    @Override
    public Packet getDescriptionPacket()
    {
        super.getDescriptionPacket();        
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.func_148857_g());
    }
    
	
	public String getName(){
		return name;
	}
}
