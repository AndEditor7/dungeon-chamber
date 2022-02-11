package com.andedit.dungeon.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public final class TilePos {
	public int x, y;
	
	public TilePos() {
	}
	
	public TilePos(int x, int y) {
		set(x, y);
	}
	
	public TilePos(TilePos pos) {
		set(pos);
	}
	
	public TilePos(GridPoint2 pos) {
		set(pos);
	}

	public GridPoint2 toGrid() {
		return new GridPoint2(x, y);
	}
	
	public TilePos set(GridPoint2 pos) {
		return set(pos.x, pos.y);
	}
	
	public TilePos set(TilePos pos) {
		return set(pos.x, pos.y);
	}
	
	public TilePos set(Vector2 pos) {
		return set(MathUtils.floor(pos.x), MathUtils.floor(pos.y));
	}
	
	public TilePos offset(Facing face) {
		return offset(face, 1);
	}
	
	public TilePos offset(Facing face, int scl) {
		return new TilePos(x + (face.x * scl) , y + (face.y * scl));
	}
	
	public TilePos set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public TilePos add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public TilePos sub(int x, int y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	@Override
	public int hashCode() {
		return x + y * 51;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (obj.getClass() == getClass()) {
			TilePos pos = (TilePos) obj;
			return x == pos.x && y == pos.y;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public TilePos clone() {
		return new TilePos(this);
	}
}
