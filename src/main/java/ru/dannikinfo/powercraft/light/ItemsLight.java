package ru.dannikinfo.powercraft.light;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import ru.dannikinfo.powercraft.light.laser.LaserCompositionBlue;
import ru.dannikinfo.powercraft.light.laser.LaserCompositionGreen;
import ru.dannikinfo.powercraft.light.laser.LaserCompositionRed;

public class ItemsLight {
		
	//create variable for item
	public static Item LaserCompositionRed;
	public static Item LaserCompositionGreen;
	public static Item LaserCompositionBlue;
		
		public static void Items(){
		//set name if not localization
		LaserCompositionRed = new LaserCompositionRed().setUnlocalizedName("Laser composition red");
		LaserCompositionGreen = new LaserCompositionGreen().setUnlocalizedName("Laser composition green");
		LaserCompositionBlue = new LaserCompositionBlue().setUnlocalizedName("Laser composition blue");
		
		//register items in game
		GameRegistry.registerItem(LaserCompositionRed, "lasercompositionred");
		GameRegistry.registerItem(LaserCompositionGreen, "lasercompositiongreen");
		GameRegistry.registerItem(LaserCompositionBlue, "lasercompositionblue");

		//add recipe in game
	        
		//localization in game
		
		LanguageRegistry.addName(LaserCompositionRed, "Laser composition red");
		LanguageRegistry.addName(LaserCompositionGreen, "Laser composition green");
		LanguageRegistry.addName(LaserCompositionBlue, "Laser composition blue");
	}

}
