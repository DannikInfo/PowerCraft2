package ru.dannikinfo.powercraft.light;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import ru.dannikinfo.powercraft.light.laser.Laser;
import ru.dannikinfo.powercraft.light.laser.TileEntityLaser;

public class BlocksLight{
	
	//Create variable for block
	public static Block laser;
	
	public static void Blocks(){
		
		//Присвоение переменных 
		laser = new Laser();

		//Регистрация блоков в игре
		GameRegistry.registerBlock(laser, "laser");
		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityLaser.class, "TileEntityLaser");
		
		//Рецепты крафта

		//Локализация в игре

		LanguageRegistry.addName(laser, "Laser");
	}
}
