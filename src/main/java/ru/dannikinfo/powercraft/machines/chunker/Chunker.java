package ru.dannikinfo.powercraft.machines.chunker;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.BlocksCore;
import ru.dannikinfo.powercraft.core.ItemsCore;
import ru.dannikinfo.powercraft.core.Main;

public class Chunker extends Block implements ITileEntityProvider{

	public Object blockIcon_back;
	public Object blockIcon_front;
	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_side;
	public NBTTagCompound nbt = new NBTTagCompound();
	public boolean isPowered;
	
	public Chunker() {
		super(Material.iron);
		setBlockName(Main.MODID + ".Chunker");
		setCreativeTab(Main.tabPowerCraft);
		setHardness(1F);
		setStepSound(soundTypeStone);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_front = reg.registerIcon("powercraftreloaded:Machines" + "_" + "top");
		blockIcon_back = reg.registerIcon("powercraftreloaded:Machines" + "_" + "top");
		blockIcon_top = reg.registerIcon("powercraftreloaded:Chunker" + "_" + "top");
		blockIcon_bottom = reg.registerIcon("powercraftreloaded:Machines" + "_" + "top");
		blockIcon_side = reg.registerIcon("powercraftreloaded:Machines" + "_" + "top");
	}
	
    public IIcon getIcon(int side, int meta) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        ForgeDirection block_dir;
        if (meta != 0)
            block_dir = ForgeDirection.getOrientation(meta);
        else
            block_dir = ForgeDirection.EAST; // Блок в инвентаре.
        if (block_dir == dir) return (IIcon) blockIcon_front;
        if (block_dir.getOpposite() == dir) return (IIcon) blockIcon_back;
        if (dir == ForgeDirection.UP) return (IIcon) blockIcon_top;
        if (dir == ForgeDirection.DOWN) return (IIcon) blockIcon_bottom;
        return (IIcon) blockIcon_side;
    }
	
    public void onBlockPlacedBy(World world, int x, int y, int z,
            EntityLivingBase entity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
        int dir = (MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        int[] r = {2, 5, 3, 4};
        world.setBlockMetadataWithNotify(x, y, z, r[dir], 3);
        nbt.setInteger("status", 0);
    } 
    
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
    	String udid = BaseUtils.udid(p_149734_2_, p_149734_3_, p_149734_4_);
		WorldData data = WorldData.forWorld(p_149734_1_);
		NBTTagCompound tag = data.getData();
		int stats = tag.getInteger("status_" + udid);
		data.markDirty();
        if(stats == 1){
			for (int l = 0; l < 8; ++l)
	        {
	            double d0 = (double)((float)p_149734_2_ + p_149734_5_.nextFloat());
	            double d1 = (double)((float)p_149734_3_ + p_149734_5_.nextFloat());
	            double d2 = (double)((float)p_149734_4_ + p_149734_5_.nextFloat());
	            double d3 = 0.0D;
	            double d4 = 0.0D;
	            double d5 = 0.0D;
	            int i1 = p_149734_5_.nextInt(2) * 2 - 1;
	            d3 = ((double)p_149734_5_.nextFloat() - 0.5D) * 0.5D;
	            d4 = ((double)p_149734_5_.nextFloat() - 0.5D) * 0.5D;
	            d5 = ((double)p_149734_5_.nextFloat() - 0.5D) * 0.5D;
	
	            if (p_149734_1_.getBlock(p_149734_2_ - 1, p_149734_3_, p_149734_4_) != this && p_149734_1_.getBlock(p_149734_2_ + 1, p_149734_3_, p_149734_4_) != this)
	            {
	                d0 = (double)p_149734_2_ + 0.5D + 0.25D * (double)i1;
	                d3 = (double)(p_149734_5_.nextFloat() * 2.0F * (float)i1);
	            }
	            else
	            {
	                d2 = (double)p_149734_4_ + 0.5D + 0.25D * (double)i1;
	                d5 = (double)(p_149734_5_.nextFloat() * 2.0F * (float)i1);
	            }
	
	            p_149734_1_.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
	        }
        }
    }

	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityChunker();
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int n1, float f1, float f2, float f3){
	    	String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		int stats = tag.getInteger("status_" + udid);
	    	if(!world.isRemote){
	    		if(player.inventory.getCurrentItem() != null){ 
	    			Item item = player.inventory.getCurrentItem().getItem();
		    		if(item == ItemsCore.ActivationCrystal){
			    		if(stats == 0){
							if(
			                        world.getBlock(x, y + 1, z) == BlocksCore.crystal && world.getBlockMetadata(x, y + 1, z) == 7 &&
			                        world.getBlock(x + 3, y + 2, z) == BlocksCore.crystal && world.getBlockMetadata(x + 3, y + 2, z) == 6 &&
			                        world.getBlock(x - 3, y + 2, z) == BlocksCore.crystal && world.getBlockMetadata(x - 3, y + 2, z) == 6 &&
			                        world.getBlock(x, y + 2, z + 3) == BlocksCore.crystal && world.getBlockMetadata(x, y + 2, z + 3) == 1 &&
			                        world.getBlock(x, y + 2, z - 3) == BlocksCore.crystal && world.getBlockMetadata(x, y + 2, z - 3) == 1 &&
			                        world.getBlock(x + 2, y + 4, z - 2) == BlocksCore.crystal && world.getBlockMetadata(x + 2, y + 4, z - 2) == 2 &&
			                        world.getBlock(x - 2, y + 4, z - 2) == BlocksCore.crystal && world.getBlockMetadata(x - 2, y + 4, z - 2) == 2 &&
			                        world.getBlock(x - 2, y + 4, z + 2) == BlocksCore.crystal && world.getBlockMetadata(x - 2, y + 4, z + 2) == 2 &&
			                        world.getBlock(x + 2, y + 4, z + 2) == BlocksCore.crystal && world.getBlockMetadata(x + 2, y + 4, z + 2) == 2 &&
			                        world.getBlock(x + 3, y + 1, z) == Blocks.diamond_block &&
			                        world.getBlock(x - 3, y + 1, z) == Blocks.diamond_block &&
			                        world.getBlock(x, y + 1, z + 3) == Blocks.diamond_block &&
			                        world.getBlock(x, y + 1 , z - 3) == Blocks.diamond_block &&
			                        world.getBlock(x + 2, y + 5, z - 2) == Blocks.diamond_block &&
			                        world.getBlock(x - 2, y + 5, z - 2) == Blocks.diamond_block &&
			                        world.getBlock(x - 2, y + 5, z + 2) == Blocks.diamond_block &&
			                        world.getBlock(x + 2, y + 5, z + 2) == Blocks.diamond_block 
									){
				    			TileEntityChunker tile = (TileEntityChunker) world.getTileEntity(x, y, z);
				    			tile.loadChunks();		
				    			tag.setInteger("status_" + udid, 1);
				    			isPowered = true;
							}
			    		}
			    		if(stats == 1){
			    			
			    			TileEntityChunker tile = (TileEntityChunker) world.getTileEntity(x, y, z);
			    			tile.unloadChunks();
			    			tag.setInteger("status_" + udid, 0);
			    			isPowered = false;
			    		}
		    		}
	    		}
	    	}
	    	data.markDirty();
	    	return true;
    }
}
