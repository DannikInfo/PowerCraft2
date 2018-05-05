package ru.dannikinfo.powercraft.transport.belt;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.transport.tile.TileEntityRedirectionBelt;

public class BeltRedirector extends BeltBase implements ITileEntityProvider{
	public static Boolean isPowered = false;
	
	public Object blockIcon_top_3;
	public Object blockIcon_top_2;
	public Object blockIcon_top_1;
	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_side;
	
    public BeltRedirector(String name){
        super(name);
    }
    
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon_top_3 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "redirector_3");
		blockIcon_top_2 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "redirector_2");
		blockIcon_top_1 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "redirector_1");
		blockIcon_top = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "redirector");
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
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityRedirectionBelt();
	}


    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        VecI pos = new VecI(i, j, k);

        if (BeltHelper.isEntityIgnored(entity))
        {
            return;
        }

        if (entity instanceof EntityItem)
        {
            BeltHelper.doSpecialItemAction(world, pos, (EntityItem) entity);

            if (BeltHelper.storeNearby(world, pos, (EntityItem) entity, false))
            {
                return;
            }
        }

        TileEntityRedirectionBelt teRedir = (TileEntityRedirectionBelt) world.getTileEntity(i, j, k);
        Direction redir = teRedir.getDirection(entity);
        ForgeDirection fdirection = ForgeDirection.getOrientation(BaseUtils.getMD(world, pos));
        Direction direction = Direction.convertForgeDir(fdirection);
        direction = direction.rotate(redir);
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
    
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
	    if(!world.isRemote){
			if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
				isPowered = true;
		    }else{
		    	isPowered = false;
		    }
	    }
   	}
}
