package ru.dannikinfo.powercraft.core;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsCore {
		
	//create variable for item
		
	public static Item ActivationCrystal;
	public static Item CraftingTool;
	//public static Item OreSniffer;
	//public static Item RadioRemote;
	public static Item Transistor;
	public static Item Silicon;
	public static Item PowerDust;
		
		public static void Items(){
		
		//set name if not localization
		
		ActivationCrystal = new ActivationCrystal().setUnlocalizedName("Activation crystal");
		CraftingTool = new CraftingTool().setUnlocalizedName("Crafting tool");
		//OreSniffer = new OreSniffer().setUnlocalizedName("Ore sniffer");
		//RadioRemote = new RadioRemote().setUnlocalizedName("Radio remote");
		Transistor = new Transistor().setUnlocalizedName("Transistor");
		Silicon = new Silicon().setUnlocalizedName("silicon");
		PowerDust = new PowerDust().setUnlocalizedName("Power dust");
		
		//register items in game
		
		GameRegistry.registerItem(ActivationCrystal, "activationcrystal");
		GameRegistry.registerItem(CraftingTool, "craftingtool");
		//GameRegistry.registerItem(OreSniffer, "oresniffer");
		//GameRegistry.registerItem(RadioRemote, "radioremote");
		GameRegistry.registerItem(Transistor, "transistor");
		GameRegistry.registerItem(Silicon, "silicon");
		GameRegistry.registerItem(PowerDust, "powerdust");

		//add recipe in game
			
		GameRegistry.addRecipe(new ItemStack(ActivationCrystal, 1), new Object[]{ "#X#", "RYR", " # ", 'X', BlocksCore.crystal, '#', Items.iron_ingot, 'Y', Items.diamond, 'R', Items.redstone});
	    GameRegistry.addRecipe(new ItemStack(CraftingTool, 1), new Object[]{ "#R#", "RXR", "#R#", 'X', Blocks.iron_block, '#', Blocks.crafting_table, 'R', Items.redstone});
	    GameRegistry.addShapelessRecipe(new ItemStack(PowerDust, 4), new Object[] {BlocksCore.crystal});
	        
		//localization in game
		
		LanguageRegistry.addName(ActivationCrystal, "Activation crystal");
		LanguageRegistry.addName(CraftingTool, "Crafting tool");
		//LanguageRegistry.addName(OreSniffer, "Ore sniffer");
		//LanguageRegistry.addName(RadioRemote, "Radio remote");
		LanguageRegistry.addName(Transistor, "Transistor");
		LanguageRegistry.addName(Silicon, "silicon");
		LanguageRegistry.addName(PowerDust, "Power dust");
	}

}
