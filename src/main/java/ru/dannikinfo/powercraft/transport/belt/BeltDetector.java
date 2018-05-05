package ru.dannikinfo.powercraft.transport.belt;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;

public class BeltDetector extends BeltBase
{
	public Object blockIcon_top_3;
	public Object blockIcon_top_2;
	public Object blockIcon_top_1;
	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_side;
	private IIconRegister iconRegister;
	
    public BeltDetector(String name){
        super(name);
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return 16777215;
    }
    
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_top_3 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "detector_3");
		blockIcon_top_2 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "detector_2");
		blockIcon_top_1 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "detector_1");
		blockIcon_top = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "detector");
		blockIcon_bottom = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "down");
		blockIcon_side = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "side");
	}
	
    public IIcon getIcon(int side, int meta) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        ForgeDirection block_dir;
        if (meta != 0)
            block_dir = ForgeDirection.getOrientation(meta);
        else
            block_dir = ForgeDirection.EAST;
        if (dir == ForgeDirection.UP){
        	if(meta == 0)return (IIcon) blockIcon_top;
        	if(meta == 2 || meta == 10)return (IIcon) blockIcon_top_2;
        	if(meta == 5 || meta == 13)return (IIcon) blockIcon_top_1;
        	if(meta == 3 || meta == 11)return (IIcon) blockIcon_top;
        	if(meta == 4 || meta == 12)return (IIcon) blockIcon_top_3;
        }
        if (dir == ForgeDirection.DOWN) return (IIcon) blockIcon_bottom;
        return (IIcon) blockIcon_side;
    }
	
    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int dir){
    	return isActive(world, x, y, z)?15:0;
	}

	@Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if (isActive(world, i, j, k))
        {
            setStateIfEntityInteractsWithDetector(world, i, j, k);
        }
    }

    private boolean isActive(IBlockAccess world, int i, int j, int k)
    {
        int meta = world.getBlockMetadata(i, j, k);
        return BeltHelper.isActive(meta);
    }

    private void setStateIfEntityInteractsWithDetector(World world, int i, int j, int k)
    {
        int meta = world.getBlockMetadata(i, j, k);
        boolean isAlreadyActive = BeltHelper.isActive(meta);
        boolean isPressed = false;
        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, j, k, (i + 1), j + 1D, (k + 1)));
        isPressed = list.size() > 0;
        
        if (isPressed != isAlreadyActive && !world.isRemote)
        {
        	BaseUtils.setMD(world, i, j, k, meta ^ 8);
            BaseUtils.hugeUpdate(world, i, j, k);
            world.playSoundEffect(i + 0.5D, j + 0.125D, k + 0.5D, "random.click", 0.15F, 0.5F);
        }

        if (isPressed)
        {
        	Block block = world.getBlock(i, j, k);
            world.scheduleBlockUpdate(i, j, k, block, tickRate(world));
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity){
        VecI pos = new VecI(i, j, k);

        if (BeltHelper.isEntityIgnored(entity)){
            return;
        }

        if (!isActive(world, i, j, k)){
            setStateIfEntityInteractsWithDetector(world, i, j, k);
        }

        if (entity instanceof EntityItem){
            BeltHelper.packItems(world, pos);
        }
        int meta = BaseUtils.getMD(world, pos);
        if(meta > 8){
        	meta = meta - 8;
        }
        ForgeDirection fdirection = ForgeDirection.getOrientation(meta);
        Direction direction = Direction.convertForgeDir(fdirection);
        VecI pos_leading_to = pos.offset(direction.getOffset());

        boolean leadsToNowhere = BeltHelper.isBlocked(world, pos_leading_to);
        leadsToNowhere = leadsToNowhere && BeltHelper.isBeyondStorageBorder(world, direction, pos, entity, BeltHelper.STORAGE_BORDER_LONG);

        if (!leadsToNowhere){
            BeltHelper.entityPreventDespawning(world, pos, true, entity);
        }

        double speed_max = BeltHelper.MAX_HORIZONTAL_SPEED;
        double boost = BeltHelper.HORIZONTAL_BOOST;
        BeltHelper.moveEntityOnBelt(world, pos, entity, true, !leadsToNowhere, direction, speed_max, boost);
    }
}
