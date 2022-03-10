package com.andedit.dungeon.handle.controller;

import com.badlogic.gdx.math.Vector2;

public class NothingController implements Controller {
	public static final Controller INSTANCE = new NothingController();
	
	private static final Vector2 tmp = new Vector2();

	@Override
	public Vector2 getMove() {
		return tmp.setZero();
	}

	@Override
	public float getLookYaw() {
		return 0;
	}

	@Override
	public boolean isUse() {
		return false;
	}

	@Override
	public void reset() {

	}

	@Override
	public void clear() {

	}
}
