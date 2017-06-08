package ru.dannik.powercraft.utils;

import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import ru.dannik.powercraft.beam.EntityLaserFX;
import ru.dannik.powercraft.blocks.BlockList;
import ru.dannik.powercraft.network.PacketManager;
import ru.dannik.powercraft.network.server.BeamClientMessage;

public class BaseUtils {
	protected static BaseUtils instance;
	private HashMap<String, Class<? extends EntityFX>> entityFX = new HashMap<String, Class<? extends EntityFX>>();
	
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
    	Minecraft.getMinecraft().effectRenderer.addEffect(new EntityLaserFX(world, x, y, z, xE, 0, zE, 3, r, g, b, death));
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
				world.getBlock(x, y, z) != BlockList.spawner &&
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
}
