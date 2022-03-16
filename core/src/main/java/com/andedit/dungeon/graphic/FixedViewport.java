package com.andedit.dungeon.graphic;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

class FixedViewport extends ScreenViewport {

	private final Vector3 tmp = new Vector3();

	public FixedViewport() {
		super(new FixedCamera());
	}

	@Override
	public Vector2 toScreenCoordinates(Vector2 worldCoords, Matrix4 transformMatrix) {
		tmp.set(worldCoords.x, worldCoords.y, 0);
		tmp.mul(transformMatrix);
		getCamera().project(tmp, getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
		tmp.y = FBO.HEIGHT - tmp.y;
		worldCoords.x = tmp.x;
		worldCoords.y = tmp.y;
		return worldCoords;
	}
}
