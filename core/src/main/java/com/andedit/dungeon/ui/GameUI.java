package com.andedit.dungeon.ui;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.TheGame;
import com.andedit.dungeon.ui.util.BaseUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameUI extends BaseUI {
	private final Label fps;
	
	public GameUI(TheGame game) {
		fps = new Label("FPS: 60", Assets.SKIN);
		fps.setPosition(10, 10);
	}
	
	@Override
	public void update() {
		fps.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
	}
}
