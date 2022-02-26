package com.andedit.dungeon;

import com.andedit.dungeon.graphic.vertex.VertContext;
import com.andedit.dungeon.ui.drawable.TexRegDrawable;
import com.andedit.dungeon.util.AssetManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Assets {
	private static final String DEFAULT = "default";
	
	public static VertContext CONTEXT;
	public static ShaderProgram SHADER;
	public static Texture TEXS, GUI;
	public static BitmapFont FONT;
	
	public static final Skin SKIN = new Skin();

	public static void load(AssetManager asset) {
		FileHandleResolver resolver = asset.getFileHandleResolver();
		asset.setLoader(Texture.class, new TextureLoader(resolver));
		asset.setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
		asset.setLoader(ShaderProgram.class, new ShaderProgramLoader(resolver));

		asset.load("textures/mozart.fnt", BitmapFont.class, t->FONT=t);
		asset.load("textures/texs.png", Texture.class, t->TEXS=t);
		asset.load("shaders/default.vert", ShaderProgram.class, t->SHADER=t);
	}
	
	public static void get(AssetManager asset) {
		asset.getAll();
		GUI = FONT.getRegion().getTexture();
		
		CONTEXT = VertContext.of(SHADER,
			new VertexAttribute(Usage.Position, 3, "pos0"),
			new VertexAttribute(Usage.ColorPacked, 4, "color0"),
			new VertexAttribute(Usage.TextureCoordinates, 2, "uv0")
		);
		
		VertInfo.init(CONTEXT.getAttrs());
		
		getSkin();
	}
	
	private static void getSkin() {
		SKIN.add(DEFAULT, FONT);
		SKIN.add(DEFAULT, new LabelStyle(FONT, Color.WHITE));
		SKIN.add(DEFAULT, new TouchpadStyle(
			new TexRegDrawable(new TextureRegion(GUI, 0, 96, 32, 32), new Color(12/255f, 94/255f, 127/255f, 0.6f)),
			new TexRegDrawable(new TextureRegion(GUI, 32, 116, 12, 12), new Color(12/255f, 94/255f, 127/255f, 1))
		));
	}
	
	public static TextureRegion getTileReg(int x, int y) {
		return new TextureRegion(TEXS, x << 4, y << 4, 16, 16);
	}
}
