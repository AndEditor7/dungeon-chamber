package com.andedit.dungeon;

import static com.badlogic.gdx.math.MathUtils.round;

import com.andedit.dungeon.handle.Inputs;
import com.andedit.dungeon.ui.util.StageUtils;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

abstract class Base extends Game {

	public Stage stage;
	public ScreenViewport view;
	public final InputMultiplexer inputs;
	public int guiOff;
	protected Screen newScreen;
	
	{
		inputs = new InputMultiplexer();
	}

	@Override
	public void render() {
		nextScreen();
		super.render();
		Gdx.gl.glUseProgram(0); // Performance fix for Nvidia GPU.
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

		screen.show();
		screen.resize(view.getScreenWidth(), view.getScreenHeight());
		resize();
	}
	
	/** Scale the UI. */
	public void scale(int scale) {
		guiOff = scale;
		resize(round(Util.getW()), round(Util.getH()));
	}

	@Override
	public void resize(int width, int height) {
		Inputs.clear();
	}
	
	/** Request for UI re-position. */
	public void resize() {
		StageUtils.resize(stage);
	}

	@Override
	public void setScreen(Screen screen) {
		newScreen = screen;
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
