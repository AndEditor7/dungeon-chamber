package com.andedit.dungeon.graphic;

import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tile;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PathTrace {
	private static final Vector2 start = new Vector2();
	private static final Vector2 end = new Vector2();
	private static final Vector2 dir = new Vector2();
	
	public static float trace(Level level, float xf, float yf) {
		start.set(xf, yf);
		float r = 0, g = 0, b = 0;
		iterate : 
		for (Light lit : level.getLights()) {
			if (start.dst2(lit.x, lit.y) > lit.s * lit.s) {
				continue;
			}
			
			end.set(lit.x, lit.y);
			dir.set(end).sub(start);
			float len = dir.len();
			if (len != 0) {
				dir.x /= len;
				dir.y /= len;
			}
			
			if (Float.isNaN(dir.x) || Float.isNaN(dir.y)) {
				continue;
			}
			
			xf = start.x + (dir.x * 0.02f);
			yf = start.y + (dir.y * 0.02f);
			
			int x, y;
			x = MathUtils.floor(xf);
			y = MathUtils.floor(yf);
			
			final int xPos, yPos;
			xPos = x;
			yPos = y;

			final int
			xStep = sign(dir.x),
			yStep = sign(dir.y);
			
			float tDeltaX = xStep == 0 ? Float.MAX_VALUE : (float) xStep / dir.x;
			float tDeltaY = yStep == 0 ? Float.MAX_VALUE : (float) yStep / dir.y;
			
			float tMaxX = tDeltaX * (xStep > 0 ? 1.0f - fract(xf) : fract(xf));
			float tMaxY = tDeltaY * (yStep > 0 ? 1.0f - fract(yf) : fract(yf));
			
			while (true) {
				Tile tile = level.getTile(x, y);
				if (tile.isOpaque()) {
					continue iterate;
				}
				
				// Traversal stepping.
				if (tMaxX < tMaxY) {
					if (tMaxX > len) break;
					x += xStep;
					tMaxX += tDeltaX;
				} else {
					if (tMaxY > len) break;
					y += yStep;
					tMaxY += tDeltaY;
				}
			}
			
			float val = ((lit.s - len) / lit.s) * lit.scl;
			r += lit.r * val;
			g += lit.g * val;
			b += lit.b * val;
		}
		r *= 0.5f;
		g *= 0.5f;
		b *= 0.5f;
		return Color.toFloatBits(Math.min(r, 1f), Math.min(g, 1f), Math.min(b, 1f), 1);
	}
	
	private static int sign(float a) {
		if (a == 0.0f) {
			return 0;
		} else {
			return a > 0.0f ? 1 : -1;
		}
	}

	private static float fract(float a) {
		return a - (float) MathUtils.floor(a);
	}
}
