package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.model.components.Power;

public class CardWorld {
	private Card card;
	private Array<Electron> electrons = new Array<Electron>();
	private Array<CardCell> destinations = new Array<CardCell>();

	public CardWorld(Card card) {
		super();
		this.card = card;
	}
	
	public void update(float delta) 
	{
		for(int y=0 ; y<card.h ; y++){
			for(int x=0 ; x<card.w ; x++){
				CardCell cell = card.cell(x, y);
				Entity entity = cell.entity;			
				if(entity instanceof Power){
					Power power = (Power)entity;
					if(power.enabled){
						
						power.period = .1f; // XXX
						
						power.timeout -= delta;
						if(power.timeout < 0){
							power.timeout = power.period;
							spawnElectron(cell);
						}
					}
				}
			}
		}
		
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
	}

	private void spawnElectron(CardCell cell) 
	{
		Electron e = new Electron();
		e.src = cell;
		electrons.add(e);
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
