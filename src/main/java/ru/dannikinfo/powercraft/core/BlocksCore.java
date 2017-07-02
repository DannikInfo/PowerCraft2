package ru.dannikinfo.powercraft.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import ru.dannikinfo.powercraft.core.crystal.Crystal;
import ru.dannikinfo.powercraft.core.crystal.ItemBlockCrystal;
import ru.dannikinfo.powercraft.core.crystal.TileEntityCrystal;

public class BlocksCore {
	
	//Create variable for block
	public static Block crystal;
	
	public static void Blocks(){
		
		//Присвоение переменных 
		crystal = new Crystal();
		
		//Регистрация блоков в игре
		GameRegistry.registerBlock(crystal, ItemBlockCrystal.class, "crystal");

		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityCrystal.class, "TileEntityCrystal");
		
	}
}
