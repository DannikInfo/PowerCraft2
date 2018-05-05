package ru.dannikinfo.powercraft.transport.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import ru.dannikinfo.powercraft.core.Main;


public class GuiSplitter extends GuiContainer{

	private static final ResourceLocation field_147017_u = new ResourceLocation(Main.MODID, "textures/blocks/transport/splitterGui.png");
	private int inventoryRows;
	private InventoryPlayer inv_p;
	private ContainerSepBelt inv;
	
	public GuiSplitter(Container cont) {
		super(cont);
		xSize = 176;
		ySize = 194;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float i1, int i2, int i3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(field_147017_u);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		
	}

}

