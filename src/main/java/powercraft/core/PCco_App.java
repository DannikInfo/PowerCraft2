package powercraft.core;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import powercraft.api.annotation.PC_FieldObject;
import powercraft.api.block.PC_Block;
import powercraft.api.gres.PC_GresBaseWithInventory;
import powercraft.api.item.PC_Item;
import powercraft.api.network.PC_IPacketHandler;
import powercraft.api.recipes.PC_IRecipe;
import powercraft.api.registry.PC_BlockRegistry;
import powercraft.api.registry.PC_ItemRegistry;
import powercraft.api.utils.PC_GlobalVariables;
import powercraft.api.utils.PC_Struct2;
import powercraft.launcher.PC_Property;
import powercraft.launcher.loader.PC_Module;
import powercraft.launcher.loader.PC_Module.PC_InitPacketHandlers;
import powercraft.launcher.loader.PC_Module.PC_InitProperties;
import powercraft.launcher.loader.PC_Module.PC_InitRecipes;
import powercraft.launcher.loader.PC_Module.PC_RegisterContainers;

@PC_Module(name="Core", version="1.1.1")
public class PCco_App {

	@PC_FieldObject(clazz=PCco_BlockPowerCrystal.class)
	public static PC_Block powerCrystal;
	@PC_FieldObject(clazz=PCco_BlockBlockSaver.class)
	public static PC_Block blockSaver;
	@PC_FieldObject(clazz=PCco_ItemPowerDust.class)
	public static PC_Item powerDust;
	@PC_FieldObject(clazz=PCco_ItemActivator.class)
	public static PC_Item activator;
	@PC_FieldObject(clazz=PCco_ItemCraftingTool.class)
	public static PC_Item craftingTool;
	@PC_FieldObject(clazz=PCco_ItemOreSniffer.class)
	public static PC_Item oreSniffer;
	@PC_FieldObject(clazz=PCco_MobSpawnerSetter.class)
	public static PCco_MobSpawnerSetter spawnerSetter;
	
	@PC_InitProperties
	public void initProperties(PC_Property config) {
		PC_GlobalVariables.consts.put("recipes.recyclation", config.getBoolean("recipes.recyclation", true, "Add new recypes allowing easy material recyclation"));
		PC_GlobalVariables.consts.put("recipes.spawner", config.getBoolean("recipes.spawner", true, "Make spawners craftable of iron and mossy cobble"));
	}
	
	@PC_InitRecipes
	public List<PC_IRecipe> initRecipes(List<PC_IRecipe> recipes) {
		GameRegistry.addRecipe(new ItemStack(PC_ItemRegistry.getPCItemByName("PCco_ItemActivator"), 1), new Object[]{ "#X#", "RYR", " # ", 'X', PC_BlockRegistry.getPCBlockByName("PCco_BlockPowerCrystal"), '#', Items.iron_ingot, 'Y', Items.diamond, 'R', Items.redstone});
	    GameRegistry.addRecipe(new ItemStack(PC_ItemRegistry.getPCItemByName("PCco_ItemCraftingTool"), 1), new Object[]{ "#R#", "RXR", "#R#", 'X', Blocks.iron_block, '#', Blocks.crafting_table, 'R', Items.redstone});
	    GameRegistry.addShapelessRecipe(new ItemStack(PC_ItemRegistry.getPCItemByName("PCco_ItemPowerDust"), 4), new Object[] {PC_BlockRegistry.getPCBlockByName("PCco_BlockPowerCrystal")});
	    GameRegistry.addRecipe(new ItemStack(PC_ItemRegistry.getPCItemByName("PCco_ItemOreSniffer"), 1), new Object[]{ " GD", "GCG", "DG ", 'G', Items.gold_ingot, 'D', Items.diamond, 'C', PC_BlockRegistry.getPCBlockByName("PCco_BlockPowerCrystal")});
	    
		//reverse recipes
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sand, 4), new Object[]{Blocks.sandstone});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 6), new Object[]{Items.wooden_door});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 8), new Object[]{Blocks.chest});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 4), new Object[]{Blocks.crafting_table});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2), new Object[]{Blocks.wooden_pressure_plate});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone, 2), new Object[]{Blocks.stone_pressure_plate});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone, 1), new Object[]{Blocks.stone_button});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 1), new Object[]{Blocks.wooden_button});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 3), new Object[]{Blocks.fence});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 7), new Object[]{Blocks.ladder});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 6), new Object[]{Items.sign});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 6), new Object[]{Items.iron_door});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone, 8), new Object[]{Blocks.furnace});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 5), new Object[]{Items.minecart});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 3), new Object[]{Items.bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 5), new Object[]{Items.boat});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8), new Object[]{Blocks.fence_gate});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone, 1), new Object[]{Blocks.stonebrick});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 7), new Object[]{Blocks.cauldron});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 3), new Object[]{Blocks.trapdoor});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 1), new Object[]{Items.stick, Items.stick});
		
		GameRegistry.addRecipe(new ItemStack(Blocks.mob_spawner, 1), new Object[]{ "#B#", "BDB", "#B#", '#', Blocks.iron_bars, 'B', Blocks.cobblestone_wall, 'D', Items.diamond});
		
		return recipes;
	}
	
	@PC_InitPacketHandlers
	public List<PC_Struct2<String, PC_IPacketHandler>> initPacketHandlers(
			List<PC_Struct2<String, PC_IPacketHandler>> packetHandlers) {
		packetHandlers.add(new PC_Struct2<String, PC_IPacketHandler>("DeleteAllPlayerStacks", new PCco_DeleteAllPlayerStacks()));
		packetHandlers.add(new PC_Struct2<String, PC_IPacketHandler>("MobSpawner", spawnerSetter));
		packetHandlers.add(new PC_Struct2<String, PC_IPacketHandler>("CraftingToolCrafter", new PCco_CraftingToolCrafter()));
		return packetHandlers;
	}
	
	@PC_RegisterContainers
	public List<PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>> registerContainers(List<PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>> guis) {
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("CraftingTool", PCco_ContainerCraftingTool.class));
		return guis;
	}
	
}
