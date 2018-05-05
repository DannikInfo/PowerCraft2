package ru.dannikinfo.powercraft.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.PBlock;
import ru.dannikinfo.powercraft.api.beam.BeamTracer;
import ru.dannikinfo.powercraft.api.beam.IBeamHandler;
import ru.dannikinfo.powercraft.api.beam.IBeamHandlerExt;
import ru.dannikinfo.powercraft.api.utils.Color;
import ru.dannikinfo.powercraft.api.utils.VecI;
import ru.dannikinfo.powercraft.core.Main;
import ru.dannikinfo.powercraft.machines.tile.TileEntityBlockBuilder;

public class BlockBuilder extends PBlock implements IBeamHandler, IBeamHandlerExt{
	private static final int TXSIDE = 0, TXFRONT = 1;
	
	public static final Block ENDBLOCK = Blocks.stonebrick;
	
	public BlockBuilder(String name) {
		super(Material.ground);
		setHardness(0.7F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(Main.tabPowerCraft);
	}

	@Override
	public int tickRate(World world) {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {

		if (world.isRemote) {
			return true;
		}

		//PC_GresRegistry.openGres("BlockBuilder", entityplayer, PC_Utils.<PC_TileEntity>getTE(world, i, j, k));

		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!world.isRemote) {
			boolean flag = isIndirectlyPowered(world, i, j, k);
			if (flag) {
				world.scheduleBlockUpdate(i, j, k, block, tickRate(world));
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (isIndirectlyPowered(world, i, j, k)) {
			buildBlocks(world, i, j, k, world.getBlockMetadata(i, j, k));
		}
	}

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param deviceMeta
	 */
	private void buildBlocks(World world, int x, int y, int z, int deviceMeta) {

		if(!world.isRemote)
			//PC_PacketHandler.sendToPacketHandler(true, world, "PCma_BlockBlockBuilder", x, y, z, deviceMeta);
		
		deviceMeta &= 0x7;

		VecI cnt = new VecI(x, y, z);
		BeamTracer beamTracer = new BeamTracer(world, this);

		beamTracer.setStartCoord(cnt);
		beamTracer.setStartMove(getRotation(deviceMeta).getOffset());
		beamTracer.setCanChangeColor(false);
		beamTracer.setDetectEntities(true);
		beamTracer.setTotalLengthLimit(8000);
		beamTracer.setMaxLengthAfterCrystal(2000);
		beamTracer.setStartLength(30);
		beamTracer.setData("crystalAdd", 100);
		beamTracer.setColor(new Color(0.001f, 0.001f, 1.0f));

		if (world.getBlock(x, y - 1, z) == ENDBLOCK) {
			beamTracer.setStartLength(1);
			beamTracer.setMaxLengthAfterCrystal(1);
		}
		
		beamTracer.flash();

	}

	private boolean isIndirectlyPowered(World world, int i, int j, int k) {

		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			return true;
		}

		if (world.isBlockIndirectlyGettingPowered(i, j - 1, k)) {
			return true;
		}
		return false;
	}


	@Override
	public boolean onBlockHit(BeamTracer beamTracer, Block block, VecI coord) {
		return false;
	}

	@Override
	public boolean onEntityHit(BeamTracer beamTracer, Entity entity, VecI coord) {
		return false;
	}

	@Override
	public boolean onEmptyBlockHit(BeamTracer beamTracer, VecI coord) {
		World world = beamTracer.getWorld();
		TileEntityBlockBuilder tebb = (TileEntityBlockBuilder) world.getTileEntity(beamTracer.getStartCoord().x, beamTracer.getStartCoord().y, beamTracer.getStartCoord().z);
		return tebb.useItem(coord);
	}

	public boolean handleIncomingPacket(EntityPlayer player, Object[] o) {
		if(player.worldObj.isRemote)
			buildBlocks(player.worldObj, (Integer)o[0], (Integer)o[1], (Integer)o[2], (Integer)o[3]);
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBlockBuilder();
	}

}
