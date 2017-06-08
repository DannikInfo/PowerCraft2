package ru.dannik.powercraft.gui.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import ru.dannik.powercraft.Main;
import ru.dannik.powercraft.gui.container.ContainerPulsar;
import ru.dannik.powercraft.network.PacketManager;
import ru.dannik.powercraft.network.server.DelayPulsarMessage;

public class GuiPulsar extends GuiContainer{
	
    final private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/gui/Pulsar.png");
    
    private GuiTextField text;
    private int delay,hold;
    NBTTagCompound nbt = new NBTTagCompound();
    private int x,y,z;
    
    public GuiPulsar(World world, int x, int y, int z){
        super(new ContainerPulsar());
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
        buttonList.add(new GuiButton(5, (this.width - xSize) / 2 + 20, this.height / 2 - 30, 110, 20, StatCollector.translateToLocal("powercraftreloaded.pulsar.gui.delay") + Integer.toString(delay) + StatCollector.translateToLocal("powercraftreloaded.pulsar.gui.seconds")));
        buttonList.add(new GuiButton(6, (this.width - xSize) / 2 + 5, this.height / 2 - 5, 10, 20, "<"));
        buttonList.add(new GuiButton(7, (this.width - xSize) / 2 + 135, this.height / 2 - 5, 10, 20, ">"));
        buttonList.add(new GuiButton(8, (this.width - xSize) / 2 + 20, this.height / 2 - 5, 110, 20, StatCollector.translateToLocal("powercraftreloaded.pulsar.gui.hold") + Integer.toString(hold) + StatCollector.translateToLocal("powercraftreloaded.pulsar.gui.seconds")));
    }
    
    protected void actionPerformed(GuiButton button){
		 switch (button.id){
		 	case 1:{
		 		this.mc.displayGuiScreen((GuiScreen)null);
		 		this.mc.setIngameFocus();
		 		break;
		 	}
		 	case 2:{
		 		PacketManager.sendToServer(new DelayPulsarMessage(delay, hold, x, y, z));
		 		this.mc.displayGuiScreen((GuiScreen)null);
		 		this.mc.setIngameFocus();
		 		break;
		 	}
		 	case 3:{if(delay > 0)delay--;this.initGui();break;}
		 	case 4:{delay++;this.initGui();break;}
		 	case 6:{if(hold > 0)hold--;this.initGui();break;}
		 	case 7:{hold++;this.initGui();break;}
    	 	default:{break;}
		}
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
        int zX = (width - xSize) / 2; //Находим "координаты верхней левой точки прямоугольника с размерами нашей текстуры, который находится в центре экрана"
        int zY = (height - ySize) / 2;// как я это называю
        mc.getTextureManager().bindTexture(texture);//Ну это понятно
        drawTexturedModalRect(zX, zY, 0, 0, xSize, ySize);//Отрисовываем на "тех самых координатах" картинку, начиная с 0, 0 координаты тестуры(u, v) и размерами, которые мы указали
        fontRendererObj.drawString(StatCollector.translateToLocal("powercraftreloaded.pulsar.gui"), (this.width - xSize) / 2 + 5, this.height / 2 - 40, 4210752);
    }

}
