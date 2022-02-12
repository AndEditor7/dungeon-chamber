package com.andedit.dungeon.tile;

import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.util.TilePos;

public class FloorTile extends Tile {
	@Override
	public boolean isOpaque(Level level, TilePos pos) {
		return false;
	}
}
