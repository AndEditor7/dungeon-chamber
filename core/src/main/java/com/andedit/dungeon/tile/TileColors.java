package com.andedit.dungeon.tile;

import com.badlogic.gdx.graphics.Color;

public class TileColors {
	private final float[] colors = new float[Tiles.INIT_SIZE];
	
	public void addColor(Tile tile, Color color) {
		colors[tile.getId()] = color.toFloatBits();
	}
	
	public void addColor(Tile tile, int r, int g, int b) {
		colors[tile.getId()] = Color.toFloatBits(r, g, b, 255);
	}
	
	public float get(Tile tile) {
		return colors[tile.getId()];
	}
}
