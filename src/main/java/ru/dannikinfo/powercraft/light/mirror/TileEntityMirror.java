package ru.dannikinfo.powercraft.light.mirror;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMirror extends TileEntity {
	
	private int mirrorColor=-1;
	
	public void setMirrorColor(int mirrorColor) {
		if(this.mirrorColor != mirrorColor){
			this.mirrorColor = mirrorColor;

		}
	}

	public int getMirrorColor() {
		return mirrorColor;
	}
}
