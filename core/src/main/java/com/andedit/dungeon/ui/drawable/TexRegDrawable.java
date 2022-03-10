package com.andedit.dungeon.ui.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TexRegDrawable extends TextureRegionDrawable {
	private static final Color TEMP = new Color();

	public final Color color;
	
	public TexRegDrawable(TextureRegion region) {
		this(region, Color.WHITE);
	}

	public TexRegDrawable(TextureRegion region, Color color) {
		super(region);
		this.color = color.cpy();
	}
	
	public TexRegDrawable(TextureRegion region, float value) {
		super(region);
		this.color = new Color(value, value, value, 1);
	}

	public void setColor(int r, int g, int b, int a) {
		color.set(r / 255f, g / 255f, b / 255f, a / 255f);
	}

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}

	public void draw(Batch batch, float x, float y, float width, float height) {
		TEMP.set(batch.getColor());
		batch.setColor(color);
		super.draw(batch, x, y, width, height);
		batch.setColor(TEMP);
	}

	public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height,
			float scaleX, float scaleY, float rotation) {
		TEMP.set(batch.getColor());
		batch.setColor(color);
		super.draw(batch, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		batch.setColor(TEMP);
	}
}
