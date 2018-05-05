package ru.dannikinfo.powercraft.deco.stairs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.deco.BlocksDeco;
import ru.dannikinfo.powercraft.transport.BlocksTransport;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;

public class Stairs extends Block implements ITileEntityProvider {

	public Stairs(String name) {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(30.0F);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(Main.tabPowerCraftDeco);
		setBlockName(name);
		setBlockTextureName("iron_block");
	}

	/**
	 * Get fences that are shown for stairs.
	 * 
	 * @param world world
	 * @param pos block pos
	 * @return bool{X+, X-, Z+, Z-}
	 */
	private boolean[] getFencesShownStairsAbsolute(World world, VecI pos) {
		boolean fences[] = { false, false, false, false };

		Direction dir = Direction.getDirFromMeta(BaseUtils.getMD(world, pos));

		if (dir == Direction.FRONT) {
			fences[0] = fences[1] = true;
		} else if (dir == Direction.RIGHT) {
			fences[2] = fences[3] = true;
		} else if (dir == Direction.BACK) {
			fences[0] = fences[1] = true;
		} else if (dir == Direction.LEFT) {
			fences[2] = fences[3] = true;
		}

		fences[0] &= isFallBlock(world, pos.offset(1, 0, 0)) && isFallBlock(world, pos.offset(1, -1, 0));
		fences[1] &= isFallBlock(world, pos.offset(-1, 0, 0)) && isFallBlock(world, pos.offset(-1, -1, 0));
		fences[2] &= isFallBlock(world, pos.offset(0, 0, 1)) && isFallBlock(world, pos.offset(0, -1, 1));
		fences[3] &= isFallBlock(world, pos.offset(0, 0, -1)) && isFallBlock(world, pos.offset(0, -1, -1));
		return fences;
	}

	/**
	 * Get which stair sides should be shown (left,right)
	 * 
	 * @param world
	 * @param pos
	 * @return left, right
	 */
	public boolean[] getFencesShownStairsRelative(World world, VecI pos) {
		boolean fences[] = getFencesShownStairsAbsolute(world, pos);
		boolean rel[] = { false, false };

		Direction dir = Direction.getDirFromMeta(BaseUtils.getMD(world, pos));

		if (dir == Direction.FRONT) {
			rel[0] = fences[0];
			rel[1] = fences[1];
		} else if (dir == Direction.RIGHT) {
			rel[0] = fences[2];
			rel[1] = fences[3];
		} else if (dir == Direction.BACK) {
			rel[0] = fences[1];
			rel[1] = fences[0];
		} else if (dir == Direction.LEFT) {
			rel[0] = fences[3];
			rel[1] = fences[2];
		}

		return rel;
	}

	private static boolean isFallBlock(World world, VecI pos) {
		Block block = world.getBlock(pos.x, pos.y, pos.z);
		if (block == Blocks.air || block == null) {
			return true;
		}

		if (block == Blocks.ladder || block == Blocks.vine) {
			return false;
		}

		if (block.getCollisionBoundingBoxFromPool(world, pos.x, pos.y, pos.z) == null) {
			return true;
		}
		if (block.getMaterial().isLiquid() || !block.getMaterial().isSolid()) {
			return true;
		}
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	public void addACollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist, Entity entity) {
		AxisAlignedBB axisalignedbb1 = super.getCollisionBoundingBoxFromPool(world, x, y, z);

        if (axisalignedbb1 != null && axisalignedbb.intersectsWith(axisalignedbb1))
        {
        	arraylist.add(axisalignedbb1);
        }
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist, Entity entity) {

		Direction dir = Direction.getDirFromMeta(BaseUtils.getMD(world, x, y, z));

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);

		if (dir == Direction.FRONT) {
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);

		} else if (dir == Direction.RIGHT) {
			setBlockBounds(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);

		} else if (dir == Direction.BACK) {
			setBlockBounds(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);

		} else if (dir == Direction.LEFT) {
			setBlockBounds(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);
		}

		// X+, X-, Z+, Z-
		boolean[] fences = getFencesShownStairsAbsolute(world, new VecI(x, y, z));

		if (fences[0]) {
			setBlockBounds(1 - 0.0625F, 0, 0, 1, 1.8F, 1);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);
		}
		if (fences[1]) {
			setBlockBounds(0, 0, 0, 0.0625F, 1.8F, 1);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);
		}
		if (fences[2]) {
			setBlockBounds(0, 0, 1 - 0.0625F, 1, 1.8F, 1);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);
		}
		if (fences[3]) {
			setBlockBounds(0, 0, 0, 1, 1.8F, 0.0625F);
			addACollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, entity);
		}

		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		setBlockBounds(0, 0, 0, 1, 1.5f, 1);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityStairs();
	}
	
	public int getRenderType() {
		return -1;
	}

    @Override
    public int getRenderBlockPass(){
        return 1;
    }
    
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is){
		int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9){
    	 ItemStack stack = entityplayer.getCurrentEquippedItem();
         
         if(stack != null){
 	        if(stack.getItem() == Item.getItemFromBlock(BlocksDeco.stairs)){
 	        	Item item = stack.getItem();
 	        	Block block = Block.getBlockFromItem(item);
 	        	int dir = BaseUtils.getMD(world, i, j, k);
 	        	int zK = k, xK = i;
 	        	for(int a = 0; a < 31; a++){
 		   			if(dir == 0)zK = k + a;
 		   			if(dir == 1)xK = i - a;
 	    			if(dir == 2)zK = k - a;
 	    			if(dir == 3)xK = i + a;
 	        		if(world.getBlock(xK, j + a, zK) != block && world.getBlock(xK, j + a, zK) == Blocks.air && (j + a) <= 250){
 	        			world.setBlock(xK, j + a, zK, block, dir, 3);
 	        			if(!entityplayer.capabilities.isCreativeMode){
 	        				stack.splitStack(1);
 	        			}
 	        			return true;
 	        		}
 	        	}
 	        }
         }
    	return false;
    }
	
}
