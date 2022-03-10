package com.andedit.dungeon.ui;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.TheGame;
import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.handle.KeyListener;
import com.andedit.dungeon.ui.util.BaseUI;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class OptionUI extends BaseUI {
	
	private final TheGame game;
	private Image background;
	
	public OptionUI(TheGame game) {
		this.game = game;
		create();
	}
	
	public void create() {
		clear();
		
		background = new Image(Assets.BLANK);
		background.setColor(0, 0, 0, 0.7f);
		background.setSize(FBO.WIDTH, FBO.HEIGHT);
		add(background);
		
		bind();
	}
	
	@Override
	public boolean isInputLock() {
		return true;
	}
	
	@Override
	public InputProcessor getInput() {
		return new KeyListener(Keys.ESCAPE, () -> game.setUI(GameUI.class));
	}
}
