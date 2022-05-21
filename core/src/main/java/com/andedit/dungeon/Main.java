package com.andedit.dungeon;

import com.andedit.dungeon.console.command.GameCmds;
import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.andedit.dungeon.graphic.StageUI;
import com.andedit.dungeon.input.ControlMultiplexer;
import com.andedit.dungeon.input.control.Inputs;
import com.andedit.dungeon.util.AssetManager;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.strongjoshua.console.CommandExecutor;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Base {
	public static final int WIDTH  = 320;
	public static final int HEIGHT = 240;
	
	public static final Main main = new Main();
	public static API api;
	
	public FBO frame;
	private AssetManager asset;

	@Override
	public void create() {
		QuadIndexBuffer.init();
		
		frame = new FBO();
		stage = new StageUI(frame);
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, inputs, Inputs.input));
		Controllers.addListener(new ControlMultiplexer(stage, controls));
		
		asset = new AssetManager();
		setScreen(new Loading(asset));
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glCullFace(GL20.GL_BACK);
		
//		GLVersion version = Gdx.graphics.getGLVersion();
//		System.out.println(version.getMajorVersion());
//		System.out.println(version.getMinorVersion());
//		System.out.println(version.getReleaseVersion());
//		System.out.println(version.getVendorString());
//		System.out.println(version.getRendererString());
//		System.out.println(version.getType());
	}
	
	@Override
	public void render() {
		//Util.glClear();
		frame.begin();
		super.render();
		frame.end();
		
		Util.glClear();
		frame.draw();
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
		asset.dispose();
		frame.dispose();
		Statics.dispose();
		QuadIndexBuffer.dispose();
	}
}