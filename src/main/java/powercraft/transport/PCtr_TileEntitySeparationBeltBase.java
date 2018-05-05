package powercraft.transport;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import powercraft.api.annotation.PC_ClientServerSync;
import powercraft.api.tileentity.PC_TileEntity;

public abstract class PCtr_TileEntitySeparationBeltBase extends PC_TileEntity{

	public static InventoryBasic sepInv;
	//protected ItemStack separatorContents[];

	@PC_ClientServerSync
	private boolean group_logs;
	@PC_ClientServerSync
	private boolean group_planks;
	@PC_ClientServerSync
	private boolean group_all;
	
	public boolean isGroupLogs() {
		return group_logs;
	}

	public void setGroupLogs(boolean state) {
		if(group_logs != state){
			group_logs = state;
			//notifyChanges("group_logs");
		}
	}

	public boolean isGroupPlanks() {
		return group_planks;
	}

	public void setGroupPlanks(boolean state) {
		if(group_planks != state){
			group_planks = state;
			//notifyChanges("group_planks");
		}
	}

	public boolean isGroupAll() {
		return group_all;
	}

	public void setGroupAll(boolean state) {
		if(group_all != state){
			group_all = state;
			//notifyChanges("group_all");
		}
	}
	
	@Override
    public final boolean canUpdate(){
        return true;
    }
	
	@Override
	public void updateEntity() {
    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		markDirty();
	}
	
    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < this.sepInv.getSizeInventory(); ++i)
        {
            if(this.sepInv.getStackInSlot(i) != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                this.sepInv.getStackInSlot(i).writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        
        compound.setTag("Items", list);
        this.markDirty();
    }

    /**
     *   Данный метод будет читать NBT и выводить значения из него.
     */
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList list = compound.getTagList("Items", 10);

        for(int i = 0; i < list.tagCount(); ++i){
            NBTTagCompound tag = list.getCompoundTagAt(i);
            int j = tag.getByte("Slot") & 255;

            if(j >= 0 && j < this.sepInv.getSizeInventory()){
                this.sepInv.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(tag));
            }
        }
        this.markDirty();
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