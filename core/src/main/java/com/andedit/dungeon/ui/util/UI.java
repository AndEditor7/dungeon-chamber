package com.andedit.dungeon.ui.util;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Null;

public interface UI {
	void bind(Stage stage);
	
	void setVisible(boolean visible);
	
	@Null
	default InputProcessor getInput() {
		return null;
	};
	
	default boolean isInputLock() {
		return false;
	}
	
	default void update() {
		
	}
}
