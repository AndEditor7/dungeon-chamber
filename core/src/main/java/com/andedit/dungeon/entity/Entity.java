package com.andedit.dungeon.entity;

import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.util.math.CollisionBox;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	public final CollisionBox box = new CollisionBox();
	public final Vector2 vel = new Vector2();
	public boolean onCollide;
	public Level level;
	
	protected boolean isDead;
	
	private final Vector2 pos = new Vector2();
	
	public void setPos(float x, float y) {
		float s = getSize() * 0.5f;
		box.set(x-s, y-s, x+s, y+s);
	}
	
	public Vector2 getPos() {
		return box.getCenter(pos);
	}
	
	public void update() {
		
	}
	
	public void move() {
		Collision.handle(this);
	}
	
	public float getSize() {
		return 0.6f;
	}
	
	public void remove() {
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}
}
