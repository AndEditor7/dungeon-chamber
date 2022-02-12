package com.andedit.dungeon;

import com.andedit.dungeon.graphic.vertex.VertContext;
import com.andedit.dungeon.ui.util.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
		
		asset.load("textures/tiles.png", Texture.class, t->TILES=t);
		asset.load("shaders/default.vert", ShaderProgram.class, t->SHADER=t);
	}
	
	public static void get(AssetManager asset) {
		asset.getAll();
		
		CONTEXT = VertContext.of(SHADER, 
			new VertexAttribute(Usage.Position, 3, "pos"),
			new VertexAttribute(Usage.ColorPacked, 4, "color0"),
			new VertexAttribute(Usage.TextureCoordinates, 2, "uv0")
		);
	}
	
	public static TextureRegion getTileReg(int x, int y) {
		return new TextureRegion(TILES, x << 4, y << 4, 16, 16);
	}
}
