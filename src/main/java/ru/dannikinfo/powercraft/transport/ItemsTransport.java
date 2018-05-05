package ru.dannikinfo.powercraft.transport;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsTransport {
	
	//create variable for item
	public static Item SlimeBoots;
	
	public static void Items(){
		
		//set name if not localization
		SlimeBoots = new SlimeBoots(0, 3).setUnlocalizedName("Slime Boots").setTextureName("powercraftreloaded:SlimeBoots");
		
		//register items in game
		GameRegistry.registerItem(SlimeBoots, "SlimeBoots");

		//add recipe in game
		GameRegistry.addRecipe(new ItemStack(SlimeBoots, 1), new Object[]{ "SSS", "SBS", "SSS", 'S', Items.slime_ball, 'B', Items.iron_boots});
        
		//localization in game
		LanguageRegistry.addName(SlimeBoots, "Slime boots");
	}

}