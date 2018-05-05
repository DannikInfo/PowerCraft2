package ru.dannikinfo.powercraft.core;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.gui.container.ContainerCraftingTool;

public class CraftingTool extends Item{

	protected CraftingTool(){
    	this.setCreativeTab(Main.tabPowerCraft);
    	this.setTextureName("powercraftreloaded:CraftingTool");
	}
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			//Контейнер с отсутсвием проверки блока
			player.openContainer = new ContainerCraftingTool(player.inventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		} else {
			//Ванильное Гуи
			//Minecraft.getMinecraft().displayGuiScreen(new GuiCrafting(player.inventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ));
		}
		return super.onItemRightClick(is, world, player);
	}
}
