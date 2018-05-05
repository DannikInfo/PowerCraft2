package ru.dannikinfo.powercraft.light.mirror;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.BlocksCore;
import ru.dannikinfo.powercraft.core.Main;

public class Mirror extends Block implements ITileEntityProvider{

	public Mirror(String name) {
		super(Material.glass);
		float f = 0.4F;
		float f1 = 1.0F;
		setBlockBounds(0.5F - f, 0.1F, 0.5F - f, 0.5F + f, f1 - 0.1F, 0.5F + f);
		setHardness(1.0F);
		setResistance(4.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(Main.tabPowerCraft);
		setBlockName(name);
		setBlockTextureName("iron_block");
	}
	
	public int getRenderType() {
		return -1;
	}

    @Override
    public int getRenderBlockPass(){
        return 1;
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int i) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase player, ItemStack itemStack)
    {
		int m = MathHelper.floor_double((((player.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf;
		BaseUtils.setMD(world, i, j, k, m);
    }
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9) {
		ItemStack ihold = player.getCurrentEquippedItem();
		//if (ihold != null && ihold.itemID<Block.blocksList.length) {
			if (world.getBlock(i, j, k) == BlocksCore.crystal) {

				TileEntityMirror teo = (TileEntityMirror) world.getTileEntity(i, j, k);
				if (teo != null) {
					teo.setMirrorColor(ihold.getItemDamage());
				}

				return true;
			}

			//if (ihold.getItem() instanceof ItemBlock && ihold.itemID != blockID) {
			//	Block bhold = Block.blocksList[ihold.getItem().itemID];
			//	return false;
			//}
		//}

		int m = MathHelper.floor_double((((player.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf;
		if(!world.isRemote)
			BaseUtils.setMD(world, i, j, k, m);

		return true;
	}

	/**
	 * Get mirror color
	 * 
	 * @param iblockaccess
	 * @param x
	 * @param y
	 * @param z
	 * @return the color index (crystal meta)
	 */
	public static int getMirrorColor(IBlockAccess iblockaccess, int x, int y, int z) {

		TileEntityMirror teo = (TileEntityMirror) iblockaccess.getTileEntity(x, y, z);

		if (teo == null) {
			return 0;
		}
		return teo.getMirrorColor();

	}

	@Override
	public int getRenderColor(int i) {
		return 0x999999;
	}

	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		return 0x999999;
	}

	/** angle rounded to 45 for vertical beam colliding with mirror */
	private static final int mirrorTo45[] = { 0, 0, 45, 90, 90, 90, 135, 180, 180, 180, 225, 270, 270, 270, 315, 0 };
	
	/**
	 * Get horizontal angle from movement vector
	 * 
	 * @param move movement vector
	 * @return angle
	 */
	private static float getAngleFromMove(VecI move) {
		float beamAngle = 0;
		if (move.x == 0 && move.z == -1) {
			beamAngle = 0;
		}
		if (move.x == 1 && move.z == -1) {
			beamAngle = 45;
		}
		if (move.x == 1 && move.z == 0) {
			beamAngle = 90;
		}
		if (move.x == 1 && move.z == 1) {
			beamAngle = 135;
		}
		if (move.x == 0 && move.z == 1) {
			beamAngle = 180;
		}
		if (move.x == -1 && move.z == 1) {
			beamAngle = 225;
		}
		if (move.x == -1 && move.z == 0) {
			beamAngle = 270;
		}
		if (move.x == -1 && move.z == -1) {
			beamAngle = 315;
		}
		return beamAngle;
	}
	
	/** mirror angle for meta */
	private static final float mirrorAngle[] = new float[16];
	static {
		for (int a = 0; a < 8; a++) {
			mirrorAngle[a] = a * 22.5F;
			mirrorAngle[a + 8] = a * 22.5F;
		}
	}
	
	/**
	 * Get movement vector from angle
	 * 
	 * @param angle
	 * @return vector (coord)
	 */
	private static VecI getMoveFromAngle(float angle) {
		int angleint = Math.round(angle);
		switch (angleint) {
			case 0:
				return new VecI(0, 0, -1);
			case 45:
				return new VecI(1, 0, -1);
			case 90:
				return new VecI(1, 0, 0);
			case 135:
				return new VecI(1, 0, 1);
			case 180:
				return new VecI(0, 0, 1);
			case 225:
				return new VecI(-1, 0, 1);
			case 270:
				return new VecI(-1, 0, 0);
			case 315:
				return new VecI(-1, 0, -1);
		}
		return null;
	}

	/**
	 * Get real difference of two angles
	 * 
	 * @param firstAngle
	 * @param secondAngle
	 * @return result
	 */
	private static float angleDiff(float firstAngle, float secondAngle) {

		float difference = secondAngle - firstAngle;

		while (difference < -180) {
			difference += 360;
		}

		while (difference > 180) {
			difference -= 360;
		}

		return difference;

	}

	/**
	 * Convert invalid angle to 0-360
	 * 
	 * @param angle to convert
	 * @return converted
	 */
	private static float fixAngle(float angle) {

		while (angle > 360) {
			angle -= 360;
		}

		while (angle < 0) {
			angle += 360;
		}

		return angle;

	}

	/*@Override
	public BeamHitResult onBlockHitByBeam(World world, int x, int y, int z, BeamSettings settings) {
		PC_VecI move = settings.getMove();
		int mirrorColor = PCli_BlockMirror.getMirrorColor(world, x, y, z);
		PC_Color c = null;
		if(mirrorColor>=0)
			c = PC_Color.fromHex(PC_Color.crystal_colors[mirrorColor]);
		if (c==null || settings.getColor().equals(c)) {
			// vertical beam
			if (move.x == 0 && move.z == 0) {
	
				int a = mirrorTo45[PC_Utils.getMD(world, x, y, z)];
				PC_VecI reflected = getMoveFromAngle(a).mul(-1);
	
				move.x = reflected.x;
				move.z = reflected.z;
	
			} else {
				float beamAngle = getAngleFromMove(move);
				float mAngle = mirrorAngle[PC_Utils.getMD(world, x, y, z)];
	
				float diff = angleDiff(beamAngle, mAngle);
	
				// the reflection
				float beamNew = beamAngle + diff * 2;
	
				beamNew = fixAngle(beamNew);
	
				PC_VecI reflected = getMoveFromAngle(beamNew).mul(-1);
	
				move.x = reflected.x;
				move.z = reflected.z;
			}
		}
		settings.setMove(move);
		return BeamHitResult.CONTINUE;
	}*/

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMirror();
	}
	
}

