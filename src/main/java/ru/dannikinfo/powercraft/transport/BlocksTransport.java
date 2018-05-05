package ru.dannikinfo.powercraft.transport;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ru.dannikinfo.powercraft.transport.belt.BeltBreak;
import ru.dannikinfo.powercraft.transport.belt.BeltDetector;
import ru.dannikinfo.powercraft.transport.belt.BeltEjector;
import ru.dannikinfo.powercraft.transport.belt.BeltNormal;
import ru.dannikinfo.powercraft.transport.belt.BeltRedirector;
import ru.dannikinfo.powercraft.transport.belt.BeltSeparator;
import ru.dannikinfo.powercraft.transport.belt.BeltSpeedy;
import ru.dannikinfo.powercraft.transport.elevator.Elevator;
import ru.dannikinfo.powercraft.transport.elevator.ItemBlockElevator;
import ru.dannikinfo.powercraft.transport.elevator.Splitter;
import ru.dannikinfo.powercraft.transport.tile.TileEntityEjectionBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntityRedirectionBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySeparationBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySplitter;

public class BlocksTransport {
	//Create variable for block
	public static Block elevator;
	public static Block splitter;
	public static Block normalbelt;
	public static Block speedybelt;
	public static Block detectorbelt;
	public static Block breakbelt;
	public static Block redirectorbelt;
	public static Block separationbelt;
	public static Block ejectionbelt;
	
	
	public static void Blocks(){
		
		//initialize variable
		elevator = new Elevator();
		splitter = new Splitter("splitter");
		normalbelt = new BeltNormal("normalbelt");
		speedybelt = new BeltSpeedy("speedybelt");
		detectorbelt = new BeltDetector("detectorbelt");
		breakbelt = new BeltBreak("breakbelt");
		redirectorbelt = new BeltRedirector("redirectorbelt");
		separationbelt = new BeltSeparator("separatorbelt");
		ejectionbelt = new BeltEjector("ejectorbelt");
		
		//registering blocks
		GameRegistry.registerBlock(elevator, ItemBlockElevator.class, "Elevator");
		GameRegistry.registerBlock(splitter, "Splitter");
		GameRegistry.registerBlock(normalbelt, "Normal belt");
		GameRegistry.registerBlock(speedybelt, "Speedy belt");
		GameRegistry.registerBlock(detectorbelt, "Detector belt");
		GameRegistry.registerBlock(breakbelt, "Break belt");
		GameRegistry.registerBlock(redirectorbelt, "Redirector belt");
		GameRegistry.registerBlock(separationbelt, "Separation belt");
		GameRegistry.registerBlock(ejectionbelt, "Ejection belt");
		
		//registering TileEntity
		GameRegistry.registerTileEntity(TileEntitySplitter.class, "TileEntitySplitter");
		GameRegistry.registerTileEntity(TileEntityRedirectionBelt.class, "TileEntityRedirectionBelt");
		GameRegistry.registerTileEntity(TileEntitySeparationBelt.class, "TileEntitySeparationBelt");
		GameRegistry.registerTileEntity(TileEntityEjectionBelt.class, "TileEntityEjectionBelt");
		
		//craft's
		GameRegistry.addRecipe(new ItemStack(normalbelt, 8), new Object[]{ "   ", "LLL", "IRI", 'L', Items.leather, 'I', Items.iron_ingot, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(speedybelt, 8), new Object[]{ "LLL", "NNN", "GRG", 'L', Items.leather, 'N', normalbelt, 'G', Items.gold_ingot, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(detectorbelt, 1), new Object[]{ "S", "N", "R", 'S', Blocks.wooden_pressure_plate, 'N', normalbelt, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(breakbelt, 1), new Object[]{ " N ", " I ", "IRI", 'N', normalbelt, 'I', Items.iron_ingot, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(redirectorbelt, 1), new Object[]{ "RNR", 'N', normalbelt, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(separationbelt, 1), new Object[]{ "DND", " R ", 'D', Items.diamond, 'N', normalbelt, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(ejectionbelt, 1), new Object[]{ "B", "N", "R", 'B', Items.bow, 'N', normalbelt, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(elevator, 4, 0), new Object[]{ "NGN", "NRN", "NGN", 'G', Items.gold_ingot, 'N', normalbelt, 'R', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(elevator, 4, 1), new Object[]{ "NGN", "N N", "NGN", 'N', normalbelt, 'G', Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(splitter, 1), new Object[]{ "IUI", "SRS", "IDI", 'I', Items.iron_ingot, 'U', new ItemStack(elevator, 1, 0), 'D', new ItemStack(elevator, 1, 1),  'R', Items.redstone});

	}
}
