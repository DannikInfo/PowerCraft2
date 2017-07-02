package ru.dannikinfo.powercraft.logic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.core.ItemsCore;
import ru.dannikinfo.powercraft.core.Main;

public class SettingsBlock extends Block{

	public SettingsBlock(){
		super(Material.iron);
		setCreativeTab(Main.tabPowerCraft);
		setHardness(0.25F);
		setBlockTextureName("cobblestone");
		setBlockName(Main.MODID + ".SettingsBlock");
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
	    if(world.isRemote){
	    	 ItemStack currentItem = player.getCurrentEquippedItem();    	
	    	 if(currentItem != null && currentItem.getItem() == ItemsCore.ActivationCrystal){
	    		 Block mechX = world.getBlock(x + 1, y, z);
	    		 Block mechY = world.getBlock(x, y + 1, z);
	    		 Block mechZ = world.getBlock(x, y, z + 1);
	    		 Block mechXD = world.getBlock(x - 1, y, z);
	    		 Block mechYD = world.getBlock(x, y - 1, z);
	    		 Block mechZD = world.getBlock(x, y, z - 1);
	    		 //pulsar on
	    		 if(mechX == BlocksLogic.pulsar){player.openGui(Main.instance, 9, world, x + 1, y, z);}
	    		 if(mechY == BlocksLogic.pulsar){player.openGui(Main.instance, 9, world, x, y + 1, z);}
	    		 if(mechZ == BlocksLogic.pulsar){player.openGui(Main.instance, 9, world, x, y, z + 1);}
	    		 if(mechXD == BlocksLogic.pulsar){player.openGui(Main.instance, 9, world, x - 1, y, z);}
	    		 if(mechYD == BlocksLogic.pulsar){player.openGui(Main.instance, 9, world, x, y - 1, z);}
	    		 if(mechZD == BlocksLogic.pulsar){player.openGui(Main.instance, 9, world, x, y, z - 1);}
	    		 //pulsar off
	    		 if(mechX == BlocksLogic.pulsaroff){player.openGui(Main.instance, 9, world, x + 1, y, z);}
	    		 if(mechY == BlocksLogic.pulsaroff){player.openGui(Main.instance, 9, world, x, y + 1, z);}
	    		 if(mechZ == BlocksLogic.pulsaroff){player.openGui(Main.instance, 9, world, x, y, z + 1);}
	    		 if(mechXD == BlocksLogic.pulsaroff){player.openGui(Main.instance, 9, world, x - 1, y, z);}
	    		 if(mechYD == BlocksLogic.pulsaroff){player.openGui(Main.instance, 9, world, x, y - 1, z);}
	    		 if(mechZD == BlocksLogic.pulsaroff){player.openGui(Main.instance, 9, world, x, y, z - 1);}
	    		 //Detector on
	    		 if(mechX == BlocksLogic.detector){player.openGui(Main.instance, 8, world, x + 1, y, z);}
	    		 if(mechY == BlocksLogic.detector){player.openGui(Main.instance, 8, world, x, y + 1, z);}
	    		 if(mechZ == BlocksLogic.detector){player.openGui(Main.instance, 8, world, x, y, z + 1);}
	    		 if(mechXD == BlocksLogic.detector){player.openGui(Main.instance, 8, world, x - 1, y, z);}
	    		 if(mechYD == BlocksLogic.detector){player.openGui(Main.instance, 8, world, x, y - 1, z);}
	    		 if(mechZD == BlocksLogic.detector){player.openGui(Main.instance, 8, world, x, y, z - 1);}
	    		 //Detector off
	    		 if(mechX == BlocksLogic.detectoroff){player.openGui(Main.instance, 8, world, x + 1, y, z);}
	    		 if(mechY == BlocksLogic.detectoroff){player.openGui(Main.instance, 8, world, x, y + 1, z);}
	    		 if(mechZ == BlocksLogic.detectoroff){player.openGui(Main.instance, 8, world, x, y, z + 1);}
	    		 if(mechXD == BlocksLogic.detectoroff){player.openGui(Main.instance, 8, world, x - 1, y, z);}
	    		 if(mechYD == BlocksLogic.detectoroff){player.openGui(Main.instance, 8, world, x, y - 1, z);}
	    		 if(mechZD == BlocksLogic.detectoroff){player.openGui(Main.instance, 8, world, x, y, z - 1);}
	    	 }
	    }
		return true;
	}
}