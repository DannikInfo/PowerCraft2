package ru.dannik.powercraft.blocks.logic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.dannik.powercraft.Main;
import ru.dannik.powercraft.blocks.BlockList;
import ru.dannik.powercraft.items.ItemList;

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
	    	 if(currentItem != null && currentItem.getItem() == ItemList.ActivationCrystal){
	    		 Block mechX = world.getBlock(x + 1, y, z);
	    		 Block mechY = world.getBlock(x, y + 1, z);
	    		 Block mechZ = world.getBlock(x, y, z + 1);
	    		 Block mechXD = world.getBlock(x - 1, y, z);
	    		 Block mechYD = world.getBlock(x, y - 1, z);
	    		 Block mechZD = world.getBlock(x, y, z - 1);
	    		 //pulsar on
	    		 if(mechX == BlockList.pulsar){player.openGui(Main.instance, 9, world, x + 1, y, z);}
	    		 if(mechY == BlockList.pulsar){player.openGui(Main.instance, 9, world, x, y + 1, z);}
	    		 if(mechZ == BlockList.pulsar){player.openGui(Main.instance, 9, world, x, y, z + 1);}
	    		 if(mechXD == BlockList.pulsar){player.openGui(Main.instance, 9, world, x - 1, y, z);}
	    		 if(mechYD == BlockList.pulsar){player.openGui(Main.instance, 9, world, x, y - 1, z);}
	    		 if(mechZD == BlockList.pulsar){player.openGui(Main.instance, 9, world, x, y, z - 1);}
	    		 //pulsar off
	    		 if(mechX == BlockList.pulsaroff){player.openGui(Main.instance, 9, world, x + 1, y, z);}
	    		 if(mechY == BlockList.pulsaroff){player.openGui(Main.instance, 9, world, x, y + 1, z);}
	    		 if(mechZ == BlockList.pulsaroff){player.openGui(Main.instance, 9, world, x, y, z + 1);}
	    		 if(mechXD == BlockList.pulsaroff){player.openGui(Main.instance, 9, world, x - 1, y, z);}
	    		 if(mechYD == BlockList.pulsaroff){player.openGui(Main.instance, 9, world, x, y - 1, z);}
	    		 if(mechZD == BlockList.pulsaroff){player.openGui(Main.instance, 9, world, x, y, z - 1);}
	    		 //Detector on
	    		 if(mechX == BlockList.detector){player.openGui(Main.instance, 8, world, x + 1, y, z);}
	    		 if(mechY == BlockList.detector){player.openGui(Main.instance, 8, world, x, y + 1, z);}
	    		 if(mechZ == BlockList.detector){player.openGui(Main.instance, 8, world, x, y, z + 1);}
	    		 if(mechXD == BlockList.detector){player.openGui(Main.instance, 8, world, x - 1, y, z);}
	    		 if(mechYD == BlockList.detector){player.openGui(Main.instance, 8, world, x, y - 1, z);}
	    		 if(mechZD == BlockList.detector){player.openGui(Main.instance, 8, world, x, y, z - 1);}
	    		 //Detector off
	    		 if(mechX == BlockList.detectoroff){player.openGui(Main.instance, 8, world, x + 1, y, z);}
	    		 if(mechY == BlockList.detectoroff){player.openGui(Main.instance, 8, world, x, y + 1, z);}
	    		 if(mechZ == BlockList.detectoroff){player.openGui(Main.instance, 8, world, x, y, z + 1);}
	    		 if(mechXD == BlockList.detectoroff){player.openGui(Main.instance, 8, world, x - 1, y, z);}
	    		 if(mechYD == BlockList.detectoroff){player.openGui(Main.instance, 8, world, x, y - 1, z);}
	    		 if(mechZD == BlockList.detectoroff){player.openGui(Main.instance, 8, world, x, y, z - 1);}
	    	 }
	    }
		return true;
	}
}