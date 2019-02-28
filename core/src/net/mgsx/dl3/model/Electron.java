package net.mgsx.dl3.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.model.components.PowerGND;

public class Electron {
	private static final Vector2 v1 = new Vector2();
	
	public Sprite sprite;
	public final Vector2 position = new Vector2();
	public CardCell src, dst;
	public float time;
	public int energy = GameRules.ELECTRON_ENERGY;
	public float scale = 1;

	public boolean toRemove;

	public boolean visible = true;

	public float speed = 8;
	
	public Electron() {
		sprite = new Sprite(GameAssets.i.electron());
		sprite.setOriginCenter();
		scale = 0;
	}
	
	public void update(float delta){
		if(dst != null && src.flow>=0){
			time += delta * speed;
		}
		if(time > 1){
			if(src.component != null && !src.component.dead){
				src.component.onElectronLeave(this);
			}
			src = dst;
			if(dst.entity instanceof PowerGND){
				((PowerGND)dst.entity).onElectronArrive(this);
				toRemove = true;
			}else{
				if(dst.component != null && !dst.component.dead){
					dst.component.onElectronArrive(this);
				}
			}
			if(energy <= 0) toRemove = true;
			
			dst = null;
			time = 0;
		}
		position.set(src.x, src.y);
		if(dst != null){
			position.lerp(v1.set(dst.x, dst.y), time);
			
			float scaleForSrc = 1;
			float scaleForDst = 1;
			if(src.component != null){
				float t = MathUtils.clamp(src.component.type.vertical ? time+.5f : time, 0, 1);
				scaleForSrc = MathUtils.lerp(0, 1, t * t);
			}else if(src.entity != null){
				float t = MathUtils.clamp(time, 0, 1);
				scaleForSrc = MathUtils.lerp(0, 1, t * t);
			}
			if(dst.component != null){
				float t = MathUtils.clamp(dst.component.type.vertical ? time+.5f : time, 0, 1);
				scaleForDst = MathUtils.lerp(1, 0, 1 - (1-t) * (1-t));
			}else if(dst.entity != null){
				float t = MathUtils.clamp(time, 0, 1);
				scaleForDst = MathUtils.lerp(1, 0, 1 - (1-t) * (1-t));
			}
			scale = Math.min(scaleForSrc, scaleForDst);
		}
	}

	public void draw(Batch batch) {
		//if(!visible) return;
		sprite.setPosition(position.x * 32, position.y * 32 - 13);
		sprite.setScale(scale * energy / 10f);
		sprite.draw(batch);
	}
}
