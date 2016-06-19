package ru.dannik.powercraft.BlocksL.Crystal.DarkBlue;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import ru.dannik.powercraft.Main;

public class DarkBlueCrystal extends Block implements ITileEntityProvider {

	public DarkBlueCrystal() {
		super(Material.glass);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(1F);
		setBlockTextureName("glass");
		setBlockName(Main.MODID + ".DarkBlueCrystal");
		
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
       return true;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityDarkBlueCrystal();
	}

	public int getRenderType() {
		return -1;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

}
