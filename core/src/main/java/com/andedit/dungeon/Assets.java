package com.andedit.dungeon;

import com.andedit.dungeon.graphic.vertex.VertContext;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Assets {
	
	public static VertContext CONTEXT;
	public static ShaderProgram SHADER;
	public static Texture TILES;

	public static void load(AssetManager asset) {
		FileHandleResolver resolver = asset.getFileHandleResolver();
		asset.setLoader(Texture.class, new TextureLoader(resolver));
		asset.setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
		asset.setLoader(ShaderProgram.class, new ShaderProgramLoader(resolver));
		
		asset.load("textures/tiles.png", Texture.class);
		asset.load("shaders/default.vert", ShaderProgram.class);
	}
	
	public static void get(AssetManager asset) {
		TILES = asset.get("textures/tiles.png", Texture.class);
		SHADER = asset.get("shaders/default.vert", ShaderProgram.class);
	}
}
