package com.andedit.dungeon.util;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.BufferUtils;

public final class Util {
	public static final ByteBuffer BUFFER = BufferUtils.newByteBuffer(QuadIndexBuffer.maxVertex * 28);

	public static float lerp(float fromValue, float toValue, float progress, float clamp) {
		final float delta = (toValue - fromValue) * progress;
		return fromValue + MathUtils.clamp(delta, -clamp, clamp);
	}

	public static boolean isGL30() {
		return Gdx.gl30 != null;
	}
	
	public static boolean isMobile() {
		return Gdx.app.getType() != ApplicationType.Desktop;
	}
	
	public static boolean isDesktop() {
		return Gdx.app.getType() == ApplicationType.Desktop;
	}
	
	public static boolean isCatched() {
		return Gdx.input.isCursorCatched();
	}
	
	public static int getMb() {
		long java = Gdx.app.getJavaHeap();
		if (isDesktop()) {
			return (int)(java / 1024L / 1024L);
		}
		return (int)((java + Gdx.app.getNativeHeap()) / 1024L / 1024L);
	}
	
	public static void setCatch(boolean isCatched) {
		Gdx.input.setCursorCatched(isCatched);
	}

	/** Returns the width of the client area in logical pixels. */
	public static int getW() {
		return Gdx.graphics.getWidth();
	}

	/** Returns the height of the client area in logical pixels. */
	public static int getH() {
		return Gdx.graphics.getHeight();
	}

	public static float modAngle(float angle) {
		float mod = angle % 360f;
		return MathUtils.clamp(mod < 0f ? mod + 360f : mod, 0f, 360f);
	}

	public static void glClear() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
	
	public static ShaderProgram newShader(String path) {
		return new ShaderProgram(Gdx.files.internal(path + ".vert"), Gdx.files.internal(path + ".frag"));
	}
	
	public static float getShade(Color color, float scl) {
		return Color.toFloatBits(color.r * scl, color.g * scl, color.b * scl, 1);
	}

	/** Create a new change listener using java 8 lambda. */
	public static EventListener newListener(Consumer<Event> listener) {
		return new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				listener.accept(event);
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
}
