package com.andedit.dungeon.ui;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.Main;
import com.andedit.dungeon.TheGame;
import com.andedit.dungeon.input.ButtonListener;
import com.andedit.dungeon.input.Codes;
import com.andedit.dungeon.input.KeyListener;
import com.andedit.dungeon.ui.actor.SimpleButt;
import com.andedit.dungeon.ui.drawable.TexRegDrawable;
import com.andedit.dungeon.ui.util.BaseUI;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
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
		
		/*
		SimpleButt buttion = new SimpleButt(new TexRegDrawable(Assets.BLANK, Color.FIREBRICK));
		buttion.setSize(48, 40);
		buttion.setPosition(Main.WIDTH, 0, Align.bottomRight);
		buttion.addListener(Util.newListener(this::setMenu));
		add(buttion); */
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
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		main.setCatched(visible);
		main.setCursorPos(!visible);
	}
	
	@Override
	public InputProcessor getInput() {
		return new KeyListener(Keys.ESCAPE, this::setMenu);
	}
	
	@Override
	public ControllerListener getControl() {
		return new ButtonListener(Codes.buttonStart, this::setMenu);
	}
	
	private void setMenu() {
		game.setUI(OptionUI.class).create();
	}
}
