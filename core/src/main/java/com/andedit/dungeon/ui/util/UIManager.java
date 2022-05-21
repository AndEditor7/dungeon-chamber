package com.andedit.dungeon.ui.util;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.input.ControlHolder;
import com.andedit.dungeon.input.InputHolder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;

public final class UIManager {
	public final InputHolder input;
	public final ControlHolder control;
	
	final ObjectMap<Class<? extends UI>, UI> map;
	final Array<UI> list;
	@Null UI current;

	public UIManager() {
		map = new ObjectMap<>();
		list = new Array<>(32);
		input = new InputHolder();
		control = new ControlHolder();
	}

	public void put(UI ui) {
		if (ui == null) throw new IllegalArgumentException("UI cannot be null.");
		map.put(ui.getClass(), ui);
		list.add(ui);
		ui.setVisible(false);
	}

	public void bind(Stage stage) {
		for (UI ui : list) ui.bind(stage);
	}

	@SuppressWarnings("unchecked")
	public <T extends UI> T setUI(Class<T> clazz) {
		final UI ui = map.get(clazz);
		if (ui == null) throw new IllegalArgumentException("Invailed class: " + clazz.getName());

		if (current != null) current.setVisible(false);
		ui.setVisible(true);
		current = ui;
		if (ui.isInputLock())
			main.addInputLock("ui");
		else main.removeInputLock("ui");
		input.set(ui.getInput());
		control.set(ui.getControl());
		return (T) ui;
	}

	@SuppressWarnings("unchecked")
	public <T extends UI> T getUI(Class<T> clazz) {
		return (T) map.get(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T extends UI> T getCurrentUI() {
		return (T) current;
	}
	
	public boolean hasUI(Class<? extends UI> clazz) {
		return current != null ? current.getClass() == clazz : false;
	}
	
	public void setVisible(boolean isVisible) {
		if (current != null) {
			current.setVisible(isVisible);
		}
	}
	
	public void update() {
		if (current != null) {
			current.update();
		}
	}
	
	public void clear() {
		map.clear();
		list.clear();
		current = null;
	}
}
