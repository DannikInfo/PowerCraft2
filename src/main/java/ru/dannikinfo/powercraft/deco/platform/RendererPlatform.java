package ru.dannikinfo.powercraft.deco.platform;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;

public class RendererPlatform extends TileEntitySpecialRenderer{
	
	public static PlatformModel model = new PlatformModel();

	@Override
	public void renderTileEntityAt(TileEntity  tile, double x, double y, double z, float f0) {
		render((TileEntityPlatform) tile, x, y, z, f0);
	}
	
	private void render(TileEntityPlatform tile, double x, double y, double z, float f0){
		float f = 1.0F;
		
		bindTexture(new ResourceLocation(Main.MODID, "textures/blocks_deco.png"));
		VecI pos = new VecI(tile.xCoord, tile.yCoord, tile.zCoord);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		boolean[] fences = Platform.getFencesShownLedge(tile.getWorldObj(), pos);
		model.setLedgeFences(fences[2], fences[3], fences[1], fences[0], fences[4]);
		
		model.render();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
}
