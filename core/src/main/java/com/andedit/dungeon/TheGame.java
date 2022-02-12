package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.entity.Player;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.handle.KeyListener;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.level.LevelRenderer;
import com.andedit.dungeon.tile.TileColors;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;

public class TheGame extends ScreenAdapter {
	
	private final Level level;
	private final LevelRenderer render = new LevelRenderer();
	private final Camera camera = new Camera();
	private final Player player = new Player(camera);
	private boolean dispose;
	
	public TheGame() {
		camera.viewportWidth  = FBO.WIDTH;
		camera.viewportHeight = FBO.HEIGHT;
		//camera.viewportWidth  = Util.getW();
		//camera.viewportHeight = Util.getH();
		camera.near = 0.2f;
		camera.far = 50;
		
		TileColors colors = new TileColors();
		colors.addColor(Tiles.FLOOR, 150, 150, 150);
		colors.addColor(Tiles.WALL,  115, 180, 230);
		level = new Level(Maps.get("test"), colors);
		player.setPos(0, 0);
		level.addEntity(player);
		
		render.setLevel(level);
	}
	
	@Override
	public void show() {
		main.inputs.addProcessor(new KeyListener(Keys.ESCAPE, () -> Gdx.app.exit()));
	}
	
	@Override
	public void render(float delta) {
		level.update();
		camera.updateRotation();
		camera.update();
		
		Util.glClear();
		render.render(camera);
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		if (dispose) return;
		render.dispose();
		dispose = true;
	}
}
