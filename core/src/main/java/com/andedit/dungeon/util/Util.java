package com.andedit.dungeon.util;

import java.nio.ByteBuffer;

import com.andedit.dungeon.graphic.FastBatch;
import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.BufferUtils;

public final class Util {
	public static final ByteBuffer BUFFER = BufferUtils.newByteBuffer(QuadIndexBuffer.maxVertex*FastBatch.SPRITE_SIZE*1000);

	public static float lerp (float fromValue, float toValue, float progress, float clamp) {
		final float delta = (toValue - fromValue) * progress;
		return fromValue + MathUtils.clamp(delta, -clamp, clamp);
	}

	public static boolean isGL30() {
		return Gdx.gl30 != null;
	}

	public static float getW() {
		return Gdx.graphics.getWidth();
	}

	public static float getH() {
		return Gdx.graphics.getHeight();
	}
	
	public static float modAngle(float angle) {
		float mod = angle % 360f;
		return MathUtils.clamp(mod < 0f ? mod+360f : mod, 0f, 360f);
	}
	
	public static void glClear() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
	
	/** Create a new change listener using java 8 lambda. */
	public static EventListener newListener(LambdaListener listener) {
		return new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				listener.changed(event);
			}
		};
	}
	
	/** Create a new change listener without Event. */
	public static EventListener newListener(Runnable runnable) {
		return new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				runnable.run();
			}
		};
	}
	
	@FunctionalInterface
	public static interface LambdaListener {
		void changed(Event event);
	}
}
