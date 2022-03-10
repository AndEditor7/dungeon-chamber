package com.andedit.dungeon.handle.controller;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Disposable;

public class ControllerManager implements Disposable {
	
	public final Controller input;
	public final Controller game = new GameController();
	public Controller current;
	
	private InputMultiplexer inputs;
	
	{
		input = current = Util.isDesktop() ? new DesktopController() : new TouchController();
	}
	
	public void init(InputMultiplexer inputs) {
		inputs.addProcessor(input.getInput());
		Controllers.addListener(game.getController());
		this.inputs = inputs;
	}
	
	public void update() {
		if (input.isUse()) {
			set(input);
		}
		if (game.isUse()) {
			set(game);
		}
	}
	
	private void set(Controller controller) {
		if (controller == current) {
			return;
		}
		current.reset();
		current = controller;
	}
	
	public void reset() {
		input.reset();
		game.reset();
	}
	
	public void clear() {
		input.clear();
		game.clear();
	}

	@Override
	public void dispose() {
		Controllers.clearListeners();
		inputs.removeProcessor(inputs);
	}
}
