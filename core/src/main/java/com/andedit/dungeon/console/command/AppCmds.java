package com.andedit.dungeon.console.command;

import static com.badlogic.gdx.Gdx.graphics;

import com.andedit.dungeon.console.ConsoleGetter;
import com.andedit.dungeon.util.Util;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.annotation.ConsoleDoc;
import com.strongjoshua.console.annotation.HiddenCommand;

public class AppCmds extends CommandExecutor implements ConsoleGetter {
	@ConsoleDoc(description = "Enable/Disable display vsynching.")
	public void vSync(boolean enable) {
		graphics.setVSync(enable);
	}
	
	@ConsoleDoc(description = "Sets the target framerate for the game.", paramDescriptions = {"fps"})
	public void fps(int fps) {
		graphics.setForegroundFPS(fps);
	}
	
	@ConsoleDoc(description = "Toggle fullscreen mode.")
	public void tFullscreen() {
		if (!Util.isDesktop()) return; 
		if (graphics.isFullscreen()) {
			graphics.setWindowedMode(640, 480);
		} else {
			graphics.setFullscreenMode(graphics.getDisplayMode());
		}
	}
	
	@Override
	@HiddenCommand
	public Console getConsole() {
		return console;
	}
}
