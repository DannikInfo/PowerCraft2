package powercraft.machines;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercraft.api.annotation.PC_FieldObject;
import powercraft.api.block.PC_Block;
import powercraft.api.gres.PC_GresBaseWithInventory;
import powercraft.api.item.PC_ItemStack;
import powercraft.api.network.PC_IPacketHandler;
import powercraft.api.recipes.PC_3DRecipe;
import powercraft.api.recipes.PC_I3DRecipeHandler;
import powercraft.api.recipes.PC_IRecipe;
import powercraft.api.recipes.PC_ShapedRecipes;
import powercraft.api.utils.PC_Struct2;
import powercraft.api.utils.PC_Utils;
import powercraft.launcher.PC_Property;
import powercraft.launcher.loader.PC_Module;
import powercraft.launcher.loader.PC_Module.PC_InitPacketHandlers;
import powercraft.launcher.loader.PC_Module.PC_InitProperties;
import powercraft.launcher.loader.PC_Module.PC_InitRecipes;
import powercraft.launcher.loader.PC_Module.PC_Instance;
import powercraft.launcher.loader.PC_Module.PC_RegisterContainers;
import powercraft.launcher.loader.PC_ModuleObject;

@PC_Module(name = "Machines", version = "0.0.1")
public class PCma_App {

	@PC_FieldObject(clazz = PCma_BlockAutomaticWorkbench.class)
	public static PC_Block automaticWorkbench;
	@PC_FieldObject(clazz = PCma_BlockRoaster.class)
	public static PC_Block roaster;
	@PC_FieldObject(clazz = PCma_BlockReplacer.class)
	public static PC_Block replacer;
	@PC_FieldObject(clazz = PCma_BlockTransmutabox.class)
	public static PC_Block transmutabox;
	@PC_FieldObject(clazz = PCma_BlockXPBank.class)
	public static PC_Block xpBank;
	@PC_FieldObject(clazz = PCma_BlockBlockBuilder.class)
	public static PC_Block blockBuilder;
	@PC_FieldObject(clazz = PCma_BlockHarvester.class)
	public static PC_Block harvester;
	@PC_FieldObject(clazz = PCma_BlockFishingMachine.class)
	public static PC_Block fishingMachine;
	@PC_FieldObject(clazz = PCma_BlockChunkLoader.class)
	public static PC_Block chunkLoader;

	public static List<Integer> roasterIgnoreBlockIDs;
	@PC_Instance
	public static PC_ModuleObject instance;

	// @PC_PostInit
	// public void postInit() {
	// PCma_ItemRanking.init();
	// }

	@PC_InitProperties
	public void initProperties(PC_Property config) {
		roasterIgnoreBlockIDs = PC_Utils.parseIntList(config.getString("PCma_BlockRoaster.roasterIgnoreBlockIDs", "1"));
	}

	@PC_InitRecipes
	public List<PC_IRecipe> initRecipes(List<PC_IRecipe> recipes) {
		GameRegistry.addRecipe(new ItemStack(automaticWorkbench),
				new Object[] { "X", "Y", "Z", 'X', Items.diamond, 'Y', Blocks.crafting_table, 'Z', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(roaster),
				new Object[] { "III", "IFI", "III", 'I', Items.iron_ingot, 'F', Items.flint_and_steel });
		GameRegistry.addRecipe(new ItemStack(xpBank), new Object[] { "ODO", "OGO", "O O", 'O', Blocks.obsidian, 'D',
				Blocks.diamond_block, 'G', Items.ghast_tear });
		GameRegistry.addRecipe(new ItemStack(transmutabox), new Object[] { "SOS", "OPO", "SOS", 'S',
				Blocks.iron_block, 'O', Blocks.obsidian, 'Z', Blocks.furnace });
		GameRegistry.addRecipe(new ItemStack(blockBuilder),
				new Object[] { "G", "D", " ", 'G', Items.gold_ingot, 'D', Blocks.dispenser });
		GameRegistry.addRecipe(new ItemStack(harvester),
				new Object[] { "P", "D", " ", 'P', Items.iron_ingot, 'D', Blocks.dispenser });
		GameRegistry.addRecipe(new ItemStack(replacer),
				new Object[] { "B", "R", "H", 'B', blockBuilder, 'R', Items.redstone, 'H', harvester });

		recipes.add(new PC_3DRecipe((PC_I3DRecipeHandler) fishingMachine, new String[] { "www", "www", "www" },
				new String[] { "www", "www", "www" }, new String[] { "www", "www", "www" },
				new String[] { "www", "www", "www" }, new String[] { "www", "www", "www" },
				new String[] { "fpf", "pip", "fpf" }, new String[] { " !c ", "!cc!c", " !c " }, 'w',
				Blocks.flowing_water, Blocks.water, 'f', Blocks.fence, 'p', Blocks.planks, 'c', Blocks.chest, 'i',
				Blocks.iron_block));

		recipes.add(new PC_3DRecipe((PC_I3DRecipeHandler) chunkLoader, new String[] { "gog", "ogo", "gog" },
				new String[] { " f ", "f f", " f " }, 'g', Blocks.glass, 'o', Blocks.obsidian, 'f', Blocks.fire));

		return recipes;
	}

	@PC_InitPacketHandlers
	public List<PC_Struct2<String, PC_IPacketHandler>> initPacketHandlers(
			List<PC_Struct2<String, PC_IPacketHandler>> packetHandlers) {
		packetHandlers.add(
				new PC_Struct2<String, PC_IPacketHandler>("PCma_BlockBlockBuilder", (PC_IPacketHandler) blockBuilder));
		packetHandlers
		.add(new PC_Struct2<String, PC_IPacketHandler>("PCma_BlockHarvester", (PC_IPacketHandler) harvester));
		return packetHandlers;
	}

	@PC_RegisterContainers
	public List<PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>> registerContainers(
			List<PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>> guis) {
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("AutomaticWorkbench",
				PCma_ContainerAutomaticWorkbench.class));
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("BlockBuilder",
				PCma_ContainerBlockBuilder.class));
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("Replacer",
				PCma_ContainerReplacer.class));
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("Roaster",
				PCma_ContainerRoaster.class));
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("Transmutabox",
				PCma_ContainerTransmutabox.class));
		return guis;
	}
}
