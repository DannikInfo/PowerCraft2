package ru.dannikinfo.powercraft.api;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import ru.dannikinfo.powercraft.api.beam.BeamTracer.BeamHitResult;
import ru.dannikinfo.powercraft.api.beam.BeamTracer.BeamSettings;
import ru.dannikinfo.powercraft.api.utils.Direction;

public abstract class PBlock extends BlockContainer {

	
	public PBlock(Material material) {
		super(material);
		disableStats();
	}

	
	public Direction getRotation(int metadata) {
			metadata &= 3;
			if (metadata == 0) {
				return Direction.FRONT;
			} else if (metadata == 1) {
				return Direction.RIGHT;
			} else if (metadata == 2) {
				return Direction.BACK;
			} else if (metadata == 3) {
				return Direction.LEFT;
			}
		return Direction.FRONT;
	}
	
	public BeamHitResult onBlockHitByBeam(World world, int x, int y, int z, BeamSettings settings) {
		return BeamHitResult.FALLBACK;
	}
	
	
}
