package ru.dannikinfo.powercraft.teleporter;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsTeleporter {
	
	//create variable for item
	public static Item CorePortal;
	
	public static void Items(){
		
		//set name if not localization
		CorePortal = new CorePortal().setUnlocalizedName("Core portal");
		
		//register items in game
		GameRegistry.registerItem(CorePortal, "coreportal");

		//add recipe in game
		GameRegistry.addRecipe(new ItemStack(CorePortal, 1), new Object[]{ "ORO", "RDR", "ORO", 'O', Blocks.obsidian, 'R', Items.redstone, 'D', Items.diamond});
        
		//localization in game
		LanguageRegistry.addName(CorePortal, "Portal core");
	}

}