package ru.dannikinfo.powercraft.api.utils.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface INBT<t extends INBT>{
		
	public t readFromNBT(NBTTagCompound nbttag);
		
	public NBTTagCompound writeToNBT(NBTTagCompound nbttag);

}
