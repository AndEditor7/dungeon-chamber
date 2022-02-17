package com.andedit.dungeon.tile.model;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.graphic.SubDivider;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.util.TilePos;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FloorModel implements Model {
	
	private final TextureRegion floor, ceil;
	
	public FloorModel(int xIndex, int yIndex) {
		this(xIndex, yIndex, xIndex, yIndex);
	}
	
	public FloorModel(int xFloorIndex, int yFloorIndex, int xCeilIndex, int yCeilIndex) {
		floor = Assets.getTileReg(xFloorIndex, yFloorIndex);
		ceil  = Assets.getTileReg(xCeilIndex, yCeilIndex);
	}
	
	@Override
	public void build(MeshBuilder consumer, Level level, TilePos pos, Tile tile) {
		final int x = pos.x, y = pos.y;
		final Color color = level.colors.get(tile);
		consumer.setRegion(floor);
		consumer.setColor(color);
		SubDivider divider = consumer.divider;
				
		divider.vert1(0+x, 1+y, 0);
		divider.vert2(0+x, 0+y, 0);
		divider.vert3(1+x, 0+y, 0);
		divider.vert4(1+x, 1+y, 0);
		divider.build();
		
		consumer.setColor(Util.getShade(color, 0.9f));
		consumer.setRegion(ceil);
		divider.vert1(0+x, 0+y, 1);
		divider.vert2(0+x, 1+y, 1);
		divider.vert3(1+x, 1+y, 1);
		divider.vert4(1+x, 0+y, 1);
		divider.build();
	}
}
