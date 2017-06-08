package ru.dannik.powercraft.beam;

import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;


/**
 * Particle for lasers; small particle, moving using given movement vector,
 * stopping in the middle of a block if required...
 * 
 * @author MightyPork
 * @editor DannikInfo
 * @copy (c) 2012 & 2017
 */

public class EntityLaserParticleFX extends EntityReddustFX {

	private int part;

	/**
	 * Laser particle
	 * 
	 * @param par1World the world
	 * @param pos double coordinate position
	 * @param color beam color
	 * @param motion laser movement vector (unit vector)
	 * @param par beam half (0 - both, 1 - first, 2 - second) - used to draw
	 *            half-block beam when the direction changes on this block.
	 */
	
	public EntityLaserParticleFX(World par1World, int xC, int yC, int zC, int colorR, int colorG, int colorB, int xM, int yM, int zM, int par) {
		super(par1World, xC, yC, zC, 0.45F, colorR, colorG, colorB);

		motionX = xM;
		motionZ = yM;
		motionY = zM;
		
		part = par;

		noClip = true;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge) {
			setDead();
		}

		setParticleTextureIndex(7 - (particleAge * 8) / particleMaxAge);

		if (!isDead) {
			moveEntity(motionX, motionY, motionZ);
		}

		if (part == 1) {

			if (motionX > 0 && posX - Math.floor(posX) > 0.45F) {
				setDead();
			}
			if (motionX < 0 && posX - Math.floor(posX) < 0.55F) {
				setDead();
			}

			if (motionY > 0 && posY - Math.floor(posY) > 0.45F) {
				setDead();
			}
			if (motionY < 0 && posY - Math.floor(posY) < 0.55F) {
				setDead();
			}

			if (motionZ > 0 && posZ - Math.floor(posZ) > 0.45F) {
				setDead();
			}
			if (motionZ < 0 && posZ - Math.floor(posZ) < 0.55F) {
				setDead();
			}

		}
	}

	@Override
	public int getBrightnessForRender(float par1) {
		return 0xf000f0;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(float par1) {
		return 0.9F;
	}
}