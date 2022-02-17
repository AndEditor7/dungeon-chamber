package com.andedit.dungeon.entity;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.graphic.PathTrace;
import com.andedit.dungeon.handle.ObjHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Goblin extends LiveEntity {
	private static final TextureRegion REGION1 = Assets.getTileReg(0, 15);
	private static final TextureRegion REGION2 = Assets.getTileReg(1, 15);
	
	private int tick;
	private boolean reg;
	
	public Goblin(ObjHandler obj) {
		super(obj);
	}
	
	@Override
	public void update() {
		if (++tick > 20) {
			tick = 0;
			reg = !reg;
		}
	}
	
	@Override
	public void render(Camera camera, MeshBuilder consumer) {
		consumer.setRegion(reg ? REGION1 : REGION2);
		consumer.setColor(Color.WHITE_FLOAT_BITS);
		
		Vector2 pos = getPos();
		consumer.setLight(PathTrace.trace(level, pos.x, pos.y));
		consumer.draw(camera, pos.x, pos.y, 0.5f);
	}
}
