package com.andedit.dungeon.input;

import com.badlogic.gdx.utils.viewport.FitViewport;

/** @author MGSX */
public class PixelPerfectViewport extends FitViewport {
	
	public PixelPerfectViewport(float worldWidth, float worldHeight) {
		super(worldWidth, worldHeight);
	}

	@Override
	public void update(int screenWidth, int screenHeight, boolean centerCamera) {
		
		// get the min screen/world rate from width and height
		float wRate = screenWidth / getWorldWidth();
		float hRate = screenHeight / getWorldHeight();
		float rate = Math.min(wRate, hRate);
		
		// round it down and limit to one
		int iRate = Math.max(1, (int) rate);
		
		// compute rounded viewport dimension
		int viewportWidth = (int)getWorldWidth() * iRate;
		int viewportHeight = (int)getWorldHeight() * iRate;
		
		// Center.
		setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);

		apply(centerCamera);
	}
}