package com.andedit.dungeon.ui;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.TheGame;
import com.andedit.dungeon.handle.KeyListener;
import com.andedit.dungeon.ui.util.BaseUI;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameUI extends BaseUI {
	private final Label label;
	private final TheGame game;
	
	public GameUI(TheGame game) {
		this.game = game;
		label = new Label("FPS: 60", Assets.SKIN);
		label.setPosition(10, 20);
		add(label);
	}
	
	@Override
	public void update() {
		StringBuilder text = label.getText();
		text.clear();
		
		text.append("FPS: ");
		text.append(Gdx.graphics.getFramesPerSecond());
		text.append("\nMem: ");
		text.append(Util.getMb()).append("mb");
		
		label.invalidateHierarchy();
	}
	
	@Override
	public InputProcessor getInput() {
		return new KeyListener(Keys.ESCAPE, () -> game.setUI(OptionUI.class));
	}
}
