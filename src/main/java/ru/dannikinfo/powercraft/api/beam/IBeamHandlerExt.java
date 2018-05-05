package ru.dannikinfo.powercraft.api.beam;

import ru.dannikinfo.powercraft.api.utils.VecI;

public interface IBeamHandlerExt extends IBeamHandler {
	
	public boolean onEmptyBlockHit(BeamTracer beamTracer, VecI coord);
	
}

