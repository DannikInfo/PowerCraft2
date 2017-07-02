package ru.dannikinfo.powercraft.deco.stairs;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.deco.platform.TileEntityPlatform;

public class RendererStairs extends TileEntitySpecialRenderer{
	
	public static StairsModel model = new StairsModel();

	@Override
	public void renderTileEntityAt(TileEntity  tile, double x, double y, double z, float f0) {
		render((TileEntityStairs) tile, x, y, z, f0);
	}
	
	private void render(TileEntityStairs tile, double x, double y, double z, float f0){
		float f = 1.0F;

		VecI pos = new VecI(tile.xCoord, tile.yCoord, tile.zCoord);
		Stairs block = (Stairs) tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);

		bindTexture(new ResourceLocation(Main.MODID, "textures/blocks_deco.png"));
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glScalef(f, -f, -f);
        if (tile != null) {
            switch(tile.getBlockMetadata()) {
            case 0:{GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);break;}
            case 1:{GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);break;}
            case 2:{GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);break;}
            case 3:{GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);break;}
            }
        }

		boolean[] fences = block.getFencesShownStairsRelative(tile.getWorldObj(), pos);
		model.setStairsFences(fences[0], fences[1]);
		
		model.render();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
}
