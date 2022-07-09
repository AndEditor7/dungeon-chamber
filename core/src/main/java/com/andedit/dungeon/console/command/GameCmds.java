package com.andedit.dungeon.console.command;

import com.andedit.dungeon.TheGame;
import com.andedit.dungeon.entity.Player;

public class GameCmds extends MainCmds  {
	
	private final TheGame game;
	
	public GameCmds(TheGame game) {
		this.game = game;
	}
	
	//@ConsoleDoc(description = "Sets the camrea's fov.")
	public void fov(float fov) {
		game.camera.fieldOfView = fov;
	}
	
	//@ConsoleDoc(description = "Sets the game's speed.")
	public void speed(float speed) {
		game.speed = speed;
	}
	
	//@ConsoleDoc(description = "Toggle player's collision cilping.")
	public void noClip() {
		player().collision = !player().collision;
	}
	
	private Player player() {
		return game.player;
	}
}
