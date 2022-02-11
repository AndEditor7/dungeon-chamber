package com.andedit.dungeon.entity;

import static com.andedit.dungeon.util.math.CollisionBox.POOL;

import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.util.TilePos;
import com.andedit.dungeon.util.math.CollisionBox;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

interface Collision<T extends Entity> {
	static final TilePos POS = new TilePos();
	static final Array<CollisionBox> BOXES = new Array<>();
	static final CollisionBox INTERSECT = new CollisionBox();
	static final Vector2 MOVE = new Vector2();
	
	static final Collision<Entity> HANDLE = new Collision<Entity>() {};
	
	static void handle(Entity e) {
		Collision.HANDLE.move(e);
	}
	
	default Array<CollisionBox> getBox(T entity, CollisionBox intersect) {
		final int xMin = MathUtils.floor(intersect.xMin);
		final int yMin = MathUtils.floor(intersect.yMin);
		final int xMax = MathUtils.ceil(intersect.xMax);
		final int yMax = MathUtils.ceil(intersect.yMax);
		
		BOXES.size = 0;
		for (int x = xMin; x < xMax; x++)
		for (int y = yMin; y < yMax; y++) {
			blockHandle(entity, POS.set(x, y));
		}
		
		return BOXES;
	}

	default void blockHandle(T entity, TilePos pos) {
		Tile tile = entity.level.getTile(pos);
		tile.addCollisions(pos, BOXES, POOL);
		tile.onCollide(entity);
	}
	
	default void collideX(T entity, Vector2 move) {
		final CollisionBox box = entity.box;
		for (CollisionBox block : BOXES) {
			move.x = block.collideX(box, move.x);
		}
		box.xMin += move.x;
		box.xMax += move.x;
	}

	default void collideY(T entity, Vector2 move) {
		final CollisionBox box = entity.box;
		for (CollisionBox block : BOXES) {
			move.y = block.collideY(box, move.y);
		}
		box.yMin += move.y;
		box.yMax += move.y;
	}
	
	default void move(T entity) {
		MOVE.set(entity.vel);
		final CollisionBox box = entity.box;
		
		box.expand(MOVE.x, MOVE.y, INTERSECT);
		getBox(entity, INTERSECT);
		
		collideX(entity, MOVE);
		collideY(entity, MOVE);
		
		moveHandle(entity, MOVE);
		
		POOL.freeAll(BOXES);
		BOXES.size = 0;
	}

	default void moveHandle(T entity, Vector2 move) {
		final Vector2 vel = entity.vel;
		
		entity.onCollide = vel.x != move.x || vel.y != move.y;
		
		if (vel.x != move.x) {
			vel.x = 0;
		}
		
		if (vel.y != move.y) {
			vel.y = 0;
		}
	}
}
