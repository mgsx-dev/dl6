package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Component {

	public final ComponentType type;

	public float energy = 0;
	public float energyMax = 10;

	public Sprite sprite;
	
	public void addEnergy(int n) {
		energy++;
	}
	
	public Component(ComponentType type) {
		this.type = type;
	}

	public int getDirs() {
		return type.dirs;
	}

	public void update(float delta) {
		type.behavior.update(this, delta);
	}

	public void draw(Batch batch) {
		if(sprite != null){
			Color c = sprite.getColor();
			c.set(Color.WHITE).lerp(Color.RED, energy / energyMax);
			sprite.setColor(c);
			sprite.draw(batch, 1f);
		}
	}

}
