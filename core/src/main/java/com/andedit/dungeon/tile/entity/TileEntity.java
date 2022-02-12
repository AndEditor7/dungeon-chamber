package com.andedit.dungeon.tile.entity;

import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.util.TilePos;

public class TileEntity {
	public final TilePos pos;
	public final Level level;
	public final Tile tile;
	
	public TileEntity(Level level, TilePos pos) {
		this.level = level;
		this.pos = pos;
		tile = level.getTile(pos);
	}
	
	public void update() {
		
	}
	
	public void render(MeshBuilder consumer) {
		
	}
	
	public boolean isValid() {
		return tile == level.getTile(pos);
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TileEntity) {
			return pos.equals(((TileEntity)obj).pos);
		}
		
		return false;
	}
}
