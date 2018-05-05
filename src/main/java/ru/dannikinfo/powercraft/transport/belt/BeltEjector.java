package ru.dannikinfo.powercraft.transport.belt;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.transport.tile.TileEntityEjectionBelt;

public class BeltEjector extends BeltBase implements ITileEntityProvider{
	
	public boolean isPowered = false;
	
	public Object blockIcon_top_3;
	public Object blockIcon_top_2;
	public Object blockIcon_top_1;
	public Object blockIcon_top;
	public Object blockIcon_bottom;
	public Object blockIcon_side;
	
    public BeltEjector(String name){
        super(name);
    }
        
    @Override
    public void registerBlockIcons(IIconRegister reg) {
    	blockIcon_top_3 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "ejector_3");
    	blockIcon_top_2 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "ejector_2");
    	blockIcon_top_1 = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "ejector_1");
    	blockIcon_top = reg.registerIcon(Main.MODID + ":" + "transport/" + "Belt_" + "ejector");
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
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity){
        VecI pos = new VecI(i, j, k);

        if (BeltHelper.isEntityIgnored(entity)){
            return;
        }

        if (entity instanceof EntityItem){
            BeltHelper.packItems(world, pos);
        }

        ForgeDirection fdirection = ForgeDirection.getOrientation(BaseUtils.getMD(world, pos));
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

    @Override
    public boolean canProvidePower(){
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block l){
    	if(!world.isRemote){
    		if (l != Blocks.air){
    			world.scheduleBlockUpdate(i, j, k, l, tickRate(world));
    		}    
			if(world.isBlockIndirectlyGettingPowered(i, j, k)) {
				isPowered = true;
		    }else{
		    	isPowered = false;
		    }
	    }
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9){
        if (BeltHelper.blockActivated(world, i, j, k, entityplayer)){
            return true;
        }
        else{
        		//TileEntityEjectionBelt te = (TileEntityEjectionBelt) world.getTileEntity(i, j, k);
        		//te.guiOpen = true;
        		entityplayer.openGui(Main.MODID, 11, world, i, j, k);
            return true;
        }
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityEjectionBelt();
	}
}