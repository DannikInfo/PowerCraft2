package ru.dannikinfo.powercraft.transport.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;

public class TileEntitySplitter extends TileEntity {
	
	public InventoryBasic basic;

	public TileEntitySplitter(){
		basic = new InventoryBasic("splitter", false, 30);
	}
	
	private Direction getDir(int dir){
		return Direction.getFromMCDir(dir);
	}
	
	public Direction getDirection(Entity entity) {
		boolean notItem = !(entity instanceof EntityItem);

        ItemStack itemstack = BeltHelper.getItemStackForEntity(entity);
        
        if (itemstack == null){
            return Direction.TOP;
        }

        int count[] = {0, 0, 0, 0, 0, 0};

        for(int s=0; s<6; s++){
        	for(int i=0; i<5; i++){
        		ItemStack stack = basic.getStackInSlot(s*5+i);
        		if (stack != null){
        			count[s] += stack.stackSize;
                }
        	}
        }

        int dir = -1;
        for(int s=0; s<6; s++){
        	if(count[s]>0){
        		if(dir==-1)
        			dir=s;
        		else
        			break;
        	}
        	if(s==5){
        		if(dir==-1)
        			dir = 0;
        		return getDir(dir);
        	}
        }
        
        int all=0;
        for(int s=0; s<6; s++){
        	all += count[s];
        }
        
        if(all==0){
        	return Direction.TOP;
        }
        
        float clip = 0;
        
        for(int s=0; s<6; s++){
        	float f = (float)count[s]/(float)all*(float)itemstack.stackSize+clip;
        	clip = f-(int)f;
        	count[s] = (int)f;
        }
        
        if (notItem){
        	int bestDir = -1;
        	int max=count[0];
        	for(int s=1; s<6; s++){
        		if(max<count[s]){
        			max = count[s];
        			bestDir=s;
        		}
        	}
        	Direction pcdir = getDir(bestDir);
        	return pcdir;
        }
        
        if(!worldObj.isRemote){
	        for(int s=0; s<6; s++){
	        	if(count[s]>0){
	        		ItemStack stack = itemstack.copy();
	        		stack.stackSize = count[s];
	        		EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, stack);
	        		entityitem.motionX = entity.motionX;
	        		entityitem.motionY = entity.motionY;
	        		entityitem.motionZ = entity.motionZ;
	                worldObj.spawnEntityInWorld(entityitem);
	        	}
	        }
        }
        entity.setDead();

        return Direction.TOP;
	}
	
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList list = compound.getTagList("Items", 10);

        for(int i = 0; i < list.tagCount(); ++i){
            NBTTagCompound tag = list.getCompoundTagAt(i);
            int j = tag.getByte("Slot") & 255;

            if(j >= 0 && j < this.basic.getSizeInventory()){
                this.basic.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(tag));
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < this.basic.getSizeInventory(); ++i)
        {
            if(this.basic.getStackInSlot(i) != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                this.basic.getStackInSlot(i).writeToNBT(tag);
                list.appendTag(tag);
            }
        }

        compound.setTag("Items", list);
    }

}

