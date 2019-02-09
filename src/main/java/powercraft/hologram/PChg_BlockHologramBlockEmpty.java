package powercraft.hologram;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercraft.api.annotation.PC_BlockInfo;
import powercraft.api.block.PC_Block;
import powercraft.api.registry.PC_LangRegistry;
import powercraft.api.renderer.PC_Renderer;
import powercraft.api.utils.PC_ClientUtils;
import powercraft.api.utils.PC_Utils;

@PC_BlockInfo(name = "Hologramblock", itemBlock = PChg_ItemBlockHologramBlockEmpty.class)
public class PChg_BlockHologramBlockEmpty extends PC_Block {

	public PChg_BlockHologramBlockEmpty(int id) {
		super(Material.ground, "hologram");
		setHardness(0.5F);
		setResistance(0.5F);
		setStepSound(Block.soundTypeGlass);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/*
	 * @Override public boolean onBlockActivated(World world, int i, int j, int k,
	 * EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
	 * if(world.isRemote) { PChg_TileEntityHologramBlock te = PC_Utils.getTE(world,
	 * new PC_VecI(i, j, k)); int sw = te.getSwitchBlock(); int swMax =
	 * te.getSwitchBlockMax(); if(sw < swMax) sw++; else sw = 0;
	 * te.setSwitchBlock(sw); PC_PacketHandler.sendToServer(new PC_PacketSyncHB(sw,
	 * swMax, te)); } return true; }
	 */

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Object renderer) {
		final Map<Integer, Block> map = new TreeMap<Integer, Block>();
		int i = 0;
		for (int xOff = -1; xOff < 2; xOff++) {
			for (int yOff = -1; yOff < 2; yOff++) {
				for (int zOff = -1; zOff < 2; zOff++) {
					if ((zOff | xOff | yOff) == 0)
						continue;
					Block b = PC_Utils.getBID(world, xOff + x, yOff + y, zOff + z);
					if (b != Blocks.water && b != Blocks.lava && b != Blocks.flowing_lava && b != Blocks.flowing_water)
						// if(!map.containsValue(b)) {
						map.put(i, b);
					i++;
					// }
				}
			}
		}
		int maxCount = 0;
		Block fitting = Blocks.air;
		for (Entry<Integer, Block> entry : map.entrySet()) {
			if (entry.getValue() != Blocks.air)
				fitting = map.get(entry.getKey());
		}

		if (fitting == Blocks.air || fitting == null)
			fitting = PChg_App.hologramBlockEmpty;

		Block renderingBlock = fitting;
		PC_Renderer.tessellatorDraw();
		PC_Renderer.tessellatorStartDrawingQuads();
		if (fitting == PChg_App.hologramBlockEmpty || (PC_ClientUtils.mc().thePlayer.getCurrentEquippedItem() != null
				&& PC_ClientUtils.mc().thePlayer.getCurrentEquippedItem().getItem() == Items.stick)) {
			PC_Renderer.renderStandardBlock(renderer, this, x, y, z);
		} else {
			if (!renderingBlock.hasTileEntity()) {
				PC_Renderer.renderBlockByRenderType(renderer, renderingBlock, x, y, z);
			} else {
				PC_Renderer.renderStandardBlock(renderer, this, x, y, z);
			}
		}
		PC_Renderer.tessellatorDraw();
		PC_Renderer.tessellatorStartDrawingQuads();
		return true;
	}
}
