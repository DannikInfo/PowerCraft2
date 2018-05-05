package ru.dannikinfo.powercraft.logic.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFlipFlop extends TileEntity{
	private int type = 0;
    private boolean clock = false;
	
    public void create(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
    	type = stack.getItemDamage();
    }

    public int getType(){
        return type;
    }

    public boolean getClock(){
        return clock;
    }

    public void setClock(boolean state)
    {
    	if(clock!=state){
    		clock=state;
    	}
    }

	public int getPickMetadata() {
		return type;
	}
    
}
