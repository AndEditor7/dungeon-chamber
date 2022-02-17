package com.andedit.dungeon.entity;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.Lights;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.handle.ObjHandler;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Atomic extends Entity {
	
	private static final TextureRegion REGION = Assets.getTileReg(0, 14);
	private static final Vector3 RED = new Vector3(1, 0.15f, 0.15f);
	private static final Vector3 GREEN = new Vector3(0.1f, 0.85f, 0.1f);
	private static final Vector3 BLUE = new Vector3(0.2f, 0.2f, 1.0f);
	private static final float off = 1.5f;
	
	private float angle;
	
	public Atomic(ObjHandler obj) {
		super(obj);
	}
	
	@Override
	public void update() {
		angle += 5f;
		angle %= 360f;
	}
	
	@Override
	public void render(Camera camera, MeshBuilder consumer) {
		float offset = 360f / 3f;
		float direct = angle;
		float x, y;
		consumer.setRegion(REGION);
		Vector2 pos = getPos();
		
		consumer.setColor(RED);
		x = (MathUtils.sinDeg(direct)*off) + pos.x;
		y = (MathUtils.cosDeg(direct)*off) + pos.y;
		consumer.draw(camera, x, y, 0.5f);
		
		direct += offset;
		consumer.setColor(GREEN);
		x = (MathUtils.sinDeg(direct)*off) + pos.x;
		y = (MathUtils.cosDeg(direct)*off) + pos.y;
		consumer.draw(camera, x, y, 0.5f);
		
		direct += offset;
		consumer.setColor(BLUE);
		x = (MathUtils.sinDeg(direct)*off) + pos.x;
		y = (MathUtils.cosDeg(direct)*off) + pos.y;
		consumer.draw(camera, x, y, 0.5f);
	}
	
	@Override
	public void addLights(Lights lights) {
		float offset = 360f / 3f;
		float direct = angle;
		float x, y;
		
		x = (MathUtils.sinDeg(direct)*off);
		y = (MathUtils.cosDeg(direct)*off);
		lights.add(getPos().add(x, y), RED, 2);
		
		direct += offset;
		x = (MathUtils.sinDeg(direct)*off);
		y = (MathUtils.cosDeg(direct)*off);
		lights.add(getPos().add(x, y), GREEN, 2);
		
		direct += offset;
		x = (MathUtils.sinDeg(direct)*off);
		y = (MathUtils.cosDeg(direct)*off);
		lights.add(getPos().add(x, y), BLUE, 2);
	}
}
