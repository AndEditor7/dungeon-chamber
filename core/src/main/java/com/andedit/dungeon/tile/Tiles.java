package com.andedit.dungeon.tile;

import com.badlogic.gdx.math.GridPoint2;

public class Tiles {
	private static int ID;
	private static final Tile[] TILES = new Tile[16];
	
	public static final Tile FLOOR = new FloorTile(new GridPoint2(2, 0));
	public static final Tile WALL = new WallTile(new GridPoint2(0, 0));
	
	static void add(Tile tile) {
		TILES[tile.id = ID++] = tile;
	}
	
	public static Tile get(int id) {
		return TILES[id];
	}
}
