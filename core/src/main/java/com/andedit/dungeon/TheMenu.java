package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.badlogic.gdx.ScreenAdapter;

public class TheMenu extends ScreenAdapter {
	
	
	
	public TheMenu() {
		
	}
	
	@Override
	public void show() {
		//main.inputs.addProcessor(new KeyListener(Keys.ESCAPE, Gdx.app::exit));
		main.setScreen(new TheGame());
	}
	
	@Override
	public void render(float delta) {
		
	}
}
