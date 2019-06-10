package powercraft.launcher;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * PowerCraft Main Class with will be constructed form forge
 * 
 * @author XOR
 * 
 */
@Mod(modid = "PowerCraft", name = "PowerCraft", version = "1.0.1a", dependencies = "after:*")
public class mod_PowerCraft {

	public static final String MODID = "powercraft"; // initialization mod id
	/**
	 * The LauncherUtils
	 */
	@SidedProxy(clientSide = "powercraft.launcher.PC_LauncherClientUtils", serverSide = "powercraft.launcher.PC_LauncherUtils")
	public static PC_LauncherUtils proxy;

	/**
	 * Mod instance
	 */
	private static mod_PowerCraft instance;

	/**
	 * 
	 * get the Mod instance
	 * 
	 * @return the Mod instance
	 */
	public static mod_PowerCraft getInstance() {
		return instance;
	}

	public mod_PowerCraft() {
		instance = this;
	}

	/**
	 * 
	 * pre init function, will be called from forge
	 * 
	 * @param event
	 */
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		hackInfo();
		PC_Launcher.preInit();

	}

	/**
	 * 
	 * init function, will be called from forge
	 * 
	 * @param event
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		PC_Launcher.init();
	}

	/**
	 * 
	 * post init function, will be called from forge
	 * 
	 * @param event
	 */
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PC_Launcher.postInit();
	}

	/**
	 * 
	 * get PowerCraft mod container from forge
	 * 
	 * @return the mod container
	 */
	public ModContainer getModContainer() {
		List<ModContainer> modContainers = Loader.instance().getModList();

		for (ModContainer modContainer : modContainers) {
			if (modContainer.matches(this)) {
				return modContainer;
			}
		}

		return null;
	}

	/**
	 * 
	 * get PowerCraft mod metadata from forge
	 * 
	 * @return the mod metadata
	 */
	public ModMetadata getModMetadata() {
		return getModContainer().getMetadata();
	}

	/**
	 * 
	 * get the powercraft version
	 * 
	 * @return powercraft version
	 */
	public String getVersion() {
		return getModMetadata().version;
	}

	/**
	 * 
	 * get the powercraft name
	 * 
	 * @return powercraft name
	 */
	public String getName() {
		return getModMetadata().name;
	}

	/**
	 * 
	 * hack forge powercraft info
	 * 
	 */
	private void hackInfo() {
		ModMetadata mm = getModMetadata();
		mm.autogenerated = false;
		mm.authorList = new ArrayList<String>();
		mm.credits = "XOR, Rapus, wolfhow42, MightyPork, RxD, LOLerul2";
		mm.authorList.add("dannikinfo");
		mm.description = "";
		mm.logoFile = "assets/powercraft/textures/PowerCraft.png";
		mm.url = "http://powercrafting.net";
	}

}
