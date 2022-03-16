package com.andedit.dungeon.graphic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

class FixedCamera extends OrthographicCamera {
	@Override
	public void setToOrtho(boolean yDown) {
		setToOrtho(yDown, FBO.WIDTH, FBO.HEIGHT);
	}

	@Override
	public Vector3 unproject(Vector3 screenCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
		float x = screenCoords.x, y = screenCoords.y;
		x = x - viewportX;
		y = FBO.HEIGHT - y;
		y = y - viewportY;
		screenCoords.x = (2 * x) / viewportWidth - 1;
		screenCoords.y = (2 * y) / viewportHeight - 1;
		screenCoords.z = 2 * screenCoords.z - 1;
		screenCoords.prj(invProjectionView);
		return screenCoords;
	}

	@Override
	public Vector3 unproject(Vector3 screenCoords) {
		unproject(screenCoords, 0, 0, FBO.WIDTH, FBO.HEIGHT);
		return screenCoords;
	}

	@Override
	public Vector3 project(Vector3 worldCoords) {
		project(worldCoords, 0, 0, FBO.WIDTH, FBO.HEIGHT);
		return worldCoords;
	}

	@Override
	public Ray getPickRay(float screenX, float screenY) {
		return getPickRay(screenX, screenY, 0, 0, FBO.WIDTH, FBO.HEIGHT);
	}
}
