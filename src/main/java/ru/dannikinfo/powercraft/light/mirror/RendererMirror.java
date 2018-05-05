package ru.dannikinfo.powercraft.light.mirror;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.deco.platform.Platform;

public class RendererMirror extends TileEntitySpecialRenderer {

	public static final ModelMirror modelMirror = new ModelMirror();
	public static final ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/blocks/Laser.png");
	public World world;
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		render((TileEntityMirror)tile, x, y, z, f);
	}

	private void render(TileEntityMirror tile, double x, double y, double z, float f) {
		modelMirror.bottomSticks.showModel = false;
		modelMirror.ceilingSticks.showModel = false;
		modelMirror.stickXplus.showModel = false;
		modelMirror.stickXminus.showModel = false;
		modelMirror.stickZplus.showModel = false;
		modelMirror.stickZminus.showModel = false;
		modelMirror.stickZminus.showModel = false;

		modelMirror.signBoard.showModel = true;
		int i, j, k;
		i = tile.xCoord;
		j = tile.yCoord;
		k = tile.zCoord;
		if (tile.getWorldObj().getBlock(i, j - 1, k).getMaterial().isSolid()) {
			modelMirror.bottomSticks.showModel = true;
		} else if (tile.getWorldObj().getBlock(i, j + 1, k).getMaterial().isSolid()) {
			modelMirror.ceilingSticks.showModel = true;
		} else if (tile.getWorldObj().getBlock(i + 1, j, k).getMaterial().isSolid()) {
			modelMirror.stickXplus.showModel = true;
		} else if (tile.getWorldObj().getBlock(i - 1, j, k).getMaterial().isSolid()) {
			modelMirror.stickXminus.showModel = true;
		} else if (tile.getWorldObj().getBlock(i, j, k + 1).getMaterial().isSolid()) {
			modelMirror.stickZplus.showModel = true;
		} else if (tile.getWorldObj().getBlock(i, j, k - 1).getMaterial().isSolid()) {
			modelMirror.stickZminus.showModel = true;
		}
		
		f = 0.6666667F;

		bindTexture(new ResourceLocation(Main.MODID, "textures/light/mirror.png"));
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f1 = (tile.getBlockMetadata() * 360) / 16F;
		GL11.glRotatef(f1, 0, 1, 0);
		modelMirror.renderMirrorNoSideSticks();
		modelMirror.renderMirrorSideSticks();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

}
