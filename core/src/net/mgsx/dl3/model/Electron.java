package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.model.components.PowerGND;

public class Electron {
	private static final Vector2 v1 = new Vector2();
	
	public Sprite sprite;
	public final Vector2 position = new Vector2();
	public CardCell src, dst;
	public float time;
	public int energy = 10;

	public boolean toRemove;
	
	public Electron() {
		sprite = new Sprite(GameAssets.i.electron());
		sprite.setOriginCenter();
	}
	
	public void update(float delta){
		if(dst != null){
			time += delta * 10;
		}
		if(time > 1){
			src = dst;
			if(dst.entity instanceof PowerGND){
				toRemove = true;
			}else{
				// ComponentBehavior type = dst.component != null ? dst.component.type.behavior : null;
				if(dst.component != null){
					if(dst.component.energy + 1 <= dst.component.energyMax){
						energy--;
						dst.component.addEnergy(1);
					}
				}
			}
			if(energy <= 0) toRemove = true;
			
			dst = null;
			time = 0;
		}
		position.set(src.x, src.y);
		if(dst != null){
			position.lerp(v1.set(dst.x, dst.y), time);
		}
	}

	public void draw(Batch batch) {
		sprite.setPosition(position.x * 32, position.y * 32 - 13);
		sprite.setScale(energy / 10f);
		sprite.draw(batch);
	}
}
