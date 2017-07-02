package ru.dannikinfo.powercraft.teleporter;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlocksTeleporter{
	
	//Create variable for block
	public static Block teleporter;
	
	public static void Blocks(){
		
		//Присвоение переменных 
		teleporter = new Teleporter();
		
		//Регистрация блоков в игре
		GameRegistry.registerBlock(teleporter, "teleporter");
		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "TileEntityTeleporter");

		
		//Рецепты крафта
		GameRegistry.addRecipe(new ItemStack(teleporter, 1), new Object[]{ " E ", "ECE", "###", '#', Items.iron_ingot, 'E', Items.ender_pearl, 'C', Blocks.obsidian});
		
		//Локализация в игре
		LanguageRegistry.addName(teleporter, "Teleporter");
	}
}
