package ru.dannikinfo.powercraft.teleporter;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.ContainerEmpty;
import ru.dannikinfo.powercraft.core.Main;

public class GuiTeleporter extends GuiContainer{
	
	    final private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/gui/Teleporter.png");//Ваши modid и путь к текстуре gui
		private GuiTextField text;
		NBTTagCompound nbt = new NBTTagCompound();
		int x, y, z;
		World world;
		EntityPlayer player;
	
	    public GuiTeleporter(EntityPlayer player, int x, int y, int z, World world){
	        super(new ContainerEmpty());
	        this.x = x;
	        this.y = y;
	        this.z = z;
	        this.world = world;
	        xSize = 180;
	        ySize = 200;
	        this.player = player;
	    }
	    
	    public void initGui(){
	    	TileEntityTeleporter tile = (TileEntityTeleporter) world.getTileEntity(x, y, z);
	    	if(tile.getName() != null){
		        buttonList.clear();
		        buttonList.add(new GuiButton(1, (this.width - ySize) / 2 + 20, this.height / 2 - 52, 60, 20, "Cancel"));
		        buttonList.add(new GuiButton(2, (this.width - ySize) / 2 + 100, this.height / 2 - 52, 60, 20 , "Settings"));
	    	}else{
	    		player.openGui(Main.MODID, 6, world, x, y, z);
	    	}
	    }
	    
	    public void updateScreen(){
	        super.updateScreen();
	        this.text.updateCursorCounter();
	    }
	    
	    protected void actionPerformed(GuiButton button){
	    	 switch (button.id){
	    	 	case 1:{
	    		 	this.mc.displayGuiScreen((GuiScreen)null);
	    		 	this.mc.setIngameFocus();
	    		 	break;
	    	 	}
	    	 	default:{
	    		 	break;
	    	 	}
	    	 }
	    }
	    
	    protected void keyTyped(char par1, int par2){
	        super.keyTyped(par1, par2);
	        this.text.textboxKeyTyped(par1, par2);
	    }
	
	    @Override
	    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
	        int zX = (width - ySize) / 2;
	        int zY = (height - ySize) / 2;
	        mc.getTextureManager().bindTexture(texture);
	        drawTexturedModalRect(zX, zY, 0, 0, xSize, ySize);
	        this.text.drawTextBox();
	        
	    }
	    
	    protected void mouseClicked(int x, int y, int btn) {
	        super.mouseClicked(x, y, btn);
	        this.text.mouseClicked(x, y, btn);
	    }

}
