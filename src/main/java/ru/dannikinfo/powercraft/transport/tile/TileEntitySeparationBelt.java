package ru.dannikinfo.powercraft.transport.tile;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;
import ru.dannikinfo.powercraft.transport.gui.ContainerSepBelt;

public class TileEntitySeparationBelt extends TileEntity{
	
    public InventoryBasic basic;

	public TileEntitySeparationBelt(){
		basic = new InventoryBasic("sepbelt", false, 18);
	}
    
    public Direction getDirection(Entity entity){
        boolean notItem = !(entity instanceof EntityItem);

        ItemStack itemstack = BeltHelper.getItemStackForEntity(entity);
        
        if (itemstack == null){
            return Direction.FRONT;
        }

        int countLeft = 0;
        int countRight = 0;

        for (int i = 0; i < basic.getSizeInventory(); i++)
        {
            ItemStack stack = basic.getStackInSlot(i);

            if (stack != null){
                int tmpi = i % 6;

                if (tmpi >= 3){
                    countLeft += stack.stackSize;
                }

                if (tmpi <= 2){
                    countRight += stack.stackSize;
                }
           	}
               
        }
        
        

        if (countLeft == 0 && countRight == 0)
        {
            return Direction.FRONT;
        }

        if (countLeft == 0 && countRight > 0)
        {
            return Direction.RIGHT;
        }

        if (countLeft > 0 && countRight == 0)
        {
            return Direction.LEFT;
        }
        
        if (countLeft > 0 && countRight > 0)
        {
            if (notItem){
                return Direction.FRONT;
            }

            Direction[] translate = { Direction.LEFT, Direction.FRONT, Direction.RIGHT };
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            int leftX = xCoord, leftZ = zCoord;
            int rightX = xCoord, rightZ = zCoord;

            switch (BeltHelper.getRotation(meta))
            {
                case 0:
                    leftX++;
                    rightX--;
                    break;

                case 1:
                    leftZ++;
                    rightZ--;
                    break;

                case 2:
                    leftX--;
                    rightX++;
                    break;

                case 3:
                    leftZ--;
                    rightZ++;
                    break;
            }

            translate[2] = (BeltHelper.isTransporterAt(worldObj, new VecI(leftX, yCoord, leftZ)) ? Direction.RIGHT : Direction.FRONT);
            translate[0] = (BeltHelper.isTransporterAt(worldObj, new VecI(rightX, yCoord, rightZ)) ? Direction.LEFT : Direction.FRONT);

            if (translate[0] == translate[2])
            {
                translate[0] = Direction.LEFT;
                translate[2] = Direction.RIGHT;
            }

            if (itemstack.stackSize == 1)
            {
            	if(worldObj.isRemote){
                    return Direction.FRONT;
                }
            	Random rand = new Random();
                int newredir = (1 + rand.nextInt((countLeft + countRight))) <= countLeft ? 1 : -1;
                return translate[1 - newredir];
            }

            float fractionLeft = (float) countLeft / (float)(countLeft + countRight);
            int partLeft = Math.round(itemstack.stackSize * fractionLeft);
            int partRight = itemstack.stackSize - partLeft;

            if (partLeft > 0)
            {
                itemstack.stackSize = partLeft;
            }
            else
            {
                return translate[2];
            }
            
            if (partRight > 0)
            {
            	if(!worldObj.isRemote){
	                ItemStack rightStack = itemstack.copy();
	                rightStack.stackSize = partRight;
	                EntityItem entityitem2 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, rightStack);
	                entityitem2.motionX = entity.motionX;
	                entityitem2.motionY = entity.motionY;
	                entityitem2.motionZ = entity.motionZ;
	                worldObj.spawnEntityInWorld(entityitem2);
	                //setItemDirection(entityitem2, translate[2]);
	                EntityItem entityNew = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, itemstack);
	                entityNew.motionX = entity.motionX;
	                entityNew.motionY = entity.motionY;
	                entityNew.motionZ = entity.motionZ;
	                worldObj.spawnEntityInWorld(entityNew);
	                entity.setDead();
	                entity = entityNew;
	            }
            }
            
            return translate[0];
        }

        return Direction.FRONT;
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

            if(j >= 0 && j < this.basic.getSizeInventory()){
                this.basic.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(tag));
            }
        }
    }


}
