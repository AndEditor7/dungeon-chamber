package com.andedit.dungeon.tile;

import com.andedit.dungeon.entity.Entity;
import com.andedit.dungeon.entity.Player;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.util.TilePos;
import com.andedit.dungeon.util.math.CollisionBox;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Tile {	
	protected static final Rectangle BOX = new Rectangle(0, 0, 1, 1);
	
	int id;
	
	Tile() {
		Tiles.add(this);
	}
	
	public boolean hasCollision(Level level, TilePos pos) {
		return isOpaque(level, pos);
	}
	
	public boolean isOpaque(Level level, TilePos pos) {
		return false;
	}
	
	public void onCollide(Entity entity, TilePos pos) {
		
	}
	
	public void onWalk(Entity entity, TilePos pos) {
		
	}
	
	public Rectangle getBox(Level level, TilePos pos) {
		return BOX;
	}
	
	/** Get eye height for player */
	public float getHeight(Player player, TilePos pos) {
		return 0.6f;
	}
	
	public int getId() {
		return id;
	}

	public void addCollisions(Level level, TilePos pos, Array<CollisionBox> boxes, Pool<CollisionBox> pool) {
		if (hasCollision(level, pos)) {
			Rectangle box = getBox(level, pos);
			boxes.add(pool.obtain().set(box.x+pos.x, box.y+pos.y, box.x+box.width+pos.x, box.y+box.height+pos.y));
		}
	}
}
