package ru.dannikinfo.powercraft.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ActivationCrystal extends Item{
	
    protected ActivationCrystal() {
    	this.setCreativeTab(Main.tabPowerCraft);
    	this.setTextureName("powercraftreloaded:ActivationCrystal");
    	maxStackSize = 1;
    }
    
	@SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack par1ItemStack)
	    {
	        return true;
	    }
}
