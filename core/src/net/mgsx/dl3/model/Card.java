package net.mgsx.dl3.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

import net.mgsx.dl3.model.components.Power;
import net.mgsx.dl3.model.components.PowerGND;

public class Card {
	public final CardCell [] cells;
	public final int w, h;
	
	private ObjectSet<CardCell> visited = new ObjectSet<CardCell>();
	private Array<CardCell> heads = new Array<CardCell>();
	private Array<CardCell> nextHeads = new Array<CardCell>();
	
	
	public Card(int w, int h) {
		super();
		this.w = w;
		this.h = h;
		cells = new CardCell[w*h];
		for(int y=0 ; y<h ; y++){
			for(int x=0 ; x<w ; x++){
				CardCell cell = new CardCell(x, y);
				cells[y*w+x] = cell;
			}
		}
	}
	public CardCell cell(int x, int y) {
		if(x>=0&&x<w&&y>=0&&y<h)
			return cells[y*w+x];
		return null;
	}
	public CardCell createComponent(int x, int y, ComponentType type) {
		CardCell cell = cell(x, y);
		cell.component = new Component(type);
		// update connections
		cell.dirs = cell.component.type.toDirs;
		
		for(int dir : Dirs.ALL){
			CardCell adj = cell(cell, dir);
			if(adj != null && adj.conductor /*&& adj.entity == null*/){
				if((cell.dirs & dir) == 0){
					adj.dirs &= ~Dirs.inverse(dir);
				}else{
					//adj.dirs |= Dirs.inverse(dir);
				}
			}
		}
		
		updateFlows();
		
		return cell;
	}
	
	public CardCell removeComponent(int x, int y) {
		CardCell cell = cell(x, y);
		cell.component = null;
		cell.dirs = cell.originDirs;
		for(int dir : Dirs.ALL){
			CardCell adj = cell(cell, dir);
			if(adj != null && adj.conductor){
				if((cell.dirs & dir) != 0){
					if(adj.component == null || (adj.component.type.toDirs & Dirs.inverse(dir)) != 0){
						adj.dirs |= Dirs.inverse(dir);
					}
				}
			}
		}
		
		updateFlows();
		
		return cell;
	}
	
	public CardCell cell(CardCell cell, int dir) {
		int [] d = Dirs.VECTORS[dir];
		return cell(cell.x + d[0], cell.y + d[1]);
	}
	
	public void updateFlows() 
	{
		CardCell powerCell = null;
		CardCell gndCell = null;
		for(CardCell cell : cells){
			if(cell.entity instanceof Power){
				powerCell = cell;
			}
			else if(cell.entity instanceof PowerGND){
				gndCell = cell;
			}
			else{
				cell.flow = -1;
			}
		}
		if(powerCell == null || gndCell == null) return;
		
		visited.clear();
		heads.clear();
		nextHeads.clear();
		nextHeads.add(gndCell);
		gndCell.flow = 0;
		
		int cableRes = 1;
		
		while(nextHeads.size > 0){
			heads.addAll(nextHeads);
			nextHeads.clear();
			while(heads.size > 0){
				CardCell head = heads.pop();
				if(head.component != null){
					// head.flow += head.component.type.behavior.getResistance();
				}
				visited.add(head);
				for(int dir : Dirs.ALL){
					if((head.dirs & dir)==0) continue;
					CardCell adj = cell(head, dir);
					if(adj != null && adj.flow >= 0 && adj.flow>head.flow+cableRes){
						adj.flow = head.flow+cableRes;
					}
					if(adj != null && !visited.contains(adj)){
						nextHeads.add(adj);
						adj.flow = head.flow+cableRes;
						if(adj.component != null){
							adj.flow += adj.component.type.behavior.getResistance();
						}
					}
				}
			}
		}
		// powerCell.flow = 100000;
	}
	
}
