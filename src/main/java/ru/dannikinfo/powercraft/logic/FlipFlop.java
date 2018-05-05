package ru.dannikinfo.powercraft.logic;

import java.util.Random;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.logic.tile.TileEntityFlipFlop;
import ru.dannikinfo.powercraft.logic.type.FlipFlopType;


public class FlipFlop extends Block{
    private static Random rand = new Random();


    public FlipFlop()
    {
        super(Material.ground);
        setHardness(0.35F);
        setStepSound(Block.soundTypeWood);
        disableStats();
        setResistance(30.0F);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random){
        TileEntityFlipFlop te = (TileEntityFlipFlop) world.getTileEntity(x, y, z);
        boolean state = isActive(world, x, y, z);
        boolean i1 = getProvidingWeakRedstonePowerValue(world, x, y, z, Direction.RIGHT)>0;
        boolean i2 = getProvidingWeakRedstonePowerValue(world, x, y, z, Direction.BACK)>0;
        boolean i3 = getProvidingWeakRedstonePowerValue(world, x, y, z, Direction.LEFT)>0;
        boolean shouldState = state;

        switch (te.getType())
        {
            case FlipFlopType.D:
            	
            	if (i3){
            		shouldState = false;
            	}
            	
                if (i1)
                {
                    shouldState = i2;
                }
                	

                break;

            case FlipFlopType.RS:

                if (i1)
                {
                    shouldState = false;
                }

                if (i3)
                {
                    shouldState = true;
                }

                break;

            case FlipFlopType.T:

                if (i2)
                {
                    if (!te.getClock())
                    {
                        te.setClock(true);
                        shouldState = !state;
                    }
                }
                else
                {
                    if (te.getClock())
                    {
                        te.setClock(false);
                    }
                }

                if (i1 || i3)
                {
                    shouldState = false;
                }

                break;

            case FlipFlopType.RANDOM:

                if (i2)
                {
                    if (!te.getClock())
                    {
                        te.setClock(true);
                        shouldState = rand.nextBoolean();
                    }
                }
                else
                {
                    if (te.getClock())
                    {
                        te.setClock(false);
                    }
                }
        }

    }

    @Override
    public int tickRate(World world){
        return 1;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        world.scheduleBlockUpdate(x, y, z, block, tickRate(world));
    }
    
	public int getProvidingWeakRedstonePowerValue(IBlockAccess world, int x, int y, int z, Direction dir) {
		return getProvidingStrongRedstonePowerValue(world, x, y, z, dir);
	}
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itmeStack){
        Block block = world.getBlock(x, y, z);
    	world.scheduleBlockUpdate(x, y, z, block, tickRate(world));
        BaseUtils.hugeUpdate(world, x, y, z);
    }
    
	public int getProvidingStrongRedstonePowerValue(IBlockAccess world, int x, int y, int z, Direction dir) {
    	if (!isActive(world, x, y, z)){
            return 0;
        }

        if (Direction.FRONT == dir){
            return 15;
        }

        if (getType(world, x, y, z) == FlipFlopType.RS){
            if (Direction.BACK == dir){
                return 15;
            }
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


    public static int getType(IBlockAccess world, int x, int y, int z){
        TileEntityFlipFlop te = (TileEntityFlipFlop) world.getTileEntity(x, y, z);

        if (te != null)
        {
            return te.getType();
        }

        return 0;
    }

    public static boolean isActive(IBlockAccess world, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) > 8 && world.getBlockMetadata(x, y, z) < 16;
    }

    public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, Direction side)
    {
        if (side ==  Direction.TOP)
        {
            return sideIcons[getType(iblockaccess, x, y, z)+2+(isActive(iblockaccess, x, y, z) ? 0 : PClo_FlipFlopType.TOTAL_FLIPFLOP_COUNT)];
        }

        if (side == Direction.BOTTOM)
        {
            return sideIcons[0];
        }

        return sideIcons[1];
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (side == PC_Direction.BOTTOM)
        {
            return sideIcons[0];
        }

        if (side == PC_Direction.TOP)
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
    public int quantityDropped(Random random){
        return 0;
    }

    @Override
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z){
        int type = getType(world, x, y, z);
        boolean remove = super.removeBlockByPlayer(world, player, x, y, z);

        if (remove && !player.capabilities.isCreativeMode)
        {
            dropBlockAsItem_do(world, x, y, z, new ItemStack(PClo_App.flipFlop, 1, type));
        }

        return remove;
    }
   	
}

