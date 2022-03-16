package com.andedit.dungeon.ui;

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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class OptionUI extends BaseUI {
	
	private final TheGame game;
	private Image background;
	
	public OptionUI(TheGame game) {
		this.game = game;
		create();
	}
	
	public void create() {
		clear();
		final float w = Main.WIDTH;
		final float h = Main.HEIGHT;
		
		background = new Image(Assets.BLANK);
		background.setColor(0, 0, 0, 0.7f);
		background.setSize(w, h);
		add(background);
		
		/*
		SimpleButt buttion = new SimpleButt(new TexRegDrawable(Assets.BLANK, Color.FIREBRICK));
		buttion.setSize(48, 40);
		buttion.setPosition(Main.WIDTH, 0, Align.bottomRight);
		buttion.addListener(Util.newListener(this::back));
		add(buttion); */
		
		// left background
		Image image = new Image(new NinePatch(Assets.SQUARE, new Color(0.1f, 0.1f, 0.1f, 1)));
		image.setSize(37, 58);
		image.setPosition(0, h, Align.topLeft);
		add(image);
		
		// right background
		image = new Image(new NinePatch(Assets.SQUARE, new Color(0.1f, 0.1f, 0.1f, 1)));
		image.setSize(37, 58);
		image.setPosition(w, h, Align.topRight);
		add(image);
		
		// center background
		image = new Image(new NinePatch(Assets.SQUARE, new Color(0.1f, 0.1f, 0.1f, 1)));
		image.setSize(w-72, 58);
		image.setPosition(36, h, Align.topLeft);
		add(image);
		
		Label label = new Label("Options", Assets.SKIN);
		label.setFontScale(3);
		label.pack();
		label.setPosition(w/2, h-16, Align.top);
		add(label);
		
		// left button
		TextureRegion region = new TextureRegion(Assets.GUI, 0, 71, 12, 24);
		region.flip(true, false);
		Drawable drawUp = new TexRegDrawable(region);
		Drawable drawDn = new TexRegDrawable(region, Color.GRAY);
		SimpleButt butt = new SimpleButt(drawUp, drawDn);
		Util.scale(butt, 2);
		butt.setPosition(6, h-5, Align.topLeft);
		add(butt);
		
		// right button
		region = new TextureRegion(region);
		region.flip(true, false);
		drawUp = new TexRegDrawable(region);
		drawDn = new TexRegDrawable(region, Color.GRAY);
		butt = new SimpleButt(drawUp, drawDn);
		Util.scale(butt, 2);
		butt.setPosition(w-6, h-5, Align.topRight);
		add(butt);
		
		bind();
	}
	
	@Override
	public boolean isInputLock() {
		return true;
	}
	
	@Override
	public InputProcessor getInput() {
		return new KeyListener(Keys.ESCAPE, this::back);
	}
	
	@Override
	public ControllerListener getControl() {
		return new ButtonListener(Codes.buttonStart, this::back);
	}
	
	private void back() {
		game.setUI(GameUI.class);
	}
}
