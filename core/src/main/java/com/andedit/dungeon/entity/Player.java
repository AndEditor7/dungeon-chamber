package com.andedit.dungeon.entity;

import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.handle.Inputs;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	
	private final Camera camera;
	
	public Player(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	public void update() {
		if (Inputs.isKeyPressed(Keys.LEFT)) {
			camera.yaw += 2;
		} if (Inputs.isKeyPressed(Keys.RIGHT)) {
			camera.yaw -= 2;
		}
		
		vel.setZero();
		float angle = camera.yaw;
		if (Inputs.isKeyPressed(Keys.W)) {
			vel.add(MathUtils.cosDeg(angle+90f), MathUtils.sinDeg(angle+90f));
		} if (Inputs.isKeyPressed(Keys.S)) {
			vel.sub(MathUtils.cosDeg(angle+90f), MathUtils.sinDeg(angle+90f));
		} if (Inputs.isKeyPressed(Keys.D)) {
			vel.add(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
		} if (Inputs.isKeyPressed(Keys.A)) {
			vel.sub(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
		}
		vel.nor().scl(0.05f);
	}
	
	@Override
	public void move() {
		super.move();
		Vector2 pos = getPos();
		camera.position.set(pos.x, pos.y, 0.6f);
	}
}
