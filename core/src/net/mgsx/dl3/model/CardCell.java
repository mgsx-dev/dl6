package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class CardCell {
	public int x, y;
	public int flow;
	public Component component;
	public int dirs;
	public Entity entity;
	public boolean conductor;
	public int originDirs;
	
	public CardCell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public void update(float delta) {
		if(component != null){
			component.update(delta);
		}
		
	}
	public void draw(Batch batch) {
		if(component != null){
			component.draw(batch);
		}
		
	}
	
}
