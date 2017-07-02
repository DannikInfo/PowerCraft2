package ru.dannikinfo.powercraft.deco.platform;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.deco.ironframe.RendererIronFrame;

public class RendererItemPlatform implements IItemRenderer{

	@Override
	public boolean handleRenderType(ItemStack is, ItemRenderType type) {
		return true;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) {
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack is, Object... data) {
		float f = 1.0F;
		RendererPlatform rp = new RendererPlatform();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPushMatrix();
		//GL11.glTranslatef(0.5F, 1.5F, 0.5F);
		//GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		RendererPlatform.model.setLedgeFences(true, false, true, false, true);
		GL11.glScalef(f, -f, -f);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Main.MODID, "textures/blocks_deco.png"));
		RendererPlatform.model.render();
		GL11.glPopMatrix();
	}
}