package com.andedit.dungeon.ui.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class BaseUI implements UI {

	protected final Array<Actor> actors = new Array<>();

	@Override
	public void bind(Stage stage) {
		actors.forEach(stage::addActor);
	}

	@Override
	public void setVisible(boolean visible) {
		for (Actor actor : actors)
			actor.setVisible(visible);
		
		if (visible) {
			show();
		} else {
			hide();
		}
	}

	protected final void add(Actor actor) {
		actors.add(actor);
	}

	protected void show() {
		
	}

	protected void hide() {

	}
}
