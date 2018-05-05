package ru.dannikinfo.powercraft.storage;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsStorage {
	
	//create variable for item
	public static Item Compressor;
	public static Item EnderCompressor;
	public static Item HighStackCompressor;
	public static Item BigCompressor;
	
	public static void Items(){
		
		//set name if not localization
		Compressor = new Compressor().setUnlocalizedName("Compressor");
		EnderCompressor = new EnderCompressor().setUnlocalizedName("Ender compressor");
		HighStackCompressor = new HighStackCompressor().setUnlocalizedName("High stack compressor");
		BigCompressor = new BigCompressor().setUnlocalizedName("BigCompressor");
		
		//register items in game
		GameRegistry.registerItem(Compressor, "compressor");
		GameRegistry.registerItem(EnderCompressor, "endercompressor");
		GameRegistry.registerItem(HighStackCompressor, "highstackcompressor");
		GameRegistry.registerItem(BigCompressor, "bigcompressor");

		//add recipe in game
        GameRegistry.addRecipe(new ItemStack(Compressor, 1), new Object[]{ "#R#", "ARA", "#R#", 'A', Blocks.piston, '#', Blocks.lever, 'R', Blocks.chest});
        GameRegistry.addRecipe(new ItemStack(EnderCompressor, 1), new Object[]{ "#R#", "ADA", "#R#", 'A', Blocks.piston, 'D', Items.diamond, '#', Blocks.lever, 'R', Blocks.ender_chest});
        GameRegistry.addRecipe(new ItemStack(BigCompressor, 1), new Object[]{ "#R#", "ADA", "#R#", 'A', Blocks.piston, 'D', Items.diamond, '#', Blocks.lever, 'R', Blocks.chest});
        GameRegistry.addRecipe(new ItemStack(HighStackCompressor, 1), new Object[]{ "ARA", "ADA", "ARA", 'A', Blocks.piston, 'D', Items.diamond, 'R', Blocks.ender_chest});
        

		//localization in game
		LanguageRegistry.addName(Compressor, "Compressor");
		LanguageRegistry.addName(EnderCompressor, "Ender compressor");
		LanguageRegistry.addName(HighStackCompressor, "High stack compressor");
		LanguageRegistry.addName(BigCompressor, "Big compressor");
	}

}