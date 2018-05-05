package ru.dannikinfo.powercraft.logic;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ru.dannikinfo.powercraft.logic.detector.Detector;
import ru.dannikinfo.powercraft.logic.detector.DetectorOff;
import ru.dannikinfo.powercraft.logic.detector.ItemBlockDetector;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetector;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetectorOff;
import ru.dannikinfo.powercraft.logic.pulsar.Pulsar;
import ru.dannikinfo.powercraft.logic.pulsar.PulsarOff;
import ru.dannikinfo.powercraft.logic.pulsar.TileEntityPulsar;

public class BlocksLogic {
	
	//Create variable for block
	public static Block spawner;
	public static Block special_controller;
	public static Block pulsar;
	public static Block pulsaroff;
	public static Block settingsblock;
	public static Block detector;
	public static Block detectoroff;
	
	public static void Blocks(){
		
		//Присвоение переменных 
		spawner = new Spawner();
		special_controller = new SpecialController();
		pulsar = new Pulsar();
		pulsaroff = new PulsarOff();
		settingsblock = new SettingsBlock();
		detector = new Detector();
		detectoroff = new DetectorOff();
		
		//Регистрация блоков в игре
		GameRegistry.registerBlock(spawner, "spawner");
		GameRegistry.registerBlock(special_controller, "specialcontroller");
		GameRegistry.registerBlock(pulsar, "redstonepulsar");
		GameRegistry.registerBlock(pulsaroff, "redstonepulsaroff");
		GameRegistry.registerBlock(settingsblock, "SettingsBlock");
		GameRegistry.registerBlock(detector, ItemBlockDetector.class, "detector");
		GameRegistry.registerBlock(detectoroff, ItemBlockDetector.class, "detectoroff");
		
		//Регистрация TileEntity
		GameRegistry.registerTileEntity(TileEntityPulsar.class, "TileEntityPulsar");
		GameRegistry.registerTileEntity(TileEntityDetector.class, "TileEntityDetector");
		GameRegistry.registerTileEntity(TileEntityDetectorOff.class, "TileEntityDetectorOFF");
		
		//Рецепты крафта
		GameRegistry.addRecipe(new ItemStack(spawner, 1), new Object[]{ "#B#", "BDB", "#B#", '#', Blocks.iron_bars, 'B', Blocks.cobblestone_wall, 'D', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(special_controller, 1), new Object[]{ "RRR", "SRS", "###", '#', Items.iron_ingot, 'R', Items.redstone, 'S', Blocks.stone});
		GameRegistry.addRecipe(new ItemStack(pulsar, 1), new Object[]{ "#R#", "ROR", "#R#", '#', Items.iron_ingot, 'R', Items.redstone, 'O', Blocks.obsidian});
		
		//Локализация в игре
		LanguageRegistry.addName(spawner, "Spawner");
		LanguageRegistry.addName(special_controller, "Special controller");
		LanguageRegistry.addName(pulsar, "Redstone pulsar");
		LanguageRegistry.addName(settingsblock, "Settings block");
	}
}
