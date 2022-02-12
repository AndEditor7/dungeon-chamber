package com.andedit.dungeon.tile.model;

import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.tile.Tiles;

public class Models {
	private static final Model[] MODELS = new Model[Tiles.INIT_SIZE]; 
	
	static {
		add(Tiles.FLOOR, new FloorModel(2, 0, 2, 1));
		add(Tiles.WALL, new WallModel(0, 0));
	}
	
	private static void add(Tile tile, Model model) {
		MODELS[tile.getId()] = model;
	}
	
	public static Model get(int id) {
		return MODELS[id];
	}
}
