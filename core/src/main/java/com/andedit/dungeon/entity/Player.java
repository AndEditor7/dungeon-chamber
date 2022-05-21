package com.andedit.dungeon.entity;

import com.andedit.dungeon.TheGame;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.level.Level;
import com.badlogic.gdx.math.Vector2;

public class Player extends LiveEntity {
	
	private final Camera camera;
	private final TheGame game;
	
	public Player(Camera camera, TheGame game) {
		this.camera = camera;
		this.game = game;
		this.health = 50;
	}
	
	@Override
	public void update() {
		vel.set(game.getController().getMove()).rotateDeg(camera.yaw);
		vel.scl(0.05f);
	}
	
	@Override
	public void move() {
		super.move();
		Vector2 pos = getPos();
		float height = level.getTile(getTilePos()).getHeight(this, getTilePos());
		camera.position.set(pos.x, pos.y, height);
	}
	
	@Override
	public boolean isDead() {
		return false;
	}
	
	@Override
	public void setLevel(Level level) {
		if (this.level != null) {
			if (this.level == level) return; 
			this.level.player = null;
		}
		super.setLevel(level);
		level.player = this;
	}
}
