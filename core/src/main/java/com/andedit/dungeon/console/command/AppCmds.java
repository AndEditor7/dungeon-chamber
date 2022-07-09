package com.andedit.dungeon.console.command;

import static com.badlogic.gdx.Gdx.graphics;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;

public class AppCmds extends ConsoleCmds {
	//@ConsoleDoc(description = "Enable/Disable display vsynching.")
	public void vSync(boolean enable) {
		graphics.setVSync(enable);
	}
	
	//@ConsoleDoc(description = "Sets the target framerate for the game.", paramDescriptions = {"fps"})
	public void fps(int fps) {
		graphics.setForegroundFPS(fps);
	}
	
	//@ConsoleDoc(description = "Toggle fullscreen mode.")
	public void tFullscreen() {
		if (graphics.isFullscreen()) {
			graphics.setWindowedMode(640, 480);
		} else {
			graphics.setFullscreenMode(graphics.getDisplayMode());
		}
	}
	
	public void exitApp() {
		Gdx.app.exit();
	}
}
