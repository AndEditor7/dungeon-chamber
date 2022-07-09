package com.andedit.dungeon.console.command;

import static com.andedit.dungeon.Main.main;

import com.andedit.console.log.LogStatus;
import com.andedit.dungeon.graphic.FBO.ColorRes;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MainCmds extends AppCmds {
	
	//@ConsoleDoc(description = "Restarts the current screen.")
	public void restart() {
		Gdx.app.postRunnable(() -> {
			Class<? extends Screen> type = main.getScreen().getClass();
			try {
				main.getScreen().dispose();
				main.setScreen(type.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	//@ConsoleDoc(description = "Toggle debug mode.")
	public void tDebug() {
		
	}
	
	//@ConsoleDoc(description = "Toggle gui debug renderer.")
	public void tDebugGui() {
		main.stage.setDebugAll(!main.stage.isDebugAll());
	}
	
	//@ConsoleDoc(description = "Sets FBO color resolution.")
	public void fboColorRes(int num) {
		if (Util.isDesktop()) return; 
		ColorRes res = ColorRes.getColorRes(num);
		if (res == null) {
			context.log("Invaild resolution color.", LogStatus.ERROR);
			return;
		}
		main.frame.setColorRes(res);
	}
}
