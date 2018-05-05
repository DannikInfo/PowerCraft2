package ru.dannikinfo.powercraft.logic.pulsar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;

public class Pulsar extends Block implements ITileEntityProvider{
	
	NBTTagCompound nbt = new NBTTagCompound();
	
	public Pulsar() {
		super(Material.redstoneLight);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(1F);
		setLightLevel(0.1F);
		setStepSound(soundTypeStone);
		setBlockTextureName("powercraftreloaded:pulsar");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {	
		return new TileEntityPulsar();
	}
	
	public boolean canProvidePower(){
	    return true;
	}
	
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		tag.removeTag("delay_st_" + udid);
	}
	
    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
    {	
    	int i1 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
    	return i1 == 5 && p_149709_5_ == 1 ? 0 : (i1 == 3 && p_149709_5_ == 3 ? 0 : (i1 == 4 && p_149709_5_ == 2 ? 0 : (i1 == 1 && p_149709_5_ == 5 ? 0 : (i1 == 2 && p_149709_5_ == 4 ? 0 : 15))));
    }

}