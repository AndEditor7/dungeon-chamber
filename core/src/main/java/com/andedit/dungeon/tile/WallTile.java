package com.andedit.dungeon.tile;

import com.badlogic.gdx.math.GridPoint2;

public class WallTile extends Tile {
	
	public WallTile(GridPoint2 index) {
		super(index);
	}
	
	@Override
	public boolean isWall() {
		return true;
	}
}
