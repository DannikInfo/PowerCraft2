package ru.dannikinfo.powercraft.teleporter;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.core.Main;

public class RendererTeleporter extends TileEntitySpecialRenderer {

	public static final TeleporterModel model = new TeleporterModel();
	public static final ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/blocks/Teleporter.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		render((TileEntityTeleporter)tile, x, y, z, f);
	}

	private void render(TileEntityTeleporter tile, double x, double y, double z, float f) {
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_BLEND); 
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef(0.5F, 1.5F, 0.5F);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        if (tile != null) {
            switch(tile.getBlockMetadata()) {
            case 0:{GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);break;}
            case 1:{GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);break;}
            case 2:{GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);break;}
            case 3:{GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);break;}
            }
        }
		bindTexture(texture);
		model.render();
		GL11.glPopMatrix();
	}

}
