package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.handle.KeyListener;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class TheMenu extends ScreenAdapter {
	public TheMenu() {
		
	}
	
	@Override
	public void show() {
		main.inputs.addProcessor(new KeyListener(Keys.ESCAPE, () -> Gdx.app.exit()));
	}
	
	@Override
	public void render(float delta) {
		
	}
}
