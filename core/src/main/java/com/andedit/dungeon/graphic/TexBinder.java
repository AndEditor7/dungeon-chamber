package com.andedit.dungeon.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.utils.Disposable;

/** To keep organize the active texture slots. */
public class TexBinder implements Disposable {
	private static final boolean[] BOUNDS = new boolean[32];
	
	public final int unit;
	private boolean disposed;
	
	public TexBinder() {
		for (int i = 1; i < BOUNDS.length; i++) {
			if (!BOUNDS[i]) {
				BOUNDS[i] = true;
				unit = i;
				return;
			}
		}
		throw new IllegalStateException("The texture slots is full!");
	}
	
	public TexBinder bind(GLTexture texture) {
		if (disposed) {
			throw new IllegalStateException("This binder has been disposed!");
		}
		texture.bind(unit);
		return this;
	}
	
	public static void deactive() {
		Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
	}

	@Override
	public void dispose() {
		BOUNDS[unit] = false;
		disposed = true;
	}
}
