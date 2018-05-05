package ru.dannikinfo.powercraft.light;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import ru.dannikinfo.powercraft.light.laser.Laser;
import ru.dannikinfo.powercraft.light.laser.TileEntityLaser;
import ru.dannikinfo.powercraft.light.mirror.Mirror;
import ru.dannikinfo.powercraft.light.mirror.TileEntityMirror;

public class BlocksLight{
	
	//Create variable for block
	public static Block laser;
	public static Block mirror;
	
	public static void Blocks(){
		
		//Присвоение переменных 
		laser = new Laser();
		mirror = new Mirror("mirror");

		//Регистрация блоков в игре
		GameRegistry.registerBlock(laser, "laser");
		GameRegistry.registerBlock(mirror, "mirror");
		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityLaser.class, "TileEntityLaser");
		GameRegistry.registerTileEntity(TileEntityMirror.class, "TileEntityMirror");
		
		//Рецепты крафта

		//Локализация в игре

		LanguageRegistry.addName(laser, "Laser");
	}
}
