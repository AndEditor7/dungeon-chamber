package com.andedit.dungeon;

import com.andedit.dungeon.console.AndConsole;
import com.andedit.dungeon.console.command.MainCmds;
import com.andedit.dungeon.graphic.StageUI;
import com.andedit.dungeon.input.ControlMultiplexer;
import com.andedit.dungeon.input.control.Inputs;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ObjectSet;
import com.strongjoshua.console.CommandExecutor;

abstract class Base extends Game {

	public StageUI stage;
	public AndConsole console;
	public final InputMultiplexer inputs;
	public final ControlMultiplexer controls;
	public TheMenu menu;
	
	protected Screen newScreen;
	protected ObjectSet<String> inputLocks;
	
	private boolean isCatched;
	
	{
		inputs = new InputMultiplexer();
		controls = new ControlMultiplexer();
		inputLocks = new ObjectSet<>();
	}

	@Override
	public void render() {
		nextScreen();
		super.render();
		Gdx.gl.glUseProgram(0);
		
		if (console != null) {
			if (Gdx.input.isKeyJustPressed(Keys.GRAVE))
				showConsole(!isConsoleShowing());
			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
				showConsole(false);
		}
		
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
		
		if (screen instanceof TheMenu) {
			if (menu != null) {
				menu.dispose();
			}
			menu = (TheMenu) screen;
		}
		
		screen = newScreen;
		newScreen = null;

		stage.clear(); // Always clear UI when switching screen.
		inputs.clear(); // Always clear the input processors.
		controls.clear();
		setCatched(false);
		inputLocks.clear();

		
		if (console != null) {
			console.setVisible(false);
			console.setCommandExecutor(new MainCmds());
			stage.addActor(console.field);
		}
		
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
	
	public void addInputLock(String key) {
		inputLocks.add(key);
	}
	
	public void removeInputLock(String key) {
		inputLocks.remove(key);
	}
	
	public boolean isInputLock() {
		return inputLocks.notEmpty();
	}
	
	protected void loadConsole() {
		console = new AndConsole();
		stage.overlap = console.field;
	}
	
	public void showConsole(boolean show) {
		console.setVisible(show);
		if (show) {
			addInputLock("console");
		} else {
			removeInputLock("console");
		}
	}
	
	public boolean isConsoleShowing() {
		return console.isVisible();
	}
	
	public void setCommand(CommandExecutor gameCmds) {
		console.setCommandExecutor(gameCmds);
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
		
		if (console != null) {
			console.dispose();
			console = null;
		}
	}
}
