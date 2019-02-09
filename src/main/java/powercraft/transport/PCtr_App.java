package powercraft.transport;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import powercraft.api.annotation.PC_FieldObject;
import powercraft.api.block.PC_Block;
import powercraft.api.gres.PC_GresBaseWithInventory;
import powercraft.api.item.PC_ItemArmor;
import powercraft.api.item.PC_ItemStack;
import powercraft.api.recipes.PC_IRecipe;
import powercraft.api.registry.PC_BlockRegistry;
import powercraft.api.registry.PC_ItemRegistry;
import powercraft.api.utils.PC_Struct2;
import powercraft.launcher.loader.PC_Module;
import powercraft.launcher.loader.PC_Module.PC_InitRecipes;
import powercraft.launcher.loader.PC_Module.PC_RegisterContainers;

@PC_Module(name = "Transport", version = "1.1.1")
public class PCtr_App {

	@PC_FieldObject(clazz = PCtr_BlockBeltNormal.class)
	public static PC_Block conveyorBelt;
	@PC_FieldObject(clazz = PCtr_BlockBeltSpeedy.class)
	public static PC_Block speedyBelt;
	@PC_FieldObject(clazz = PCtr_BlockBeltDetector.class)
	public static PC_Block detectionBelt;
	@PC_FieldObject(clazz = PCtr_BlockBeltBreak.class)
	public static PC_Block breakBelt;
	@PC_FieldObject(clazz = PCtr_BlockBeltRedirector.class)
	public static PC_Block redirectionBelt;
	@PC_FieldObject(clazz = PCtr_BlockBeltSeparator.class)
	public static PC_Block separationBelt;
	@PC_FieldObject(clazz = PCtr_BlockBeltEjector.class)
	public static PC_Block ejectionBelt;
	@PC_FieldObject(clazz = PCtr_BlockElevator.class)
	public static PC_Block elevator;
	@PC_FieldObject(clazz = PCtr_BlockSplitter.class)
	public static PC_Block splitter;
	@PC_FieldObject(clazz = PCtr_ItemArmorStickyBoots.class)
	public static PC_ItemArmor slimeboots;

	@PC_InitRecipes
	public List<PC_IRecipe> initRecipes(List<PC_IRecipe> recipes) {
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 16),
				new Object[] { "XXX", "YRY", "   ", 'X', Items.leather, 'Y', Items.iron_ingot, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 4),
				new Object[] { "XXX", "YRY", "   ", 'X', Items.paper, 'Y', Items.iron_ingot, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltSpeedy"), 16),
				new Object[] { "XXX", "YRY", "   ", 'X', Items.leather, 'Y', Items.gold_ingot, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltSpeedy"), 4),
				new Object[] { "XXX", "YRY", "   ", 'X', Items.paper, 'Y', Items.gold_ingot, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltEjector"), 1),
				new Object[] { "X", "Y", "Z", 'X', Items.bow, 'Y', conveyorBelt, 'Z', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltDetector"), 1),
				new Object[] { "X", "Y", "Z", 'X', Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, 'Y',
						conveyorBelt, 'Z', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltSeparator"), 1),
				new Object[] { "X", "Y", "Z", 'X', Items.diamond, 'Y',
						PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 'Z', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltBreak"), 1),
				new Object[] { "X", "Y", "Z", 'X', Items.iron_ingot, 'Y',
						PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 'Z', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltRedirector"), 1),
				new Object[] { "X", "Y", "   ", 'X', PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 'Y',
						Items.redstone });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockElevator"), 6, 0),
				new Object[] { "XGX", "X X", "XGX", 'X', PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 'G',
						Items.gold_ingot });
		GameRegistry.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockElevator"), 6, 1),
				new Object[] { "XGX", "XRX", "XGX", 'X', PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 'G',
						Items.gold_ingot, 'R', Items.redstone });
		GameRegistry
				.addRecipe(new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockSplitter"), 1, 0),
						new Object[] { " U ", "XSX", " D ", 'U',
								new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockElevator").getItemBlock(), 1,
										0),
								'X', PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltNormal"), 'S',
								PC_BlockRegistry.getPCBlockByName("PCtr_BlockBeltSeparation"), 'D',
								new ItemStack(PC_BlockRegistry.getPCBlockByName("PCtr_BlockElevator"), 1, 1) });
		GameRegistry.addRecipe(new ItemStack(PC_ItemRegistry.getPCItemByName("PCtr_ItemArmorStickyBoots"), 1, 0),
				new Object[] { "B", "S", "B", 'B', Items.iron_boots, 'S', Items.slime_ball });
		return recipes;
	}

	@PC_RegisterContainers
	public List<PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>> registerContainers(
			List<PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>> guis) {
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("SeperationBelt",
				PCtr_ContainerSeparationBelt.class));
		guis.add(new PC_Struct2<String, Class<? extends PC_GresBaseWithInventory>>("Splitter",
				PCtr_ContainerSplitter.class));
		return guis;
	}
}
