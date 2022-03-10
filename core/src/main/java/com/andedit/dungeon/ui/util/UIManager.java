package com.andedit.dungeon.ui.util;

import com.andedit.dungeon.util.InputHolder;
import com.andedit.dungeon.util.InputLock;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public final class UIManager {
	public final InputHolder input;
	
	final ObjectMap<Class<? extends UI>, UI> map;
	final Array<UI> list;
	boolean inputLock;
	UI currentUI;

	public UIManager() {
		map = new ObjectMap<>();
		list = new Array<>(32);
		input = new InputHolder();
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

		if (currentUI != null) currentUI.setVisible(false);
		ui.setVisible(true);
		currentUI = ui;
		InputProcessor processor = ui.getInput();
		inputLock = ui.isInputLock();
		input.set(inputLock ? InputLock.of(processor) : processor);
		return (T) ui;
	}

	@SuppressWarnings("unchecked")
	public <T extends UI> T getUI(Class<T> clazz) {
		return (T) map.get(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T extends UI> T getCurrentUI() {
		return (T) currentUI;
	}
	
	public boolean hasUI(Class<? extends UI> clazz) {
		return currentUI != null ? currentUI.getClass() == clazz : false;
	}
	
	public void setVisible(boolean isVisible) {
		if (currentUI != null) {
			currentUI.setVisible(isVisible);
		}
	}
	
	public void update() {
		if (currentUI != null) {
			currentUI.update();
		}
	}
	
	public void clear() {
		map.clear();
		list.clear();
		currentUI = null;
	}

	public boolean isInputLock() {
		return inputLock;
	}
}
