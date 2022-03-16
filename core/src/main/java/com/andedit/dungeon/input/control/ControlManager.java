package com.andedit.dungeon.input.control;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.utils.Disposable;

public class ControlManager implements Disposable {
	
	public final Control input;
	public final Control game = new GameControl();
	public Control current;
	
	{
		input = current = Util.isDesktop() ? new DesktopControl() : new TouchControl();
	}
	
	public void init() {
		main.inputs.addProcessor(input.getInput());
		main.controls.addProcessor(game.getController());
	}
	
	public void update() {
		if (input.isUse()) {
			set(input);
		}
		if (game.isUse()) {
			set(game);
		}
	}
	
	private void set(Control controller) {
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
		main.inputs.removeProcessor(input.getInput());
		main.controls.removeProcessor(game.getController());
	}
}
