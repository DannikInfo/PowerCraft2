package ru.dannikinfo.powercraft.api.beam;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.light.laser.TileEntityLaser;

public class EntityLaserFX extends EntityFX {
		
	private boolean kill=false;
	Minecraft mc;
	final private ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/laser.png");
	public static int death = 0;
	TileEntityLaser tile;
	
	public EntityLaserFX(World world, int xC, int yC, int zC, int xM, int yM, int zM, float strength, float x, float y, float z, int death) {
		super(world, xC + 0.5, yC + 0.5, zC + 0.5, 0.0, 0.0, 0.0);
		//this.tile = (TileEntityLaser) world.getTileEntity(xC, yC, zC);
		motionX = xM;
		motionY = yM;
		motionZ = zM;
		setRBGColorF(x, y, z);
		particleScale = strength * 1F;
		this.death = death;	
		particleMaxAge = 1;
		
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float tickTime, float rotationX, float rotationXZ, float rotationZ, float rotationYZ, float rotationXY) {
		double size = 0.05F * this.particleScale;
		double x1 = posX - interpPosX;
        double y1 = posY - interpPosY;
        double z1 = posZ - interpPosZ;
        double x2 = x1 + motionX;
	    double y2 = y1 + motionY;
	    double z2 = z1 + motionZ;
	    double x3 = -x1;
	    double y3 = -y1;
	    double z3 = -z1;
	        
	    double x4 = motionX;
	    double y4 = motionY;
	    double z4 = motionZ;
	        
	    double xn = y3 * z4 - z3 * y4;
	    double yn = z3 * x4 - x3 * z4;
	    double zn = x3 * y4 - y3 * x4;
	        
	    double length = Math.sqrt(xn*xn+yn*yn+zn*zn);
	        xn /= length;
	        yn /= length;
	       	zn /= length;
	        
	        xn *= size;
	        yn *= size;
	        zn *= size;
	         
	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
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
			/*GL11.glTranslated(-x1, -y1, -z1);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	        double x = tile.xCoord;
	        double y = tile.yCoord;
	        double z = tile.zCoord;
	        
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex3d(x + 0.1, y1 + 0.1, z + 0.1);
	        GL11.glVertex3d(x - 0.1, y1 - 0.1, z - 0.1);
	        GL11.glVertex3d(x2 + 5 + 0.1, y2 - 0.1, z2 - 0.1);
	        GL11.glVertex3d(x2 + 5 - 0.1, y2 + 0.1, z2 + 0.1);
	        GL11.glEnd();*/
		}
		
    @Override
    public int getFXLayer(){
        return 3;
    }
	
		@Override
		public void onUpdate(){
			if(particleAge > 5)
				setDead();
			particleAge++;
		}
		
	}
