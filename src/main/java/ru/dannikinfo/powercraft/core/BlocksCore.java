package ru.dannikinfo.powercraft.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import ru.dannikinfo.powercraft.core.crystal.Crystal;
import ru.dannikinfo.powercraft.core.crystal.ItemBlockCrystal;
import ru.dannikinfo.powercraft.core.crystal.TileEntityCrystal;
import ru.dannikinfo.powercraft.core.security.Security;

public class BlocksCore {
	
	//Create variable for block
	public static Block crystal;
	//public static Block security; Fucken bugs
	
	public static void Blocks(){
		
		//Присвоение переменных 
		crystal = new Crystal();
		//security = new Security();
		
		//Регистрация блоков в игре
		GameRegistry.registerBlock(crystal, ItemBlockCrystal.class, "crystal");
		//GameRegistry.registerBlock(security, "security");
		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityCrystal.class, "TileEntityCrystal");
		
	}
}
