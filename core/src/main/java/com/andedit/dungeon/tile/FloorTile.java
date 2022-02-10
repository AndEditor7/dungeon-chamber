package com.andedit.dungeon.tile;

import com.badlogic.gdx.math.GridPoint2;

public class FloorTile extends Tile {
	
	public FloorTile(GridPoint2 index) {
		super(index);
	}
	
	@Override
	public boolean isWall() {
		return false;
	}
}
