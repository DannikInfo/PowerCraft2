package ru.dannikinfo.powercraft.hologram;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsHolo{
	
	//create variable for item
	public static Item HoloLens;
	
	public static void Items(){
		
		//set name if not localization
		HoloLens = new HoloLens(0, 0).setUnlocalizedName("NightVision glasses").setTextureName("powercraftreloaded:HoloLens");
		
		//register items in game
		GameRegistry.registerItem(HoloLens, "hololens");
		
		//add recipe in game
        GameRegistry.addRecipe(new ItemStack(HoloLens, 1), new Object[]{ "III", "GBG", "I I", 'I', Items.iron_ingot, 'G', Blocks.glass, 'B', Items.golden_apple});
        

		//localization in game
		LanguageRegistry.addName(HoloLens, "Night Vision Glasses");

	}

}