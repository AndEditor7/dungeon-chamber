package com.andedit.dungeon.ui.util;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface UI {
	void bind(Stage stage);
	void setVisible(boolean visible);
	default void update() {}
}
