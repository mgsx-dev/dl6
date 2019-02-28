package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.assets.GameAssets;

public class ComponentType {

	public final String id;
	public final int tileBaseID;
	public final ComponentBehavior behavior;
	public int fromDirs, toDirs;
	public boolean vertical;
	
	public Animation<TextureRegion> idleAnimation;
	public Animation<TextureRegion> activeAnimation;
	public Animation<TextureRegion> limitAnimation;
	public Animation<TextureRegion> deadAnimation;
	
	public int cost;
	public int absorption = 1;

	public ComponentType(String id, int tileBaseID, ComponentBehavior behavior, boolean vertical, int dirs) {
		this(id, tileBaseID, behavior, vertical, dirs, dirs);
	}
	public ComponentType(String id, int tileBaseID, ComponentBehavior behavior, boolean vertical, int fromDirs, int toDirs) {
		this.id = id;
		this.tileBaseID = tileBaseID;
		this.behavior = behavior;
		this.vertical = vertical;
		this.fromDirs = fromDirs | toDirs; // XXX
		this.toDirs = toDirs | fromDirs; // XXX
		
		this.idleAnimation = animation(tileBaseID, 0, 1);
		this.activeAnimation = animation(tileBaseID, 1, 8);
		this.limitAnimation = animation(tileBaseID, 9, 2);
		this.deadAnimation = animation(tileBaseID, 12, 1);
	}

	private static Animation<TextureRegion> animation(int tileId, int offset, int len) {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 0 ; i<len ; i++){
			int id = tileId + offset + i;
			frames.add(GameAssets.i.tile(id));
		}
		return new Animation<TextureRegion>(1f, frames);
	}
	public Drawable getButtonUp() {
		return GameAssets.i.getButtonUp(tileBaseID);
	}
	public Drawable getButtonDown() {
		return GameAssets.i.getButtonDown(tileBaseID);
	}

}
