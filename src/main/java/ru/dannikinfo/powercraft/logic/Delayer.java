package ru.dannikinfo.powercraft.logic;

import java.util.Random;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.core.Main;

public class Delayer extends Block
{

    public static Delayer on;
    public static Delayer off;

    public Delayer(String name){
        super(Material.ground);
        setHardness(0.35F);
        setStepSound(Block.soundTypeWood);
        disableStats();
        setResistance(30.0F);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
        setCreativeTab(Main.tabPowerCraft);
    }
    
    @Override
	public void initConfig(PC_Property config) {
		super.initConfig(config);
		on.setLightValue(config.getInt("brightness", 7) * 0.0625F);
	}

	@Override
    public void updateTick(World world, int x, int y, int z, Random random){
        TileEntityDelayer te = getTE(world, x, y, z);
        boolean data = getRedstonePowerValueFromInput(world, x, y, z, PC_Direction.BACK)>0;
        
        switch(te.getType()){
        case DelayerType.FIFO:
        	boolean stop = getRedstonePowerValueFromInput(world, x, y, z, PC_Direction.RIGHT)>0;
        	boolean reset = getRedstonePowerValueFromInput(world, x, y, z, PC_Direction.LEFT)>0;
       		boolean[] stateBuffer = te.getStateBuffer();

	        if (!stop && !reset)
	        {
	            boolean shouldState = stateBuffer[stateBuffer.length - 1];
	
	            if (shouldState != isActive(world, x, y, z)){
	                BaseUtils.setBlockState(world, x, y, z, shouldState);
	            }
	
	            for (int i = stateBuffer.length - 1; i > 0; i--)
	            {
	                stateBuffer[i] = stateBuffer[i - 1];
	            }
	
	            stateBuffer[0] = data;
	        }
	
	        if (reset){
	            if (isActive(world, x, y, z)){
	                BaseUtils.setBlockState(world, x, y, z, false);
	            }
	
	            for (int i = 0; i < stateBuffer.length; i++)
	            {
	                stateBuffer[i] = false;
	            }
	        }
	        break;
        case DelayerType.HOLD:
        	
        	boolean should = true;
        	
        	if(te.decRemainingTicks()){
        		should = false;
        	}
        	
        	if(data){
        		te.resetRemainingTicks();
        		should = true;
        	}
        	
        	if (isActive(world, x, y, z)!=should){
        		BaseUtils.setBlockState(world, x, y, z, should);
        	}
        	
        }
    }

    @Override
    public int tickRate(World par1World){
        return 1;
    }
    
    @Override
	public int getProvidingWeakRedstonePowerValue(IBlockAccess world, int x, int y, int z, Direction dir) {
		return getProvidingStrongRedstonePowerValue(world, x, y, z, dir);
	}

	@Override
	public int getProvidingStrongRedstonePowerValue(IBlockAccess world, int x, int y, int z, Direction dir) {
        if (isActive(world, x, y, z) && dir==Direction.FRONT){
            return 15;
        }

        return 0;
	}

    @Override
    public boolean canProvidePower(){
        return true;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    public static TileEntityDelayer getTE(IBlockAccess world, int x, int y, int z){
        TileEntity te = PC_Utils.getTE(world, x, y, z);;

        if (te instanceof TileEntityDelayer){
            return (TileEntityDelayer)te;
        }

        return null;
    }

    public static int getType(IBlockAccess world, int x, int y, int z){
        TileEntityDelayer te = getTE(world, x, y, z);

        if (te != null)
        {
            return te.getType();
        }

        return 0;
    }

    public static boolean isActive(IBlockAccess world, int x, int y, int z){
        return PC_Utils.getBID(world, x, y, z) == on.blockID;
    }

    @Override
    public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, Direction side)
    {
        if (side == Direction.TOP){
            return sideIcons[getType(iblockaccess, x, y, z)+2+(isActive(iblockaccess, x, y, z) ? 0 : PClo_DelayerType.TOTAL_DELAYER_COUNT)];
        }

        if (side == Direction.BOTTOM)
        {
            return sideIcons[0];
        }

        return sideIcons[1];
    }

    @Override
    public Icon getIcon(Direction side, int meta)
    {
        if (side == Direction.BOTTOM)
        {
            return sideIcons[0];
        }

        if (side == Direction.TOP)
        {
            return sideIcons[meta+2];
        }
        else
        {
            return sideIcons[1];
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itmeStack){
        if (entityliving instanceof EntityPlayer){
            //PC_GresRegistry.openGres("Delayer", (EntityPlayer)player, PC_Utils.<TileEntity>getTE(world, x, y, z));
        }
        world.scheduleBlockUpdate(x, y, z, blockID, tickRate(world));
        PC_Utils.hugeUpdate(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        PC_GresRegistry.openGres("Delayer", player, PC_Utils.<PC_TileEntity>getTE(world, x, y, z));
        return true;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (!isActive(world, x, y, z))
        {
            return;
        }

        if (random.nextInt(3) != 0)
        {
            return;
        }

        double d = (x + 0.5F) + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d1 = (y + 0.2F) + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
        double d2 = (z + 0.5F) + (random.nextFloat() - 0.5F) * 0.20000000000000001D;
        world.spawnParticle("reddust", d, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public int idDropped(int i, Random random, int j)
    {
        return -1;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        int type = getType(world, x, y, z);
        boolean remove = super.removeBlockByPlayer(world, player, x, y, z);

        if (remove && !PC_Utils.isCreative(player))
        {
            dropBlockAsItem_do(world, x, y, z, new ItemStack(PClo_App.delayer, 1, type));
        }

        return remove;
    }
    
}
