package com.andedit.dungeon;

import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.graphic.FastBatch;
import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.andedit.dungeon.handle.KeyListener;
import com.andedit.dungeon.handle.Inputs;
import com.andedit.dungeon.ui.util.AssetManager;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Base {
	public static final Main main = new Main();
	
	public FastBatch batch;
	public TheMenu menu;
	
	private FBO frame;
	private AssetManager asset;

	@Override
	public void create() {
		QuadIndexBuffer.init();
		frame = new FBO();
		stage = new Stage(view = new ScreenViewport(), batch = new FastBatch());
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, inputs, Inputs.input));
		asset = new AssetManager();
		setScreen(new Loading(asset));
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glCullFace(GL20.GL_BACK);
	}
	
	@Override
	public void render() {
		frame.begin();
		super.render();
		frame.end();
		
		Util.glClear();
		frame.draw();
	}
	
	void setMenu() {
		setScreen(menu = new TheMenu());
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		frame.resize(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		batch.dispose();
		asset.dispose();
		frame.dispose();
		Statics.dispose();
		QuadIndexBuffer.dispose();
	}
}