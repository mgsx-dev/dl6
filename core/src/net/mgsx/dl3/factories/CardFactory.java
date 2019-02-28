package net.mgsx.dl3.factories;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.model.Card;
import net.mgsx.dl3.model.CardCell;
import net.mgsx.dl3.model.Component;
import net.mgsx.dl3.model.Dirs;
import net.mgsx.dl3.model.components.Power;
import net.mgsx.dl3.model.components.PowerGND;
import net.mgsx.dl3.model.components.PowerSwitch;

public class CardFactory {

	public static Card fromMap(TiledMap map){
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
		
		Card card = new Card(layer.getWidth(), layer.getHeight());
		
		ObjectMap<Integer, Integer> tileToDirs = Dirs.createTileToDirs();
		
		for(int y=0 ; y<card.h ; y++){
			for(int x=0 ; x<card.w ; x++){
				Cell cell = layer.getCell(x, y);
				CardCell cardCell = card.cell(x, y);
				Component component = null;
				if(cell != null && cell.getTile() != null){
					int tid = cell.getTile().getId() - 1;
					Integer dirs = tileToDirs.get(tid);
					if(dirs != null){
						cardCell.dirs = dirs;
						cardCell.conductor = true;
					}else{
						if(tid == 4){
							cardCell.dirs = Dirs.RIGHT | Dirs.LEFT;
							cardCell.entity = card.power = new Power();
							cardCell.conductor = true;
						}else if(tid == 68){
							cardCell.dirs = Dirs.LEFT | Dirs.RIGHT;
							cardCell.entity = card.powerGnd = new PowerGND();
							cardCell.conductor = true;
						}else if(tid == 5 || tid == 37){
							cardCell.entity = card.powerSwitch = new PowerSwitch();
							card.powerSwitchCell = cardCell;
						}
					}
				}
				cardCell.originDirs = cardCell.dirs;
				card.cell(x,y).component = component;
			}
		}
		
		return card;
	}

	public static void setTile(TiledMap map, CardCell cell) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		TiledMapTileSet tileset = map.getTileSets().getTileSet(0);
		
		Integer id = null;
		if(cell.component != null){
			if(cell.component.type.vertical){
				id = 101;
			}else{
				id = 100;
			}
		}else if(cell.conductor && cell.entity == null){
			ObjectMap<Integer, Integer> dirsToTile = Dirs.createDirsToTile();
			id = dirsToTile.get(cell.dirs);
		}
		
		if(id != null){
			layer.getCell(cell.x, cell.y).setTile(tileset.getTile(id + 1));
		}
		
	}
	
	public static void setAdjTiles(Card card, TiledMap map, CardCell cell) {
		for(int dir : Dirs.ALL){
			CardCell adj = card.cell(cell, dir);
			if(adj != null){
				setTile(map, adj);
			}
		}
		
	}

	public static void setPowerSwitch(TiledMap map, CardCell cell, boolean on) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
		layer.getCell(cell.x, cell.y).setTile(GameAssets.i.getTile(on ? 37 : 5));
	}

	public static void setMeterTile(TiledMap map, CardCell cell, float rate) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
		int rateTile = MathUtils.clamp( MathUtils.round(rate * 10), 0, 10);
		layer.getCell(cell.x, cell.y+1).setTile(GameAssets.i.getTile(71 + rateTile));
	
		int rateTileFace = MathUtils.clamp( MathUtils.floor(rate * 5), 0, 4);
		layer.getCell(cell.x, cell.y+2).setTile(GameAssets.i.getTile(39 + rateTileFace));
	}

	
}
