package net.mgsx.dl3.model;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import net.mgsx.dl3.assets.GameAssets;

public class ComponentType {

	public final String id;
	public final int tileBaseID;
	public final ComponentBehavior behavior;
	public int fromDirs, toDirs;
	public boolean vertical;

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
	}

	public Drawable getButtonUp() {
		return GameAssets.i.getButtonUp(tileBaseID);
	}
	public Drawable getButtonDown() {
		return GameAssets.i.getButtonDown(tileBaseID);
	}

}
