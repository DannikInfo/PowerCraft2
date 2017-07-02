package ru.dannikinfo.powercraft.api.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.beam.EntityLaserFX;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.interfaces.INBT;
import ru.dannikinfo.powercraft.light.laser.BeamClientMessage;
import ru.dannikinfo.powercraft.logic.BlocksLogic;

public class BaseUtils {
	protected static BaseUtils instance;
	private HashMap<String, Class<? extends EntityFX>> entityFX = new HashMap<String, Class<? extends EntityFX>>();
	public static final int BLOCK_NOTIFY = 1, BLOCK_UPDATE = 2, BLOCK_ONLY_SERVERSIDE = 4;
	
	public static void spawnMobs(World world, int x, int y, int z, String type) {
		byte count = 5;
		
		for (int q = 0; q < count; q++) {
			EntityLiving entityliving = (EntityLiving) EntityList.createEntityByName(type, world);
			
			if (entityliving == null) {
				return;
			}
			
			int c = world.getEntitiesWithinAABB(entityliving.getClass(), AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(8D, 4D, 8D)).size();
			
			if (c >= 6) {
					double d = world.rand.nextGaussian() * 0.02D;
					double d1 = world.rand.nextGaussian() * 0.02D;
					double d2 = world.rand.nextGaussian() * 0.02D;
					world.spawnParticle("smoke", x + 0.5D, y + 0.4D, z + 0.5D, d, d1, d2);
				
				return;
			}
			double d3 = x + (world.rand.nextDouble() - world.rand.nextDouble()) * 3D;
			double d4 = (y + world.rand.nextInt(3)) - 1;
			double d5 = z + (world.rand.nextDouble() - world.rand.nextDouble()) * 3D;
			entityliving.setLocationAndAngles(d3, d4, d5, world.rand.nextFloat() * 360F, 0.0F);
			
			if (world.checkNoEntityCollision(entityliving.boundingBox) && world.getCollidingBoundingBoxes(entityliving, entityliving.boundingBox).size() == 0) {
				world.spawnEntityInWorld(entityliving);
				world.playAuxSFX(2004, x, y, z, 0);
				entityliving.spawnExplosionParticle();
				
				return;
			}
		}
	}
	
	public static String udid(int x, int y, int z){
		String sX = Integer.toString(x);
		String sY = Integer.toString(y);
		String sZ = Integer.toString(z);
		String preUdid = DigestUtils.md5Hex(sX)+DigestUtils.md5Hex(sY)+DigestUtils.md5Hex(sZ); 
		String udid = DigestUtils.md5Hex(preUdid);
		return udid;
	}
	
	public static Minecraft mc() {
		return Minecraft.getMinecraft();
	}
	
    public void traceBeam(World world, int count, int dir, int x, int y, int z, int r, int g, int b, int death){
    	int zE, xE;
    	zE = 0;
    	xE = 0;
    	if(dir == 0)zE = count;
    	if(dir == 1)xE = -count;
    	if(dir == 2)zE = -count;
    	if(dir == 3)xE = count;
    	VecI cnt = new VecI(x, y, z);
    	VecI move = new VecI(xE, 0, zE);
		cnt = cnt.copy();
		VecI oldMove = move;
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
    	Minecraft.getMinecraft().effectRenderer.addEffect(new EntityLaserFX(world, cnt.x, cnt.y, cnt.z, move.x, move.y, move.z, 3, r, g, b, death));
    }

	public static void traceBeamMessageRe(World world, int count, int dir, int x, int y, int z, EntityPlayer player, int r, int g, int b, int death){
    	PacketManager.sendTo(new BeamClientMessage(count, dir, x, y, z, r, g, b, death), (EntityPlayerMP) player);
    }
	
	public static void harvest(World world, int x, int y, int z, int count, int dir){
		//unbreaking list
		if(world.getBlock(x, y, z) != Blocks.air &&
				world.getBlock(x, y, z) != Blocks.obsidian &&
				world.getBlock(x, y, z) != Blocks.stonebrick &&
				world.getBlock(x, y, z) != Blocks.water &&
				world.getBlock(x, y, z) != Blocks.flowing_water &&
				world.getBlock(x, y, z) != Blocks.lava &&
				world.getBlock(x, y, z) != Blocks.flowing_lava &&
				world.getBlock(x, y, z) != Blocks.bedrock &&
				world.getBlock(x, y, z) != Blocks.sapling &&
				world.getBlock(x, y, z) != Blocks.redstone_block &&
				world.getBlock(x, y, z) != Blocks.redstone_lamp &&
				world.getBlock(x, y, z) != Blocks.redstone_wire &&
				world.getBlock(x, y, z) != Blocks.lit_redstone_lamp &&
				world.getBlock(x, y, z) != Blocks.unlit_redstone_torch &&
				world.getBlock(x, y, z) != Blocks.glass &&
				world.getBlock(x, y, z) != Blocks.glass_pane &&
				world.getBlock(x, y, z) != Blocks.stained_glass &&
				world.getBlock(x, y, z) != Blocks.stained_glass_pane &&
				world.getBlock(x, y, z) != Blocks.fire &&
				world.getBlock(x, y, z) != Blocks.lever &&
				world.getBlock(x, y, z) != Blocks.stone_button &&
				world.getBlock(x, y, z) != Blocks.wooden_button &&
				world.getBlock(x, y, z) != Blocks.piston &&
				world.getBlock(x, y, z) != Blocks.piston_extension &&
				world.getBlock(x, y, z) != Blocks.piston_head &&
				world.getBlock(x, y, z) != Blocks.sticky_piston &&
				world.getBlock(x, y, z) != Blocks.pumpkin_stem &&
				world.getBlock(x, y, z) != Blocks.cake &&
				world.getBlock(x, y, z) != Blocks.torch &&
				world.getBlock(x, y, z) != Blocks.rail &&
				world.getBlock(x, y, z) != Blocks.powered_comparator &&
				world.getBlock(x, y, z) != Blocks.unpowered_comparator &&
				world.getBlock(x, y, z) != Blocks.powered_repeater &&
				world.getBlock(x, y, z) != Blocks.unpowered_repeater &&
				world.getBlock(x, y, z) != Blocks.mob_spawner &&
				world.getBlock(x, y, z) != BlocksLogic.spawner &&
				world.getBlock(x, y, z) != Blocks.activator_rail &&
				world.getBlock(x, y, z) != Blocks.detector_rail &&
				world.getBlock(x, y, z) != Blocks.golden_rail
				){
			//Special breaking list
			if(world.getBlock(x, y, z) != Blocks.log &&
					world.getBlock(x, y, z) != Blocks.log2 &&
					world.getBlock(x, y, z) != Blocks.wheat &&
					world.getBlock(x, y, z) != Blocks.carrots &&
					world.getBlock(x, y, z) != Blocks.potatoes &&
					world.getBlock(x, y, z) != Blocks.cocoa
				){
				Block block = world.getBlock(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, Blocks.air);
				int xE = x;
				int zE = z;
				if(dir == 0)zE = z - count - 1;
				if(dir == 1)xE = x + count + 1;
				if(dir == 2)zE = z + count + 1;
				if(dir == 3)xE = x - count - 1;
				block.dropBlockAsItem(world, xE, y, zE, meta, 0);
			}else{
				if(world.getBlock(x, y, z) == Blocks.wheat ||
						world.getBlock(x, y, z) == Blocks.carrots ||
						world.getBlock(x, y, z) == Blocks.potatoes ||
						world.getBlock(x, y, z) == Blocks.cocoa
					){
					harvestSeed(world, x, y, z, dir, count);
				}
				if(world.getBlock(x, y, z) == Blocks.log || world.getBlock(x, y, z) == Blocks.log2){
					harvestTree(world, x, y, z, dir, count, 100);
					for(int i = 0; i < 23; ++i){
						harvestTree(world, x, y, z, dir, count, i);
					}
				}
			}
		}
	}
	
	public static void harvestTree(World world, int x, int y, int z, int dir, int count, int treeDir){
		int i = 0;
		if(treeDir == 0)x = x + 1;
		if(treeDir == 1)z = z + 3;
		if(treeDir == 2)x = x - 1;
		if(treeDir == 3)z = z + 1;
		if(treeDir == 4){x = x + 1; z = z + 1;}
		if(treeDir == 5){x = x + 1; z = z - 1;}
		if(treeDir == 6){x = x - 1; z = z + 1;}
		if(treeDir == 7){x = x - 1; z = z - 1;}
		if(treeDir == 8)x = x + 2;
		if(treeDir == 9)z = z - 2;
		if(treeDir == 10)x = x - 2;
		if(treeDir == 11)z = z + 2;
		if(treeDir == 12){x = x + 2; z = z + 2;}
		if(treeDir == 13){x = x + 2; z = z - 2;}
		if(treeDir == 14){x = x - 2; z = z + 2;}
		if(treeDir == 15){x = x - 2; z = z - 2;}
		if(treeDir == 16){x = x + 1; z = z + 2;}
		if(treeDir == 17){x = x + 2; z = z - 1;}
		if(treeDir == 18){x = x - 1; z = z + 2;}
		if(treeDir == 19){x = x - 2; z = z - 1;}
		if(treeDir == 20){x = x + 2; z = z + 1;}
		if(treeDir == 21){x = x + 1; z = z - 2;}
		if(treeDir == 22){x = x - 2; z = z + 1;}
		if(treeDir == 23){x = x - 1; z = z - 2;}
		do{
			if(world.getBlock(x, y + i, z) == Blocks.log || world.getBlock(x, y + i, z) == Blocks.log2 || world.getBlock(x, y + i, z) == Blocks.leaves || world.getBlock(x, y + i, z) == Blocks.leaves2){
				Block block = world.getBlock(x, y + i, z);
				int meta = world.getBlockMetadata(x, y + i, z);
				world.setBlock(x, y + i, z, Blocks.air);
				int xE = x;
				int zE = z;
				if(dir == 0)zE = z - count - 1;
				if(dir == 1)xE = x + count + 1;
				if(dir == 2)zE = z + count + 1;
				if(dir == 3)xE = x - count - 1;
				block.dropBlockAsItem(world, xE, y, zE, meta, 0);
			}
			i++;
		}while(i < 256 - y);
	}

	public static void harvestSeed(World world, int x, int y, int z, int dir, int count){
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 7){
			world.setBlock(x, y, z, block, 0, 0);
			int xE = x;
			int zE = z;
			if(dir == 0)zE = z - count - 1;
			if(dir == 1)xE = x + count + 1;
			if(dir == 2)zE = z + count + 1;
			if(dir == 3)xE = x - count - 1;
			block.dropBlockAsItem(world, xE, y, zE, meta, 0);
		}
	}
	
	public static int getMD(IBlockAccess blockAccess, int x, int y, int z) {
		return blockAccess.getBlockMetadata(x, y, z);
	}
	
	public static int getMD(IBlockAccess blockAccess, VecI pos) {
		return getMD(blockAccess, pos.x, pos.y, pos.z);
	}
	
	public static boolean setMD(World world, int x, int y, int z, int meta, int flag) {
		return world.setBlockMetadataWithNotify(x, y, z, meta, flag);
	}
	
	public static boolean setMD(World world, VecI pos, int meta, int flag) {
		return setMD(world, pos.x, pos.y, pos.z, meta, flag);
	}
	
	public static boolean setMD(World world, int x, int y, int z, int meta) {
		return world.setBlockMetadataWithNotify(x, y, z, meta, BLOCK_NOTIFY | BLOCK_UPDATE);
	}
	
	public static boolean setMD(World world, VecI pos, int meta) {
		return setMD(world, pos.x, pos.y, pos.z, meta);
	}
	
	public static Block getBlock(World world, VecI pos) {
		return world.getBlock(pos.x, pos.y, pos.z);
	}
	
	public static Direction getRotation(World world, VecI pos) {
		return Direction.TOP;
	}
	
	public static void setBlockBounds(Block block, double x, double y, double z, double width, double height, double depht) {
		block.setBlockBounds((float)x, (float)y, (float)z, (float)width, (float)height, (float)depht);
	}
	
	public static void hugeUpdate(World world, int x, int y, int z) {
		Block blockID = world.getBlock(x, y, z);
		notifyBlockOfNeighborChange(world, x - 2, y, z, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y, z, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y, z, blockID);
		notifyBlockOfNeighborChange(world, x + 2, y, z, blockID);
		notifyBlockOfNeighborChange(world, x, y - 2, z, blockID);
		notifyBlockOfNeighborChange(world, x, y - 1, z, blockID);
		notifyBlockOfNeighborChange(world, x, y + 1, z, blockID);
		notifyBlockOfNeighborChange(world, x, y + 2, z, blockID);
		notifyBlockOfNeighborChange(world, x, y, z - 2, blockID);
		notifyBlockOfNeighborChange(world, x, y, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x, y, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x, y, z + 2, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y + 1, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y + 1, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y + 1, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y + 1, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y + 1, z, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y + 1, z, blockID);
		notifyBlockOfNeighborChange(world, x, y + 1, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x, y + 1, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y - 1, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y - 1, z - 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y - 1, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y - 1, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x + 1, y - 1, z, blockID);
		notifyBlockOfNeighborChange(world, x - 1, y - 1, z, blockID);
		notifyBlockOfNeighborChange(world, x, y - 1, z + 1, blockID);
		notifyBlockOfNeighborChange(world, x, y - 1, z - 1, blockID);
	}
	
	public static void hugeUpdate(World world, VecI pos) {
		hugeUpdate(world, pos.x, pos.y, pos.z);
	}
	
	public static void notifyBlockOfNeighborChange(World world, int x, int y, int z, Block blockID) {
		Block block = world.getBlock(x, y, z);
		if (block != null) {
			block.onNeighborBlockChange(world, x, y, z, block);
		}
	}
	
	public static void saveToNBT(NBTTagCompound nbtTag, String key, Object value) {
		if (value == null) {
			return;
		} else if (value.getClass().isArray()) {
			NBTTagCompound nbtTag2 = new NBTTagCompound();
			int size = Array.getLength(value);
			nbtTag2.setInteger("count", size);
			nbtTag2.setString("type", value.getClass().getName());
			for (int i = 0; i < size; i++) {
				saveToNBT(nbtTag2, "value[" + i + "]", Array.get(value, i));
			}
			nbtTag.setTag(key, nbtTag2);
		} else if (value instanceof INBT) {
			NBTTagCompound nbtTag2 = new NBTTagCompound();
			nbtTag2.setString("type", value.getClass().getName());
			saveToNBT(nbtTag2, "value", (INBT) value);
			nbtTag.setTag(key, nbtTag2);
		} else if (value instanceof List) {
			List l = (List) value;
			NBTTagCompound nbtTag2 = new NBTTagCompound();
			int size = l.size();
			nbtTag2.setInteger("count", size);
			nbtTag2.setString("type", l.getClass().getName());
			for (int i = 0; i < size; i++) {
				saveToNBT(nbtTag2, "value[" + i + "]", l.get(i));
			}
			nbtTag.setTag(key, nbtTag2);
		} else if (value instanceof Map) {
			Map<?, ?> m = (Map) value;
			NBTTagCompound nbtTag2 = new NBTTagCompound();
			int size = m.size();
			nbtTag2.setInteger("count", size);
			nbtTag2.setString("type", m.getClass().getName());
			int i=0;
			for (Entry e:m.entrySet()) {
				saveToNBT(nbtTag2, "key[" + i + "]", e.getKey());
				saveToNBT(nbtTag2, "value[" + i + "]", e.getValue());
				i++;
			}
			nbtTag.setTag(key, nbtTag2);
		} else if (value instanceof Byte) {
			nbtTag.setByte(key, (Byte) value);
		} else if (value instanceof Short) {
			nbtTag.setShort(key, (Short) value);
		} else if (value instanceof Integer) {
			nbtTag.setInteger(key, (Integer) value);
		} else if (value instanceof Long) {
			nbtTag.setLong(key, (Long) value);
		} else if (value instanceof Float) {
			nbtTag.setFloat(key, (Float) value);
		} else if (value instanceof Double) {
			nbtTag.setDouble(key, (Double) value);
		} else if (value instanceof Boolean) {
			NBTTagCompound nbtTag2 = new NBTTagCompound();
			nbtTag2.setString("type", Boolean.class.getName());
			nbtTag2.setBoolean("value", (Boolean) value);
			nbtTag.setTag(key, nbtTag2);
		} else if (value instanceof String) {
			nbtTag.setString(key, (String) value);
		} else if (value instanceof ItemStack) {
			NBTTagCompound nbtTag2 = new NBTTagCompound();
			nbtTag2.setString("type", ItemStack.class.getName());
			((ItemStack) value).writeToNBT(nbtTag2);
			nbtTag.setTag(key, nbtTag2);
		}
	}
	
	public static Object loadFromNBT(NBTTagCompound nbtTag, String key) {
		Object value = nbtTag.getTag(key);
		if (value instanceof NBTTagCompound) {
			NBTTagCompound nbtTag2 = nbtTag.getCompoundTag(key);
			try {
				Class c = Class.forName(nbtTag2.getString("type"));
				if (c.isArray()) {
					int size = nbtTag2.getInteger("count");
					Object a = Array.newInstance(c.getComponentType(), size);
					for (int i = 0; i < size; i++) {
						Array.set(a, i, loadFromNBT(nbtTag2, "value[" + i + "]"));
					}
					return a;
				} else if (c == ItemStack.class) {
					return ItemStack.loadItemStackFromNBT(nbtTag2);
				} else if (c == Boolean.class) {
					return nbtTag2.getBoolean("value");
				} else {
					try {
						Object o = c.newInstance();
						if (o instanceof INBT) {
							o =loadFromNBT(nbtTag2, "value", (INBT) o);
						} else if (o instanceof List) {
							int size = nbtTag2.getInteger("count");
							for (int i = 0; i < size; i++) {
								((List) o).add(loadFromNBT(nbtTag2, "value[" + i + "]"));
							}
						} else if (o instanceof Map) {
							int size = nbtTag2.getInteger("count");
							for (int i = 0; i < size; i++) {
								((Map) o).put(loadFromNBT(nbtTag2, "key[" + i + "]"), loadFromNBT(nbtTag2, "value[" + i + "]"));
							}
						}
						return o;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if (value instanceof NBTTagByte) {
			return ((NBTTagByte) value).getId();
		} else if (value instanceof NBTTagShort) {
			return ((NBTTagShort) value).getId();
		} else if (value instanceof NBTTagInt) {
			return ((NBTTagInt) value).getId();
		} else if (value instanceof NBTTagLong) {
			return ((NBTTagLong) value).getId();
		} else if (value instanceof NBTTagFloat) {
			return ((NBTTagFloat) value).getId();
		} else if (value instanceof NBTTagDouble) {
			return ((NBTTagDouble) value).getId();
		} else if (value instanceof NBTTagString) {
			return ((NBTTagString) value).getId();
		}
		return null;
	}
	
	public static <T extends INBT<T>> T loadFromNBT(NBTTagCompound nbttagcompound, String string, T nbt) {
		NBTTagCompound nbttag = nbttagcompound.getCompoundTag(string);
		return nbt.readFromNBT(nbttag);
	}
}
