package ru.dannikinfo.powercraft.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.ForgeChunkManager;
import ru.dannikinfo.powercraft.api.network.CommonProxy;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.deco.BlocksDeco;
import ru.dannikinfo.powercraft.gui.GuiHandler;
import ru.dannikinfo.powercraft.hologram.ItemsHolo;
import ru.dannikinfo.powercraft.light.BlocksLight;
import ru.dannikinfo.powercraft.light.ItemsLight;
import ru.dannikinfo.powercraft.logic.BlocksLogic;
import ru.dannikinfo.powercraft.machines.BlocksMachines;
import ru.dannikinfo.powercraft.machines.chunker.ChunkerCallback;
import ru.dannikinfo.powercraft.storage.ItemsStorage;
import ru.dannikinfo.powercraft.teleporter.BlocksTeleporter;
import ru.dannikinfo.powercraft.teleporter.ItemsTeleporter;
import ru.dannikinfo.powercraft.transport.BlocksTransport;
import ru.dannikinfo.powercraft.transport.ItemsTransport;

@Mod (modid = "powercraftreloaded", name = "PowerCraft Reloaded", version = "1.0")	//basic info mod

public class Main {
	@SidedProxy(clientSide = "ru.dannikinfo.powercraft.api.network.ClientProxy", serverSide = "ru.dannikinfo.powercraft.api.network.CommonProxy") 	//proxy registering
	public static CommonProxy proxy;	//initialization proxy variable
	@Instance("powercraftreloaded")		//instance initialization
	public static Main instance;		//or here initialization instance :)
	
	public static CreativeTabs tabPowerCraft = new TabPowerCraft(CreativeTabs.getNextID(),"Power craft");	//initialization 1 creative tab
	public static CreativeTabs tabPowerCraftDeco = new TabPowerCraftDeco(CreativeTabs.getNextID(),"Power craft decoration"); 	//initialization 2 creative tab
	public static final String MODID = "powercraftreloaded";	//initialization mod id
	public static final Logger logger = LogManager.getLogger(MODID);	//initialization logger 
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		//adding block lists
		BlocksCore.Blocks(); 
		BlocksDeco.Blocks(); 
		BlocksLight.Blocks(); 
		BlocksLogic.Blocks(); 
		BlocksMachines.Blocks(); 
		BlocksTeleporter.Blocks(); 
		BlocksTransport.Blocks(); 
		//adding inverse vanilla recipe
		Recipe.recipe(); 
		//adding item lists
		ItemsCore.Items();
		ItemsLight.Items();
		ItemsHolo.Items();
		ItemsStorage.Items();
		ItemsTeleporter.Items();
		ItemsTransport.Items();
		//get preInit method of proxy
		proxy.preInit(); 	
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler()); //register Gui Handler
		WorldModGenerator generator = new WorldModGenerator();	//initialization variable generator
		GameRegistry.registerWorldGenerator(generator, 0); 		//register generator
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		PacketManager.registerPackets(); //adding register list packets
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(); 	//get init method of proxy
	}
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkerCallback()); //oh no this not WORKED TODO: fixing and adding ChunkLoader
    }
	
	public static Main getMod(){
		return instance; 	//return mod container
	}
	
}
