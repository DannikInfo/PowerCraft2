package ru.dannikinfo.powercraft.light.laser;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.beam.EntityLaserFX;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.light.ItemsLight;

public class Laser extends Block implements ITileEntityProvider {

	public NBTTagCompound nbt = new NBTTagCompound();
	
	public Laser() {
		super(Material.rock);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(0.25F);
		setBlockTextureName("cobblestone");
		setBlockName(Main.MODID + ".Laser");
	}    
    
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityLaser();
	}

	public int getRenderType() {
		return -1;
	}

    @Override
    public int getRenderBlockPass(){
        return 1;
    }
	
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is){
		int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}
	
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		tag.removeTag("color_" + udid);
		data.markDirty();
		EntityLaserFX.death = 1;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int n1, float f1, float f2, float f3){
	    if(!world.isRemote){
	    	ItemStack currentItem = player.getCurrentEquippedItem();    	
	    	if(currentItem != null && currentItem.getItem() == ItemsLight.LaserCompositionGreen){
	    		String udid = BaseUtils.udid(x, y, z);
	    		WorldData data = WorldData.forWorld(world);
	    		NBTTagCompound nbt = data.getData();
	    		int l = nbt.getInteger("color_" + udid);
	    		nbt.setInteger("color_" + udid, 2);
    		    currentItem.splitStack(1);
    		    player.setCurrentItemOrArmor(0, currentItem);
	    		if(l != 0){
	    			ItemStack itemC = null;
	    			if(l == 1)itemC = new ItemStack(ItemsLight.LaserCompositionBlue);
	    			if(l == 2)itemC = new ItemStack(ItemsLight.LaserCompositionGreen);
	    			if(l == 3)itemC = new ItemStack(ItemsLight.LaserCompositionRed);
	    			EntityItem item = new EntityItem(world, x, y + 1, z, itemC); 
	    			world.spawnEntityInWorld(item);
	    		}
	    		data.markDirty();
	    	}
	    	if(currentItem != null && currentItem.getItem() == ItemsLight.LaserCompositionBlue){
			   	String udid = BaseUtils.udid(x, y, z);
				WorldData data = WorldData.forWorld(world);
				NBTTagCompound nbt = data.getData();
		    	int l = nbt.getInteger("color_" + udid);
		    	nbt.setInteger("color_" + udid, 1);
		    	currentItem.splitStack(1);
		    	player.setCurrentItemOrArmor(0, currentItem);
		    	if(l != 0){
		    		ItemStack itemC = null;
		    		if(l == 1)itemC = new ItemStack(ItemsLight.LaserCompositionBlue);
		    		if(l == 2)itemC = new ItemStack(ItemsLight.LaserCompositionGreen);
		    		if(l == 3)itemC = new ItemStack(ItemsLight.LaserCompositionRed);
		    		EntityItem item = new EntityItem(world, x, y + 1, z, itemC); 
		    		world.spawnEntityInWorld(item);
		    	}
		    	data.markDirty();
	    	}
	    	if(currentItem != null && currentItem.getItem() == ItemsLight.LaserCompositionRed){
			    String udid = BaseUtils.udid(x, y, z);
				WorldData data = WorldData.forWorld(world);
				NBTTagCompound nbt = data.getData();
		    	int l = nbt.getInteger("color_" + udid);
		    	nbt.setInteger("color_" + udid, 3);
		    	currentItem.splitStack(1);
		    	player.setCurrentItemOrArmor(0, currentItem);
		    	if(l != 0){
		    		ItemStack itemC = null;
		    		if(l == 1)itemC = new ItemStack(ItemsLight.LaserCompositionBlue);
		    		if(l == 2)itemC = new ItemStack(ItemsLight.LaserCompositionGreen);
		    		if(l == 3)itemC = new ItemStack(ItemsLight.LaserCompositionRed);
		    		EntityItem item = new EntityItem(world, x, y + 1, z, itemC); 
		    		world.spawnEntityInWorld(item);
		    	}
		    	data.markDirty();
	    	}
	    	if(currentItem == null){
		    	String udid = BaseUtils.udid(x, y, z);
				WorldData data = WorldData.forWorld(world);
				NBTTagCompound nbt = data.getData();
	    		int l = nbt.getInteger("color_" + udid);
	    		nbt.setInteger("color_" + udid, 0);
	    		if(l != 0){
	    			ItemStack itemC = null;
	    			if(l == 1)itemC = new ItemStack(ItemsLight.LaserCompositionBlue);
	    			if(l == 2)itemC = new ItemStack(ItemsLight.LaserCompositionGreen);
	    			if(l == 3)itemC = new ItemStack(ItemsLight.LaserCompositionRed);
	    			EntityItem item = new EntityItem(world, x, y + 1, z, itemC); 
	    			world.spawnEntityInWorld(item);
	    		}
	    		data.markDirty();
	    	}
			if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
		    	String udid = BaseUtils.udid(x, y, z);
				WorldData data = WorldData.forWorld(world);
				NBTTagCompound nbt = data.getData();
			    int color = nbt.getInteger("color_" + udid);
				data.markDirty();
	    		int r = 0, g = 0, bl = 0, lenght = 16;
				if(color == 0){r = 255; g = 255; bl = 255;}
				if(color == 1){r = 0; g = 0; bl = 255; lenght = 32;}
				if(color == 2){r = 0; g = 255; bl = 0;}
				if(color == 3){r = 255; g = 0; bl = 0;}
			    int meta = world.getBlockMetadata(x, y, z);
			    EntityLaserFX.death = 1;
			    PacketManager.sendToServer(new BeamMessage(lenght, meta, x, y, z, r, g, bl, 0));
			}
			
	    }
	    return true;
	}
	
	public static int getColor(World world, int x, int y, int z){
    	String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound nbt = data.getData();
	    int color = nbt.getInteger("color_" + udid);
		data.markDirty();
		return color;
	}
	
	//public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
	   /* if(!world.isRemote){
			if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
		    	String udid = BaseUtils.udid(x, y, z);
				WorldData data = WorldData.forWorld(world);
				NBTTagCompound nbt = data.getData();
	    		int color = nbt.getInteger("color_" + udid);
	    		data.markDirty();
				int dir = world.getBlockMetadata(x, y, z);
				int r = 0, g = 0, bl = 0;
				if(color == 0){r = 255; g = 255; bl = 255;}
				if(color == 1){r = 0; g = 0; bl = 255;}
				if(color == 2){r = 0; g = 255; bl = 0;}
				if(color == 3){r = 255; g = 0; bl = 0;}
				PacketManager.sendToServer(new BeamMessage(31, dir, x, y, z, r, g, bl, 0));
			}else{
				EntityLaserFX.death = 1;
			}
	    }*/
	//}
}
