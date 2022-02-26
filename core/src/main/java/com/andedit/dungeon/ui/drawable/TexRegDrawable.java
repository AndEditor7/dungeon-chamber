package com.andedit.dungeon.ui.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TexRegDrawable extends TextureRegionDrawable {
	private static final Color TEMP = new Color();
	
	private final Color color;
	
	public TexRegDrawable (TextureRegion region, Color color) {
		super(region);
		this.color = color;
	}
	
	public void draw (Batch batch, float x, float y, float width, float height) {
		TEMP.set(batch.getColor());
		batch.setColor(color);
		super.draw(batch, x, y, width, height);
		batch.setColor(TEMP);
	}

	public void draw (Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX,
		float scaleY, float rotation) {
		TEMP.set(batch.getColor());
		batch.setColor(color);
		super.draw(batch, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
		batch.setColor(TEMP);
	}
}
