package ru.dannikinfo.powercraft.teleporter;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.ContainerEmpty;
import ru.dannikinfo.powercraft.core.Main;

public class GuiTeleporterSettings extends GuiContainer{

    final private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/gui/Teleporter.png");//Ваши modid и путь к текстуре gu

    public GuiTeleporterSettings(EntityPlayer player, int x, int y, int z, World world){
        super(new ContainerEmpty());
        xSize = 180;
        ySize = 200;
    }
    
    public void initGui(){
    	
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
        int zX = (width - ySize) / 2;
        int zY = (height - ySize) / 2;
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(zX, zY, 0, 0, xSize, ySize);
    }
}
