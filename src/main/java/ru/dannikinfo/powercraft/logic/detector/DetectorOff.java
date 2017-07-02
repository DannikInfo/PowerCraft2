package ru.dannikinfo.powercraft.logic.detector;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;

public class DetectorOff extends Block implements ITileEntityProvider {
	
	NBTTagCompound nbt = new NBTTagCompound();

    public static final String[] metadata = new String[]{
            "Player_off",
            "Entity_off",
            "Item_off",
        };
	
	public DetectorOff() {
		super(Material.rock);
		setHardness(1F);
		setBlockTextureName("cobblestone");
		setBlockName(Main.MODID + ".Detector");
		
	}
	
    public int damageDropped(int par1)
    {
        return par1;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
	
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		tag.removeTag("range_st_" + udid);
	}
    
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityDetectorOff();
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){	
	    player.openGui(Main.instance, 8, world, x, y, z);
	    return true;
    }

	public int getRenderType() {
		return -1;
	}
	
	public boolean canProvidePower(){
	    return true;
	}
	
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int j = 0; j < metadata.length; ++j) {
            
            list.add(new ItemStack(item, 1, j));
            
        }
    }

}
