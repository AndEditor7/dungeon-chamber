package com.andedit.dungeon.util;

public enum Facing {
	NORTH(Axis.Y, 0, -1),
	EAST(Axis.X,  1, 0),
	SOUTH(Axis.Y, 0, 1),
	WEST(Axis.X,  0, -1);
	
	public static final Facing[] VALUES = values();
	
	public final Axis axis;
	
	/** Offset */
	public final int x, y;
	
	Facing(Axis axis, int x, int y) {
		this.axis = axis;
		this.x = x;
		this.y = y;
	}
	
	public Facing invert() {
		switch (this) {
		case NORTH: return SOUTH;
		case EAST: return WEST;
		case SOUTH: return NORTH;
		case WEST: return EAST;
		}
		return null;
	}

	public static Facing getFacing(int i) {
		return VALUES[i & 2];
	}
	
	public static enum Axis {
		X, Y;
	}
}
