package ru.dannik.powercraft.blocks.logic.pulsar;

import org.apache.commons.codec.digest.DigestUtils;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.dannik.powercraft.utils.WorldData;

public class PulsarOff extends Block implements ITileEntityProvider{
	
	NBTTagCompound nbt = new NBTTagCompound();
	
	public PulsarOff() {
		super(Material.redstoneLight);
		setHardness(1F);
		setStepSound(soundTypeStone);
		setBlockTextureName("powercraftreloaded:pulsaroff");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityPulsar();
	}
	
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		String sX = Integer.toString(x);
		String sY = Integer.toString(y);
		String sZ = Integer.toString(z);
		String preUdid = DigestUtils.md5Hex(sX)+DigestUtils.md5Hex(sY)+DigestUtils.md5Hex(sZ); 
		String udid = DigestUtils.md5Hex(preUdid);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		tag.removeTag("delay_st_" + udid);
	}
	
	public boolean canProvidePower(){
	    return true;
	}
	

}
