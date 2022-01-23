package com.andedit.dungeon.handle;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class EscKey extends InputAdapter {
	
	private final Runnable runnable;
	
	public EscKey(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
			runnable.run();
			return true;
		}
		return false;
	}
}
