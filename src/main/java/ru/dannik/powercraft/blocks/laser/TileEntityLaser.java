package ru.dannik.powercraft.blocks.laser;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import ru.dannik.powercraft.beam.EntityLaserFX;
import ru.dannik.powercraft.network.PacketManager;
import ru.dannik.powercraft.network.server.BeamMessage;
import ru.dannik.powercraft.utils.BaseUtils;
import ru.dannik.powercraft.utils.WorldData;

public class TileEntityLaser extends TileEntity {
	
	int lenght;

	public TileEntityLaser() {

	}
	
    @Override
    public boolean canUpdate()
    {
        return true;
    }
	
	public void updateEntity(){
		if(!worldObj.isRemote){
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				if(getLaserLenght() != lenght || lenght == 0){
				    drawLaser();
				}
				if(EntityLaserFX.death == 1){
					drawLaser();
				}
			}else{
				EntityLaserFX.death = 1;
				lenght = 0;
			}
		}
	}
	
	public void drawLaser(){
		lenght = getLaserLenght();
		String udid = BaseUtils.udid(xCoord, yCoord, zCoord);
		WorldData data = WorldData.forWorld(worldObj);
		NBTTagCompound nbt = data.getData();
	    int color = nbt.getInteger("color_" + udid);
		data.markDirty();
		int r = 0, g = 0, bl = 0;
		if(color == 0){r = 255; g = 255; bl = 255;}
		if(color == 1){r = 0; g = 0; bl = 255;}
		if(color == 2){r = 0; g = 255; bl = 0;}
		if(color == 3){r = 255; g = 0; bl = 0;}
	    int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	    EntityLaserFX.death = 1;
	    PacketManager.sendToServer(new BeamMessage(lenght, meta, xCoord, yCoord, zCoord, r, g, bl, 0));
	}
	
	public int getLaserLenght(){
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int zE=zCoord, xE=xCoord, lenght=31;
		for(int i = 1; i < 32; i++){
			if(meta == 0)zE = zCoord + i;
			if(meta == 1)xE = xCoord - i;
			if(meta == 2)zE = zCoord - i;
			if(meta == 3)xE = xCoord + i;
			Block block = worldObj.getBlock(xE, yCoord, zE);
			if(block != Blocks.air &&
					block != Blocks.glass &&
					block != Blocks.glass_pane &&
					block != Blocks.beacon &&
					block != Blocks.ice &&
					block != Blocks.iron_bars &&
					block != Blocks.sapling &&
					block != Blocks.stained_glass &&
					block != Blocks.stained_glass_pane &&
					block != Blocks.torch &&
					block != Blocks.rail &&
					block != Blocks.activator_rail &&
					block != Blocks.leaves &&
					block != Blocks.leaves2 &&
					block != Blocks.flower_pot &&
					block != Blocks.red_flower &&
					block != Blocks.yellow_flower &&
					block != Blocks.web &&
					block != Blocks.ladder &&
					block != Blocks.vine &&
					block != Blocks.carpet &&
					block != Blocks.detector_rail &&
					block != Blocks.golden_rail &&
					block != Blocks.waterlily &&
					block != Blocks.snow_layer 
					){
				lenght = i;
			}
			zE = zCoord;
			xE = xCoord;
			int zS = zCoord, xS = xCoord;
			if(zCoord < 0)zS = zCoord + 1;
			if(zCoord > 0)zS = zCoord - 1;
			if(xCoord > 0)xS = xCoord - 1;
			if(xCoord < 0)xS = xCoord + 1;
			int rX=xCoord, rZ=zCoord;
			if(meta == 0){rZ = zCoord + i + 1; zE = zCoord + 1;}
			if(meta == 1){rX = xCoord - i - 1; xE = xCoord + 1;}
			if(meta == 2){rZ = zCoord - i - 1; zE = zCoord + 1;}
			if(meta == 3){rX = xCoord + i + 1; xE = xCoord + 1;}
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xS, yCoord, zS, rX, yCoord, rZ).expand(0, 1, 0);
			int count = worldObj.getEntitiesWithinAABB(EntityAnimal.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntityCreature.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntitySlime.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntityCreature.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb).size();
			if(count != 0){
				lenght = i;
				System.out.println(bb);
				return lenght;
			}
			
		}	
		return lenght;
	}

}
