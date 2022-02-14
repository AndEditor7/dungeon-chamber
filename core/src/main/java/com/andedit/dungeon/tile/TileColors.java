package com.andedit.dungeon.tile;

import com.badlogic.gdx.graphics.Color;

public class TileColors {
	private final Color[] colors = new Color[Tiles.INIT_SIZE];
	
	public void addColor(Tile tile, Color color) {
		colors[tile.getId()] = color;
	}
	
	public void addColor(Tile tile, int r, int g, int b) {
		colors[tile.getId()] = new Color(r/255f, g/255f, b/255f, 1);
	}
	
	public Color get(Tile tile) {
		return colors[tile.getId()];
	}
}
