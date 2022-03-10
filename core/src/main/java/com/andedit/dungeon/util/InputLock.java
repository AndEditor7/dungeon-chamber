package com.andedit.dungeon.util;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class InputLock implements InputProcessor {
	public static final InputLock INSTANCE = new InputLock();
	
	public static InputProcessor of(InputProcessor processor) {
		return new InputMultiplexer(processor, INSTANCE);
	}

	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return true;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return true;
	}

}
