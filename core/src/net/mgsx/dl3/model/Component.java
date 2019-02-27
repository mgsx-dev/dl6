package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Component {

	public final ComponentType type;

	public float energy = 0;
	public float energyMax = 10;

	public float animationTime;
	public float animationSpeed = 10;
	public float timeAtLimit;
	
	public Sprite sprite;

	public boolean dead;
	
	public void addEnergy(int n) {
		energy++;
	}
	
	public Component(ComponentType type) {
		this.type = type;
	}

	public void update(float delta) {
		animationTime += animationSpeed * delta;
		type.behavior.update(this, delta);
	}

	public void draw(Batch batch) {
		if(sprite != null){
			Color c = sprite.getColor();
			c.set(Color.WHITE); //.lerp(Color.RED, energy / energyMax);
			sprite.setColor(c);
			sprite.draw(batch, 1f);
		}
	}

	public void onElectronArrive(Electron electron) {
		type.behavior.onElectronArrive(this, electron);
	}

	public void onElectronLeave(Electron electron) {
		type.behavior.onElectronLeave(this, electron);
	}

	public int collect() {
		return type.behavior.collect(this);
	}

}
