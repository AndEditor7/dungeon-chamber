package com.andedit.dungeon.ui.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class BaseUI implements UI {

	protected final Array<Actor> actors = new Array<>();
	protected Stage stage;
	
	private boolean visible;

	@Override
	public void bind(Stage stage) {
		actors.forEach(stage::addActor);
		this.stage = stage;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
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
	
	protected final boolean isVisible() {
		return visible;
	}
	
	protected void bind() {
		if (stage == null) return; 
		for (Actor actor : actors) {
			stage.addActor(actor);
			actor.setVisible(visible);
		}
	}
	
	protected void clear() {
		actors.forEach(Actor::remove);
		actors.clear();
	}

	protected void show() {
		
	}

	protected void hide() {

	}
}
