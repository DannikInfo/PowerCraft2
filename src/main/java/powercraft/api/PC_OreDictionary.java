package powercraft.api;

import java.util.HashMap;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import powercraft.api.annotation.PC_OreInfo;
import powercraft.api.block.PC_Block;

public class PC_OreDictionary implements IWorldGenerator{
	
	private static HashMap<Integer, PC_OreInfo> info = new HashMap<Integer, PC_OreInfo>();
	private static HashMap<Integer, PC_Block> stacks = new HashMap<Integer, PC_Block>();
	
	static int counter = 0;

	public static void register(PC_OreInfo ore, PC_Block block) {
		info.put(counter, ore);
		stacks.put(counter++, block);
	}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		generateOverworld(rand, chunkX, chunkZ, world);
	}

	private void generateOverworld(Random rand, int chunkX, int chunkZ, World world) {
		generateOverworld(world, rand, chunkX * 16, chunkZ * 16);
	}

	public void generateOverworld(World world, Random rand, int blockXPos, int blockZPos) {
		for(int i = 0; i < info.size(); i++) {
			PC_OreInfo ore = info.get(i);
	    	PC_Block block= stacks.get(i);
	    	addOreSpawn(block, world, rand, blockXPos, blockZPos, 16, 16, ore.genOresDepositMaxCount(),
	    			block.getGenOresSpawnMetadata(rand, world, blockXPos, blockZPos), ore.genOresInChunk() * ore.genOresDepositMaxCount(), ore.genOresMinY(), ore.genOresMaxY());                             
	    }
	}
	    
	/**
	* @param block Блок, который хотите генерировать
	* @param world Мир (не измерение), в котором этот блок должен генерироваться
	* @param random Случайное число для получения координат генерации блока
	* @param blockXPos Число для того, чтобы было пустое место по координатам X для метода генерации (использует кварцевая руда)
	* @param blockZPos Число для того, чтобы было пустое место по координатам Z для метода генерации (использует кварцевая руда)
	* @param maxX Число, которое настроит максимальную X координату для генерации руды на оси X на чанк
	* @param maxZ Число, которое настроит максимальную Z координату для генерации руды на оси Z на чанк
	* @param maxVeinSize Максимальное число блоков руды в одной жиле
	* @param chancesToSpawn Шанс генерации блоков на чанк
	* @param minY Минимальная координата Y на которой руда может сгенерироваться
	* @param maxY Максимальная координата Y на которой руда может сгенерироваться
	*/
	public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ,
		int maxVeinSize, int meta, int chancesToSpawn, int minY, int maxY){
	    int maxPossY = minY + (maxY - 1);
	                
	    int diffBtwnMinMaxY = maxY - minY;
	    for (int x = 0; x < chancesToSpawn; x++){
	    	int posX = blockXPos + random.nextInt(maxX);
	        int posY = minY + random.nextInt(diffBtwnMinMaxY);
	        int posZ = blockZPos + random.nextInt(maxZ);
	        (new WorldGenMinable(block, meta, maxVeinSize, Blocks.stone)).generate(world, random, posX, posY, posZ);
	    }
	}	
}
