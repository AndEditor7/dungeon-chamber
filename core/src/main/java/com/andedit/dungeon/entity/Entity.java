package com.andedit.dungeon.entity;

import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.ui.util.ObjHandler;
import com.andedit.dungeon.util.TilePos;
import com.andedit.dungeon.util.math.CollisionBox;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Null;

public class Entity {
	public final CollisionBox box = new CollisionBox();
	public final Vector2 vel = new Vector2();
	public boolean onCollide;
	
	@Null
	public Level level;
	
	protected boolean isDead;
	
	private final Vector2 pos = new Vector2();
	private final TilePos tilePos = new TilePos();
	
	/** Soon will be removed */
	@Deprecated
	Entity() {
	}
	
	public Entity(ObjHandler obj) {
		setLevel(obj.level);
		setPos(obj.getPos());
	}
	
	public void setPos(Vector2 pos) {
		setPos(pos.x, pos.y);
	}
	
	public void setPos(float x, float y) {
		float s = getSize() * 0.5f;
		box.set(x-s, y-s, x+s, y+s);
	}
	
	public Vector2 getPos() {
		return box.getCenter(pos);
	}
	
	public TilePos getTilePos() {
		return tilePos.set(getPos());
	}
	
	public void update() {
		
	}
	
	public void render(Camera camera, MeshBuilder consumer) {
		
	}
	
	public void move() {
		Collision.handle(this);
	}
	
	public float getSize() {
		return 0.6f;
	}
	
	public void remove() {
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setLevel(Level level) {
		if (this.level != null) {
			if (this.level == level) return;
			this.level.removeEntity(this);
		}
		this.level = level;
	}

	public float dst(Camera camera) {
		Vector3 pos = camera.position;
		return getPos().dst2(pos.x, pos.y);
	}
}
