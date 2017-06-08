package ru.dannik.powercraft.blocks.logic.pulsar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.dannik.powercraft.blocks.BlockList;
import ru.dannik.powercraft.utils.BaseUtils;
import ru.dannik.powercraft.utils.WorldData;

public class TileEntityPulsar extends TileEntity{
	
	public int delayTimer = 0;
    public int delay = 20;
    private int holdtime = 4;
    private boolean paused = false;
    private boolean silent = false;
    private boolean should = true;
    public int delay_st, hold_st;
    NBTTagCompound nbt;
    
    public void setTimes(int gdelay, int ghold, World world, int x, int y, int z){
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		String udid = BaseUtils.udid(x, y, z);
		tag.setInteger("delay_st_" + udid, gdelay);
		tag.setInteger("hold_st_" + udid, ghold);
		data.markDirty();
		world.setBlock(x, y, z, BlockList.pulsaroff);
    }

    public boolean isActive()
    {
    	if(worldObj.getBlock(xCoord, yCoord, zCoord) == BlockList.pulsar){
    		paused = false;
    	}else{
    		if(worldObj.getBlock(xCoord, yCoord, zCoord) == BlockList.pulsaroff){
    			paused = true;
    		}
    	}
        return paused;
    }
    
	public void updateEntity(){
		String udid = BaseUtils.udid(xCoord, yCoord, zCoord);
		WorldData data = WorldData.forWorld(worldObj);
		NBTTagCompound tag = data.getData();
		delay_st = tag.getInteger("delay_st_" + udid);
		hold_st = tag.getInteger("hold_st_" + udid);
		data.markDirty();
		if (delayTimer == (getDelay() * delay_st) && isActive())
        {
        	worldObj.setBlock(xCoord, yCoord, zCoord, BlockList.pulsar);
        	delayTimer = -1;
        }

        delayTimer++;

        if (delayTimer == (getDelay() * hold_st) && !isActive())
        {
        	worldObj.setBlock(xCoord, yCoord, zCoord, BlockList.pulsaroff);
        	delayTimer = -1;
        }
	}
    @Override
    public boolean canUpdate()
    {
        return true;
    }

    public boolean isSilent()
    {
        return silent;
    }

    public int getDelay()
    {
        return delay;
    }

    public boolean isPaused()
    {
        return paused;
    }

}
