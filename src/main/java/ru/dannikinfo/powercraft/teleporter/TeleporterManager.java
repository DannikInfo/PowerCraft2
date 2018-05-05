package ru.dannikinfo.powercraft.teleporter;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.Main;

public class TeleporterManager {
	
	Map<Integer, TileEntity> teleporterMap = new HashMap<Integer, TileEntity>();
	
	public void create(World world, int x, int y, int z) {
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		NBTTagCompound tagTP = tag.getCompoundTag("tagTP");
		int tCount = tagTP.getInteger("tpCount");
		tCount++;
		tagTP.setInteger("x" + tCount, x);
		tagTP.setInteger("y" + tCount, y);
		tagTP.setInteger("z" + tCount, z);
		tagTP.setInteger("tpCount", tCount);
		tag.setTag("TagTp", tagTP);
		data.writeToNBT(tag);
		data.markDirty();
		Main.logger.debug("created teleporter");
	}
	
	public void remove(World world, int x, int y, int z) {
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		NBTTagCompound tagTP = tag.getCompoundTag("tagTP");
		int tCount = tagTP.getInteger("tpCount");
		tCount--;
		tagTP.removeTag("x" + tCount);
		tagTP.removeTag("y" + tCount);
		tagTP.removeTag("z" + tCount);
		tagTP.setInteger("tpCount", tCount);
		tag.setTag("TagTp", tagTP);
		data.writeToNBT(tag);
		data.markDirty();
	}
	
	public Map<Integer, TileEntity> getMap(World world){
		if(teleporterMap == null) {
			WorldData data = WorldData.forWorld(world);
			NBTTagCompound tag = data.getData();
			NBTTagCompound tagTP = tag.getCompoundTag("tagTP");
			int tCount = tagTP.getInteger("tpCount");
			//Map<Integer, TileEntity> m = new HashMap<Integer, TileEntity>();
			for(int i = 0; i < tCount; i++) {
				//TileEntity t = world.getTileEntity(tagTP.getInteger("x" + tCount), tagTP.getInteger("y" + tCount), tagTP.getInteger("z" + tCount));
				//teleporterMap.put(i, t);
			}
			System.out.println(tCount);
			data.markDirty();
		}
		return teleporterMap;
	}

}
