package ru.dannikinfo.powercraft.api.beam;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import ru.dannikinfo.powercraft.api.utils.VecI;

public interface IBeamHandler {
	
	public boolean onBlockHit(BeamTracer beamTracer, Block block, VecI coord);
	
	public boolean onEntityHit(BeamTracer beamTracer, Entity entity, VecI coord);
	
}