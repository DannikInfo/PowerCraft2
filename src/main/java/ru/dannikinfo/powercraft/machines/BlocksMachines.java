package ru.dannikinfo.powercraft.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;

public class BlocksMachines {
	//Create variable for block
	public static Block harvester;
	//public static Block chunker; добавлю в будущих обновлениях

	
	public static void Blocks(){
		
		//Присвоение переменных 
		harvester = new Harvester();
		//chunker = new Chunker();

		
		//Регистрация блоков в игре
		GameRegistry.registerBlock(harvester, "harvester");
		//GameRegistry.registerBlock(chunker, "chunk loader");
		
		//Регистрация TileEntity
		//GameRegistry.registerTileEntity(TileEntityChunker.class, "TileEntityChunker");
		
		//Рецепты крафта
			
		//Локализация в игре
		LanguageRegistry.addName(harvester, "Harvester");
		//LanguageRegistry.addName(chunker, "Chunk loader");
	}
}
