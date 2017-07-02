package ru.dannikinfo.powercraft.core;
 
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
 
public class TabPowerCraft extends CreativeTabs {
 
	public TabPowerCraft(int position, String tabID)
	{
		super(position, tabID);
	}
	 
	public String getTranslatedTabLabel()
	{
		return "Power Craft";
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ItemsCore.ActivationCrystal;
	}
}
