package ru.dannikinfo.powercraft.machines;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import ru.dannikinfo.powercraft.machines.chunker.Chunker;
import ru.dannikinfo.powercraft.machines.chunker.TileEntityChunker;
import ru.dannikinfo.powercraft.machines.tile.TileEntityBlockBuilder;

public class BlocksMachines {
	//Create variable for block
	public static Block harvester;
	public static Block blockbuilder;
	public static Block chunker;

	
	public static void Blocks(){
		
		//Присвоение переменных 
		harvester = new Harvester();
		chunker = new Chunker();
		blockbuilder = new BlockBuilder("blockbuilder");

		
		//Регистрация блоков в игре
		GameRegistry.registerBlock(harvester, "harvester");
		GameRegistry.registerBlock(chunker, "chunk loader");
		GameRegistry.registerBlock(blockbuilder, "BlockBuilder");
		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityChunker.class, "TileEntityChunker");
		GameRegistry.registerTileEntity(TileEntityBlockBuilder.class, "TileEntityBlockBuilder");
		
		//Рецепты крафта
			
		//Локализация в игре
		LanguageRegistry.addName(harvester, "Harvester");
		LanguageRegistry.addName(chunker, "Chunk loader");
	}
}
