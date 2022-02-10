package com.andedit.dungeon.entity;

import com.badlogic.gdx.math.Vector2;

public class Entity {
	public final Vector2 pos = new Vector2();
	public final Vector2 vel = new Vector2();
	
	public void update() {
		
	}
	
	public boolean isDead() {
		return false;
	}
}
