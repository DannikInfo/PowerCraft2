package ru.dannikinfo.powercraft.machines;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.light.laser.BeamMessage;

public class Harvester extends Block{
	
	public Object blockIcon_back;
	public Object blockIcon_front;
	public Object blockIcon_side;
	NBTTagCompound nbt = new NBTTagCompound();
	int i;
	int meta;
	int count = 0;
	boolean isp = false;
	
	public Harvester() {
		super(Material.rock);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(0.25F);
		setBlockTextureName("Coblestone");
		setBlockName(Main.MODID + ".Harvester");
	}
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_front = reg.registerIcon("powercraftreloaded:Harvester" + "_" + "side");
		blockIcon_back = reg.registerIcon("powercraftreloaded:Harvester" + "_" + "front");
		blockIcon_side = reg.registerIcon("powercraftreloaded:Machines" + "_" + "top");
	}
	
    public IIcon getIcon(int side, int meta) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        ForgeDirection block_dir;
        if (meta != 0)
            block_dir = ForgeDirection.getOrientation(meta);
        else
            block_dir = ForgeDirection.EAST;
        if (block_dir == dir) return (IIcon) blockIcon_front;
        if (block_dir.getOpposite() == dir) return (IIcon) blockIcon_back;
        return (IIcon) blockIcon_side;
    }
    
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		tag.removeTag("front_" + udid);
	}
	
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
        int dir = (MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        int[] r = {2, 5, 3, 4};
        world.setBlockMetadataWithNotify(x, y, z, r[dir], 3);
        String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound nbt = data.getData();
        nbt.setInteger("Block", 1);
		nbt.setInteger("front_" + udid, dir);
		data.markDirty();
    }
       
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
	    if(!world.isRemote){
			if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
		    	String udid = BaseUtils.udid(x, y, z);
			WorldData data = WorldData.forWorld(world);
			NBTTagCompound nbt = data.getData();
	    		int dir = nbt.getInteger("front_" + udid);
	    		data.markDirty();
			i = nbt.getInteger("Block");
	    		isp = nbt.getBoolean("isp");	
	    		Next:if(isp == false){ 
	    			for(int k = 1; k < 32; ++k){
			   		int zK = z, xK = x;
			   		if(dir == 0)zK = z + k;
			   		if(dir == 1)xK = x - k;
		    			if(dir == 2)zK = z - k;
		    			if(dir == 3)xK = x + k;
		    			if(world.isAirBlock(xK, y, zK) || world.getBlock(xK, y , zK) == Blocks.wheat && world.getBlockMetadata(xK, y, zK) != 7  || world.getBlock(xK, y , zK) == Blocks.sapling){
		    			}else{
			   				i = k;
		    					nbt.setBoolean("isp", true);
		    					isp = true;
		    					break Next;
		    				}
		   			}
		   		}else{
		   			nbt.setBoolean("isp", false);
			   		if(i < 32){
			   			int zS = z, xS = x, zE = z, xE = x;
			   			if(dir == 0)zS = z + i;
			   			if(dir == 1)xS = x - i;
		    			if(dir == 2)zS = z - i;
		    			if(dir == 3)xS = x + i;
	   					int a;
	   					boolean c = true;
	    				for(a = 1; a <= 32; ++a){
				   			if(dir == 0)zE = z + a;
				   			if(dir == 1)xE = x - a;
				  			if(dir == 2)zE = z - a;
				  			if(dir == 3)xE = x + a;
				   			if(world.getBlock(xE, y, zE) != Blocks.stonebrick){
				   				c = true;
				   			}else{
				   				c = false;
			    				break;
			    			}
		    			}
	    				if(c == true){
		    				PacketManager.sendToServer(new BeamMessage(31, dir, x, y, z, 0, 255, 0, 0));
		    			}else{
		    				PacketManager.sendToServer(new BeamMessage(a, dir, x, y, z, 0, 255, 0, 0));
		    			}
		    			BaseUtils.harvest(world, xS, y, zS, i, dir);
		   				i++;
		   			}
		    	}
		    		nbt.setInteger("Block", i);
		    		return;		
		    }
	    }
   	}	
}