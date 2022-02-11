package com.andedit.dungeon.tile;

import com.andedit.dungeon.entity.Entity;
import com.andedit.dungeon.util.TilePos;
import com.andedit.dungeon.util.math.CollisionBox;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Tile {	
	protected static final Rectangle BOX = new Rectangle(0, 0, 1, 1);
	
	/** Texture index */
	public final GridPoint2 index;
	
	int id;
	
	/** @param index of texture x and y coordinates. */
	Tile(GridPoint2 index) {
		Tiles.add(this);
		this.index = index;
	}
	
	public boolean hasCollision() {
		return isWall();
	}
	
	public boolean isWall() {
		return false;
	}
	
	public void onCollide(Entity entity) {
		
	}
	
	public void onWalk(Entity entity) {
		
	}
	
	public Rectangle getBox() {
		return BOX;
	}
	
	public int getId() {
		return id;
	}

	public void addCollisions(TilePos pos, Array<CollisionBox> boxes, Pool<CollisionBox> pool) {
		if (hasCollision()) {
			Rectangle box = getBox();
			boxes.add(pool.obtain().set(box.x+pos.x, box.y+pos.y, box.x+box.width+pos.x, box.y+box.height+pos.y));
		}
	}
}
