package com.andedit.dungeon.tile;

public class Tiles {
	public static final int INIT_SIZE = 16;
	
	private static int ID;
	private static final Tile[] TILES = new Tile[INIT_SIZE];
	
	public static final Tile FLOOR = new FloorTile();
	public static final Tile WALL = new WallTile();
	
	static void add(Tile tile) {
		TILES[tile.id = ID++] = tile;
	}
	
	public static Tile get(int id) {
		return TILES[id];
	}
}
