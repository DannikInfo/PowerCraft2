package ru.dannik.powercraft.beam;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ru.dannik.powercraft.Main;
import ru.dannik.powercraft.utils.BaseUtils;

public class EntityLaserFX extends EntityFX {
		
	private boolean kill=false;
	Minecraft mc;
	final private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/laser.png");
	public static int death;
	
	public EntityLaserFX(World world, int xC, int yC, int zC, int xM, int yM, int zM, float strength, int colorR, int colorG, int colorB, int death) {
		super(world, xC + 0.5, yC + 0.5, zC + 0.5, xM, yM, zM);
		motionX = xM;
		motionY = yM;
		motionZ = zM;
		setRBGColorF(colorR, colorG, colorB);
		particleScale = strength * 1F;
		this.death = death;
		
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float tickTime, float rotationX, float rotationXZ, float rotationZ, float rotationYZ, float rotationXY) {
        float size = 0.05F * this.particleScale;
        
        float x1 = (float)(posX - interpPosX);
        float y1 = (float)(posY - interpPosY);
        float z1 = (float)(posZ - interpPosZ);
	        
        float x2 = x1 + (float)motionX;
	    float y2 = y1 + (float)motionY;
	    float z2 = z1 + (float)motionZ;
	        
	    float x3 = -x1;
	    float y3 = -y1;
	    float z3 = -z1;
	        
	    float x4 = (float)(motionX);
	    float y4 = (float)(motionY);
	    float z4 = (float)(motionZ);
	        
	    float xn = y3 * z4 - z3 * y4;
	    float yn = z3 * x4 - x3 * z4;
	    float zn = x3 * y4 - y3 * x4;
	        
	    float length = (float)Math.sqrt(xn*xn+yn*yn+zn*zn);
	        xn /= length;
	        yn /= length;
	       	zn /= length;
	        
	        xn *= size;
	        yn *= size;
	        zn *= size;
	        
	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	        GL11.glDepthMask(false);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
	        tessellator.startDrawingQuads();
	        tessellator.setBrightness(128);
	        tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, 0.6F);
	        tessellator.addVertexWithUV((double)(x1 + xn), (double)(y1 + yn), (double)(z1 + zn), 1, 0);
	        tessellator.addVertexWithUV((double)(x1 - xn), (double)(y1 - yn), (double)(z1 - zn), 1, 1);
	        tessellator.addVertexWithUV((double)(x2 - xn), (double)(y2 - yn), (double)(z2 - zn), 0, 1);
	        tessellator.addVertexWithUV((double)(x2 + xn), (double)(y2 + yn), (double)(z2 + zn), 0, 0);
	        tessellator.draw();
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glDepthMask(true);
	        if(death == 1){
	        	setDead();
	        }
		}
		
    @Override
    public int getFXLayer(){
        return 3;
    }
	
		@Override
		public void onUpdate(){
			if(death == 1){
				if(kill)
					setDead();
				kill = true;
			}
		}
		
	}
