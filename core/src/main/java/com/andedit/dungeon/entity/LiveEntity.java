package com.andedit.dungeon.entity;

public class LiveEntity extends Entity {
	public int health;
	
	public void hit(Entity entity, int hit) {
		damage(hit);
	}
	
	public void damage(int hit) {
		health = Math.max(health-hit, 0);
		if (health == 0) {
			killed();
		}
	}
	
	public void killed() {
		
	}
}
