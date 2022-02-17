package com.andedit.dungeon.entity;

import com.andedit.dungeon.handle.ObjHandler;

public class LiveEntity extends Entity {
	public int health;
	
	@Deprecated
	LiveEntity() {
	}
	
	public LiveEntity(ObjHandler obj) {
		super(obj);
		health = obj.get("Health");
	}
	
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
