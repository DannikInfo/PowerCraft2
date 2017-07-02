package ru.dannikinfo.powercraft.transport.tile;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Direction;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.transport.belt.BeltHelper;
import ru.dannikinfo.powercraft.transport.belt.BeltRedirector;


public class TileEntityRedirectionBelt extends TileEntity{
    
	protected Random rand = new Random();
	
	public TileEntityRedirectionBelt() {}

    @Override
    public final boolean canUpdate()
    {
        return true;
    }

    private Hashtable<Integer, Direction> redirList = new Hashtable<Integer, Direction>();

    @Override
    public final void updateEntity()
    {
        Enumeration<Integer> enumer = redirList.keys();
        
        while (enumer.hasMoreElements())
        {
        	int id = enumer.nextElement();
            Entity thisItem = worldObj.getEntityByID(id);

            if(thisItem==null){
            	redirList.remove(id);
            }else{
	            if (thisItem.posX < xCoord - 0.2F || thisItem.posY < yCoord - 0.2F || thisItem.posZ < zCoord - 0.2F || thisItem.posX > xCoord + 1.2F
	                    || thisItem.posY > yCoord + 2.2F || thisItem.posZ > zCoord + 1.2F)
	            {
	                redirList.remove(id);
	            }
            }
        }
    }

    public final Direction getDirection(Entity entity){
        if (redirList.containsKey(entity.getEntityId())){
        	return redirList.get(entity.getEntityId());
        }
        else{
            return calculateItemDirection(entity);
        }
        
    }
    
    protected Direction calculateItemDirection(Entity entity){
        BeltRedirector block = (BeltRedirector) worldObj.getBlock(xCoord, yCoord, zCoord);
        VecI pos = new VecI(xCoord, yCoord, zCoord);
        int meta = BaseUtils.getMD(worldObj, pos);
        Direction redir = null;
        if (block.isPowered){
            switch (meta)
            {
                case 3:
                    if (BeltHelper.isTransporterAt(worldObj, pos.offset(1, 0, 0))){
                        redir = Direction.RIGHT;
                    }
                    else if (BeltHelper.isTransporterAt(worldObj, pos.offset(-1, 0, 0))){
                        redir = Direction.LEFT;
                    }

                    break;

                case 5:
                    if (BeltHelper.isTransporterAt(worldObj, pos.offset(0, 0, 1))){
                        redir = Direction.LEFT;
                    }
                    else if (BeltHelper.isTransporterAt(worldObj, pos.offset(0, 0, -1))){
                        redir = Direction.RIGHT;
                    }

                    break;

                case 2:
                    if (BeltHelper.isTransporterAt(worldObj, pos.offset(-1, 0, 0)))
                    {
                        redir = Direction.RIGHT;
                    }
                    else if (BeltHelper.isTransporterAt(worldObj, pos.offset(1, 0, 0)))
                    {
                        redir = Direction.LEFT;
                    }

                    break;

                case 4:
                    if (BeltHelper.isTransporterAt(worldObj, pos.offset(0, 0, -1)))
                    {
                        redir = Direction.LEFT;
                    }
                    else if (BeltHelper.isTransporterAt(worldObj, pos.offset(0, 0, 1)))
                    {
                        redir = Direction.RIGHT;
                    }

                    break;
            }
        }

        if(redir==null){
        	redir = Direction.FRONT;
        }
        System.out.println(meta);
        return redir;
    }
}
