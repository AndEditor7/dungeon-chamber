package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.handle.KeyListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;

public class TheGame extends ScreenAdapter {
	@Override
	public void show() {
		main.inputs.addProcessor(new KeyListener(Keys.ESCAPE, () -> Gdx.app.exit()));
	}
}
