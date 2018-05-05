package ru.dannikinfo.powercraft.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.gui.container.ContainerBigCompressor;
import ru.dannikinfo.powercraft.gui.container.ContainerCompressor;
import ru.dannikinfo.powercraft.gui.container.ContainerDetector;
import ru.dannikinfo.powercraft.gui.container.ContainerHighStackCompressor;
import ru.dannikinfo.powercraft.gui.container.ContainerPulsar;
import ru.dannikinfo.powercraft.gui.container.ContainerSpawner;
import ru.dannikinfo.powercraft.gui.container.ContainerTeleporter;
import ru.dannikinfo.powercraft.gui.gui.GuiBigCompressor;
import ru.dannikinfo.powercraft.gui.gui.GuiCompressor;
import ru.dannikinfo.powercraft.gui.gui.GuiDetector;
import ru.dannikinfo.powercraft.gui.gui.GuiHighStackCompressor;
import ru.dannikinfo.powercraft.gui.gui.GuiPulsar;
import ru.dannikinfo.powercraft.gui.gui.GuiSpawner;
import ru.dannikinfo.powercraft.storage.InventoryBigCompressor;
import ru.dannikinfo.powercraft.storage.InventoryCompressor;
import ru.dannikinfo.powercraft.storage.InventoryHighStackCompressor;
import ru.dannikinfo.powercraft.teleporter.GuiTeleporter;
import ru.dannikinfo.powercraft.teleporter.GuiTeleporterSettings;
import ru.dannikinfo.powercraft.teleporter.GuiTeleporterShift;
import ru.dannikinfo.powercraft.transport.belt.EjectBeltMessage;
import ru.dannikinfo.powercraft.transport.gui.ContainerSepBelt;
import ru.dannikinfo.powercraft.transport.gui.ContainerSplitter;
import ru.dannikinfo.powercraft.transport.gui.GuiEjectBelt;
import ru.dannikinfo.powercraft.transport.gui.GuiSepBelt;
import ru.dannikinfo.powercraft.transport.gui.GuiSplitter;
import ru.dannikinfo.powercraft.transport.tile.TileEntityEjectionBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySeparationBelt;
import ru.dannikinfo.powercraft.transport.tile.TileEntitySplitter;

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
        	case 10:
        		return new ContainerSepBelt(player.inventory, (TileEntitySeparationBelt) world.getTileEntity(x, y, z));
        	case 12:
        		return new ContainerSplitter(player.inventory, (TileEntitySplitter) world.getTileEntity(x, y, z));
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
            	return new GuiTeleporter(player, x, y, z, world);
            case 5:
            	return new GuiSpawner(player, world, x, y, z);
            case 6:
            	return new GuiTeleporterSettings(player, x, y, z, world);
            case 7:
            	return new GuiTeleporterShift(player, udid);
            case 8:
            	return new GuiDetector(player, world, x, y, z);
            case 9:
            	return new GuiPulsar(world, x, y, z);
            case 10:
            	return new GuiSepBelt(new ContainerSepBelt(player.inventory, (TileEntitySeparationBelt) world.getTileEntity(x, y, z)));
            case 11:
            		PacketManager.sendToServer(new EjectBeltMessage(-1, -1, 0, 0, x, y, z));
            	return new GuiEjectBelt((TileEntityEjectionBelt) world.getTileEntity(x, y, z), x, y, z);
            case 12:
            	return new GuiSplitter(new ContainerSplitter(player.inventory, (TileEntitySplitter) world.getTileEntity(x, y, z)));
            default:
                return null;
        }
    }
}