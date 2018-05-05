package ru.dannikinfo.powercraft.transport.tile;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.transport.belt.BeltEjector;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;

public class TileEntityEjectionBelt extends TileEntity{
	public static Random rand = new Random();

    public int actionType = 0;
    public int numStacksEjected = 0;
    public int numItemsEjected = 0;
    public int itemSelectMode = 0;
    VecI pos = new VecI(xCoord, yCoord, zCoord);
	public boolean isActive = false;
	public boolean guiOpen = false;
	NBTTagCompound data = new NBTTagCompound();
    
    
	public TileEntityEjectionBelt(){
		
	}
    
    public int getActionType() {
		return actionType;
	}
    
    public void getData(){
    		//PacketManager.sendToAll(new EjectBeltMessage(actionType, itemSelectMode, numStacksEjected, numItemsEjected, xCoord, yCoord, zCoord));
    }

	public void setActionType(int actionType) {
		if(this.actionType != actionType){
			this.actionType = actionType;
		}
	}

	public int getNumStacksEjected() {
		markDirty();
		return numStacksEjected;
	}

	public void setNumStacksEjected(int numStacksEjected) {
		if(this.numStacksEjected != numStacksEjected){
			this.numStacksEjected = numStacksEjected;
		}
	}

	public int getNumItemsEjected() {
		markDirty();
		return numItemsEjected;
	}

	public void setNumItemsEjected(int numItemsEjected) {
		if(this.numItemsEjected != numItemsEjected){
			this.numItemsEjected = numItemsEjected;
		}
	}

	public int getItemSelectMode() {
		markDirty();
		return itemSelectMode;
	}

	public void setItemSelectMode(int itemSelectMode) {
		if(this.itemSelectMode != itemSelectMode){
			this.itemSelectMode = itemSelectMode;
		}
	}

	@Override
    public final boolean canUpdate(){
        return true;
    }

    @Override
    public final void updateEntity() {
	    	if(!worldObj.isRemote){
		    	VecI pos = new VecI(xCoord, yCoord, zCoord);
		    	BeltEjector block = (BeltEjector) worldObj.getBlock(xCoord, yCoord, zCoord);
		    	if(block.isPowered){
			        if(!isActive){
				        if (!BeltHelper.dispenseStackFromNearbyMinecart(worldObj, pos)){
				        		BeltHelper.tryToDispenseItem(worldObj, pos);
				        }
				        isActive = true;
			        }
		    	}else{
		    		isActive = false;
		    	}
		    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		    	markDirty();
			WorldData data = WorldData.forWorld(worldObj);
			NBTTagCompound tag = data.getData();
	    	}
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setInteger("actionType", this.actionType);
        tag.setInteger("itemSelectMode", this.itemSelectMode);
        tag.setInteger("numItemsEjected", this.numItemsEjected);
        tag.setInteger("numStacksEjected", this.numStacksEjected);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.actionType = tag.getInteger("actionType");
        this.itemSelectMode = tag.getInteger("itemSelectMode");
        this.numItemsEjected = tag.getInteger("numItemsEjected");
        this.numStacksEjected = tag.getInteger("numStacksEjected");
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
    
}
