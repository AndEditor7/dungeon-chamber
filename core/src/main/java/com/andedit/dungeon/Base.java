package com.andedit.dungeon;

import com.andedit.dungeon.graphic.StageUI;
import com.andedit.dungeon.input.ControlMultiplexer;
import com.andedit.dungeon.input.control.Inputs;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

abstract class Base extends Game {

	public StageUI stage;
	public final InputMultiplexer inputs;
	public final ControlMultiplexer controls;
	public int guiOff;
	public boolean inputLock;
	
	protected Screen newScreen;
	
	private boolean isCatched;
	
	{
		inputs = new InputMultiplexer();
		controls = new ControlMultiplexer();
	}

	@Override
	public void render() {
		nextScreen();
		super.render();
		Gdx.gl.glUseProgram(0);
		
		stage.act();
		stage.draw();
		Gdx.gl.glUseProgram(0);
		Inputs.reset();
	}

	protected void nextScreen() {
		if (newScreen == null)
			return;

		if (screen != null)
			screen.hide();
		
		screen = newScreen;
		newScreen = null;

		stage.clear(); // Always clear UI when switching screen.
		inputs.clear(); // Always clear the input processors.
		controls.clear();
		setCatched(false);
		inputLock = false;

		screen.show();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Inputs.clear();
	}

	@Override
	public void setScreen(Screen screen) {
		newScreen = screen;
	}
	
	public boolean isCatched() {
		return isCatched;
	}
	
	public void setCatched(boolean isCatched) {
		Gdx.input.setCursorCatched(isCatched);
		this.isCatched = isCatched;
	}
	
	public void setCursorPos(boolean centor) {
		if (centor) {
			Gdx.input.setCursorPosition(Util.getW()>>1, Util.getH()>>1);
			stage.setCrossPos(Main.WIDTH>>1, Main.HEIGHT>>1);
		} else {
			Gdx.input.setCursorPosition(0, 0);
		}
	}

	@Override
	public void dispose() {
		if (screen != null) {
			screen.dispose();
			screen = null;
		}
			
		if (newScreen != null) {
			newScreen.dispose();
			newScreen = null;
		}
	}
}
