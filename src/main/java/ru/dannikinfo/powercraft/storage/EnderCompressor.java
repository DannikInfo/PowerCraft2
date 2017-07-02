package ru.dannikinfo.powercraft.storage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.gui.container.ContainerCraftingTool;

public class EnderCompressor extends Item{

	protected EnderCompressor(){
		this.setCreativeTab(Main.tabPowerCraft);
		this.setTextureName("powercraftreloaded:EnderCompressor");
		maxStackSize = 1;
	}
	
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player){
		InventoryEnderChest inventoryenderchest = player.getInventoryEnderChest();
        player.displayGUIChest(inventoryenderchest);
        return super.onItemRightClick(is, world, player);
	}
}
