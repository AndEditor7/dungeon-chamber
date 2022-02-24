package com.andedit.dungeon.entity;

import static com.badlogic.gdx.math.MathUtils.floor;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.Lights;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.handle.ObjHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Atomic extends Entity {
	
	private static final TextureRegion REGION = Assets.getTileReg(0, 14);
	private static final float off = 1.5f;
	
	private static final Color RED = new Color(1, 0.1f, 0.1f, 1);
	private static final Color GREEN = new Color(0.05f, 0.85f, 0.05f, 1);
	private static final Color BLUE = new Color(0.1f, 0.1f, 1.0f, 1);
	
	private float angle;
	
	public Atomic(ObjHandler obj) {
		super(obj);
	}
	
	@Override
	public void update() {
		angle += 1.5f;
		angle %= 360f;
	}
	
	@Override
	public void render(Camera camera, MeshBuilder consumer) {
		float offset = 360f / 3f;
		float direct = angle;
		float x, y;
		consumer.setRegion(REGION);
		Vector2 pos = getPos();
		float off = Atomic.off + 0.01f;
		
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
		Vector2 p = getPos().add(x, y);
		if (!level.getTile(floor(p.x), floor(p.y)).isOpaque())
		lights.add(p, RED, 5.5f, 0.5f, 1.3f);
		
		direct += offset;
		x = (MathUtils.sinDeg(direct)*off);
		y = (MathUtils.cosDeg(direct)*off);
		getPos().add(x, y);
		if (!level.getTile(floor(p.x), floor(p.y)).isOpaque())
		lights.add(p, GREEN, 5f, 0.5f, 1.3f);
		
		direct += offset;
		x = (MathUtils.sinDeg(direct)*off);
		y = (MathUtils.cosDeg(direct)*off);
		getPos().add(x, y);
		lights.add(p, BLUE, 5f, 0.5f, 1.3f);
	}
}
