package com.andedit.dungeon.ui.util;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface UI {
	void bind(Stage stage);
	void setVisible(boolean visible);
	void resize(Viewport view);
}
