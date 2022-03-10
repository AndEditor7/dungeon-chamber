package com.andedit.dungeon.handle.controller;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class TouchController extends InputAdapter implements Controller {
	
	private int movePointer = -1;
	private int lookPointer = -1;
	
	private final Vector2 stratPos = new Vector2();
	private final Vector2 move = new Vector2();
	private int lastX, yawDelta;
	
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if (screenX < Util.getW() / 3) {
			movePointer = pointer;
			stratPos.set(screenX, screenY);
		} else {
			lookPointer = pointer;
			lastX = screenX;
		}
		return false;
	}

	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (movePointer == pointer) {
			movePointer = -1;
			move.setZero();
		} if (lookPointer == pointer) {
			lookPointer = -1;
		}
		return false;
	}

	public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (movePointer == pointer) {
			move.set(screenX, screenY).sub(stratPos);
			move.scl(0.02f);
			move.clamp(0, 1);
			move.y = -move.y;
		} if (lookPointer == pointer) {
			yawDelta = lastX - screenX;
			lastX = screenX;
		}
		return false;
	}
	
	@Override
	public Vector2 getMove() {
		return move;
	}

	@Override
	public float getLookYaw() {
		return yawDelta;
	}

	@Override
	public InputProcessor getInput() {
		return this;
	}
	
	@Override
	public boolean isUse() {
		return Util.isMobile() && Gdx.input.isTouched();
	}

	@Override
	public void reset() {
		movePointer = -1;
		lookPointer = -1;
		stratPos.setZero();
		move.setZero();
		clear();
	}
	
	@Override
	public void clear() {
		yawDelta = 0;
	}
}
