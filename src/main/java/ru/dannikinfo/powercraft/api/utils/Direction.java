package ru.dannikinfo.powercraft.api.utils;

import java.io.Serializable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import ru.dannikinfo.powercraft.api.utils.interfaces.INBT;

public class Direction  implements Serializable, INBT<Direction> {
		
		public static final long serialVersionUID = 1522073818686692234L;
		
		public static final Direction BACK = new Direction(0), RIGHT = new Direction(1), LEFT = new Direction(2), FRONT = new Direction(3),
				BOTTOM = new Direction(4), TOP = new Direction(5);
		
		public static final int[] dir2Side = {4, 5, 0, 3, 1, 2};
		public static final int[] side2Dir = {2, 4, 5, 3, 0, 1};
		public static final int[] side2XOffset = {0, -1, 1, 0, 0, 0};
		public static final int[] side2YOffset = {0, 0, 0, 0, -1, 1};
		public static final int[] side2ZOffset = {-1, 0, 0, 1, 0, 0};
		public static final int[] rrot = {1, 3, 0, 2, 4, 5};
		public static final int[] lrot = {2, 0, 3, 1, 4, 5};
		public static final int[] frot = {3, 2, 1, 0, 4, 5};
		public static final int[] mirror = {3, 2, 1, 0, 5, 4};
		public static final int[] playerDir2Side = {3, 1, 0, 2};
		public static final Direction[] side2PCDir = {BACK, RIGHT, LEFT, FRONT, BOTTOM, TOP};
		public static final String[] names = {"BACK", "RIGHT", "LEFT", "FRONT", "BOTTOM", "TOP"};
		
		private final int mcSide;
		
		public Direction() {
			mcSide = -1;
		}
		public static Direction getDirFromMeta(int meta){
			if(meta == 0) return Direction.BACK;
			if(meta == 1) return Direction.LEFT;
			if(meta == 2) return Direction.FRONT;
			if(meta == 3) return Direction.RIGHT;
			return Direction.FRONT;
		}

		public static Direction convertForgeDir(ForgeDirection fdir){
			if(fdir == ForgeDirection.SOUTH) return Direction.BACK;
			if(fdir == ForgeDirection.NORTH) return Direction.FRONT;
			if(fdir == ForgeDirection.EAST) return Direction.RIGHT;
			if(fdir == ForgeDirection.WEST) return Direction.LEFT;
			return null;
		}
		
		private Direction(int dir) {
			mcSide = dir;
		}
		
		public int getMCSide() {
			return mcSide;
		}
		
		public int getMCDir() {
			return side2Dir[mcSide];
		}
		
		public VecI getOffset() {
			return new VecI(side2XOffset[mcSide], side2YOffset[mcSide], side2ZOffset[mcSide]);
		}
		
		public static Direction getFromVec(VecI vec) {
		int max = vec.x;
		Direction side = Direction.LEFT;
		if (Math.abs(max) < Math.abs(vec.y)) {
			max = vec.y;
			side = Direction.TOP;
		}
		if (Math.abs(max) < Math.abs(vec.z)) {
			max = vec.z;
			side = Direction.FRONT;
		}
		if (max < 0) {
			side = side.mirror();
		}
		return side;
	}
		
	public Direction rotateRight() {
		return useTable(rrot);
	}
		
	public Direction rotateLeft() {
		return useTable(lrot);
	}
	
	public Direction rotateFull() {
		return useTable(frot);
	}
		
	public Direction mirror() {
		return useTable(mirror);
	}
	
	public static Direction getFromMCSide(int side) {
		return side2PCDir[side];
	}
	
	public static Direction getFromMCDir(int dir) {
		return getFromMCSide(dir2Side[dir]);
	}
	
	public static Direction getFromPlayerDir(int dir) {
		return getFromMCSide(playerDir2Side[dir]);
	}
	
	@Override
	public Direction readFromNBT(NBTTagCompound nbttag) {
		return Direction.getFromMCSide(nbttag.getInteger("dir"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttag) {
		nbttag.setInteger("dir", mcSide);
		return nbttag;
	}
		
	@Override
	public String toString() {
		return "Direction: " + getSideName();
	}
		
	public String getSideName() {
		return names[mcSide];
	}
		
	public Direction useTable(int[] table){
		return getFromMCSide(table[mcSide]);
	}
		
	public Direction rotate(Direction rotation) {
		switch (rotation.mcSide) {
			case 0:
				return rotateFull();
			case 1:
				return rotateRight();
			case 2:
				return rotateLeft();
		}
		return this;
	}
		
	public Direction rotateRev(Direction rotation) {
		switch (rotation.mcSide) {
			case 0:
				return rotateFull();
			case 1:
				return rotateLeft();
			case 2:
				return rotateRight();
		}
		return this;
	}
		
}

