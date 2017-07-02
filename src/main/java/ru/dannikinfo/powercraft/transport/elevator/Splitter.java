package ru.dannikinfo.powercraft.transport.elevator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySplitter;

public class Splitter extends BlockContainer implements ITileEntityProvider{

	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_left;
	public Object blockIcon_right;
	public Object blockIcon_front;
	public Object blockIcon_back;
	
	public static int color[] = {0x49C0FF, 0xFF4C7B, 0xFF8849, 0xE8FF42, 0x4CFF7F, 0x5149FF};
	
	public Splitter(String name) {
		super(Material.iron);
		setHardness(0.5F);
        setResistance(8.0F);
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(Main.tabPowerCraft);
        setBlockName(name);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_top = reg.registerIcon(Main.MODID + ":" + "transport/" + "splitter1");
		blockIcon_bottom = reg.registerIcon(Main.MODID + ":" + "transport/" + "splitter0");
		blockIcon_left = reg.registerIcon(Main.MODID + ":" + "transport/" + "splitter4");
		blockIcon_right = reg.registerIcon(Main.MODID + ":" + "transport/" + "splitter5");
		blockIcon_front = reg.registerIcon(Main.MODID + ":" + "transport/" + "splitter2");
		blockIcon_back = reg.registerIcon(Main.MODID + ":" + "transport/" + "splitter3");
	}
	
    public IIcon getIcon(int side, int meta) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        ForgeDirection block_dir;
        if (meta != 0)
            block_dir = ForgeDirection.getOrientation(meta);
        else
            block_dir = ForgeDirection.EAST;
        if (dir == ForgeDirection.UP)return (IIcon) blockIcon_top;
        if (dir == ForgeDirection.DOWN) return (IIcon) blockIcon_bottom;
        if (dir == ForgeDirection.SOUTH)return (IIcon) blockIcon_back;
        if (dir == ForgeDirection.NORTH)return (IIcon) blockIcon_front;
        if (dir == ForgeDirection.EAST)return (IIcon) blockIcon_right;
        if (dir == ForgeDirection.WEST)return (IIcon) blockIcon_left;
        return null;
    }
	
	@Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity){
        VecI pos = new VecI(i, j, k);

        if (BeltHelper.isEntityIgnored(entity)){
            return;
        }

        TileEntitySplitter tes = (TileEntitySplitter) world.getTileEntity(i, j, k);
        Direction redir = tes.getDirection(entity);

        VecI pos_leading_to = pos.offset(redir.getOffset());
        
        if (entity instanceof EntityItem && BeltHelper.storeEntityItemAt(world, pos_leading_to, (EntityItem) entity, redir)){
            return;
        }
        
        boolean leadsToNowhere = BeltHelper.isBlocked(world, pos_leading_to);

        if (!leadsToNowhere){
            BeltHelper.entityPreventDespawning(world, pos, true, entity);
        }

        if(redir.getMCSide()<4){
        	entity.motionY=0;
        	entity.onGround=true;
        }
        
        leadsToNowhere = leadsToNowhere && BeltHelper.isBeyondStorageBorder(world, redir, pos, entity, BeltHelper.STORAGE_BORDER_LONG);
        BeltHelper.moveEntityOnBelt(world, pos, entity, true, !leadsToNowhere, redir, BeltHelper.MAX_HORIZONTAL_SPEED,
                BeltHelper.HORIZONTAL_BOOST);
    }
	
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer p_149681_6_) {
		TileEntitySplitter te = (TileEntitySplitter) world.getTileEntity(x, y, z);
		for(int a = 0; a < te.basic.getSizeInventory(); a++){
			if(te.basic.getStackInSlot(a) != null){
				EntityItem ei = new EntityItem(world, x, y, z, te.basic.getStackInSlot(a));
				world.spawnEntityInWorld(ei);
			}
		}
	}

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9){
        if (BeltHelper.blockActivated(world, i, j, k, entityplayer)){
            return true;
        }
        else{
        	entityplayer.openGui(Main.MODID, 12, world, i, j, k);
            return true;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l){
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySplitter();
	}
	
}
