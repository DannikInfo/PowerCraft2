package ru.dannikinfo.powercraft.transport.belt;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.MathHelper;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.logic.detector.TileEntityDetector;

public abstract class BeltBase extends Block{
    
	public BeltBase(String name){
        super(Material.iron);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, BeltHelper.HEIGHT, 1.0F);
        setHardness(0.22F);
        setResistance(8.0F);
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(Main.tabPowerCraft);
        setBlockName(name);
    }

    @Override
    public abstract void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity);
    
    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k){
        return AxisAlignedBB.getBoundingBox(i, 0.0F + j, k, (i + 1), (j + BeltHelper.HEIGHT_COLLISION + 0.0F), (k + 1));
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        float f = 0;
        f = 0.0F + BeltHelper.HEIGHT_SELECTED;
        return AxisAlignedBB.getBoundingBox(i, 0.0F + j, k, (i + 1), j + f, (float) k + 1);
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
        int dir = (MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        int[] r = {2, 5, 3, 4};
        world.setBlockMetadataWithNotify(x, y, z, r[dir], 3);
        String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound nbt = data.getData();
		nbt.setInteger("front_" + udid, dir);
		data.markDirty();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k){
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0F + BeltHelper.HEIGHT, 1.0F);
    }

    @Override
    public void setBlockBoundsForItemRender(){
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0F + BeltHelper.HEIGHT, 1.0F);
    }

    @Override
    public int tickRate(World world){
        return 1;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9){
        return BeltHelper.blockActivated(world, i, j, k, entityplayer);
    }
    
}
