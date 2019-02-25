package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.mgsx.dl3.assets.GameAssets;

public class ComponentType {

	public final String id;
	public final int tileBaseID;
	public final ComponentBehavior behavior;
	public int dirs;
	public boolean vertical;

	public ComponentType(String id, int tileBaseID, ComponentBehavior behavior, boolean vertical, int dirs) {
		this.id = id;
		this.tileBaseID = tileBaseID;
		this.behavior = behavior;
		this.vertical = vertical;
		this.dirs = dirs;
	}

	public Drawable getButtonUp() {
		TiledMapTileSet tileset = GameAssets.i.card1.getTileSets().getTileSet(0);
		return new TextureRegionDrawable(tileset.getTile(tileBaseID + 1).getTextureRegion());
	}
	public Drawable getButtonDown() {
		TextureRegionDrawable drawable = (TextureRegionDrawable)getButtonUp();
		return drawable.tint(Color.GRAY);
	}

}
