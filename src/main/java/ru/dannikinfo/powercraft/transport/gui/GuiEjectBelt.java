package ru.dannikinfo.powercraft.transport.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.ContainerEmpty;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.transport.belt.EjectBeltMessage;
import ru.dannikinfo.powercraft.transport.tile.TileEntityEjectionBelt;


public class GuiEjectBelt extends GuiContainer{

	public TileEntityEjectionBelt te;
	String atype1 = "OFF";
	String atype2 = "OFF";
	String atype3 = "OFF";
	String smode1 = "OFF";
	String smode2 = "OFF";
	String smode3 = "OFF";
	public static int stacks = 1;
	public static int items = 1;
	public static int x,y,z,a,s;
	
	public GuiEjectBelt(TileEntityEjectionBelt te, int x, int y, int z) {
		super(new ContainerEmpty());
		xSize=150;
		ySize=200;
		this.te = te;
		this.x = x;
		this.y = y;
		this.z = z;
		if(a == 0)  atype1 = "ON";
		if(a == 1)  atype2 = "ON";
		if(a == 2)  atype3 = "ON";
		if(s == 0) smode1 = "ON";
		if(s == 1) smode2 = "ON";
		if(s == 2) smode3 = "ON";
	}

	private static final ResourceLocation field_147017_u = new ResourceLocation(Main.MODID, "textures/gui/guiSpawner.png");

    public void initGui(){
        buttonList.clear();
        buttonList.add(new GuiButton(1, (this.width - xSize) / 2 + 5, this.height / 2 + 75, 60, 20, "Cancel"));
        buttonList.add(new GuiButton(2, (this.width - xSize) / 2 + 85, this.height / 2 + 75, 60, 20 , "OK"));
        buttonList.add(new GuiButton(3, (this.width - xSize) / 2 + 93, this.height / 2 - 80, 10, 20, "<"));
        buttonList.add(new GuiButton(4, (this.width - xSize) / 2 + 135, this.height / 2 - 80, 10, 20, ">"));
        buttonList.add(new GuiButton(5, (this.width - xSize) / 2 + 104, this.height / 2 - 80, 30, 20, Integer.toString(stacks)));
        buttonList.add(new GuiButton(6, (this.width - xSize) / 2 + 93, this.height / 2 - 55, 10, 20, "<"));
        buttonList.add(new GuiButton(7, (this.width - xSize) / 2 + 135, this.height / 2 - 55, 10, 20, ">"));
        buttonList.add(new GuiButton(8, (this.width - xSize) / 2 + 104, this.height / 2 - 55, 30, 20, Integer.toString(items)));
        buttonList.add(new GuiButton(9, (this.width - xSize) / 2 + 5, this.height / 2 - 80, 20, 20, atype1));
        buttonList.add(new GuiButton(10, (this.width - xSize) / 2 + 5, this.height / 2 - 55, 20, 20, atype2));
        buttonList.add(new GuiButton(11, (this.width - xSize) / 2 + 5, this.height / 2 - 30, 20, 20, atype3));
        buttonList.add(new GuiButton(12, (this.width - xSize) / 2 + 5, this.height / 2 + 0, 20, 20, smode1));
        buttonList.add(new GuiButton(13, (this.width - xSize) / 2 + 5, this.height / 2 + 25, 20, 20, smode2));
        buttonList.add(new GuiButton(14, (this.width - xSize) / 2 + 5, this.height / 2 + 50, 20, 20, smode3));
        
    }
    
    protected void actionPerformed(GuiButton button){
		 switch (button.id){
		 	case 1:{
		 		this.mc.displayGuiScreen((GuiScreen)null);
		 		this.mc.setIngameFocus();
		 		break;
		 	}
		 	case 2:{
		 		int a = 0, s = 0;
		 		if(atype1 == "ON") a = 0;
		 		if(atype2 == "ON") a = 1;
		 		if(atype3 == "ON") a = 2;
		 		if(smode1 == "ON") s = 0;
		 		if(smode2 == "ON") s = 1;
		 		if(smode3 == "ON") s = 2;
		 		PacketManager.sendToServer(new EjectBeltMessage(a, s, stacks, items, x, y, z));
		 		this.mc.displayGuiScreen((GuiScreen)null);
		 		this.mc.setIngameFocus();
		 		break;
		 	}
		 	case 3:{if(stacks > 1)stacks--;this.initGui();break;}
		 	case 4:{stacks++;this.initGui();break;}
		 	case 6:{if(items > 1)items--;this.initGui();break;}
		 	case 7:{items++;this.initGui();break;}
		 	case 9:{if(atype1 != "ON"){atype1 = "ON"; atype2 = "OFF"; atype3 = "OFF";}this.initGui();break;}
		 	case 10:{if(atype2 != "ON"){atype2 = "ON"; atype1 = "OFF"; atype3 = "OFF";}this.initGui();break;}
		 	case 11:{if(atype3 != "ON"){atype3 = "ON"; atype2 = "OFF"; atype1 = "OFF";}this.initGui();break;}
		 	case 12:{if(smode1 != "ON"){smode1 = "ON"; smode2 = "OFF"; smode3 = "OFF";}this.initGui();break;}
		 	case 13:{if(smode2 != "ON"){smode2 = "ON"; smode1 = "OFF"; smode3 = "OFF";}this.initGui();break;}
		 	case 14:{if(smode3 != "ON"){smode3 = "ON"; smode2 = "OFF"; smode1 = "OFF";}this.initGui();break;}
   	 	default:{break;}
		}
	}
    
	@Override
	public void drawGuiContainerBackgroundLayer(float i1, int i2, int i3) {	
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(field_147017_u);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		int x = k+xSize;
		int y = l+ySize;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(k, y, 0, 0.0f, 1.0f);
		tessellator.addVertexWithUV(x, y, 0, 1.0f, 1.0f);
		tessellator.addVertexWithUV(x, l, 0, 1.0f, 0.0f);    
		tessellator.addVertexWithUV(k, l, 0, 0.0f, 0.0f);
		tessellator.draw();
		GL11.glColor3f(0.2f, 0.2f, 0.2f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(k + 5,this.height / 2 - 5);
		GL11.glVertex2d(k + xSize - 5,this.height / 2 - 5);
		GL11.glEnd();
		this.fontRendererObj.drawString(StatCollector.translateToLocal("transport.gui.WholeStacks"), (this.width - xSize) / 2 + 30, this.height / 2 - 75, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("transport.gui.SingleItems"), (this.width - xSize) / 2 + 30, this.height / 2 - 50, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("transport.gui.AllContentsAtOne"), (this.width - xSize) / 2 + 30, this.height / 2 - 25, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("transport.gui.FirstSlot"), (this.width - xSize) / 2 + 30, this.height / 2 + 5, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("transport.gui.LastSlot"), (this.width - xSize) / 2 + 30, this.height / 2 + 30, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("transport.gui.RandomSlot"), (this.width - xSize) / 2 + 30, this.height / 2 + 55, 4210752);
	}

}

