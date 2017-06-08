package ru.dannik.powercraft.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.dannik.powercraft.blocks.logic.pulsar.TileEntityPulsar;
import ru.dannik.powercraft.gui.container.ContainerBigCompressor;
import ru.dannik.powercraft.gui.container.ContainerCompressor;
import ru.dannik.powercraft.gui.container.ContainerDetector;
import ru.dannik.powercraft.gui.container.ContainerHighStackCompressor;
import ru.dannik.powercraft.gui.container.ContainerPulsar;
import ru.dannik.powercraft.gui.container.ContainerSpawner;
import ru.dannik.powercraft.gui.container.ContainerTeleporter;
import ru.dannik.powercraft.gui.gui.GuiBigCompressor;
import ru.dannik.powercraft.gui.gui.GuiCompressor;
import ru.dannik.powercraft.gui.gui.GuiDetector;
import ru.dannik.powercraft.gui.gui.GuiHighStackCompressor;
import ru.dannik.powercraft.gui.gui.GuiPulsar;
import ru.dannik.powercraft.gui.gui.GuiSpawner;
import ru.dannik.powercraft.gui.gui.GuiTeleporter;
import ru.dannik.powercraft.gui.gui.GuiTeleporterSettings;
import ru.dannik.powercraft.gui.gui.GuiTeleporterShift;
import ru.dannik.powercraft.storage.InventoryBigCompressor;
import ru.dannik.powercraft.storage.InventoryCompressor;
import ru.dannik.powercraft.storage.InventoryHighStackCompressor;
import ru.dannik.powercraft.utils.BaseUtils;

public class GuiHandler implements IGuiHandler{
	
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
    	 ItemStack current = player.getCurrentEquippedItem();
    	switch(id){ 
        	case 1:
                return new ContainerCompressor(player, new InventoryCompressor(current));
        	case 2:
                return new ContainerBigCompressor(player, new InventoryBigCompressor(current));
        	case 3:
                return new ContainerHighStackCompressor(player, new InventoryHighStackCompressor(current));
        	case 4:
                return new ContainerTeleporter();
        	case 5:
        		return new ContainerSpawner();
        	case 6:
        		return new ContainerTeleporter();
        	case 7:
        		return new ContainerTeleporter();
        	case 8:
        		return new ContainerDetector();
        	case 9:
        		return new ContainerPulsar();
        	default:
                return null;
        }
    }
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
    	ItemStack current = player.getCurrentEquippedItem();
    	String udid = BaseUtils.udid(x, y, z);
    	switch(id){     
        	case 1:      
            	return new GuiCompressor(player, new InventoryCompressor(current));
            case 2:
            	return new GuiBigCompressor(player, new InventoryBigCompressor(current));
            case 3:
            	return new GuiHighStackCompressor(player, new InventoryHighStackCompressor(current));
            case 4:
            	return new GuiTeleporter(player, udid);
            case 5:
            	return new GuiSpawner(player, world, x, y, z);
            case 6:
            	return new GuiTeleporterSettings(player, udid);
            case 7:
            	return new GuiTeleporterShift(player, udid);
            case 8:
            	return new GuiDetector(player, world, x, y, z);
            case 9:
            	return new GuiPulsar(world, x, y, z);
            default:
                return null;
        }
    }
}