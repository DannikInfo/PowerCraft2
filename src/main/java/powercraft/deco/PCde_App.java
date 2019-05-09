package powercraft.deco;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import powercraft.api.annotation.PC_FieldObject;
import powercraft.api.block.PC_Block;
import powercraft.api.item.PC_ItemStack;
import powercraft.api.recipes.PC_IRecipe;
import powercraft.api.registry.PC_BlockRegistry;
import powercraft.launcher.loader.PC_Module;
import powercraft.launcher.loader.PC_Module.PC_InitRecipes;
import powercraft.launcher.loader.PC_Module.PC_Instance;
import powercraft.launcher.loader.PC_ModuleObject;

@PC_Module(name = "Deco", version = "0.0.1")
public class PCde_App {

	@PC_FieldObject(clazz = PCde_BlockRedstoneStorage.class)
	public static PC_Block redstoneStorage;
	@PC_FieldObject(clazz=PCde_BlockIronFrame.class)
	public static PC_Block ironFrame;
	@PC_FieldObject(clazz = PCde_BlockChimney.class)
	public static PC_Block chimney;
	@PC_FieldObject(clazz = PCde_BlockPlatform.class)
	public static PC_Block platform;
	@PC_FieldObject(clazz = PCde_BlockStairs.class)
	public static PC_Block stairs;
	@PC_Instance
	public static PC_ModuleObject instance;

	@PC_InitRecipes
	public List<PC_IRecipe> initRecipes(List<PC_IRecipe> recipes) {
		
		GameRegistry.addRecipe(new ItemStack(ironFrame, 32, 0), new Object[]{ "XXX",
		  "X X", "XXX", 'X', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(platform, 15),
				new Object[] { "X  ", "X  ", "XXX", 'X', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(stairs, 15), new Object[] { "X  ", "XX  ", " XX", 'X', Items.iron_ingot });

		// Chimney's
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 0),
				new Object[] { "X X", "X X", "X X", 'X', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 1),
				new Object[] { "X X", "X X", "X X", 'X', Blocks.brick_block });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 2),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.stonebrick, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 3),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.stonebrick, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 4),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.stonebrick, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 5),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.sandstone, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 6),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.sandstone, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 7),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.sandstone, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 8),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.nether_brick, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 9),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.quartz_block, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 10),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.quartz_block, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 11),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.quartz_block, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 12),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.mossy_cobblestone, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 13),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.stonebrick, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 14),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.clay, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(chimney, 6, 15),
				new Object[] { "X X", "X X", "X X", 'X', new ItemStack(Blocks.iron_block, 1, 0) });
		return recipes;
	}

}
