package com.andedit.dungeon.tile.model;

import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.util.Mod;
import com.andedit.dungeon.util.TilePos;

@FunctionalInterface
public interface Model {
	void build(MeshBuilder consumer, Level level, @Mod TilePos pos, Tile tile);
}
