package ru.dannikinfo.powercraft.api.beam;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.PBlock;
import ru.dannikinfo.powercraft.api.utils.Color;
import ru.dannikinfo.powercraft.api.utils.VecI;

/**
 * Laser beam tracing class
 * 
 * @author MightyPork
 * @copy (c) 2012
 */
public class BeamTracer {
	
	private Random rand = new Random();
	
	private VecI startCoord, startMove;
	
	/**
	 * The beam color.<br>
	 * This object also contains information about beam visibility (particles
	 * enabled) and power crystal metadata.
	 */
	private Color origColor;
	
	private IBeamHandler handler;
	
	/** The current world */
	protected World world;
	
	private boolean canChangeColor = false;
	private boolean canHitEntity = false;
	private boolean handleBlocks = true;
	
	private int maxTotalLength = 8000;
	private int start_limit = 40;
	private int maximum_current_limit = 80;
	private float strength = 0.2f;
	
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	/**
	 * Laser beam raytracer
	 * 
	 * @param worldObj
	 *            the world
	 * @param handler
	 *            laser handler (interface)
	 */
	public BeamTracer(World worldObj, IBeamHandler handler) {
		this.handler = handler;
		this.world = worldObj;
	}
	
	public World getWorld() {
		return world;
	}
	
	/**
	 * Set if the beam can change color on PowerCrystals
	 * 
	 * @param state
	 *            boolean value
	 * @return self
	 */
	public BeamTracer setCanChangeColor(boolean state) {
		canChangeColor = state;
		return this;
	}
	
	/**
	 * Set starting strength
	 * 
	 * @param strength
	 * @return self
	 */
	public BeamTracer setStartStrength(float strength) {
		this.strength = strength;
		return this;
	}
	
	public BeamTracer setBlockHandels(boolean b) {
		handleBlocks = b;
		return this;
	}
	
	/**
	 * Set starting range
	 * 
	 * @param length
	 *            range in blocks
	 * @return self
	 */
	public BeamTracer setStartLength(int length) {
		start_limit = length;
		return this;
	}
	
	/**
	 * Set maximum current limit. When crystal adds some length, it is checked
	 * against this limit and shortened if needed.<br>
	 * If set to small number, crystals won't add more than this number.
	 * 
	 * @param length
	 *            range in blocks
	 * @return self
	 */
	public BeamTracer setMaxLengthAfterCrystal(int length) {
		maximum_current_limit = length;
		return this;
	}
	
	/**
	 * Set how many blocks a crystal adds.
	 * 
	 * @param length
	 *            range in blocks
	 * @return self
	 */
	public BeamTracer setData(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	/**
	 * Set the highest possible length (all forked beams together)
	 * 
	 * @param length
	 *            range in blocks
	 * @return self
	 */
	public BeamTracer setTotalLengthLimit(int length) {
		maxTotalLength = length;
		return this;
	}
	
	/**
	 * Set starting coordinates of the beam (the device)
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return self
	 */
	public BeamTracer setStartCoord(int x, int y, int z) {
		startCoord = new VecI(x, y, z);
		return this;
	}
	
	/**
	 * Set starting coordinates of the beam (the device)
	 * 
	 * @param coord
	 * @return self
	 */
	public BeamTracer setStartCoord(VecI coord) {
		startCoord = coord.copy();
		return this;
	}
	
	public VecI getStartCoord() {
		return startCoord.copy();
	}
	
	/**
	 * Set starting movement vector of the beam
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return self
	 */
	public BeamTracer setStartMove(int x, int y, int z) {
		startMove = new VecI(x, y, z);
		return this;
	}
	
	/**
	 * Set starting movement vector of the beam
	 * 
	 * @param coord
	 * @return self
	 */
	public BeamTracer setStartMove(VecI coord) {
		startMove = coord.copy();
		return this;
	}
	
	/**
	 * Set starting beam color (can be changed by power crystals)
	 * 
	 * @param color
	 *            color object representing the color;<br>
	 *            Color must also contain information about beam metadata, and
	 *            visibility flag.
	 * @return self
	 */
	public BeamTracer setColor(Color color) {
		origColor = color.copy();
		return this;
	}
	
	/**
	 * Set initial beam color
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param meta
	 *            metadata of corresponding power crystal -1 <br>
	 *            (crystals meta starts at 1, which equals 0 here)
	 * @return self
	 */
	public BeamTracer setColor(float r, float g, float b, int meta) {
		origColor = new Color(r, g, b);
		return this;
	}
	
	/**
	 * Set whether the beam can detect entities
	 * 
	 * @param state
	 * @return self
	 */
	public BeamTracer setDetectEntities(boolean state) {
		canHitEntity = state;
		return this;
	}
	
	/**
	 * Total beam length in this flash. <br>
	 * Used to prevent infinite loops and stack overflow.
	 */
	private int totalLength = 0;
	
	/**
	 * Send one light quantum and spawn particles on the way.
	 */
	public void flash() {
		totalLength = 0;
		
		forkBeam(new BeamSettings(this, startCoord, startMove, origColor, strength, start_limit));
	}
	
	/**
	 * Fork current beam. To be called only by subclasses.
	 * 
	 * @param par_cnt
	 *            starting coordinate
	 * @param par_move
	 *            starting movement
	 * @param par_color
	 *            starting color object
	 * @param limit
	 *            length limit for this fork
	 */
	public void forkBeam(BeamSettings settings) {
		// copy parameters to prevent interference
		
		for (; settings.length > 0; settings.length--) {
			
			if (++totalLength > maxTotalLength) {
				return;
			}
			
			if (world.isRemote) {
				
				addLaser(world, settings.pos, settings.move, strength, settings.color);
				
			}
			
			settings.pos.add(settings.move);
			
			Block b = world.getBlock(settings.pos.x, settings.pos.y, settings.pos.z);

			BeamHitResult res = BeamHitResult.CONTINUE;
			if (b != null) {
				res = BeamHitResult.FALLBACK;
				if (b instanceof PBlock && handleBlocks) {
					res = ((PBlock) b).onBlockHitByBeam(world, settings.pos.x, settings.pos.y, settings.pos.z, settings);
				}
			} else if (handler instanceof IBeamHandlerExt) {
				boolean stop = ((IBeamHandlerExt) handler).onEmptyBlockHit(this, settings.pos);
				if (stop) {
					return;
				}
			}
			
			if (res == BeamHitResult.FALLBACK) {
				
				boolean stop = true;
				if (handler != null)
					stop = handler.onBlockHit(this, b, settings.pos);
				
				if (stop) {
					return;
				}
				
			} else if (res == BeamHitResult.CONTINUE) {
				
				// just continue
				
			} else if (res == BeamHitResult.STOP) {
				
				// break loop
				return;
				
			}
			
			if (canHitEntity) {
				
				// check for entities in this block.
				/**
				 * @todo getBoundingBox??
				 */
				List<Entity> hitList = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(settings.pos.x, settings.pos.y, settings.pos.z,
						settings.pos.x + 1, settings.pos.y + 1, settings.pos.z + 1));
				
				boolean stop = false;
				for (Entity entity : hitList) {
					res = BeamHitResult.FALLBACK;
					//if (entity instanceof PC_IMSG && handleBlocks) {
					//	Object o = ((PC_IMSG) entity).msg(PC_MSGRegistry.MSG_ON_HIT_BY_BEAM_TRACER, world, settings);
					//	if (o instanceof BeamHitResult)
					//		res = (BeamHitResult) o;
					//}
					if (res == BeamHitResult.FALLBACK) {
						
						if (handler != null) {
							//if (handler.onEntityHit(this, entity, settings.pos)) {
							//	stop = true;
							//}
						} else {
							stop = true;
						}
						
					} else if (res == BeamHitResult.CONTINUE) {
						
						// just continue
						
					} else if (res == BeamHitResult.STOP) {
						
						stop = true;
						
					}
				}
				
				if (stop) {
					return;
				}
				
			}
		}
		
	}
	
	public static void addLaser(World world, VecI cnt, VecI move, float strength, Color color) {
		cnt = cnt.copy();
		VecI oldMove = move;
		move = move.copy();
		color = color.copy();
		boolean dirChage = false;
		if (move.x < 0) {
			move.x = -move.x;
			move.y = -move.y;
			move.z = -move.z;
			dirChage = true;
		} else if (move.x == 0 && move.y < 0) {
			move.y = -move.y;
			move.z = -move.z;
			dirChage = true;
		} else if (move.x == 0 && move.y == 0 && move.z < 0) {
			move.z = -move.z;
			dirChage = true;
		}
		if (dirChage)
			cnt = cnt.offset(oldMove);
		
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityLaserFX(world, cnt.x, cnt.y, cnt.z, move.x, move.y, move.z, 3, color.x, color.y, color.z, 0));
	}
	
	/**
	 * Result state enum for extending class's block hit method.
	 */
	public static enum BeamHitResult {
		/** Fall back to handler */
		FALLBACK,
		/** Continue to next block */
		CONTINUE,
		/** Stop the beam propagation */
		STOP;
	}
	
	public static class BeamSettings {
		private BeamTracer beamTracer;
		private VecI pos;
		private VecI move;
		private Color color;
		private float strength;
		private int length;
		
		public BeamSettings(BeamTracer beamTracer, VecI startCoord, VecI startMove, Color origColor, float strength2, int start_limit) {
			this.beamTracer = beamTracer;
			pos = startCoord.copy();
			move = startMove.copy();
			color = origColor.copy();
			strength = strength2;
			length = start_limit;
		}
		
		public BeamTracer getBeamTracer() {
			return beamTracer;
		}
		
		public VecI getPos() {
			return pos.copy();
		}
		
		public VecI getMove() {
			return move.copy();
		}
		
		public Color getColor() {
			return color.copy();
		}
		
		public float getStrength() {
			return strength;
		}
		
		public int getLength() {
			return length;
		}
		
		public Object getData(String key) {
			return beamTracer.data.get(key);
		}
		
		public void setPos(VecI newPos) {
			pos.setTo(newPos);
		}
		
		public void setMove(VecI newMove) {
			move.setTo(newMove);
		}
		
		public void setColor(Color newColor) {
			if (beamTracer.canChangeColor)
				color.setTo(newColor);
		}
		
		public void setStrength(float newStrength) {
			strength = newStrength;
		}
		
		public void setLength(int newLength) {
			length = newLength;
		}
		
	}
	
}

