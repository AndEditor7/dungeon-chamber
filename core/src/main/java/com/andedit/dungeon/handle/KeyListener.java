package com.andedit.dungeon.handle;

import com.badlogic.gdx.InputAdapter;

public class KeyListener extends InputAdapter {

	private final int key;
	private final Runnable runnable;

	public KeyListener(int key, Runnable runnable) {
		this.key = key;
		this.runnable = runnable;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == key) {
			runnable.run();
			return true;
		}
		return false;
	}
}
