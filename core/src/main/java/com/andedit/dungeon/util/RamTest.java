package com.andedit.dungeon.util;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.ByteBuffer;

public class RamTest {
	public final RandomXS128 rand = new RandomXS128();
	public final ByteBuffer memory = BufferUtils.newUnsafeByteBuffer(32768*8);

	public void update() {
		for (int i = 0; i < 5; i++) {
			memory.put(rand.nextInt(memory.capacity()), (byte)rand.nextLong());
		}
	}
}
