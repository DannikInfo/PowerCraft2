package ru.dannikinfo.powercraft.deco;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ru.dannikinfo.powercraft.deco.chimeny.Chimeny;
import ru.dannikinfo.powercraft.deco.chimeny.ItemBlockChimeny;
import ru.dannikinfo.powercraft.deco.chimeny.TileEntityChimeny;
import ru.dannikinfo.powercraft.deco.ironframe.IronFrame;
import ru.dannikinfo.powercraft.deco.ironframe.TileEntityIronFrame;
import ru.dannikinfo.powercraft.deco.platform.Platform;
import ru.dannikinfo.powercraft.deco.platform.TileEntityPlatform;
import ru.dannikinfo.powercraft.deco.stairs.Stairs;
import ru.dannikinfo.powercraft.deco.stairs.TileEntityStairs;

public class BlocksDeco {
	//Create variable for block
	public static Block ironframe;
	public static Block chimeny;
	public static Block platform;
	public static Block stairs;
	
	public static void Blocks(){
		
		//initialization variable
		ironframe = new IronFrame("ironframe");
		chimeny = new Chimeny("ciemeny");
		platform = new Platform("platform");
		stairs = new Stairs("stairs");
		
		//Block register
		GameRegistry.registerBlock(ironframe, "ironframe");
		GameRegistry.registerBlock(chimeny, ItemBlockChimeny.class, "chimeny");
		GameRegistry.registerBlock(platform,  "platform");
		GameRegistry.registerBlock(stairs,  "stairs");
	
		//TileEntity register
		GameRegistry.registerTileEntity(TileEntityIronFrame.class, "TileEntityIronFrame");
		GameRegistry.registerTileEntity(TileEntityChimeny.class, "TileEntityChimeny");
		GameRegistry.registerTileEntity(TileEntityPlatform.class, "TileEntityPlatform");
		GameRegistry.registerTileEntity(TileEntityStairs.class, "TileEntityStairs");
		
		//craft's
		GameRegistry.addRecipe(new ItemStack(ironframe, 8), new Object[]{ "###", "# #", "###", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(platform, 5), new Object[]{ "#  ", "#  ", "###", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(stairs, 5), new Object[]{ "#", "## ", "##", '#', Items.iron_ingot});
		
		//chimeny's craft's
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 0), new Object[]{ "# #", "# # ", "# #", '#', Blocks.cobblestone});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 1), new Object[]{ "# #", "# # ", "# #", '#', Blocks.mossy_cobblestone});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 2), new Object[]{ "# #", "# # ", "# #", '#', Blocks.brick_block});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 3), new Object[]{ "# #", "# # ", "# #", '#', Blocks.stonebrick});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 4), new Object[]{ "# #", "# # ", "# #", '#', Blocks.sandstone});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 5), new Object[]{ "# #", "# # ", "# #", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 6), new Object[]{ "# #", "# # ", "# #", '#', Blocks.nether_brick});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 7), new Object[]{ "# #", "# # ", "# #", '#', Blocks.quartz_block});
		GameRegistry.addRecipe(new ItemStack(chimeny, 6, 8), new Object[]{ "# #", "# # ", "# #", '#', Blocks.hardened_clay});
		
		//reverse craft's
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 1), new Object[]{platform});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 1), new Object[]{stairs});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 1), new Object[]{ironframe});
		
		
	}
}
