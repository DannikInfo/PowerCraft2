package powercraft.light;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercraft.api.annotation.PC_FieldObject;
import powercraft.api.block.PC_Block;
import powercraft.api.item.PC_Item;
import powercraft.api.item.PC_ItemStack;
import powercraft.api.recipes.PC_IRecipe;
import powercraft.api.recipes.PC_ShapedRecipes;
import powercraft.api.recipes.PC_ShapelessRecipes;
import powercraft.api.registry.PC_BlockRegistry;
import powercraft.core.PCco_App;
import powercraft.launcher.loader.PC_Module;
import powercraft.launcher.loader.PC_Module.PC_InitRecipes;
import powercraft.launcher.loader.PC_Module.PC_Instance;
import powercraft.launcher.loader.PC_ModuleObject;

@PC_Module(name="Light", version="0.0.2")
public class PCli_App{

	@PC_FieldObject(clazz=PCli_BlockLight.class)
	public static PC_Block light;
	@PC_FieldObject(clazz=PCli_BlockLightningConductor.class)
	public static PC_Block lightningConductor;
	@PC_FieldObject(clazz=PCli_BlockLaser.class)
	public static PC_Block laser;
	@PC_FieldObject(clazz=PCli_BlockMirror.class)
	public static PC_Block mirror;
	@PC_FieldObject(clazz=PCli_BlockPrism.class)
	public static PC_Block prism;
	@PC_FieldObject(clazz=PCli_BlockLaserSensor.class)
	public static PC_Block laserSensor;
	@PC_FieldObject(clazz=PCli_ItemLaserComposition.class)
	public static PC_Item laserComposition;
	@PC_Instance
	public static PC_ModuleObject instance;

	@PC_InitRecipes
	public List<PC_IRecipe> initRecipes(List<PC_IRecipe> recipes) {
		GameRegistry.addShapelessRecipe(new ItemStack(light), new Object[]{Items.redstone, Blocks.glowstone});
		GameRegistry.addRecipe(new ItemStack(lightningConductor), new Object[] {
				" X ", 
				" X ", 
				"XXX",
				'X', Blocks.iron_block});

		GameRegistry.addRecipe(new ItemStack(laser), new Object[] {
				" WD", 
				" S ", 
				"SSS",
				'S', Blocks.stone, 'W', new ItemStack(Blocks.planks, 1), 'D', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(laser), new Object[] {
				" WD", 
				" S ", 
				"SSS",
				'S', Blocks.cobblestone, 'W', new ItemStack(Blocks.planks, 1), 'D', Items.diamond});

		GameRegistry.addRecipe(new ItemStack(laserSensor, 1), new Object[] {
				"L", 
				"R", 
				'L', new ItemStack(laser, 1), 'R', Items.redstone});

		GameRegistry.addRecipe(new ItemStack(mirror, 2, 0), new Object[] {
				"GI", 
				" I",	
				'G', Blocks.glass_pane, 'I', Items.iron_ingot});

		GameRegistry.addRecipe(new ItemStack(prism, 1), new Object[] {
				"GG", 
				"GG", 
				'G', Blocks.glass});

		List<ItemStack> l = laserComposition.getItemStacks(new ArrayList<ItemStack>());

		GameRegistry.addRecipe(l.get(0), new Object[] {
				"XXX", 
				"XPX", 
				"XXX",
				'X', Blocks.glass, 'P', new ItemStack(PCco_App.powerCrystal, 1, 0)});

		GameRegistry.addRecipe(l.get(1), new Object[] {
				"XXX", 
				"XPX", 
				"XXX",
				'X', Blocks.glass, 'P', new ItemStack(PCco_App.powerCrystal, 1, 1)});

		GameRegistry.addRecipe(l.get(2), new Object[] {
				"XXX", 
				"XPX", 
				"XXX",
				'X', Blocks.glass, 'P', new ItemStack(PCco_App.powerCrystal, 1, 2)});
		return recipes;
	}

}
