package com.andedit.dungeon.tile.entity;

import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.util.Clone;
import com.andedit.dungeon.util.TilePos;
import com.badlogic.gdx.math.Vector3;

public class TileEntity {
	public final TilePos pos;
	public final Level level;
	public final Tile tile;
	
	public TileEntity(Level level, @Clone TilePos pos) {
		this.level = level;
		this.pos = pos;
		tile = level.getTile(pos);
	}
	
	public void update() {
		
	}
	
	public void render(Camera camera, MeshBuilder consumer) {
		
	}
	
	public boolean isValid() {
		return tile == level.getTile(pos);
	}
	
	public boolean match(TilePos pos) {
		return this.pos.match(pos);
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TileEntity) {
			return match(((TileEntity)obj).pos);
		}
		
		return false;
	}

	public float dst(Camera camera) {
		Vector3 vec = camera.position;
		final float a = (pos.x + 0.5f) - vec.x;
		final float b = (pos.y + 0.5f) - vec.y;
		return a * a + b * b;
	}
}
