package ru.dannikinfo.powercraft.light.laser;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.Color;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.light.BlocksLight;

public class TileEntityLaser extends TileEntity {
	
	int lenght;
	public boolean isPowered;

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
				drawLaser();
			}else{
				lenght = 0;
				//isPowered = true;
			//}else{
				//isPowered = false;
			}
		}
	}
	
	public void handler(List entity){
		int color = Laser.getColor(worldObj, xCoord, yCoord, zCoord);
		if(color == 3){
			if(entity.size() != 0){
				Entity entity1 = (Entity) entity.get(0);			
				entity1.attackEntityFrom(DamageSource.cactus, 5);
			}
		}
	}
	
	public void drawLaser(){
		
		String udid = BaseUtils.udid(xCoord, yCoord, zCoord);
		WorldData data = WorldData.forWorld(worldObj);
		NBTTagCompound nbt = data.getData();
	    int color = nbt.getInteger("color_" + udid);
		data.markDirty();
		lenght = getLaserLenght(color);
		int r = 0, g = 0, bl = 0;
		if(color == 0){r = 255; g = 255; bl = 255;}
		if(color == 1){r = 0; g = 0; bl = 255;}
		if(color == 2){r = 0; g = 255; bl = 0;}
		if(color == 3){r = 255; g = 0; bl = 0;}
	    int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	    PacketManager.sendToServer(new BeamMessage(lenght, meta, xCoord, yCoord, zCoord, r, g, bl, 0));
	}
	
	public int getLaserLenght(int color){
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int zE=zCoord, xE=xCoord, lenght = 16, maxLenght = 16;
		if(color == 1){maxLenght = 32; lenght = 32;}
		for(int i = 1; i < maxLenght; i++){
			if(meta == 0)zE = zCoord + i;
			if(meta == 1)xE = xCoord - i;
			if(meta == 2)zE = zCoord - i;
			if(meta == 3)xE = xCoord + i;
			Block block = worldObj.getBlock(xE, yCoord, zE);
			if(block == BlocksLight.mirror){
				int r = 0, g = 0, bl = 0;
				if(color == 0){r = 255; g = 255; bl = 255;}
				if(color == 1){r = 0; g = 0; bl = 255;}
				if(color == 2){r = 0; g = 255; bl = 0;}
				if(color == 3){r = 255; g = 0; bl = 0;}
				int meta1 = meta;
				meta = worldObj.getBlockMetadata(xE, yCoord, zE);
				PacketManager.sendToServer(new BeamMessage(lenght - i, meta, xE, yCoord, zE, r, g, bl, meta1 + 1));
			}
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
				return i;
			}
			zE = zCoord;
			xE = xCoord;
			double zS = zCoord, xS = xCoord;
			if(zCoord < 0)zS = zCoord + 1;
			if(zCoord > 0)zS = zCoord - 1;
			if(xCoord > 0)xS = xCoord - 1;
			if(xCoord < 0)xS = xCoord + 1;
			double rX = 0, rZ = 0;
			if(meta == 0){rZ = zCoord + i + 1; rX = xCoord + 1;}
			if(meta == 1){rX = xCoord - i - 1; rZ = zCoord + 1;}
			if(meta == 2){rZ = zCoord + i - 1; rX = xCoord + 1;}
			if(meta == 3){rX = xCoord + i + 1; rZ = zCoord + 1;}
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, rX, yCoord + 1, rZ);
			int count = worldObj.getEntitiesWithinAABB(EntityAnimal.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntityCreature.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntitySlime.class, bb).size();
			count += worldObj.getEntitiesWithinAABB(EntityItem.class, bb).size();
			if(count != 0){
				lenght = i;
				if(worldObj.getEntitiesWithinAABB(EntityCreature.class, bb) != null)handler(worldObj.getEntitiesWithinAABB(EntityCreature.class, bb));
				if(worldObj.getEntitiesWithinAABB(EntityAnimal.class, bb) != null)handler(worldObj.getEntitiesWithinAABB(EntityAnimal.class, bb));
				if(worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb) != null)handler(worldObj.getEntitiesWithinAABB(EntityPlayer.class, bb));
				if(worldObj.getEntitiesWithinAABB(EntitySlime.class, bb) != null)handler(worldObj.getEntitiesWithinAABB(EntitySlime.class, bb));
				return lenght;
			}
			
			System.out.println(meta);
		}	
		return lenght;
	}

	public Color getColor() {
		String udid = BaseUtils.udid(xCoord, yCoord, zCoord);
		WorldData data = WorldData.forWorld(worldObj);
		NBTTagCompound nbt = data.getData();
	    int color = nbt.getInteger("color_" + udid);
		data.markDirty();
		Color c = new Color();

		if(color == 0){c.x = 1; c.y = 1; c.z = 1;}
		if(color == 1){c.x = 0; c.y = 0; c.z = 1;}
		if(color == 2){c.x = 0; c.y = 1; c.z = 0;}
		if(color == 3){c.x = 1; c.y = 0; c.z = 0;}
		return c;
	}

}
