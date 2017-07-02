package ru.dannikinfo.powercraft.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipe {
	
	public static void recipe(){
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
		
	}
	
}
