package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.factories.CardFactory;
import net.mgsx.dl3.model.components.Power;
import net.mgsx.dl3.model.components.PowerGND;

public class CardWorld {
	
	public static final float COLLECT_FREQUENCY = 5;
	public static final float POWER_PERIOD = .25f;
	
	private Card card;
	private Array<Electron> electrons = new Array<Electron>();
	private Array<CardCell> destinations = new Array<CardCell>();
	private float collectTimeout;
	private Stage stage;
	private TiledMap map;
	
	public CardWorld(Card card, TiledMap map, Stage stage) {
		super();
		this.card = card;
		this.stage = stage;
		this.map = map;
	}
	
	public void update(float delta) 
	{
		collectTimeout -= delta;
		boolean collect = false;
		if(collectTimeout < 0){
			collectTimeout = COLLECT_FREQUENCY;
			collect = true;
		}
		int collected = 0;
		for(int y=0 ; y<card.h ; y++){
			for(int x=0 ; x<card.w ; x++){
				CardCell cell = card.cell(x, y);
				Entity entity = cell.entity;			
				if(entity instanceof Power){
					Power power = (Power)entity;
					if(power.enabled){
						
						power.period = POWER_PERIOD;
						
						power.timeout -= delta;
						if(power.timeout < 0){
							power.timeout = power.period;
							if(electrons.size < 101){
								spawnElectron(cell);
							}
						}
					}
				}else if(entity instanceof PowerGND){
					entity.update(delta);
					// check game over
					PowerGND gnd = (PowerGND) entity;
					if(gnd.energy > GameRules.POWER_LOAD_SUPPORT){
						// System.out.println("shortcut overload !!!!");
						// card.power.enabled = false;
						card.shortcut = true;
						// TODO game over ?
					}
					float rate = gnd.energy / GameRules.POWER_LOAD_SUPPORT;
					CardFactory.setMeterTile(map, cell, rate);
				}
				if(collect && cell.component != null && !cell.component.dead){
					int amount = cell.component.collect();
					if(amount > 0){
						collected += amount;
						spawnCollect(cell, amount);
					}
				}
			}
		}
		card.money += collected;
		
		for(Electron e : electrons){
			e.update(delta);
			if(e.dst == null){
				destinations.clear();
				for(int dir : Dirs.ALL){
					if((e.src.dirs & dir) != 0){
						CardCell adj = card.cell(e.src, dir);
						if(adj != null && adj.conductor){
							if(adj.flow <= e.src.flow)
								destinations.add(adj);
						}
					}
				}
				if(destinations.size > 0){
					e.dst = destinations.random();
				}
				
			}
		}
		
		for(int i=0 ; i<electrons.size ; ){
			if(electrons.get(i).toRemove){
				electrons.removeIndex(i);
			}else{
				i++;
			}
		}
		
		for(CardCell cell : card.cells){
			cell.update(delta);
		}
		
		if(card.power.enabled && card.power.electronsRemain <= 0){
			card.power.enabled = false;
		}
		if(card.power.electronsRemain <= 0 && electrons.size == 0){
			card.finished = true;
		}
	}

	private void spawnCollect(CardCell cell, int amount) {
		Label label = GameAssets.i.createLabel("+" + amount);
		label.setColor(Color.CYAN);
		label.setPosition(cell.x * 32, cell.y*32);
		stage.addActor(label);
		label.addAction(Actions.sequence(
				Actions.parallel(
						Actions.moveBy(0, 32, 1f),
						Actions.alpha(0, 1f)
						),
				Actions.removeActor()
				));
	}

	private void spawnElectron(CardCell cell) 
	{
		Electron e = new Electron();
		e.src = cell;
		electrons.add(e);
		card.power.electronsRemain--;
	}
	
	public void draw(Batch batch){
		for(Electron e : electrons){
			e.draw(batch);
		}
		for(CardCell cell : card.cells){
			cell.draw(batch);
		}
	}
}
