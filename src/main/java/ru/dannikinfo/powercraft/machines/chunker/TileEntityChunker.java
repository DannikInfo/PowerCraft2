package ru.dannikinfo.powercraft.machines.chunker;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import ru.dannikinfo.powercraft.api.network.PacketManager;
import ru.dannikinfo.powercraft.api.utils.BaseUtils;
import ru.dannikinfo.powercraft.api.utils.WorldData;
import ru.dannikinfo.powercraft.core.BlocksCore;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.core.crystal.Crystal;
import ru.dannikinfo.powercraft.machines.BlocksMachines;

public class TileEntityChunker extends TileEntity{
	
	private Ticket chunkTicket;
	public World world;
	int count = 0;
	
	public void loadChunks(){
		chunkTicket = ForgeChunkManager.requestTicket(Main.instance, worldObj, Type.NORMAL);
		if(chunkTicket != null){
			chunkTicket.getModData().setInteger("type", ChunkerCallback.ChunkLoaderBlockID);
			chunkTicket.getModData().setInteger("blockX", xCoord);
			chunkTicket.getModData().setInteger("blockY", yCoord);
			chunkTicket.getModData().setInteger("blockZ", zCoord);
				
			ForgeChunkManager.forceChunk(chunkTicket, new ChunkCoordIntPair(xCoord>>4, zCoord >>4));
		}
	}
	
	 
	 public void unloadChunks(){
		 ForgeChunkManager.unforceChunk(chunkTicket, new ChunkCoordIntPair(xCoord>>4, zCoord >>4));
	 }
	 
	 public void loadTicket(Ticket ticket)
	 {
		 if (chunkTicket == null)
		 {
				chunkTicket = ticket;
		 }
		 ChunkCoordIntPair loadChunk = new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4);
		 ForgeChunkManager.forceChunk(ticket, loadChunk);
	 }
	 
	 @Override
	 public void updateEntity(){
		 int x = xCoord;
		 int y = yCoord;
		 int z = zCoord;
		 World world = worldObj;
	    String udid = BaseUtils.udid(x, y, z);
		WorldData data = WorldData.forWorld(world);
		NBTTagCompound tag = data.getData();
		int status = tag.getInteger("status_" + udid);
		 Block block = worldObj.getBlock(x, y, z);
		 if(block == BlocksMachines.chunker){
			Chunker ch = (Chunker) block;
			if(world.getBlock(x, y + 1, z) == BlocksCore.crystal && world.getBlockMetadata(x, y + 1, z) == 7 &&
               world.getBlock(x + 3, y + 2, z) == BlocksCore.crystal && world.getBlockMetadata(x + 3, y + 2, z) == 6 &&
               world.getBlock(x - 3, y + 2, z) == BlocksCore.crystal && world.getBlockMetadata(x - 3, y + 2, z) == 6 &&
               world.getBlock(x, y + 2, z + 3) == BlocksCore.crystal && world.getBlockMetadata(x, y + 2, z + 3) == 1 &&
               world.getBlock(x, y + 2, z - 3) == BlocksCore.crystal && world.getBlockMetadata(x, y + 2, z - 3) == 1  &&
               world.getBlock(x + 2, y + 4, z - 2) == BlocksCore.crystal && world.getBlockMetadata(x + 2, y + 4, z - 2) == 2  &&
               world.getBlock(x - 2, y + 4, z - 2) == BlocksCore.crystal && world.getBlockMetadata(x - 2, y + 4, z - 2) == 2 &&
               world.getBlock(x - 2, y + 4, z + 2) == BlocksCore.crystal && world.getBlockMetadata(x - 2, y + 4, z + 2) == 2 &&
               world.getBlock(x + 2, y + 4, z + 2) == BlocksCore.crystal && world.getBlockMetadata(x + 2, y + 4, z + 2) == 2 &&
               world.getBlock(x + 3, y + 1, z) == Blocks.diamond_block &&
               world.getBlock(x - 3, y + 1, z) == Blocks.diamond_block &&
               world.getBlock(x, y + 1, z + 3) == Blocks.diamond_block &&
               world.getBlock(x, y + 1 , z - 3) == Blocks.diamond_block &&
               world.getBlock(x + 2, y + 5, z - 2) == Blocks.diamond_block &&
               world.getBlock(x - 2, y + 5, z - 2) == Blocks.diamond_block &&
               world.getBlock(x - 2, y + 5, z + 2) == Blocks.diamond_block &&
               world.getBlock(x + 2, y + 5, z + 2) == Blocks.diamond_block 
                         ){
				 if(status == 1){
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+1, z, 2, 3, 2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+1, z, -2, 3, -2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+1, z, 2, 3, -2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+1, z, -2, 3, 2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x+3, y+2, z, -1, 2, -2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x+3, y+2, z, -1, 2, 2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x-3, y+2, z, 1, 2, -2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x-3, y+2, z, 1, 2, 2, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+2, z+3, 2, 2, -1, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+2, z+3, -2, 2, -1, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+2, z-3, -2, 2, 1, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+2, z-3, 2, 2, 1, 0, 255, 0, 0));
						PacketManager.sendToServer(new BeamChunkerMessage(x, y+1, z, 0, 32, 0, 0, 255, 0, 0));
						Random rand = new Random();
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, rand.nextInt(10), rand.nextInt(10), rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, -rand.nextInt(10), rand.nextInt(10), rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, rand.nextInt(10), -rand.nextInt(10), rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, -rand.nextInt(10), -rand.nextInt(10), rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, rand.nextInt(10), rand.nextInt(10), -rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, rand.nextInt(10), -rand.nextInt(10), -rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, -rand.nextInt(10), -rand.nextInt(10), -rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
			 			PacketManager.sendToServer(new BeamChunkerMessage(x, y+33, z, -rand.nextInt(10), rand.nextInt(10), -rand.nextInt(10), rand.nextInt(254), rand.nextInt(254), rand.nextInt(254), 0));
				 }
			}else{
				tag.setInteger("status_" + udid, 0);
				
			}
		 }
		 data.markDirty();
	 }
}