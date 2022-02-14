package com.andedit.dungeon.tile.model;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.util.TilePos;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WallModel implements Model {
	
	private final TextureRegion region;
	
	public WallModel(int xIndex, int yIndex) {
		region = Assets.getTileReg(xIndex, yIndex);
	}

	@Override
	public void build(MeshBuilder consumer, Level level, TilePos pos, Tile tile) {
		final int x = pos.x, y = pos.y;
		final Color color = level.colors.get(tile);
		consumer.setRegion(region);
		consumer.setColor(color.toFloatBits());
		
		// north y-
		if (!level.getTile(pos.set(x, y-1)).isOpaque(level, pos)) {
			consumer.vert1(1+x, 0+y, 0);
			consumer.vert2(1+x, 0+y, 1);
			consumer.vert3(0+x, 0+y, 1);
			consumer.vert4(0+x, 0+y, 0);
		}
		
		// south y+
		if (!level.getTile(pos.set(x, y+1)).isOpaque(level, pos)) {
			consumer.vert1(0+x, 1+y, 0);
			consumer.vert2(0+x, 1+y, 1);
			consumer.vert3(1+x, 1+y, 1);
			consumer.vert4(1+x, 1+y, 0);
		}
		
		consumer.setColor(Util.getShade(color, 0.85f));		
		// east x+
		if (!level.getTile(pos.set(x+1, y)).isOpaque(level, pos)) {
			consumer.vert1(1+x, 1+y, 0);
			consumer.vert2(1+x, 1+y, 1);
			consumer.vert3(1+x, 0+y, 1);
			consumer.vert4(1+x, 0+y, 0);
		}
		
		// west x-
		if (!level.getTile(pos.set(x-1, y)).isOpaque(level, pos)) {
			consumer.vert1(0+x, 0+y, 0);
			consumer.vert2(0+x, 0+y, 1);
			consumer.vert3(0+x, 1+y, 1);
			consumer.vert4(0+x, 1+y, 0);
		}
	}
}
