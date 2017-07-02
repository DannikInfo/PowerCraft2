package ru.dannikinfo.powercraft.transport.belt;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;


public class BeltBreak extends BeltBase
{
	public Object blockIcon_top_3;
	public Object blockIcon_top_2;
	public Object blockIcon_top_1;
	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_side;
	
    public BeltBreak(String name){
        super(name);
    }
    
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_top_3 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "break_3");
		blockIcon_top_2 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "break_2");
		blockIcon_top_1 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "break_1");
		blockIcon_top = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "break");
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
        	if(meta == 2)return (IIcon) blockIcon_top_2;
        	if(meta == 5)return (IIcon) blockIcon_top_1;
        	if(meta == 3)return (IIcon) blockIcon_top;
        	if(meta == 4)return (IIcon) blockIcon_top_3;
        }
        if (dir == ForgeDirection.DOWN) return (IIcon) blockIcon_bottom;
        return (IIcon) blockIcon_side;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        VecI pos = new VecI(i, j, k);

        if(BeltHelper.isEntityIgnored(entity)){
            return;
        }

        if (entity instanceof EntityItem)
        {
            BeltHelper.packItems(world, pos);
        }

        if (entity instanceof EntityItem)
        {
            BeltHelper.doSpecialItemAction(world, pos, (EntityItem) entity);

            if (BeltHelper.storeNearby(world, pos, (EntityItem) entity, isPowered(world, pos)))
            {
                return;
            }
        }

        boolean halted = isPowered(world, pos);

        if (halted)
        {
            if (entity instanceof EntityMinecart && halted)
            {
                entity.motionX *= 0.2D;
                entity.motionZ *= 0.2D;
            }
            else
            {
                entity.motionX *= 0.6D;
                entity.motionZ *= 0.6D;
            }
        }

        ForgeDirection fdirection = ForgeDirection.getOrientation(BaseUtils.getMD(world, pos));
        Direction direction = Direction.convertForgeDir(fdirection);

        VecI pos_leading_to = pos.offset(direction.getOffset());

        boolean leadsToNowhere = BeltHelper.isBlocked(world, pos_leading_to);
        leadsToNowhere = leadsToNowhere && BeltHelper.isBeyondStorageBorder(world, direction, pos, entity, BeltHelper.STORAGE_BORDER_LONG);

        if (!leadsToNowhere)
        {
            BeltHelper.entityPreventDespawning(world, pos, !halted, entity);
        }

        double speed_max = BeltHelper.MAX_HORIZONTAL_SPEED * 0.6D;
        double boost = BeltHelper.HORIZONTAL_BOOST * 0.6D;
        BeltHelper.moveEntityOnBelt(world, pos, entity, true, !halted && !leadsToNowhere, direction, speed_max, boost);
    }

    private boolean isPowered(World world, VecI pos)
    {
    	return false;
    }

}
