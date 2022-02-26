package com.andedit.dungeon;

import com.andedit.dungeon.handle.Inputs;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

abstract class Base extends Game {

	public Stage stage;
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
