package powercraft.hologram;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import powercraft.api.annotation.PC_FieldObject;
import powercraft.api.block.PC_Block;
import powercraft.api.item.PC_ItemArmor;
import powercraft.api.item.PC_ItemStack;
import powercraft.api.recipes.PC_IRecipe;
import powercraft.api.recipes.PC_ShapedRecipes;
import powercraft.api.registry.PC_BlockRegistry;
import powercraft.api.registry.PC_ItemRegistry;
import powercraft.launcher.loader.PC_Module;
import powercraft.launcher.loader.PC_Module.PC_InitRecipes;
import powercraft.launcher.loader.PC_Module.PC_Instance;
import powercraft.launcher.loader.PC_ModuleObject;

@PC_Module(name = "Hologram", version = "0.0.1")
public class PChg_App {

	@PC_FieldObject(clazz = PChg_BlockHologramBlockEmpty.class)
	public static PC_Block hologramBlockEmpty;
	@PC_FieldObject(clazz = PChg_BlockHologramBlock.class)
	public static PC_Block hologramBlock;
	@PC_FieldObject(clazz = PChg_BlockHologramField.class)
	public static PC_Block hologramField;
	@PC_FieldObject(clazz = PChg_ItemArmorHologramGlasses.class)
	public static PC_ItemArmor hologramGlasses;
	@PC_Instance
	private static PC_ModuleObject instance;

	public static PChg_App getInstance() {
		return (PChg_App) instance.getModule();
	}

	@PC_InitRecipes
	public List<PC_IRecipe> initRecipes(List<PC_IRecipe> recipes) {
		//GameRegistry.addRecipe(new PChg_HologramBackRecipe());
		for(int i = 0; i < 8; i++)
			GameRegistry.addRecipe(new ItemStack(hologramBlockEmpty), new Object[] {" p ", "gcg", "ggg", 'g', Blocks.glass,
					'c', Blocks.chest, 'p', new ItemStack(PC_BlockRegistry.getPCBlockByName("PCco_BlockPowerCrystal"), 1, i)});
		/*GameRegistry.addRecipe(new ItemStack(hologramField), new Object[] { "ggg", "hhh", "ioi", 'i', Items.iron_ingot, 'g',
				Blocks.glass, 'h', new PC_ItemStack(hologramBlockEmpty), 'o',
				new ItemStack(PC_ItemRegistry.getPCItemByName("PCco_ItemOreSniffer"))});
		GameRegistry.addRecipe(new ItemStack(hologramGlasses), new Object[] {"i i", "ghg", 'i', Items.iron_ingot, 'g',
				Blocks.glass_pane, 'h', new PC_ItemStack(hologramField)});*/
		return recipes;
	}

	/*public List<PC_ItemStack> getAllAccepptedBlocksForHologramBlock() {
		List<PC_ItemStack> l = new ArrayList<PC_ItemStack>();
		Block b;
		for (int i = 1; i < b.blockBlocks.blocksList.length; i++) {
			if (Block.blocksList[i] != null && Item.itemsList[i] != null) {
				if (Block.renderAsNormalBlock()) {
					l.add(new PC_ItemStack(Block.blocksList[i], 1, -1));
				}
			}
		}
		return l;
	}*/

	public void renderHologramField(PChg_TileEntityHologramField te, double x, double y, double z) {

	}

}
