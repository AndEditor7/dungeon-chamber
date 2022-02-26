package com.andedit.dungeon.handle;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;

public class DesktopController extends InputAdapter implements Controller {
	
	private final Vector2 move = new Vector2();
	private final IntArray keysPressed = new IntArray(false, 16);
	private int lastX, yawDelta;
	
	@Override
	public boolean keyDown (int keycode) {
		if (!keysPressed.contains(keycode)) {
			keysPressed.add(keycode);
		}
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		keysPressed.removeValue(keycode);
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		yawDelta = lastX - screenX;
		lastX = screenX;
		return false;
	}

	@Override
	public Vector2 getMove() {
		move.setZero();
		if (keysPressed.contains(Keys.W)) {
			move.y += 1;
		} if (keysPressed.contains(Keys.S)) {
			move.y -= 1;
		} if (keysPressed.contains(Keys.D)) {
			move.x += 1;
		} if (keysPressed.contains(Keys.A)) {
			move.x -= 1;
		}
		return move.nor();
	}

	@Override
	public float getLookYaw() {
		float var = yawDelta;
		yawDelta = 0;
		return var;
	}

	@Override
	public InputProcessor getProcessor() {
		return this;
	}

	@Override
	public void reset() {
		keysPressed.clear();
	}
}
