package ru.dannikinfo.powercraft.transport.elevator;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.MathHelper;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.transport.BlocksTransport;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;

public class Elevator extends Block{
    private static final double BORDERS = 0.25D;
    private static final double BORDER_BOOST = 0.062D;
    public static final String[] metadata = new String[] {
            "elevator_up",
            "elevator_down",
        };
	@SideOnly(Side.CLIENT)
	private IIcon[] textures = new IIcon[this.metadata.length];
	
    public Elevator()
    {
        super(Material.glass);
        setHardness(0.5F);
        setResistance(8.0F);
        setCreativeTab(Main.tabPowerCraft);
        setBlockName("elevator");
        setBlockTextureName("glass");
    }
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	     super.registerBlockIcons(iconRegister);
	
	     for (int i1 = 0; i1 < this.textures.length; ++i1){
	    	 this.textures[i1] = iconRegister.registerIcon(Main.MODID + ":" + "transport/elevator_" + i1);
	     }
	}
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
		metadata &= 7;
		return textures[MathHelper.clamp_int(metadata, 0, 5)];
     
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return true;
    }

	@Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
		VecI pos = new VecI(i, j, k);

        if (BeltHelper.isEntityIgnored(entity))
        {
            return;
        }

        boolean down = (BaseUtils.getMD(world, i, j, k) == 1);
        BeltHelper.entityPreventDespawning(world, pos, true, entity);
        boolean halted = world.isBlockIndirectlyGettingPowered(i, j, k);
        double BBOOST = (entity instanceof EntityPlayer) ? BORDER_BOOST / 4.0D : BORDER_BOOST;
        
        if (Math.abs(entity.motionY) > 0.4D)
        {
            entity.motionY *= 0.3D;
        }

        entity.fallDistance = 0;

            if (entity instanceof EntityLiving)
            {
            	Direction side = null;

                if ((BeltHelper.isConveyorAt(world, pos.offset(1, 0, 0)) && world.isAirBlock(i + 1, j + 1, k)))
                {
                    side = Direction.LEFT;
                }
                else if ((BeltHelper.isConveyorAt(world, pos.offset(-1, 0, 0)) && world.isAirBlock(i - 1, j + 1, k)))
                {
                    side = Direction.RIGHT;
                }
                else if ((BeltHelper.isConveyorAt(world, pos.offset(0, 0, 1)) && world.isAirBlock(i, j + 1, k + 1)))
                {
                    side = Direction.FRONT;
                }
                else if ((BeltHelper.isConveyorAt(world, pos.offset(0, 0, -1)) && world.isAirBlock(i, j + 1, k - 1)))
                {
                    side = Direction.BACK;
                }
                else if ((world.isAirBlock(i + 1, j, k) && !world.isAirBlock(i + 1, j - 1, k)))
                {
                    side = Direction.LEFT;
                }
                else if ((world.isAirBlock(i - 1, j, k) && !world.isAirBlock(i - 1, j - 1, k)))
                {
                    side = Direction.RIGHT;
                }
                else if ((world.isAirBlock(i, j, k + 1) && !world.isAirBlock(i, j - 1, k + 1)))
                {
                    side = Direction.FRONT;
                }
                else if ((world.isAirBlock(i, j, k - 1) && !world.isAirBlock(i, j - 1, k - 1)))
                {
                    side = Direction.BACK;
                }

                if (side != null)
                {
                    BeltHelper.moveEntityOnBelt(world, pos, entity, true, true, side, BeltHelper.MAX_HORIZONTAL_SPEED,
                            BeltHelper.HORIZONTAL_BOOST);
                }
            }
            else
            {
                if ((down && entity.posY < j + 0.6D) || (!down && entity.posY > j + 0.1D))
                {
                    if (BeltHelper.isConveyorAt(world, pos.offset(1, 0, 0)))
                    {
                        BeltHelper.moveEntityOnBelt(world, pos, entity, true, true, Direction.LEFT, BeltHelper.MAX_HORIZONTAL_SPEED,
                                BeltHelper.HORIZONTAL_BOOST * (down ? 1.2D : 1));
                    }
                    else if (BeltHelper.isConveyorAt(world, pos.offset(-1, 0, 0)))
                    {
                        BeltHelper.moveEntityOnBelt(world, pos, entity, true, true, Direction.RIGHT, BeltHelper.MAX_HORIZONTAL_SPEED,
                                BeltHelper.HORIZONTAL_BOOST * (down ? 1.2D : 1));
                    }
                    else if (BeltHelper.isConveyorAt(world, pos.offset(0, 0, 1)))
                    {
                        BeltHelper.moveEntityOnBelt(world, pos, entity, true, true, Direction.FRONT, BeltHelper.MAX_HORIZONTAL_SPEED,
                                BeltHelper.HORIZONTAL_BOOST * (down ? 1.2D : 1));
                    }
                    else if (BeltHelper.isConveyorAt(world, pos.offset(0, 0, -1)))
                    {
                       BeltHelper.moveEntityOnBelt(world, pos, entity, true, true, Direction.BACK, BeltHelper.MAX_HORIZONTAL_SPEED,
                                BeltHelper.HORIZONTAL_BOOST * (down ? 1.2D : 1));
                }
            }
            if (!down){
                if (entity.motionY < ((halted) ? 0.2D : 0.3D))
                {
                    entity.motionY = ((halted) ? 0.2D : 0.3D);

                    if (entity.onGround)
                    {
                        entity.moveEntity(0, 0.01D, 0);
                    }
                }
            }

            if (entity.posX > pos.x + (1D - BORDERS))
            {
                entity.motionX -= BBOOST;
            }

            if (entity.posX < pos.x + BORDERS)
            {
                entity.motionX += BBOOST;
            }

            if (entity.posZ > pos.z + BORDERS)
            {
                entity.motionZ -= BBOOST;
            }

            if (entity.posZ < pos.z + (1D - BORDERS))
            {
                entity.motionZ += BBOOST;
            }

            if (halted)
            {
                entity.motionZ = MathHelper.clamp_float((float) entity.motionZ, (float) - (BORDER_BOOST * 1.5D), (float)(BORDER_BOOST * 1.5D));
                entity.motionX = MathHelper.clamp_float((float) entity.motionX, (float) - (BORDER_BOOST * 1.5D), (float)(BORDER_BOOST * 1.5D));
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k){
        boolean down = world.getBlockMetadata(i, j, k) == 1;
        boolean bottom = world.getBlock(i, j - 1, k) != BlocksTransport.elevator;

        if (down && bottom){
        	return AxisAlignedBB.getBoundingBox(i, 0.0F + j, k, (i + 1), (j + BeltHelper.HEIGHT_COLLISION + 0.0F), (k + 1));
        }

        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int colorMultiplier(IBlockAccess world, int i, int j, int k)
    {
        return getRenderColor(world.getBlockMetadata(i, j, k));
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int j = 0; j < metadata.length; ++j) {
            
            list.add(new ItemStack(item, 1, j));
        }
    }

}
