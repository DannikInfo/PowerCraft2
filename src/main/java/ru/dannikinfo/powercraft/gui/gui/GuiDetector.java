package ru.dannikinfo.powercraft.gui.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.gui.container.ContainerDetector;
import ru.dannikinfo.powercraft.logic.pulsar.DistanceDetectorMessage;

public class GuiDetector extends GuiContainer{

	 	final private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/gui/Pulsar.png");
	    public int x,y,z,range;

		
	    public GuiDetector(EntityPlayer player, World world, int x, int y, int z){
	        super(new ContainerDetector());
	        xSize = 150;
	        ySize = 90;
		
	        this.x = x;
			this.y = y;
			this.z = z;
	    }
	    
	    public void initGui(){
	        buttonList.clear();
	        buttonList.add(new GuiButton(1, (this.width - xSize) / 2 + 5, this.height / 2 + 20, 60, 20, "Cancel"));
	        buttonList.add(new GuiButton(2, (this.width - xSize) / 2 + 85, this.height / 2 + 20, 60, 20 , "OK"));
	        buttonList.add(new GuiButton(3, (this.width - xSize) / 2 + 5, this.height / 2 - 30, 10, 20, "<"));
	        buttonList.add(new GuiButton(4, (this.width - xSize) / 2 + 135, this.height / 2 - 30, 10, 20, ">"));
	        buttonList.add(new GuiButton(5, (this.width - xSize) / 2 + 20, this.height / 2 - 30, 110, 20, StatCollector.translateToLocal("powercraftreloaded.detector.gui.range") + " " + Integer.toString(range) + " " + StatCollector.translateToLocal("powercraftreloaded.detector.gui.block")));
	    }
	    
	    protected void actionPerformed(GuiButton button){
			 switch (button.id){
			 	case 1:{
			 		this.mc.displayGuiScreen((GuiScreen)null);
			 		this.mc.setIngameFocus();
			 		break;
			 	}
			 	case 2:{
			 		PacketManager.sendToServer(new DistanceDetectorMessage(range, x, y, z));
			 		this.mc.displayGuiScreen((GuiScreen)null);
			 		this.mc.setIngameFocus();
			 		break;
			 	}
			 	case 3:{if(range > 0)range--;this.initGui();break;}
			 	case 4:{if(range < 32)range++;this.initGui();break;}
	    	 	default:{break;}
			}
		}

	    @Override
	    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
	        int zX = (width - xSize) / 2;
	        int zY = (height - ySize) / 2;
	        mc.getTextureManager().bindTexture(texture);
	        drawTexturedModalRect(zX, zY, 0, 0, xSize, ySize);
	        fontRendererObj.drawString(StatCollector.translateToLocal("powercraftreloaded.detector.gui"), (this.width - xSize) / 2 + 5, this.height / 2 - 40, 4210752);
	    }

}
