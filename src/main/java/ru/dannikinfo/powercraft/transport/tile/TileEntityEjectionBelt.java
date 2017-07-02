package ru.dannikinfo.powercraft.transport.tile;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.transport.belt.BeltEjector;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;
import ru.dannikinfo.powercraft.transport.belt.EjectBeltMessage;
import ru.dannikinfo.powercraft.transport.gui.GuiEjectBelt;

public class TileEntityEjectionBelt extends TileEntity{
	public static Random rand = new Random();

    public int actionType = 0;
    public int numStacksEjected = 0;
    public int numItemsEjected = 0;
    public int itemSelectMode = 0;
    VecI pos = new VecI(xCoord, yCoord, zCoord);
	public boolean isActive = false;
	public boolean guiOpen = false;
    
    
	public TileEntityEjectionBelt(){
		
	}
    
    public int getActionType() {
		return actionType;
	}
    public void getData(){
    	PacketManager.sendToAll(new EjectBeltMessage(actionType, itemSelectMode, numStacksEjected, numItemsEjected, xCoord, yCoord, zCoord));
    }


	public void setActionType(int actionType) {
		if(this.actionType != actionType){
			this.actionType = actionType;
		}
	}

	public int getNumStacksEjected() {
		return numStacksEjected;
	}

	public void setNumStacksEjected(int numStacksEjected) {
		if(this.numStacksEjected != numStacksEjected){
			this.numStacksEjected = numStacksEjected;
		}
	}

	public int getNumItemsEjected() {
		return numItemsEjected;
	}

	public void setNumItemsEjected(int numItemsEjected) {
		if(this.numItemsEjected != numItemsEjected){
			this.numItemsEjected = numItemsEjected;
		}
	}

	public int getItemSelectMode() {
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
	    	if(!guiOpen){
	    		if(GuiEjectBelt.x != 0) getData();
	    		guiOpen = true;
	    	}
    	}
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
    }

}
