package ru.dannikinfo.powercraft.light.mirror;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.light.laser.RendererLaser;

public class RendererItemMirror implements IItemRenderer {

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
		RendererMirror.modelMirror.bottomSticks.showModel = true;
		RendererMirror.modelMirror.ceilingSticks.showModel = false;
		RendererMirror.modelMirror.stickXplus.showModel = true;
		RendererMirror.modelMirror.stickXminus.showModel = true;
		RendererMirror.modelMirror.stickZplus.showModel = false;
		RendererMirror.modelMirror.stickZminus.showModel = false;
		RendererMirror.modelMirror.stickZminus.showModel = false;

		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Main.MODID, "textures/light/mirror.png"));
		RendererMirror.modelMirror.renderMirrorNoSideSticks();
		GL11.glPopMatrix();
	}
}
