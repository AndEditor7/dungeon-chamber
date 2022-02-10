package com.andedit.dungeon.tile;

import com.andedit.dungeon.entity.Entity;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class Tile {	
	protected static final Rectangle BOX = new Rectangle(0, 0, 1, 1);
	
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
			
}
